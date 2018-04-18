package analyser;

import implementaions.BackupFileParserImpl;
import implementaions.ZipFileServiceImpl;
import interfaces.BackupFileParser;
import interfaces.ZipFileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.BackupAnalyserResource;
import server.ServerRunner;

import java.io.File;
import java.io.IOException;

public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        //staticalAnalyser();

        //Start server
        ServerRunner serverRunner = new ServerRunner();
        serverRunner.start(8090);



    }

    private static void staticalAnalyser() {

        String fileNameUnzip = "backupmedia.zip";

        String sourcePathUnzip = BackupAnalyserResource.home + "/Temp/";
        String sourcePathZip = BackupAnalyserResource.home + "/Temp/OUT/";
        ZipFileService zipFileService = ZipFileServiceImpl.getInstance();
        BackupFileParser backupFileParser = BackupFileParserImpl.getInstance();

        fileNameUnzip = sourcePathUnzip + fileNameUnzip;
        String fileNameZip = BackupAnalyserResource.home + "/Temp/backup/backup.zip";

        Main.logger.debug("Unzip");
        //new directory IN
        File newDir = new File(sourcePathUnzip + "IN");
        newDir.mkdir();
        sourcePathUnzip = newDir.getPath() + "/";
        zipFileService.unzip(fileNameUnzip, sourcePathUnzip);

        Main.logger.debug("Parse");
        backupFileParser.parseBackupFile(sourcePathUnzip);

        Main.logger.debug("Zip");
        zipFileService.zip(fileNameZip, sourcePathZip);
    }

}