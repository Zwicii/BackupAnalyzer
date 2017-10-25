package impl;

import analyser.*;
import analyser.platformParser.PermissionParser;
import analyser.platformParser.UserParser;
import interfaces.BackupFileParser;
import interfaces.JsonFileParser;
import server.BackupAnalyserResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Parse backupfile
 */
public class BackupFileParserImpl implements BackupFileParser {

    private JsonFileParser jsonFileParserMedia = MediaParser.getInstance();
    private JsonFileParser jsonFileParserMediaCategory = MediaCategoryParser.getInstance();
    private JsonFileParser jsonFileParserMediaStore = MediaStoreParser.getInstance();
    private JsonFileParser jsonFileParserSecurityPermission = PermissionParser.getInstance();
    private JsonFileParser jsonFileParserUser = UserParser.getInstance();

    public HashMap<String, Boolean> hashMapBackupFileContents = new HashMap<>();
    public HashMap<Integer, String> hashMapAllEntities = new HashMap<>();

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

        //Eventuell FilePath gleich bei den Parsern angeben
        String filePathMediaStore = BackupAnalyserResource.home + "/Temp/IN/mediastore/";

        String filePathMediaCategory = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.mediastore.MediaCategory.json";
        String filePathMedia = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.mediastore.Media.json";
        String filePathSecurityPermission = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.security.Permission.json";
        String filePathUser = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.security.User.json";

        //Variables
        boolean found;
        int i = 0;
        File directory = new File(filePath);
        File[] fList = directory.listFiles(); //get all the files from a directory

        for (File file : fList) { //for each = mit for schleife array durchlaufen und dann immer File file = fList[i]
            //Zuerst Daten aus MediaCategory und mediastore holen, sonst NullPointerException weil MediaParser sonst Daten zum vergleichen fehlen

            hashMapAllEntities.put(i, file.getName());
            i++;

            if (file.getName().endsWith(".MediaCategory.json")) {
                Main.logger.info("\nfound MediaCategory");
                jsonFileParserMediaCategory.parse(filePathMediaCategory);
            }

            if (file.getName().equals("mediastore")) {
                Main.logger.info("\nfound mediastore");
                jsonFileParserMediaStore.parse(filePathMediaStore);
            }

            if (file.getName().endsWith("Permission.json")) {
                Main.logger.info("\nfound Permission");
                jsonFileParserSecurityPermission.parse(filePathSecurityPermission);
            }

            if (file.getName().endsWith("User.json")) {
                Main.logger.info("\nfound User");
                jsonFileParserUser.parse(filePathUser);
            }
        }

        //Schaut ob Alle Json-Files, md5.txt und backup.zip in backupfile enthalten sind
        for(String name : getFile("BackupFileContents")){
            found = false;

            for (File file : fList) {

                if(name != null){ //Weil sonst NPE und file.getName = null in Checkresult hashmap
                    if(name.equals(file.getName())){
                        found = true;
                        hashMapBackupFileContents.put(file.getName(), found);
                        break;
                    }
                }
            }

            if(name != null){
                if (!found){
                    hashMapBackupFileContents.put(name, found);
                }
            }
        }

        Store.storeCheckResults(directory.getName(), hashMapBackupFileContents); //directory.getName() = backupaudio.zip

        for (File file : fList) {
            if (file.getName().endsWith(".Media.json")) {
                Main.logger.info("\nfound Media");
                jsonFileParserMedia.parse(filePathMedia);
            }
        }

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
}