package analyser;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public static String[] getMediaCategory() {
        String[] arr = new String[1000];
        int k = 0;
        //get all Data from MediaCategory
        HashMap<Object, Object> map = (HashMap<Object, Object>) Store.hashMapOriginalData.get("com.commend.platform.mediastore.MediaCategory.json");

        if (map.get("entities") instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

            for (int i = 0; i < arrayList.size(); i++) {

                if (arrayList.get(i) instanceof LinkedHashMap) {

                    LinkedHashMap<Object, Object> hashMap = (LinkedHashMap<Object, Object>) arrayList.get(i); //Hashmap for every entry (sound/snapshots)
                    arr[k] = (String) hashMap.get("id");
                    k++;
                    arr[k] = (String) hashMap.get("name");
                    k++;
                }
            }
        }
        return arr;
    }

    public static String[] getMedia() {
        String[] arr = new String[1000];
        int k = 0;
        // get all Data from Media
        HashMap<Object, Object> map = (HashMap<Object, Object>) Store.hashMapOriginalData.get("com.commend.platform.mediastore.Media.json");

        if (map.get("entities") instanceof ArrayList) {

            ArrayList arrayList = (ArrayList) map.get("entities");

            for (int i = 0; i < arrayList.size(); i++) {

                Main.logger.info(arrayList.get(i));

                if (arrayList.get(i) instanceof LinkedHashMap) {

                    LinkedHashMap<Object, Object> hashMapM = (LinkedHashMap<Object, Object>) arrayList.get(i); //Hashmap for every entry

                    arr[k] = (String) hashMapM.get("id");
                    k++;
                    LinkedHashMap<Object, Object> hashMapMC = (LinkedHashMap<Object, Object>) hashMapM.get("mediaCategory"); //eigene Hashmap für MediaCategory
                    arr[k] = (String) hashMapMC.get("id");
                    k++;
                    arr[k] = (String) hashMapMC.get("name");
                    k++;
                }
            }
        }
        return arr;
    }
}
