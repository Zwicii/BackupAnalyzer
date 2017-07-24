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

    // TODO [STC]: Diese Liste entfernen (nicht nötig)
    // Anstattdessen direkt die entsprechende Sub-Liste aus Store.hashMapOriginalData verwenden;
    // Vorschlag: zusätzliche Funktion im Store namens getMediaCategory(...)
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

            // TODO [STC]: Ersetzen durch (z.B.) Main.logger.debug(...)
            Main.logger.info(map.get("entities"));

            // TODO [STC]: Überlegen, ob du diesen ganzen Code-Block überhaupt noch brauchst. Ohne den letzten Teil mit Store.storeData(...) (der wegfallen sollte, siehe erstes TODO) sind das hier nur Log Ausgaben, die man evtl. weglassen könnte.
//            if (map.get("entities") instanceof ArrayList) {
//
//                ArrayList arrayList = (ArrayList) map.get("entities");
//
//                for (int i = 0; i < arrayList.size(); i++) {
//
//                    Main.logger.info(arrayList.get(i));
//
//                    if (arrayList.get(i) instanceof LinkedHashMap) {
//
//                        LinkedHashMap<Object, Object> hashMap = (LinkedHashMap<Object, Object>) arrayList.get(i);
//                        String id = (String) hashMap.get("id");
//                        Main.logger.info("id: " + id);
//                        String name = (String) hashMap.get("name");
//                        Main.logger.info("name: " + name);
//
//                        // TODO [STC]: Diesen Code-Block entfernen (unnötig, siehe erstes TODO)
//                        Store.storeData(j, id, hashMapMediaCategory);
//                        j++;
//                        Store.storeData(j, name, hashMapMediaCategory);
//                        j++;
//                    }
//                }
//            }

            Store.storeOriginalData(fileName.getName(), map);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Store.storeOriginalData(fileName.getName(), hashMapMediaCategory);
    }
}
