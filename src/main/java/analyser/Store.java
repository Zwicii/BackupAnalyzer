package analyser;

import java.util.HashMap;

/**
 * Speichert Daten in Mashmaps
 */
public class Store {

//    public static HashMap<Integer, String> hashMapMediaC = new HashMap<>();
//    public static HashMap<Integer, String> hashMapMediaS = new HashMap<>();

    public static HashMap<String, Object> hashMapOriginalData = new HashMap<>();
    public static HashMap<String, Object> hashMapCheckResults = new HashMap<>();

    public static void storeOriginalData(String key, Object value) {

        hashMapOriginalData.put(key, value);
        Main.logger.info("Key: " + key + " " + "Value: " + value);
    }

    public static void storeCheckResults(String key, Object value) {

        hashMapCheckResults.put(key, value);
        Main.logger.info("Key: " + key + " " + "Value: " + value);
    }

    public static void storeData(int key, String value, HashMap<Integer, String> hashMap) {

        hashMap.put(key, value);
//        Test1.logger.info("Key: " + key + " " + "Value: " + hashMapMediaC.get(key));
    }


}
