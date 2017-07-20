package analyser;

import impl.BackupFileParserImpl;
import server.ServerRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Objects;
//import jdk.internal.org.xml.sax.SAXException;


public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void getLoggerEntries() throws IOException {

        //        logger.info("Gradle Test");

        String fileZip = "/home/victoria/Downloads/backupaudio.bak";
        String destination = "/home/victoria/Temp/";
        Path winPath = Paths.get("/home/victoria/Downloads/presets.zip/");

        //        for (HashMap.Entry<String, HashMap> entry : hashMapCheck.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            Main.logger.info("\nKey: " + key);
//
//
//            if (key.equals("com.commend.platform.mediastore.MediaCategory")) {
//                for (HashMap.Entry<String, Boolean> entryMC : hashMapMC.entrySet()) {
//
//                    String keyMC = entryMC.getKey();
//                    Boolean valueMC = entryMC.getValue();
//                    Main.logger.info(keyMC + ": " + valueMC);
//                }
//            }
//
//            if (key.equals("com.commend.platform.mediastore.Media")) {
//                for (HashMap.Entry<String, Boolean> entryMF : hashMapMF.entrySet()) {
//
//                    String keyMF = entryMF.getKey();
//                    Boolean valueMF = entryMF.getValue();
//                    Main.logger.info(keyMF + ": " + valueMF);
//                }
//            }
//
//        }

//        impl.ZipFileServiceImpl.unzip(fileZip, destination);
//        readZipFile1(winPath);
//        extractZipFileUsingUnixFileSystem();

//        String jsonData = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.Media.json/")));
//        jsonData = Inhalt von Mediafile
//        impl.BackupFileParserImpl.parseMedia(jsonData);

//        final String directory = "/home/victoria/Temp/backup";
//        BackupFileParserImpl.parseBackupFile(directory);
//
//        logger.info("\nanalyser.Compare MediaCategory");
//        Compare.compareMedia(Store.hashMapMediaC, Store.hashMapMediaCategory, Compare.hashMapMC);
//
//        logger.info("\nanalyser.Compare Mediastore");
//        Compare.compareMedia(Store.hashMapMediaS, Store.hashMapMediaStore, Compare.hashMapMF);
//
//        logger.info("\nCheck");
//        Compare.check();
    }

    public static void main(String[] args) throws IOException {

        //Start server
        ServerRunner serverRunner = new ServerRunner();
        serverRunner.start(8090);


    }

}






