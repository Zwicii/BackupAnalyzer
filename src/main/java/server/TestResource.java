package server;

import impl.BackupFileParserImpl;
import impl.ZipFileServiceImpl;
import interfaces.BackupFileParser;
import interfaces.ZipFileService;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

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
@Path("/test")
public class TestResource {

    private String destinationPathUnzip = "/home/victoria/Temp/";
    private final String destinationPathZip = "/home/victoria/Temp/";


    private BackupFileParser backupFileParser = BackupFileParserImpl.getInstance();
    private ZipFileService zipFileService = ZipFileServiceImpl.getInstance();

    public TestResource() throws IOException {
    }

//    UserService userService = UserServiceImpl.getInstance();
//    ObjectMapper mapper = new ObjectMapper();

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
    @Path("/asdf")
    @Produces(MediaType.APPLICATION_JSON)
    public String asdf() {
        System.out.println("asdf");
        return "{\"asdf\": true}";
    }

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(MultipartFormDataInput input) {
        String fileNameUnzip = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("datei");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileNameUnzip = getFileName(header);

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path
                fileNameUnzip = destinationPathUnzip + fileNameUnzip;
                String fileNameZip = destinationPathZip + "backup.zip";


                writeFile(bytes, fileNameUnzip);

                System.out.println("Unzip");
                File newDir = new File(destinationPathUnzip + "IN"); //new directory IN
                newDir.mkdir();
                destinationPathUnzip = newDir.getPath() + "/";
                zipFileService.unzip(fileNameUnzip, destinationPathUnzip);

                System.out.println("Parse");
                backupFileParser.parseBackupFile(destinationPathUnzip);

                zipFileService.zip(fileNameZip, destinationPathZip);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return Response.status(200)
                .entity("{}").build();

    }

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

        fop.write(content);
        fop.flush();
        fop.close();

    }
}

