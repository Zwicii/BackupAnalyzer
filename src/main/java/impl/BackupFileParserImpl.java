package impl;

import analyser.*;
import analyser.MediaCategoryParser;
import interfaces.BackupFileParser;
import interfaces.JsonFileParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Parsed backupfile
 */
public class BackupFileParserImpl implements BackupFileParser {

    private JsonFileParser jsonFileParserM = MediaParser.getInstance();
    private JsonFileParser jsonFileParserMC = MediaCategoryParser.getInstance();

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
        final String directorymediastore = "/home/victoria/Temp/backup/mediastore";

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
                BackupFileParserImpl.parseMediaStore(directorymediastore);
            }
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

                Main.logger.info("\nfound sounds");

                final String directorymediastoresounds = "/home/victoria/Temp/backup/mediastore/sounds";
                File directorymediasounds = new File(directorymediastoresounds);
                File[] msoundsfList = directorymediasounds.listFiles();

                for (File msfile : msoundsfList) {

                    Main.logger.info(msfile.getName());
                    Store.storeDataMediaStore(j, msfile.getName());
                    j++;
                    k = j;

                }
            }

            if (mfile.getName().equals("snapshots")) {

                Main.logger.info("\nfound snapshots");

                final String directorymediastoresnap = "/home/victoria/Temp/backup/mediastore/snapshots/";
                File directorymediasnap = new File(directorymediastoresnap);
                File[] msnaphotfList = directorymediasnap.listFiles();

                for (File mshfile : msnaphotfList) {

                    Main.logger.info(mshfile.getName());
                    Store.storeDataMediaStore(k, mshfile.getName());
                    k++;

                }
            }
        }

    }
}
