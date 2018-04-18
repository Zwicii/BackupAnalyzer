package server;

import analyser.JsonFileSanityCheckParser;
import analyser.Main;
import analyser.Store;
import analyser.platformParser.MediaCategoryParser;
import implementaions.BackupFileParserImpl;
import implementaions.ZipFileServiceImpl;
import interfaces.BackupFileParser;
import interfaces.ZipFileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * REST Interface für Nutzer um über HTTP auf Daten zugreifen zu können
 */
@Path("/BackupAnalyser")
public class BackupAnalyserResource {

    public static String home = System.getProperty("user.home"); //home Verzeichnis
    private String sourcePathUnzip = home + "/Temp/";
    private String sourcePathZip = home + "/Temp/";
    //Anfangspfad wo man auswählen kann wo File gespeichert werden kann
    public static final String UPLOAD_FILE_SERVER = BackupAnalyserResource.home + "/Documents/Schule/Matura Projekt";

    private BackupFileParser backupFileParser = BackupFileParserImpl.getInstance();
    private ZipFileService zipFileService = ZipFileServiceImpl.getInstance();

    public BackupAnalyserResource() throws IOException {
    }

    /**
     * Test-Methode um funktionierende Kommunikation aufbauen zu können
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String test(@QueryParam("name") String name) { //?name=Victoria
        System.out.println(name);
        return "{}";
    }

    @GET
    @Path("/DeviceDescription")
    @Produces(MediaType.APPLICATION_JSON)
    public String DeviceDescription() {
        JSONObject jsonDeviceDescription = new JSONObject(JsonFileSanityCheckParser.linkedHashMapDeviceDescription);
        Main.logger.info("DeviceDescription");
        return jsonDeviceDescription.toString();
        //return "{\"asdf\": true}";
    }

    @GET
    @Path("/Space")
    @Produces(MediaType.APPLICATION_JSON)
    public String Storage() {
        JSONObject jsonSpace = new JSONObject(MediaCategoryParser.hashMapStorage);
        Main.logger.info("Space");
        return jsonSpace.toString();
    }

    @GET
    @Path("/AllEntities")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTestInfo() {
        JSONObject jsonAllEntities = new JSONObject(BackupFileParserImpl.hashMapAllEntities);
        Main.logger.info("AllEntities");
        return jsonAllEntities.toString();
        //return "{\"asdf\": true}";
    }

    @GET //GET-Request: Daten im Header gesendet
    @Path("/OriginalData") //Pfad : localhost:Port/api/test/OriginalData
    @Produces(MediaType.APPLICATION_JSON) //Gibt an was returned werden soll
    public String OriginalData() {
        JSONObject jsonOriginalData = new JSONObject(Store.hashMapOriginalData);
        Main.logger.info("Original Data");
        return jsonOriginalData.toString();
    }

    @GET
    @Path("/CheckResults")
    @Produces(MediaType.APPLICATION_JSON)
    public String CheckResults() {
        JSONObject jsonCheckResults = new JSONObject(Store.hashMapCheckResults);
        Main.logger.info("Check Results");
        return jsonCheckResults.toString();
    }

    @GET
    @Path("/Errors")
    @Produces(MediaType.APPLICATION_JSON)
    public String Errors() {
        JSONObject jsonErrors = new JSONObject(BackupFileParserImpl.hashMapErrors);
        Main.logger.info("Errors");
        return jsonErrors.toString();
    }


    @GET
    @Path("/repair")
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadFile() {

        // set file (and path) to be download
        File file = new File(BackupAnalyserResource.home + "/Documents/Schule/Matura Projekt/backup.zip");

        Response.ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"repairedBackup.zip\"");
        return responseBuilder.build();
    }



    @POST //POST-Request: Daten im BODY nach dem Header gesendet
    @Path("/upload")
    @Consumes("multipart/form-data") //MIME-Type: html-enctype: allows entire files to be included in the data
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(MultipartFormDataInput input) {

        String fileNameUnzip;
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("datei"); //Get inputParts from name (datei = name von Input (Formular) von Index.html)

        JSONObject ok = new JSONObject();
        ok.put("Status", "upload successful");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders(); //header Daten
                fileNameUnzip = getFileName(header);


                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);//body Daten ?

                byte[] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path
                fileNameUnzip = sourcePathUnzip + fileNameUnzip;
                String fileNameZip = home + "/Temp/backup/backup.zip";

                writeFile(bytes, fileNameUnzip);//creates backupaudio.bak file to unzip it later

                Main.logger.debug("Unzip");
                //new directory IN
                File dirIN = new File(sourcePathUnzip + "IN");
                dirIN.mkdir();
                sourcePathUnzip = dirIN.getPath() + "/";
                zipFileService.unzip(fileNameUnzip, sourcePathUnzip);

                Main.logger.debug("Parse");
                backupFileParser.parseBackupFile(sourcePathUnzip);

                //Main.logger.debug("Zip");
                //File dirOUT = new File(sourcePathZip + "OUT");
                //dirOUT.mkdir();
                //sourcePathZip = dirOUT.getPath() + "/";
                //zipFileService.zip(fileNameZip, sourcePathZip);

                //directories IN and OUT löschen
                FileUtils.deleteDirectory(dirIN);
                //FileUtils.deleteDirectory(dirOUT);

            } catch (IOException e) {
                Main.logger.error("IOException: ", e);
            }
        }

        return Response.status(200)
                .entity(ok.toString()) //new JSONObject(BackupFileParserImpl.hashMapAllEntities).toString()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "*")
                .header("Access-Control-Allow-Headers", "*")
                .allow("OPTIONS").build();
    }

    //Holt Filename aus Map mit allen headers
    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content); //content of backupaudio.bak
        fop.flush();
        fop.close();
    }

}