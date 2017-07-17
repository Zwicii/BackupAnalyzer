package BackupAnalyser;

import Server.ServerRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Objects;
//import jdk.internal.org.xml.sax.SAXException;


/**
 * Created by victoria on 07.07.17.
 */
public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void getLoggerEntries() throws IOException {

        //        logger.info("Gradle Test");

        String fileZip = "/home/victoria/Downloads/backupaudio.bak";
        String destination = "/home/victoria/Temp/";
        Path winPath = Paths.get("/home/victoria/Downloads/presets.zip/");

//        BackupAnalyser.Unzip.unzip(fileZip, destination);
//        readZipFile1(winPath);
//        extractZipFileUsingUnixFileSystem();

//        String jsonData = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.Media.json/")));
//        jsonData = Inhalt von Mediafile
//        BackupAnalyser.Parser.parseMedia(jsonData);

        final String directory = "/home/victoria/Temp/backup";
        Parser.parseBackupFile(directory);

        logger.info("\nBackupAnalyser.Compare MediaCategory");
        Compare.compare(Store.hashMapMediaC, Store.hashMapmediaCategory, Compare.hashMapMC);

        logger.info("\nBackupAnalyser.Compare Mediastore");
        Compare.compare(Store.hashMapMediaS, Store.hashMapMediaStore, Compare.hashMapMF);

        logger.info("\nCheck");
        Compare.check();
    }

    public static void main(String[] args) throws IOException {

        //Start Server
        ServerRunner serverRunner = new ServerRunner();
        serverRunner.start(8090);

    }

}






