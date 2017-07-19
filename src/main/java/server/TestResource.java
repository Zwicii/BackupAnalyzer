package server;

import analyser.Unzip;
import impl.BackupFileParserImpl;
import interfaces.BackupFileParser;
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
// TODO [STC]: Diesse Klasse umbenennen (z.B.: BackupAnalyzerResource (oder etwas anderes sprechendes)
public class TestResource {

    // TODO [STC]: Versuchen, anstatt "/home/victoria" das Home-Verzeichnis des aktuellen Users dynamisch herauszubekommen
    private final String destinationPath = "/home/victoria/Temp/";

    private BackupFileParser backupFileParser = BackupFileParserImpl.getInstance();

    public TestResource() throws IOException {
    }

    // TODO [STC]: Auskommentierten (alten) Code entfernen
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

    // TODO [STC]: Neue Methoden (REST Endpoints) anlegen zum Auslesen der hashMapOriginalData und hashMapCheckResults aus dem Store (im JSON Format, für Fabian später)

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(MultipartFormDataInput input) {
        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        // TODO [STC]: Dazu kommentieren, wo der Name "datei" herkommt bzw. womit er zusammenhängt
        List<InputPart> inputParts = uploadForm.get("datei");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path
                fileName = destinationPath + fileName;

                writeFile(bytes, fileName);

                // TODO [STC]: Ersetzen durch (z.B.) Main.logger.debug(...)
                System.out.println("Unzip");
                Unzip.unzip(fileName, destinationPath);

                // TODO [STC]: Ersetzen durch (z.B.) Main.logger.debug(...)
                System.out.println("Parse");
                backupFileParser.parseBackupFile(destinationPath);


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

