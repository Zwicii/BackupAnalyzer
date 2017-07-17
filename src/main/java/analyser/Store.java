package analyser;

import java.util.HashMap;

/**
 * Speichert Daten in Mashmaps
 */
public class Store {

    public static HashMap<Integer, String> hashMapMediaC = new HashMap<Integer, String>();
    public static HashMap<Integer, String> hashMapmediaCategory = new HashMap<>();
    public static HashMap<Integer, String> hashMapMediaStore = new HashMap<>();
    public static HashMap<Integer, String> hashMapMediaS = new HashMap<>();


    public static void storeDataMediaC(int key, String value) {

        hashMapMediaC.put(key, value);
//        Test1.logger.info("Key: " + key + " " + "Value: " + hashMapMediaC.get(key));
    }

    public static void storeDataMediaCategory(int key, String value) {

        hashMapmediaCategory.put(key, value);
//        Test1.logger.info("Key: " + key + " " + "Value: " + hashMapmediaCategory.get(key));

    }

    public static void storeDataMediaStore(int key, String value) {

        hashMapMediaStore.put(key, value);
        Main.logger.info("Key: " + key + " " + "Value: " + hashMapMediaStore.get(key));
    }

    public static void storeDataMediaS(int key, String value) {

        hashMapMediaS.put(key, value);
        Main.logger.info("Key: " + key + " " + "Value: " + hashMapMediaS.get(key));
    }

}
