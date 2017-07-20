package impl;

import analyser.Main;
import analyser.Store;
import interfaces.ZipFileService;
import org.codehaus.jackson.map.ObjectMapper;

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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void zip(String File, String destPath) {

        File newDir = new File(destPath);
        newDir.mkdir();
//        destPath = newDir.getPath() + "/";
        String srcPathCopy = "/home/victoria/Temp/IN/";

        String[] arrKey = new String[1000];
        int k = 0;
        int l = 0;

        try {

            ObjectMapper mapper = new ObjectMapper();

            // write JSON to a file
            for (String key : Store.hashMapOriginalData.keySet()) { //alle Keys durchlaufen
                if (key.endsWith(".json")) {
                    mapper.writeValue(new File(destPath + key), Store.hashMapOriginalData.get(key));
                    arrKey[k] = key;
                    k++;
                }
            }

            File IN = new File(srcPathCopy);
            File[] filesIN = IN.listFiles();

            for (int i = 0; i < filesIN.length; i++) {
                if (filesIN[i].getName().endsWith(".json")) {

                    if (filesIN[i].getName() != arrKey[l]) {
                        File srcFile = new File(srcPathCopy + filesIN[i].getName());
                        File destFile = new File(destPath + filesIN[i].getName());
                        org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
                    }
                    l++;
                }
            }

            org.apache.commons.io.FileUtils.copyDirectory(new File(srcPathCopy + "mediastore"), new File(destPath + "mediastore")); //copy MediaStore

            File backup = new File("/home/victoria/Temp/backup");
            backup.mkdir();

            zipFile(File, destPath);

//            ZipUtil.pack(new File(destPath), new File("/home/victoria/Temp/backup/backup.zip")); //Zip backup.zip in backup directory

            //Checksum berechnen
//            calculateChecksum();
//
//            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex("/home/victoria/Temp/backupaudio.bak/backup.zip");
//            PrintWriter out = new PrintWriter("/home/victoria/Temp/backup/md5.txt");
//            out.write(md5);
//            out.close();

//            ZipUtil.pack(new File("/home/victoria/Temp/backup"), new File("/home/victoria/Temp/backup.bak")); //Zip backup.zip in backup directory

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zipFile(String File, String destPath) {
        File destFile = new File(destPath);
        File[] files = destFile.listFiles();

        try {
            FileOutputStream fos = new FileOutputStream(File);
            ZipOutputStream zos = new ZipOutputStream(fos);

            Main.logger.info("Adding directory: " + destFile.getName());

            for (int i = 0; i < files.length; i++) {
                // if the file is directory, use recursion

                if (files[i].isDirectory()) {

                    String fileZip = File + "/" + files[i].getName();
                    String destinationPath = destPath + "/" + files[i].getName();
                    zipFile(fileZip, destinationPath);
                    continue;
                }

                Main.logger.info("tAdding file: " + files[i].getName());

                // create byte buffer
                byte[] buffer = new byte[1024];

                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));

                int length;

                while ((length = fis.read(buffer)) > 0) {

                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();

                // close the InputStream
                fis.close();
            }
            zos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String calculateChecksum() {

        try {

            String yourString = "/home/victoria/Temp/IN/backup.zip";
            byte[] bytesOfMessage = Files.readAllBytes(Paths.get(yourString));

            MessageDigest md = MessageDigest.getInstance("MD5");            //2.514.217
            byte[] mdbytes = md.digest(bytesOfMessage);

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            Main.logger.info("Digest(in hex format): " + sb.toString());

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


