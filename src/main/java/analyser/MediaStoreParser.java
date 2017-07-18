package analyser;

import interfaces.JsonFileParser;

import java.io.File;

/**
 * parse directoy mediastore
 */
public class MediaStoreParser implements JsonFileParser {

    private static MediaStoreParser instance = null;

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
        int k = 0;
        File directorymedia = new File(filePath);
        File[] mfList = directorymedia.listFiles();

        for (File mfile : mfList) {

//                    System.out.println(mfile.getName());


            if (mfile.getName().equals("sounds")) {

                Main.logger.info("\nfound sounds");

                final String directorymediastoresounds = filePath + "sounds";
                File directorymediasounds = new File(directorymediastoresounds);
                File[] msoundsfList = directorymediasounds.listFiles();

                for (File msfile : msoundsfList) {

                    Main.logger.info(msfile.getName());
                    Store.storeDataMediaStore(j, msfile.getName());
                    j++;
                    k = j;

                }
            }

            if (mfile.getName().equals("snapshots")) {

                Main.logger.info("\nfound snapshots");

                final String directorymediastoresnap = filePath + "snapshots";
                File directorymediasnap = new File(directorymediastoresnap);
                File[] msnaphotfList = directorymediasnap.listFiles();

                for (File mshfile : msnaphotfList) {

                    Main.logger.info(mshfile.getName());
                    Store.storeDataMediaStore(k, mshfile.getName());
                    k++;

                }
            }
        }
    }
}
