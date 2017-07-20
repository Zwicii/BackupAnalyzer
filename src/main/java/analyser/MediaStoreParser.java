package analyser;

import interfaces.JsonFileParser;

import java.io.File;
import java.util.HashMap;

/**
 * parse directoy mediastore
 */
public class MediaStoreParser implements JsonFileParser {

    private static MediaStoreParser instance = null;
    public static HashMap<Integer, String> hashMapMediaStore = new HashMap<>();

    private MediaStoreParser() {
    }

    public static MediaStoreParser getInstance() {
        if (instance == null) {
            instance = new MediaStoreParser();
        }
        return instance;
    }

    // TODO [STC]: Sprechende Variablen Namen verwenden (-> überarbeiten)
    // Was bedeutet im nachfolgenden Code
    //      MS ? (an mehreren Stellen)
    //      fList ?
    //      mfile ?
    @Override
    public void parse(String filePath) {
        int j = 0;
        int k = 0;
        File directoryMS = new File(filePath);
        File[] MSfList = directoryMS.listFiles();

        for (File mfile : MSfList) {

            if (mfile.getName().equals("sounds")) {

                Main.logger.info("\nfound sounds");

                final String soundsPath = filePath + "sounds";
                File directoryMSsounds = new File(soundsPath);
                File[] MSsoundsfList = directoryMSsounds.listFiles();

                for (File msfile : MSsoundsfList) {

                    Main.logger.info(msfile.getName());
                    Store.storeData(j, msfile.getName(), hashMapMediaStore);
                    j++;
                    k = j;
                }
            }

            if (mfile.getName().equals("snapshots")) {

                Main.logger.info("\nfound snapshots");

                final String snapshotsPath = filePath + "snapshots";
                File directoryMSsnapshots = new File(snapshotsPath);
                File[] MSsnapshotsList = directoryMSsnapshots.listFiles();

                for (File mshfile : MSsnapshotsList) {

                    Main.logger.info(mshfile.getName());
                    Store.storeData(k, mshfile.getName(), hashMapMediaStore);
                    k++;
                }
            }
        }

        Store.storeOriginalData(directoryMS.getName(), hashMapMediaStore);
    }
}
