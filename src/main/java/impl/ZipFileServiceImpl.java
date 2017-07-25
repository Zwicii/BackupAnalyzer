package impl;

import analyser.Main;
import analyser.Store;
import interfaces.ZipFileService;
import org.codehaus.jackson.map.ObjectMapper;
import server.BackupAnalyserResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Unzip: entpackt Zipfile
 * Zip: Speichert Daten aus hashMapOriginalDaten wieder in JSON Files und kopiert danach alle noch fehlenden JSON Files
 * berechnet md5 Checksum und erstellt Zip File mit gleicher Struktur wie jedes das entpackt wurde
 */

public class ZipFileServiceImpl implements ZipFileService {

    private static ZipFileServiceImpl instance = null;

    private ZipFileServiceImpl() {
    }

    public static ZipFileServiceImpl getInstance() {
        if (instance == null) {
            instance = new ZipFileServiceImpl();
        }
        return instance;
    }

    @Override
    public void unzip(String fileZip, String destPath) {

        try {

            byte[] buffer = new byte[1024];

            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(destPath + fileName);
                newFile.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len); //buffer, Start, Stop
                }
                fos.close();

                if (zipEntry.getName().endsWith(".zip")) {

                    String backupFile = destPath + zipEntry.getName();
                    unzip(backupFile, destPath);
                }

                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();

        } catch (FileNotFoundException e) {
            Main.logger.error("FileNotFoundException: ", e);
        } catch (IOException e) {
            Main.logger.error("IOException: ", e);
        }
    }

    @Override
    public void zip(String File, String srcPath) { //home + "/Temp/backup/backup.zip", home + "/Temp/OUT/"

        File newDir = new File(srcPath);
        newDir.mkdir();
        String srcPathCopy = BackupAnalyserResource.home + "/Temp/IN/";

        String[] arrKey = new String[1000];
        int k = 0;

        try {

            ObjectMapper mapper = new ObjectMapper();

            // write JSON to a file
            for (String key : Store.hashMapOriginalData.keySet()) { //alle Keys durchlaufen
                if (key.endsWith(".json")) {
                    mapper.writeValue(new File(srcPath + key), Store.hashMapOriginalData.get(key));
                    arrKey[k] = key;
                    k++;
                }
            }

            File IN = new File(srcPathCopy);
            File[] filesIN = IN.listFiles();

            if(filesIN != null){
                for (File file: filesIN ) { //int i = 0; i < filesIN.length; i++
                    boolean found = false;

                    if (file.getName().endsWith(".json")) {

                        for(int i=0; arrKey[i] != null; i++){
                            if (file.getName().equals(arrKey[i])) {
                                found = true;
                            }
                        }

                        if(found == false){

                            File srcFile = new File(srcPathCopy + file.getName());
                            File destFile = new File(srcPath + file.getName());
                            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
                            Main.logger.info("COPY: " + file.getName());
                        }
                    }
                }
            }


            org.apache.commons.io.FileUtils.copyDirectory(new File(srcPathCopy + "mediastore"), new File(srcPath + "mediastore")); //copy MediaStore

            File backup = new File( BackupAnalyserResource.home + "/Temp/backup");
            backup.mkdir();

            zipFile(File, srcPath);

//            ZipUtil.pack(new File(srcPath), new File(BackupAnalyserResource.home + "/Temp/backup/backup.zip")); //Zip backup.zip in backup directory

            //Checksum berechnen
            Main.logger.debug("Calculate Checksum");
            String md5 = calculateChecksum();
//            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(BackupAnalyserResource.home + "/Temp/backupaudio.bak/backup.zip");
            PrintWriter out = new PrintWriter(BackupAnalyserResource.home + "/Temp/backup/md5.txt");
            out.write(md5);
            out.close();

            zipFile(BackupAnalyserResource.home + "/Temp/backup.bak",BackupAnalyserResource.home + "/Temp/backup");

//            ZipUtil.pack(new File(BackupAnalyserResource.home + "/Temp/backup"), new File(BackupAnalyserResource.home + "/Temp/backup.bak")); //Zip backup.zip in backup directory

        } catch (FileNotFoundException e) {
            Main.logger.error("FileNotFoundException: ", e);
        } catch (IOException e) {
            Main.logger.error("IOException: ", e);
        }
    }

    public void zipFile(String File, String srcPath) {
        File destFile = new File(srcPath);
        File[] files = destFile.listFiles();

        try {
            FileOutputStream fos = new FileOutputStream(File);
            ZipOutputStream zos = new ZipOutputStream(fos);

            Main.logger.info("Adding directory: " + destFile.getName());

            for (File f : files) {

                putZipEntry(zos, f, "");
            }
            zos.close();

        } catch (FileNotFoundException e) {
            Main.logger.error("FileNotFoundException: ", e);
        } catch (IOException e) {
            Main.logger.error("IOException: ", e);
        }
    }

    private void putZipEntry(ZipOutputStream zos, File file, String path) {
        if (file.isDirectory()) {
            putDirectoryZipEntry(zos, file, path);
        } else {
            putFileZipEntry(zos, file, path);
        }
    }

    private void putDirectoryZipEntry(ZipOutputStream zos, File file, String path) {
        Main.logger.info("Adding directory: "+ file.getName());
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                putZipEntry(zos, f, path + (!path.isEmpty() ? "/" : "") + file.getName()); //wenn path nicht leer ist, dann hängt / drann
            }
        }
    }

    private void putFileZipEntry(ZipOutputStream zos, File file, String path) {
        try {
            // create byte buffer
            Main.logger.info("Adding file: " + file.getName());
            byte[] buffer = new byte[1024];

            FileInputStream fis = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(path + (!path.isEmpty() ? "/" : "") + file.getName())); //wenn path leer ist, dann alles in backup.zip

            int length;

            while ((length = fis.read(buffer)) > 0) {

                zos.write(buffer, 0, length);
            }

            zos.closeEntry();

            // close the InputStream
            fis.close();
        } catch (IOException e) {
            Main.logger.error("IOException: ", e);
        }
    }

    public String calculateChecksum() {

        try {

            String zipFilePath = BackupAnalyserResource.home + "/Temp/backup/backup.zip"; //gezipt: 2.514.487 bytes original: 2.514.217 bytes
            byte[] bytesOfMessage = Files.readAllBytes(Paths.get(zipFilePath));

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mdbytes = md.digest(bytesOfMessage);

            //convert the byte to hex format
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            Main.logger.info("Digest(in hex format) Md5: " + sb.toString());
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            Main.logger.error("NoSuchAlgorithmException: ", e);
        } catch (IOException e) {
            Main.logger.error("IOException: ", e);
        }
        return null;
    }
}