package analyser;

import implementaions.BackupFileParserImpl;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonFileSanityCheckParser implements JsonFileParser {

    public static HashMap<String, Boolean> hashMapCompareJsonSanityCheck = new HashMap<String, Boolean>();
    public static HashMap<String, HashMap> hashMapCompareDeviceDescription = new HashMap<String, HashMap>();
    public static LinkedHashMap<String, String> linkedHashMapDeviceDescription = new LinkedHashMap<>();

    //Singleton Pattern
    private static JsonFileSanityCheckParser instance = null;

    private JsonFileSanityCheckParser() {
    }

    public static JsonFileSanityCheckParser getInstance() {
        if (instance == null) {
            instance = new JsonFileSanityCheckParser();
        }
        return instance;
    }
    @Override
    public void parse(String filePath) {

        File fileName = new File(filePath);
        boolean check = true;

        try {

            ObjectMapper mapper = new ObjectMapper(); //converting between Java objects and matching JSON constructs.

            // read JSON from a file and put it into map
            Map<String, Object> map = mapper.readValue(
                    new File(filePath),
                    new TypeReference<Map<String, Object>>() {
                    });


            //Alle Daten in hashMap Original Data speichern
            Store.storeOriginalData(fileName.getName(), map);

            //Kontrolliert ob bei _type alles passt
            if (map.containsKey("_type")) {

                String type = (String) map.get("_type");
                if (! type.equals("com.commend.iss.backup.DataContainer")) {
                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Wrong _type");
                    check = false;
                }
            }
            else {
                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "_type does not exist");
                check = false;
            }

            //Kontrolliert ob bei deviceDescription alles passt
            if (map.containsKey("deviceDescription")) {

                if (map.get("deviceDescription") instanceof LinkedHashMap) {

                    //Alle Hasmaps von deviceDescription
                    linkedHashMapDeviceDescription = (LinkedHashMap<String, String>) ((LinkedHashMap) map.get("deviceDescription"));
                    hashMapCompareDeviceDescription.put(fileName.getName(), linkedHashMapDeviceDescription);

                    //applicationName
                    if ((linkedHashMapDeviceDescription.containsKey("applicationName"))) {

                        if (linkedHashMapDeviceDescription.get("applicationName") == null || linkedHashMapDeviceDescription.get("applicationName") instanceof String == false) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationName is null or not a String");
                        }
                    } else {
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationName does not exist");
                    }

                    //applicationVersion
                    if ((linkedHashMapDeviceDescription.containsKey("applicationVersion"))) {

                        if (linkedHashMapDeviceDescription.get("applicationVersion") == null || linkedHashMapDeviceDescription.get("applicationVersion") instanceof String == false) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationVersion is null or not a String");
                        }
                    } else {
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationVersion does not exist");
                    }

                    //imageVersion
                    if ((linkedHashMapDeviceDescription.containsKey("imageVersion"))) {

                        if (linkedHashMapDeviceDescription.get("imageVersion") == null || linkedHashMapDeviceDescription.get("imageVersion") instanceof String == false) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: imageVersion is null or not a String");
                        }
                    } else {
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: imageVersion does not exist");
                    }

                    //os
                    if ((linkedHashMapDeviceDescription.containsKey("os"))) {

                        if (linkedHashMapDeviceDescription.get("os") == null || linkedHashMapDeviceDescription.get("os") instanceof String == false) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: os is null or not a String");
                        }
                    } else {
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: os does not exist");
                    }

                    //javaVersion
                    if ((linkedHashMapDeviceDescription.containsKey("javaVersion"))) {

                        if (linkedHashMapDeviceDescription.get("javaVersion") == null || linkedHashMapDeviceDescription.get("javaVersion") instanceof String == false) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: javaVersion is null or not a String");
                        }
                    } else {
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: javaVersion does not exist");
                    }

                    //deviceType
                    if ((linkedHashMapDeviceDescription.containsKey("deviceType"))) {

                        if (linkedHashMapDeviceDescription.get("deviceType") == null || linkedHashMapDeviceDescription.get("deviceType") instanceof String == false) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: deviceType is null or not a String");
                        }
                    } else {
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: deviceType does not exist");
                    }
                }
            } else {
                check = false;
                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription does not exist");
            }

            //Kontrolliert ob bei entities alles passt
            if (map.containsKey("entities")) {

                String[] ids = new String[1000];
                String[] types = new String[1000];


                //Arraylist von Entities
                if (map.get("entities") instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                    if (arrayList.size() != 0) {

                        for (int i = 0; i < arrayList.size(); i++) {
                            LinkedHashMap<Object, Object> a = (LinkedHashMap<Object, Object>) arrayList.get(i);

                            if (a.containsKey("id")){
                                ids[i] = a.get("id").toString();
                            }


                            if (a.containsKey("_type")) {
                                types[i] = a.get("_type").toString();
                            }
                        }

                        //id: muss vorhanden sein, darf aber auch null sein
                        //UUID: muss richtiges Format haben
                        if(ids != null){
                            for (int i=0 ; ids[i] != null ; i++) { //damit nicht das ganze Array durchlaufen wird
                                String id = ids[i];
                                if(id != null){
                                    CharSequence uuid = id;

                                    if (!checkUUID(uuid)) {
                                         check = false;
                                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "entities: UUID is not correct");
                                    }
                                }
                            }
                        }



                        //_type: muss gleich sein wie filename
                        for (int i = 0; types[i] != null; i++ ) { //damit nicht das ganze Array durchlaufen wird

                            String type = types[i];

                            if (type != null){
                                //TODO[OBV]: Optimieren
                                type = type + ".json"; // type muss gleich sein wie Filename
                                String name = fileName.getName();
                                if (!type.equals(name)) {
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "entities: _type is not correct");
                                }
                            }
                        }
                    }
                }
            } else {
                check = false;
                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "entities does not exist");
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Speichert check in hashMapCompareJsonSanityCheck
        hashMapCompareJsonSanityCheck.put(fileName.getName(), check);

    }

    public Boolean checkUUID(CharSequence uuid) {

        Pattern pattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f‌​]{4}-[0-9a-f]{12}$"); //Übersetzt den regulären Ausdruck in ein Pattern-Objekt (regex)
        Matcher matcher = pattern.matcher(uuid); //Liefert ein Matcher-Objekt, das prüft (String ist schon eine CharSequence (input)

        return matcher.matches(); //true oder false
    }
}
