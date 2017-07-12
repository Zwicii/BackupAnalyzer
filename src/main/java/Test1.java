import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
//import java.util.Objects;
//import jdk.internal.org.xml.sax.SAXException;


/**
 * Created by victoria on 07.07.17.
 */
public class Test1 {

    public static HashMap<String, String> hashMap = new HashMap<String, String>();

    /**
     * Erzeugen eines zip-Filesystems mit einen Windowspfad
     * Auslesen des Inhalts
     * Elegant und einfach mit ReadZipFileVisitor = Implementierung von FileVisitor
     */
//    private static void readZipFile1(Path winPath) {
//
//        Path absolute = winPath.toAbsolutePath();
//        Path normalized = absolute.normalize();
//
//        try (FileSystem zipFileSystem = FileSystems.newFileSystem(normalized, null)) //gilt in try/catch
//        {
//            System.out.println("Zipped size: " + zipFileSystem.getFileStores().iterator().next().getTotalSpace());
//            Iterable<Path> roots = zipFileSystem.getRootDirectories(); // there is only one
//            Path root = roots.iterator().next(); //  root = /
//            ReadZipFileVisitor readZipFileVisitor = new ReadZipFileVisitor();
//            Files.walkFileTree(root, readZipFileVisitor);
//            System.out.println("Unzipped size = " + readZipFileVisitor.getUnzippedSize() + " bytes");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private static void extractZipFileUsingUnixFileSystem() {
//        Path zipFile = Paths.get("/home/victoria/Downloads/presets.zip");
//        Path absPath = zipFile.toAbsolutePath().normalize(); // Windowspfad
//        URI uri = URI.create("jar:file:///" + absPath.toString().replace('\\', '/')); // jar: ist notwendig
//        Path dest = Paths.get("/home/victoria/Temp/");
//
//        Map<String, String> env = new HashMap<>();
//        env.put("create", "true");
//        try (FileSystem zipFileSystem = FileSystems.newFileSystem(uri, env)) {
//            Path root = zipFileSystem.getPath("/");
//            Files.walkFileTree(root, new ExtractZipFileVisitor(dest));
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//    }
    public static void main(String[] args) throws IOException {
        System.out.println("Gradle Test");

        String fileZip = "/home/victoria/Downloads/backupaudio.bak";
        String destination = "/home/victoria/Temp/";
        Path winPath = Paths.get("/home/victoria/Downloads/presets.zip/");

//        Unzip.unzip(fileZip, destination);
//        readZipFile1(winPath);
//        extractZipFileUsingUnixFileSystem();

        String jsonData = new String(Files.readAllBytes(Paths.get("/home/victoria/Temp/com.commend.platform.mediastore.Media.json/")));
//        jsonData = Inhalt von Mediafile
//        Parser.parseMedia(jsonData);

        final String directory = "/home/victoria/Temp/backup";
        Parser.parseBackupFile(directory);

        System.out.println("\nCompare MediaCategory");
        Compare.compare(Store.hashMapMediaC, Store.hashMapmediaCategory, Compare.hashMapMC);

        System.out.println("\nCompare Mediastore");
        Compare.compare(Store.hashMapMediaS, Store.hashMapMediaStore, Compare.hashMapMF);

        System.out.println("\nCheck");
        Compare.check();


    }

}






