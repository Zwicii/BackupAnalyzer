package analyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * entpackt Zipfiles
 */

public class Unzip {


    public static void unzip(String fileZip, String destination) throws IOException {

        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();


        while (zipEntry != null) {
            String fileName = zipEntry.getName();
            File newFile = new File(destination + fileName);
            newFile.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len); //buffer, Start, Stop
            }
            fos.close();

            if (zipEntry.getName().endsWith(".zip")) {

                String backupfile = "/home/victoria/Temp/" + zipEntry.getName();
                unzip(backupfile, destination);
            }


            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

    }
}


