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
import java.util.HashMap;
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
        Map<Object, Object> map = (Map<Object, Object>) Store.hashMapOriginalData.get("com.commend.iss.activity.ActivityCard.json");
        Boolean check = true;

        //Kontrolliert ob bei entities alles passt
        if (map.containsKey("entities")) {

            //Arraylist von Entities
            if (map.get("entities") instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                if (arrayList.size() != 0) {

                    for (int i = 0; i < arrayList.size(); i++) {
                        LinkedHashMap<Object, Object> a = (LinkedHashMap<Object, Object>) arrayList.get(i);

                        //activityEvent: ob existiert, ob nicht null ist und ob properties _type und id enthalten sind
                        if (!a.containsKey("activityEvent")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: activityEvent does not exist");
                        }

                        if (a.get("activityEvent") != null) {

                            HashMap<String, String> hashMapActivitySet = (HashMap<String, String>) a.get("activityEvent");
                            if (!hashMapActivitySet.containsKey("_type")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: activityEvent does not contain property _type");
                            }
                            if (!hashMapActivitySet.containsKey("id")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: activityEvent does not contain property id");
                            }

                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: activityEvent is null");
                        }

                        //actionSet: ob existiert, ob Property _type und id enthalten sind oder darf auch null sein
                        if (a.containsKey("actionSet")) {

                            if (a.get("actionSet") != null) {

                                HashMap<String, String> hashMapActionSet = (HashMap<String, String>) a.get("actionSet");
                                if (!hashMapActionSet.containsKey("_type")) {
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: actionSet does not contain property _type");
                                }
                                if (!hashMapActionSet.containsKey("id")) {
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: actionSet does not contain property id");
                                }
                            }

                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: actionSet does not exist");
                        }

                        //telephonyState: ob existiert
                        if (!a.containsKey("telephonyState")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: telephonyState does not exist");
                        }

                        if (a.get("telephonyState") == null || a.get("telephonyState").equals("INITIALIZING") || a.get("telephonyState").equals("IDLE")
                                || a.get("telephonyState").equals("ACTIVE") || a.get("telephonyState").equals("RINGING") || a.get("telephonyState").equals("DIALLING")
                                || a.get("telephonyState").equals("ERROR") ) {
                            continue;
                        }
                        else{
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: telephonyState has the wrong value");
                        }

                        //system: ob existiert
                        if (!a.containsKey("system")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entity[" + i + "]: system does not exist");
                        }
                    }
                }
            }
        }
        Store.hashMapCheckResults.put(fileName.getName(), check);
    }
}
