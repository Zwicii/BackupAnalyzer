package analyser;

import interfaces.JsonFileParser;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * parse com.commend.platform.mediastore.Media.json
 */
public class MediaParser implements JsonFileParser {

    // TODO [STC]: Diese Listen entfernen (nicht nötig)
    // Anstattdessen in compareMedia(...) direkt die entsprechenden Sub-Listen aus Store.hashMapOriginalData verwenden;
    private static HashMap<String, Boolean> hashMapCompareMedia = new HashMap<>();

    //Singleton Pattern
    private static MediaParser instance = null;

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

        File fileName = new File(filePath);
        try {

            ObjectMapper mapper = new ObjectMapper();

            // read JSON from a file and put it into map
            Map<String, Object> map = mapper.readValue(
                    new File(filePath),
                    new TypeReference<Map<String, Object>>() {
                    });

            Main.logger.info(map.get("entities"));

            Store.storeOriginalData(fileName.getName(), map);
            compareMedia();
            Store.storeCheckResults(fileName.getName(), hashMapCompareMedia);

        } catch (JsonGenerationException e) {
            Main.logger.error("JsonGenerationException: ", e);
        } catch (JsonMappingException e) {
            Main.logger.error("JsonMappingExeption: ", e);
        } catch (IOException e) {
            Main.logger.error("IOException: ", e);
        }
    }

    public static void compareMedia() {

        String[] arrMedia = Store.getMedia(); //Data which will be compared from Media
        String[] arrMediaCategory = Store.getMediaCategory(); //Data which will be compared from MediaCategory

        for (int i = 0; arrMedia[i] != null; i++) {

            boolean found = false;

            for (int j = 0; arrMediaCategory[j] != null; j++) {

                if (arrMedia[i].equals(arrMediaCategory[j])) {//look if arrMediaCategory[] contains arrMedia[i]
                    Main.logger.info(arrMedia[i] + ": OK - found in Media Category");
                    found = true;
                    hashMapCompareMedia.put(arrMedia[i], found);
                    j++;
                    break;
                }
            }

            for (int l = 0; l < MediaStoreParser.hashMapMediaStore.size(); l++) {//look if hashMapMediaStore contains arrMedia[i]
                if (arrMedia[i].equals(MediaStoreParser.hashMapMediaStore.get(l))) {
                    Main.logger.info(arrMedia[i] + ": OK - found in Media Store");
                    found = true;
                    hashMapCompareMedia.put(arrMedia[i], found);
                    break;
                }
            }

            if (!found) {
                Main.logger.error(arrMedia[i] + ": MediaCategory or Data in MediaStore don't exist");
                hashMapCompareMedia.put(arrMedia[i], found);
            }
        }
    }
}
