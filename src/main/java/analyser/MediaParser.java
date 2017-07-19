package analyser;

import interfaces.JsonFileParser;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * parse com.commend.platform.mediastore.Media.json
 */
public class MediaParser implements JsonFileParser {

    private static MediaParser instance = null;
    private static HashMap<Integer, String> hashMapCompareData = new HashMap<>();
    private static HashMap<String, Boolean> hashMapCompareMedia = new HashMap<>();

    private MediaParser() {
    }

    public static MediaParser getInstance() {
        if (instance == null) {
            instance = new MediaParser();
        }
        return instance;
    }


    @Override
    public void parse(String filePath) {
        int j = 0;
        File fileName = new File(filePath);
        try {

            ObjectMapper mapper = new ObjectMapper();

            // read JSON from a file
            Map<String, Object> map = mapper.readValue(
                    new File(filePath),
                    new TypeReference<Map<String, Object>>() {
                    });

            System.out.println(map.get("entities"));

            if (map.get("entities") instanceof ArrayList) {

                ArrayList arrayList = (ArrayList) map.get("entities");

                for (int i = 0; i < arrayList.size(); i++) {

                    Main.logger.info(arrayList.get(i));

                    if (arrayList.get(i) instanceof LinkedHashMap) {

                        LinkedHashMap<Object, Object> hashMapM = (LinkedHashMap<Object, Object>) arrayList.get(i);

                        String id = (String) hashMapM.get("id");
                        Main.logger.info("id: " + id);
                        String displayName = (String) hashMapM.get("displayName");
                        Main.logger.info("displayName: " + displayName);

                        Main.logger.info("MediaCategory: " + hashMapM.get("mediaCategory"));
                        LinkedHashMap<Object, Object> hashMapMC = (LinkedHashMap<Object, Object>) hashMapM.get("mediaCategory");
                        String mediaCategoryid = (String) hashMapMC.get("id");
                        Main.logger.info("mediaCategory-id: " + mediaCategoryid);
                        String mediaCategoryName = (String) hashMapMC.get("name");
                        Main.logger.info("mediaCategory-name: " + mediaCategoryName);

                        Store.storeData(j, id, hashMapCompareData);
                        j++;
                        Store.storeData(j, mediaCategoryid, hashMapCompareData);
                        j++;
                        Store.storeData(j, mediaCategoryName, hashMapCompareData);
                        j++;
                    }
                }
            }

            Store.storeOriginalData(fileName.getName(), map);

            compareMedia(map);

            Store.storeCheckResults(fileName.getName(), hashMapCompareMedia);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void compareMedia(Map<String, Object> map) {
        int k = 0;

        String[] arr = new String[1000];

        if (map.get("entities") instanceof ArrayList) {

            ArrayList arrayList = (ArrayList) map.get("entities");

            for (int i = 0; i < arrayList.size(); i++) {

                Main.logger.info(arrayList.get(i));

                if (arrayList.get(i) instanceof LinkedHashMap) {

                    LinkedHashMap<Object, Object> hashMapM = (LinkedHashMap<Object, Object>) arrayList.get(i);

                    arr[k] = (String) hashMapM.get("id");
                    k++;

                    LinkedHashMap<Object, Object> hashMapMC = (LinkedHashMap<Object, Object>) hashMapM.get("mediaCategory");
                    arr[k] = (String) hashMapMC.get("id");
                    k++;

                    arr[k] = (String) hashMapMC.get("name");
                    k++;

                }
            }
        }

        for (int i = 0; i < k; i++) {

            boolean found = false;

            for (int j = 0; j < MediaCategoryParser.hashMapMediaCategory.size(); j++) {
                if (arr[i].equals(MediaCategoryParser.hashMapMediaCategory.get(j))) {
                    Main.logger.info(MediaParser.hashMapCompareData.get(i) + ": OK");
                    found = true;
                    hashMapCompareMedia.put(arr[i], found);
                    break;
                }
            }

            for (int l = 0; l < MediaStoreParser.hashMapMediaStore.size(); l++) {
                if (arr[i].equals(MediaStoreParser.hashMapMediaStore.get(l))) {
                    Main.logger.info(MediaParser.hashMapCompareData.get(i) + ": OK");
                    found = true;
                    hashMapCompareMedia.put(arr[i], found);
                    break;
                }
            }

            if (found == false) {
                Main.logger.error(MediaParser.hashMapCompareData.get(i) + ": MediaCategory doest't exist");
                hashMapCompareMedia.put(arr[i], found);
            }
        }
    }
}
