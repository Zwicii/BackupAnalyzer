package impl;

import analyser.JsonFileSanityCheckParser;
import analyser.Main;
import analyser.MediaStoreParser;
import analyser.Store;
import analyser.issParser.ActionSetParser;
import analyser.issParser.ActivityCardParser;
import analyser.platformParser.*;
import interfaces.BackupFileParser;
import interfaces.JsonFileParser;
import server.BackupAnalyserResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Parse backupfile
 */
public class BackupFileParserImpl implements BackupFileParser {

    private JsonFileParser jsonFileParserSanity = JsonFileSanityCheckParser.getInstance();

    private JsonFileParser jsonFileParserMedia = MediaParser.getInstance();
    private JsonFileParser jsonFileParserMediaCategory = MediaCategoryParser.getInstance();
    private JsonFileParser jsonFileParserMigrationScript = MigrationScriptParser.getInstance();
    private JsonFileParser jsonFIleParserActionSet = ActionSetParser.getInstance();
    private JsonFileParser jsonFileParserActivityCard = ActivityCardParser.getInstance();
    private JsonFileParser jsonFileParserSecurityPermission = PermissionParser.getInstance();
    private JsonFileParser jsonFileParserUser = UserParser.getInstance();

    private JsonFileParser jsonFileParserMediaStore = MediaStoreParser.getInstance();

    public HashMap<String, Boolean> hashMapBackupFileContents = new HashMap<>();
    public static HashMap<Integer, String> hashMapAllEntities = new HashMap<>();
    public static HashMap<String, String> hashMapErrors = new HashMap<>();


    //Singleton Pattern
    private static BackupFileParserImpl instance = null;

    private BackupFileParserImpl() {
    }

    public static BackupFileParserImpl getInstance() {
        if (instance == null) {
            instance = new BackupFileParserImpl();
        }
        return instance;
    }

    public void parseBackupFile(String filePath) {

        boolean found;
        int i = 0;
        File directory = new File(filePath);
        File[] fList = directory.listFiles(); //get all the files from a directory

        for (File file : fList) {
            //JSONFileSanityParser
            if (file.getName().endsWith(".json")) {
                jsonFileParserSanity.parse(getJSONFilePath(file));
            }

            //MediaStoreParser sonst ist bei MediaStoreParser.arrayListMediaStore nichts drinnen wenn bei MediaParser abgeprüft wird
            if (file.getName().equals("mediastore")) {
                jsonFileParserMediaStore.parse(getJSONFilePath(file));
            }

            //MediaCategoryParser sonst bei hashmapsMediaCategory kein Inhalt
            if (file.getName().equals("com.commend.platform.mediastore.MediaCategory.json")) {
                jsonFileParserMediaCategory.parse(getJSONFilePath(file));
            }
        }


        for (File file : fList) { //for each = mit for schleife array durchlaufen und dann immer File file = fList[i]

            //Zuerst Daten aus MediaCategory und mediastore holen, sonst NullPointerException weil MediaParser sonst Daten zum vergleichen fehlen
            hashMapAllEntities.put(i, file.getName());
            i++;


            //JsonFileParser
            if (file.getName().equals("com.commend.platform.mediastore.Media.json")) {
                jsonFileParserMedia.parse(getJSONFilePath(file));
            }


            if (file.getName().equals("com.commend.platform.db.MigrationScript.json")) {
                jsonFileParserMigrationScript.parse(getJSONFilePath(file));
            }

            if (file.getName().equals("com.commend.iss.activity.ActionSet.json")) {
                jsonFIleParserActionSet.parse(getJSONFilePath(file));
            }

            if (file.getName().equals("com.commend.iss.activity.ActivityCard.json")) {
                jsonFileParserActivityCard.parse(getJSONFilePath(file));
            }


        }

        //Schaut ob Alle Json-Files, md5.txt und backup.zip in backupfile enthalten sind
        {
            for (String name : getFile("BackupFileContents")) {
                found = false;

                for (File file : fList) { //TODO[OBV]: fList sind nur backup und backupaudio
                    if (name != null) { //Weil sonst NPE und file.getName = null in Checkresult hashmap

                        if (name.equals(file.getName())) {
                            found = true;
                            hashMapBackupFileContents.put(file.getName(), found);
                            break;
                        }
                    }
                }

                if (name != null) {
                    if (!found) {
                        hashMapBackupFileContents.put(name, found);
                        hashMapErrors.put(name, "File does not exists");
                    }
                }
            }
        }

        for (File file : fList) {
            if (file.getName().endsWith(".Media.json")) {
                Main.logger.info("\nfound Media");
                jsonFileParserMedia.parse(getJSONFilePath(file));
            }
        }

        //Speichert Ergebnisse von hashMapCOmpareJsonSanityCheck in hashmapCheckResults
        if(JsonFileSanityCheckParser.hashMapCompareJsonSanityCheck.containsValue("false")){
            Store.storeCheckResults("JsonFileSanityCheck", false);
        }
        else {
            Store.storeCheckResults("JsonFileSanityCheck", true);
        }
        //Store.storeCheckResults("JsonFileSanityCheck", JsonFileSanityCheckParser.hashMapCompareJsonSanityCheck);

        //Speichert Ergebnisse von hashMapBackupfileContent in hashMapCheckResults
        if(hashMapBackupFileContents.containsValue("false")){
            Store.storeCheckResults("BackupFileContents", false);
        }
        else {
            Store.storeCheckResults("BackupFileContents", true);
        }
        //Store.storeCheckResults("BackupFileContents", hashMapBackupFileContents); //directory.getName() = backupaudio.zip
        Store.storeCheckResults("DeviceDescription", checkDeviceDescription());

    }

    //Schaut ob alle Sub-Properties von deviceDescription gleich sind
    public Boolean checkDeviceDescription() {

        HashMap<Object, Object> value = null;

        for (Map.Entry e : JsonFileSanityCheckParser.hashMapCompareDeviceDescription.entrySet()) {
            if (value == null) {
                value = (HashMap<Object, Object>) e.getValue();
                System.out.println("VALUE: " + value);
                System.out.println("Entity: " + e.getValue());
            }
            if (!value.equals(e.getValue())) {
                hashMapErrors.put(e.getKey().toString(), "Wrong deviceDescription");
                return false;
            }
        }
        return true;
    }


    public String[] getFile(String fileName) {

        String[] names = new String[60];

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            for (int i = 0; scanner.hasNextLine(); i++) {
                names[i] = scanner.nextLine();
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    public String getJSONFilePath(File file) {

        return BackupAnalyserResource.home + "/Temp/IN/" + file.getName() + "/";
    }
}