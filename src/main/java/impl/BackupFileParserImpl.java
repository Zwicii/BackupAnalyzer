package impl;

import analyser.*;
import analyser.MediaCategoryParser;
import interfaces.BackupFileParser;
import interfaces.JsonFileParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Parse backupfile
 */
public class BackupFileParserImpl implements BackupFileParser {

    private JsonFileParser jsonFileParserM = MediaParser.getInstance();
    private JsonFileParser jsonFileParserMC = MediaCategoryParser.getInstance();
    private JsonFileParser jsonFileParserMS = MediaStoreParser.getInstance();

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

        String directoryMediaStore = "/home/victoria/Temp/IN/mediastore/";
        String filePathMC = "/home/victoria/Temp/IN/com.commend.platform.mediastore.MediaCategory.json/";
        String filePathM = "/home/victoria/Temp/IN/com.commend.platform.mediastore.Media.json/";

        File directory = new File(filePath);
        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList) { //for each = mit for schleife array durchlaufen und dann immer File file = fList[i]

            if (file.getName().endsWith(".MediaCategory.json")) {
                Main.logger.info("\nfound MediaCategory");
                jsonFileParserMC.parse(filePathMC);
            }

            if (file.getName().equals("mediastore")) {
                Main.logger.info("\nfound mediastore");
                jsonFileParserMS.parse(directoryMediaStore);
            }
        }

        for (File file : fList) { //for each = mit for schleife array durchlaufen und dann immer File file = fList[i]

            if (file.getName().endsWith(".Media.json")) {
                Main.logger.info("\nfound Media");
                jsonFileParserM.parse(filePathM);
            }
        }
    }
}
