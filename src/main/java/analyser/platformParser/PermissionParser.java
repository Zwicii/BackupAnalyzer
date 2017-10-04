package analyser.platformParser;

import analyser.Main;
import analyser.Store;
import interfaces.JsonFileParser;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by victoria on 26.07.17.
 */
public class PermissionParser implements JsonFileParser {

    //Singleton Pattern
    private static PermissionParser instance = null;

    private PermissionParser() {
    }

    public static PermissionParser getInstance() {
        if (instance == null) {
            instance = new PermissionParser();
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