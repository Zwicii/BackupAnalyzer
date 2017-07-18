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

        String jsonDataMedia = null;
        String jsonDataMediaCategory = null;
        final String directorymediastore = "/home/victoria/Temp/mediastore/";

        try {
            jsonDataMedia = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.Media.json/")));
            jsonDataMediaCategory = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.MediaCategory.json/")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        File directory = new File(filePath);
        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList) { //for each = mit for schleife array durchlaufen und dann immer File file = fList[i]

//            Test1.logger.info(file.getName());

            if (file.getName().endsWith(".Media.json")) {

                Main.logger.info("\nfound Media");

                jsonFileParserM.parse(jsonDataMedia);
//                BackupFileParserImpl.parseMedia(jsonDataMedia);
            }

            if (file.getName().endsWith(".MediaCategory.json")) {
                Main.logger.info("\nfound MediaCategory");
                jsonFileParserMC.parse(jsonDataMediaCategory);
//                BackupFileParserImpl.parsemediaCategory(jsonDataMediaCategory);
            }

            if (file.getName().equals("mediastore")) {
                Main.logger.info("\nfound mediastore");
                jsonFileParserMS.parse(directorymediastore);
            }
        }
    }


}
