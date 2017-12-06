package analyser.issParser;

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
 * parse com.commend.iss.activity.ActivityCard.json
 */
public class ActivityCardParser implements JsonFileParser {

    private static ActivityCardParser instance = null;

    private ActivityCardParser() {
    }

    public static ActivityCardParser getInstance() {
        if (instance == null) {
            instance = new ActivityCardParser();
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

                            //activityEvent: ob existiert
                            if(!a.containsKey("activityEvent")){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: activityEvent doesn not exist");
                            }
                            if(a.get("activityEvent") == null){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: activityEvent is null");
                            }

                            //actionSet: ob existiert
                            if(!a.containsKey("actionSet")){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: actionSet does not exist");
                            }

                            //telephonyState: ob existiert
                            if(!a.containsKey("telephonyState")){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: telephonyState does not exist");
                            }
                            if(a.get("telephonyState") != null || a.get("telephonyState") != "INITIALIZING" || a.get("telephonyState") != "IDLE"
                                    || a.get("telephonyState") != "ACTIVE" || a.get("telephonyState") != "RINGING" || a.get("telephonyState") != "DIALLING" || a.get("telephonyState") != "ERROR"  ){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: telephonyState has the wrong value");
                            }

                            //system: ob existiert
                            if(!a.containsKey("system")){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" +i +"]: system does not exist");
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
