package analyser;

import interfaces.JsonFileParser;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * parse com.commend.platform.mediastore.MediaCategory.json
 */
public class MediaCategoryParser implements JsonFileParser {

    //Singleton Pattern
    private static MediaCategoryParser instance = null;

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

        File fileName = new File(filePath);

        try {

            ObjectMapper mapper = new ObjectMapper(); //converting between Java objects and matching JSON constructs.

            // read JSON from a file and put it into map
            Map<String, Object> map = mapper.readValue(
                    new File(filePath),
                    new TypeReference<Map<String, Object>>() {
                    });

            Main.logger.info(map.get("entities"));

            Store.storeOriginalData(fileName.getName(), map);

        } catch (JsonGenerationException e) {
            Main.logger.error("JsonGenerationException: ", e);
        } catch (JsonMappingException e) {
            Main.logger.error("JsonMappingException: ", e);
        } catch (IOException e) {
            Main.logger.error("IOException: ", e);
        }

    }
}
