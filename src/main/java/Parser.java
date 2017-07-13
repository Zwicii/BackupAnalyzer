import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by victoria on 11.07.17.
 */
public class Parser {

    public static void parseBackupFile(String directorybackup) {

        String jsonDataMedia = null;
        String jsonDataMediaCategory = null;
        final String directorymediastore = "/home/victoria/Temp/backup/mediastore";

        try {
            jsonDataMedia = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.Media.json/")));
            jsonDataMediaCategory = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.MediaCategory.json/")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        File directory = new File(directorybackup);
        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList) { //for each = mit for schleife array durchlaufen und dann immer File file = fList[i]

//            Test1.logger.info(file.getName());

            if (file.getName().endsWith(".Media.json")) {

                Test1.logger.info("\nfound Media");
                Parser.parseMedia(jsonDataMedia);
            }

            if (file.getName().endsWith(".MediaCategory.json")) {
                Test1.logger.info("\nfound MediaCategory");
                Parser.parsemediaCategory(jsonDataMediaCategory);
            }

            if (file.getName().equals("mediastore")) {
                Test1.logger.info("\nfound mediastore");
                Parser.parseMediaStore(directorymediastore);
            }
        }
    }

    public static void parseMedia(String jsonData) {
        JSONObject obj = new JSONObject(jsonData);
        int j = 0;
        int k = 0;
//        String deviceDescription = obj.getJSONObject("deviceDescription").getString("applicationName");
//        Test1.logger.info("deviceDescription: " + deviceDescription);


        JSONArray arr = obj.getJSONArray("entities");
        for (int i = 0; i < arr.length(); i++) {

            JSONObject objmediaCategory = arr.getJSONObject(i).getJSONObject("mediaCategory");
            String idmediaCategory = objmediaCategory.getString("id");
            Test1.logger.info(i + ": mediaCategory-id: " + idmediaCategory);

            String namemediaCategory = objmediaCategory.getString("name");
            Test1.logger.info(i + ": mediaCategory-name: " + namemediaCategory);

            String id = arr.getJSONObject(i).getString("id");
            Test1.logger.info(i + ": entities-id: " + id);

            String displayName = arr.getJSONObject(i).getString("displayName");
            Test1.logger.info(i + ": entities-displayName: " + displayName);

            //Namen holen und dann mit Store in Name hashmap speichern als value und dann mit id als key value wiederfinden


            Store.storeDataMediaC(j, idmediaCategory);
            j++;
            Store.storeDataMediaC(j, namemediaCategory);
            j++;
            Store.storeDataMediaS(k, id);
            k++;

        }
    }

    public static void parsemediaCategory(String jsonData) {

        JSONObject obj = new JSONObject(jsonData);
        int j = 0;

        JSONArray arr = obj.getJSONArray("entities");
        for (int i = 0; i < arr.length(); i++) {

            String id = arr.getJSONObject(i).getString("id");
            Test1.logger.info(i + ": entities-id: " + id);
            String name = arr.getJSONObject(i).getString("name");
            Test1.logger.info(i + ": entities-name: " + name);

            Store.storeDataMediaCategory(j, id);
            j++;
            Store.storeDataMediaCategory(j, name);
            j++;
        }
    }

    public static void parseMediaStore(String directorymediastore) {

        int j = 0;
        int k = 0;
        File directorymedia = new File(directorymediastore);
        File[] mfList = directorymedia.listFiles();

        for (File mfile : mfList) {

//                    System.out.println(mfile.getName());


            if (mfile.getName().equals("sounds")) {

                Test1.logger.info("\nfound sounds");

                final String directorymediastoresounds = "/home/victoria/Temp/backup/mediastore/sounds";
                File directorymediasounds = new File(directorymediastoresounds);
                File[] msoundsfList = directorymediasounds.listFiles();

                for (File msfile : msoundsfList) {

                    Test1.logger.info(msfile.getName());
                    Store.storeDataMediaStore(j, msfile.getName());
                    j++;
                    k = j;

                }
            }

            if (mfile.getName().equals("snapshots")) {

                Test1.logger.info("\nfound snapshots");

                final String directorymediastoresnap = "/home/victoria/Temp/backup/mediastore/snapshots/";
                File directorymediasnap = new File(directorymediastoresnap);
                File[] msnaphotfList = directorymediasnap.listFiles();

                for (File mshfile : msnaphotfList) {

                    Test1.logger.info(mshfile.getName());
                    Store.storeDataMediaStore(k, mshfile.getName());
                    k++;

                }
            }
        }
    }
}
