package analyser;

import interfaces.JsonFileParser;

import java.io.File;
import java.util.ArrayList;

/**
 * parse directory mediastore
 */
public class MediaStoreParser implements JsonFileParser {

    private static MediaStoreParser instance = null;
    public static ArrayList<Object> arrayListMediaStore = new ArrayList<>();//Beinhaltet alle Daten im Mediastore, wei map von den json Files
    public static long spaceSnapshots = 0;
    public static long spaceSounds = 0;

    private MediaStoreParser() {
    }

    public static MediaStoreParser getInstance() {
        if (instance == null) {
            instance = new MediaStoreParser();
        }
        return instance;
    }


    @Override
    public void parse(String filePath) {
        File MediaStore = new File(filePath);
        File[] MediaStoreFileList = MediaStore.listFiles();

        if (MediaStoreFileList != null) {
            for (File fileMediaStore : MediaStoreFileList) { //parse Mediastore

                if (fileMediaStore.getName().equals("sounds")) {

                    Main.logger.info("\nfound sounds");

                    spaceSounds = 0;

                    final String soundsPath = filePath + "sounds";
                    File Sounds = new File(soundsPath);
                    File[] SoundsFileList = Sounds.listFiles();

                    if (SoundsFileList != null) {
                        for (File fileSounds : SoundsFileList) { //parse Sounds
                            Main.logger.info(fileSounds.getName());
                            arrayListMediaStore.add(fileSounds.getName());

                            spaceSounds = spaceSounds + fileSounds.length();
                        }
                    }
                }

                if (fileMediaStore.getName().equals("snapshots")) {

                    spaceSnapshots = 0;

                    final String snapshotsPath = filePath + "snapshots";
                    File Snapshots = new File(snapshotsPath);
                    File[] SnapshotsFileList = Snapshots.listFiles();

                    if (SnapshotsFileList != null) {
                        for (File fileSnapshots : SnapshotsFileList) { //parse Snapshots

                            Main.logger.info(fileSnapshots.getName());
                            arrayListMediaStore.add(fileSnapshots.getName());

                            spaceSnapshots = spaceSnapshots + fileSnapshots.length();
                        }
                    }
                }
            }
        }
        Store.storeOriginalData(MediaStore.getName(), arrayListMediaStore);
    }
}
