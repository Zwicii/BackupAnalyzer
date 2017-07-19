package analyser;

import interfaces.JsonFileParser;
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
 * parse com.commend.platform.mediastore.MediaCategory.json
 */
public class MediaCategoryParser implements JsonFileParser {

    private static MediaCategoryParser instance = null;
    public static HashMap<Integer, String> hashMapMediaCategory = new HashMap<>();


    private MediaCategoryParser() {
    }

    public static MediaCategoryParser getInstance() {
        if (instance == null) {
            instance = new MediaCategoryParser();
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

                        LinkedHashMap<Object, Object> hashMap = (LinkedHashMap<Object, Object>) arrayList.get(i);
                        String id = (String) hashMap.get("id");
                        Main.logger.info("id: " + id);
                        String name = (String) hashMap.get("name");
                        Main.logger.info("name: " + name);

                        Store.storeData(j, id, hashMapMediaCategory);
                        j++;
                        Store.storeData(j, name, hashMapMediaCategory);
                        j++;
                    }
                }
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Store.storeOriginalData( fileName.getName(), hashMapMediaCategory);
    }

}
