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

//        analyser.Unzip.unzip(fileZip, destination);
//        readZipFile1(winPath);
//        extractZipFileUsingUnixFileSystem();

//        String jsonData = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.Media.json/")));
//        jsonData = Inhalt von Mediafile
//        impl.BackupFileParserImpl.parseMedia(jsonData);

//        final String directory = "/home/victoria/Temp/backup";
//        BackupFileParserImpl.parseBackupFile(directory);
//
//        logger.info("\nanalyser.Compare MediaCategory");
//        Compare.compare(Store.hashMapMediaC, Store.hashMapmediaCategory, Compare.hashMapMC);
//
//        logger.info("\nanalyser.Compare Mediastore");
//        Compare.compare(Store.hashMapMediaS, Store.hashMapMediaStore, Compare.hashMapMF);
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





