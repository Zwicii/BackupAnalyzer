package analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Speichert Daten in Mashmaps
 */
public class Store {

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

    public static ArrayList<Object> getMediaCategory() {

        ArrayList<Object> arrayListMediaCategory = new ArrayList<>();
        //get all Data from MediaCategory
        HashMap<Object, Object> map = (HashMap<Object, Object>) Store.hashMapOriginalData.get("com.commend.platform.mediastore.MediaCategory.json");

        if (map.get("entities") instanceof ArrayList) {
            ArrayList arrayListEntities = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

            for (Object entity : arrayListEntities) {

                if (entity instanceof LinkedHashMap) {

                    LinkedHashMap<Object, Object> linkedHashMap = (LinkedHashMap<Object, Object>) ((LinkedHashMap) entity);

                    if (linkedHashMap.containsKey("id")) {
                        arrayListMediaCategory.add(linkedHashMap.get("id"));
                    }
                    if (linkedHashMap.containsKey("name")) {
                        arrayListMediaCategory.add(linkedHashMap.get("name"));
                    }
                    if (linkedHashMap.containsKey("maxSpace")) {
                        arrayListMediaCategory.add(linkedHashMap.get("maxSpace"));
                    }
                    if (linkedHashMap.containsKey("usedSpace")) {
                        arrayListMediaCategory.add(linkedHashMap.get("usedSpace"));
                    }
                }
            }
        }
        return arrayListMediaCategory;
    }

    public static ArrayList<Object> getMedia() {
        //String[] arr = new String[1000];
        //int k = 0;
        // get all Data from Media
        HashMap<Object, Object> map = (HashMap<Object, Object>) Store.hashMapOriginalData.get("com.commend.platform.mediastore.Media.json");
        ArrayList<Object> arrayListMedia = new ArrayList<>();

        if (map.get("entities") instanceof ArrayList) {

            ArrayList arrayListEntities = (ArrayList) map.get("entities");

            for (Object entity : arrayListEntities) {

                if (entity instanceof LinkedHashMap) {

                    LinkedHashMap<Object, Object> linkedHashMap = (LinkedHashMap<Object, Object>) ((LinkedHashMap) entity);

                    if (linkedHashMap.containsKey("id")) {
                        arrayListMedia.add(linkedHashMap.get("id"));
                    }
                    if (linkedHashMap.containsKey("mediaCategory")) {
                        LinkedHashMap<Object, Object> linkedHashMapMediaCategory = (LinkedHashMap<Object, Object>) linkedHashMap.get("mediaCategory"); //eigene Hashmap für MediaCategory

                        if (linkedHashMapMediaCategory.containsKey("id")) {
                            arrayListMedia.add(linkedHashMapMediaCategory.get("id"));
                        }

                        if (linkedHashMapMediaCategory.containsKey("name")) {
                            arrayListMedia.add(linkedHashMapMediaCategory.get("name"));
                        }
                    }
                }
            }
        }
        return arrayListMedia;
    }
}
