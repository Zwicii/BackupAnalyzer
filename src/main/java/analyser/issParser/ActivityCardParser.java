package analyser.issParser;

import analyser.Store;
import implementaions.BackupFileParserImpl;
import interfaces.JsonFileParser;

import java.io.File;
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
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: activityEvent does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        if (a.get("activityEvent") != null) {

                            HashMap<String, String> hashMapActivitySet = (HashMap<String, String>) a.get("activityEvent");
                            if (!hashMapActivitySet.containsKey("_type")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: activityEvent does not contain property _type");
                                BackupFileParserImpl.errorNumber++;
                            }
                            if (!hashMapActivitySet.containsKey("id")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: activityEvent does not contain property id");
                                BackupFileParserImpl.errorNumber++;
                            }

                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: activityEvent is null");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //actionSet: ob existiert, ob Property _type und id enthalten sind oder darf auch null sein
                        if (a.containsKey("actionSet")) {

                            if (a.get("actionSet") != null) {

                                HashMap<String, String> hashMapActionSet = (HashMap<String, String>) a.get("actionSet");
                                if (!hashMapActionSet.containsKey("_type")) {
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: actionSet does not contain property _type");
                                    BackupFileParserImpl.errorNumber++;
                                }
                                if (!hashMapActionSet.containsKey("id")) {
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: actionSet does not contain property id");
                                    BackupFileParserImpl.errorNumber++;
                                }
                            }

                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: actionSet does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //telephonyState: ob existiert
                        if (!a.containsKey("telephonyState")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: telephonyState does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        if (a.get("telephonyState") == null || a.get("telephonyState").equals("INITIALIZING") || a.get("telephonyState").equals("IDLE")
                                || a.get("telephonyState").equals("ACTIVE") || a.get("telephonyState").equals("RINGING") || a.get("telephonyState").equals("DIALLING")
                                || a.get("telephonyState").equals("ERROR") ) {
                            continue;
                        }
                        else{
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: telephonyState has the wrong value");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //system: ob existiert
                        if (!a.containsKey("system")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: system does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }
                    }
                }
            }
        }
        Store.hashMapCheckResults.put(fileName.getName(), check);
    }
}
