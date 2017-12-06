package analyser.platformParser;

import analyser.Main;
import analyser.Store;
import impl.BackupFileParserImpl;
import interfaces.JsonFileParser;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * parse com.commend.platform.db.MigrationScript.json
 */
public class MigrationScriptParser implements JsonFileParser {

    private static MigrationScriptParser instance = null;

    private MigrationScriptParser() {
    }

    public static MigrationScriptParser getInstance() {
        if (instance == null) {
            instance = new MigrationScriptParser();
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

            Boolean check = true;

            //Kontrolliert ob bei entities alles passt
            if (map.containsKey("entities")) {

                //Arraylist von Entities
                if (map.get("entities") instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                    if (arrayList.size() != 0) {

                        for (int i = 0; i < arrayList.size(); i++) {
                            LinkedHashMap<Object, Object> a = (LinkedHashMap<Object, Object>) arrayList.get(i);

                            //name: ob existiert und nicht null ist
                            if(!a.containsKey("name")){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: name doesn not exist");
                            }
                            if(a.get("name") == null){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: name is null");
                            }


                        }

                    }
                }
            }
            Store.hashMapCheckResults.put(fileName.getName(), check);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
