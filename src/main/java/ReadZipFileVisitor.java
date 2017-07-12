import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by victoria on 10.07.17.
 */
    public class ReadZipFileVisitor implements FileVisitor<Path>
    {
        private long unzippedSize = 0;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
        {
            System.out.println("<dir> " + dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
        {
            long size = Files.size(file);
            unzippedSize += size;
            System.out.println("   <file>" + file + " " + size);
            // die angegebene Groesse ist die die originale der nicht gezippten Datei !

            if(file.endsWith(".zip")){

                try(FileSystem zipFileSystem = FileSystems.newFileSystem(file, null)) //gilt in try/catch
                {
                    System.out.println("Zipped size: " + zipFileSystem.getFileStores().iterator().next().getTotalSpace()  );
                    Iterable<Path> roots = zipFileSystem.getRootDirectories(); // there is only one
                    Path root = roots.iterator().next(); //  root = /

                    ReadZipFileVisitor readZipFileVisitor = new ReadZipFileVisitor();
                    Files.walkFileTree(root, readZipFileVisitor);
                    System.out.println("Unzipped size = " + readZipFileVisitor.getUnzippedSize() + " bytes");
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }


            }



            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
        {
            System.out.println("<end> " + dir );
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
        {
            return FileVisitResult.CONTINUE;
        }

        // nice to have
        public long getUnzippedSize()
        {
            return unzippedSize;
        }
    }

