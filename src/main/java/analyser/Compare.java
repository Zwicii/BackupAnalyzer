package analyser;

import java.util.HashMap;

/**
 * Vergleicht Einträge von 2 HasMaps ob sie gleich sind und speichert das Ergebnis in eine dritte HashMap
 */
public class Compare {

    public static HashMap<String, HashMap> hashMapCheck = new HashMap<>();
    public static HashMap<String, Boolean> hashMapMC = new HashMap<>();
    public static HashMap<String, Boolean> hashMapMF = new HashMap<>();


    public static void compare(HashMap<Integer, String> hashMap1, HashMap<Integer, String> hashMap2, HashMap<String, Boolean> hashMap3) {


        for (int i = 0; i < hashMap1.size(); i++) {

            boolean found = false;

            for (int j = 0; j < hashMap2.size(); j++) {
                if (hashMap1.get(i).equals(hashMap2.get(j))) {
                    Main.logger.info(hashMap1.get(i) + ": OK");
                    found = true;
                    hashMap3.put(hashMap1.get(i), found);

                    break;
                }
            }

            if (found == false) {
                Main.logger.error(hashMap1.get(i) + ": MediaCategory doest't exist");
                hashMap3.put(hashMap1.get(i), found);

            }
        }
    }

    public static void check() {

        hashMapCheck.put("com.commend.platform.mediastore.MediaCategory", hashMapMC);
        hashMapCheck.put("com.commend.platform.mediastore.Media", hashMapMF);


        for (HashMap.Entry<String, HashMap> entry : hashMapCheck.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Main.logger.info("\nKey: " + key);


            if (key.equals("com.commend.platform.mediastore.MediaCategory")) {
                for (HashMap.Entry<String, Boolean> entryMC : hashMapMC.entrySet()) {

                    String keyMC = entryMC.getKey();
                    Boolean valueMC = entryMC.getValue();
                    Main.logger.info(keyMC + ": " + valueMC);
                }
            }

            if (key.equals("com.commend.platform.mediastore.Media")) {
                for (HashMap.Entry<String, Boolean> entryMF : hashMapMF.entrySet()) {

                    String keyMF = entryMF.getKey();
                    Boolean valueMF = entryMF.getValue();
                    Main.logger.info(keyMF + ": " + valueMF);
                }
            }

        }
    }
}