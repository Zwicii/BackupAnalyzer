package impl;

import analyser.*;
import analyser.platformParser.PermissionParser;
import analyser.platformParser.UserParser;
import interfaces.BackupFileParser;
import interfaces.JsonFileParser;
import server.BackupAnalyserResource;

import java.io.File;

/**
 * Parse backupfile
 */
public class BackupFileParserImpl implements BackupFileParser {

    private JsonFileParser jsonFileParserMedia = MediaParser.getInstance();
    private JsonFileParser jsonFileParserMediaCategory = MediaCategoryParser.getInstance();
    private JsonFileParser jsonFileParserMediaStore = MediaStoreParser.getInstance();
    private JsonFileParser jsonFileParserSecurityPermission = PermissionParser.getInstance();
    private JsonFileParser jsonFileParserUser = UserParser.getInstance();

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

        int i = 0;

        //Eventuell FilePath gleich bei den Parsern angeben
        String filePathMediaStore = BackupAnalyserResource.home + "/Temp/IN/mediastore/";

        String filePathMediaCategory = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.mediastore.MediaCategory.json/";
        String filePathMedia = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.mediastore.Media.json/";
        String filePathSecurityPermission = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.security.Permission.json/";
        String filePathUser = BackupAnalyserResource.home + "/Temp/IN/com.commend.platform.security.User.json";

        File directory = new File(filePath);
        File[] fList = directory.listFiles(); //get all the files from a directory

        for (File file : fList) { //for each = mit for schleife array durchlaufen und dann immer File file = fList[i]
            //Zuerst Daten aus MediaCategory und mediastore holen, sonst NullPointerException weil MediaParser sonst Daten zum vergleichen fehlen

            Store.storeAllEntities( i , file.getName());
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
                jsonFileParserUser.parse(filePathSecurityPermission);
            }
        }

        for (File file : fList) {
            if (file.getName().endsWith(".Media.json")) {
                Main.logger.info("\nfound Media");
                jsonFileParserMedia.parse(filePathMedia);
            }

        }
    }
}