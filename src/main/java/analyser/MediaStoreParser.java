package analyser;

import interfaces.JsonFileParser;

import java.io.File;
import java.util.HashMap;

/**
 * parse directory mediastore
 */
public class MediaStoreParser implements JsonFileParser {

    private static MediaStoreParser instance = null;
    public static HashMap<Integer, String> hashMapMediaStore = new HashMap<>(); //Beinhaltet alle Daten im Mediastore, wei map von den json Files

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
        int j = 0;
        File MediaStore = new File(filePath);
        File[] MediaStoreFileList = MediaStore.listFiles();

        if (MediaStoreFileList != null) {
            for (File fileMediaStore : MediaStoreFileList) { //parse Mediastore

                if (fileMediaStore.getName().equals("sounds")) {

                    Main.logger.info("\nfound sounds");

                    final String soundsPath = filePath + "sounds";
                    File Sounds = new File(soundsPath);
                    File[] SoundsFileList = Sounds.listFiles();

                    if (SoundsFileList != null) {
                        for (File fileSounds : SoundsFileList) { //parse Sounds

                            Main.logger.info(fileSounds.getName());
                            hashMapMediaStore.put(j, fileSounds.getName());
                            j++;
                        }
                    }
                }

                if (fileMediaStore.getName().equals("snapshots")) {

                    Main.logger.info("\nfound snapshots");

                    final String snapshotsPath = filePath + "snapshots";
                    File Snapshots = new File(snapshotsPath);
                    File[] SnapshotsFileList = Snapshots.listFiles();

                    if (SnapshotsFileList != null) {
                        for (File fileSnapshots : SnapshotsFileList) { //parse Snapshots

                            Main.logger.info(fileSnapshots.getName());
                            hashMapMediaStore.put(j, fileSnapshots.getName());
                            j++;
                        }
                    }
                }
            }
        }

        Store.storeOriginalData(MediaStore.getName(), hashMapMediaStore);
    }
}
