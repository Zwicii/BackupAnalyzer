package analyser;

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

public class JsonFileSanityCheckParser implements JsonFileParser {

    public static HashMap<String, Boolean> hashMapCompareJsonSanityCheck = new HashMap<String, Boolean>();
    public static HashMap<String, HashMap> hashMapCompareDeviceDescription = new HashMap<String, HashMap>();

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
            if(map.containsValue("_type")){
                if(! map.get("_type").equals("com.commend.iss.DataContainer")){
                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "_type does not exist");
                    check = false;
                }
            }

            //Kontrolliert ob bei deviceDescription alles passt
            if(map.containsValue("deviceDescription")){

                if(map.get("deviceDescription") instanceof LinkedHashMap){

                    //Alle Hasmaps von deviceDescription
                    LinkedHashMap<Object, Object> linkedHashMapDeviceDescription= (LinkedHashMap<Object, Object>) ((LinkedHashMap) map.get("deviceDescription")).values();
                    hashMapCompareDeviceDescription.put(fileName.getName(), linkedHashMapDeviceDescription);

                    //applicationName
                    if((linkedHashMapDeviceDescription.containsValue("applicationName"))){

                        if(linkedHashMapDeviceDescription.get("applicationName") == null || linkedHashMapDeviceDescription.get("applicationName") instanceof String == false){
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationName is null or not a String");
                        }
                    }
                    else{
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationName does not exist");
                    }

                    //applicationVersion
                    if((linkedHashMapDeviceDescription.containsValue("applicationVersion"))){

                        if(linkedHashMapDeviceDescription.get("applicationVersion") == null || linkedHashMapDeviceDescription.get("applicationVersion") instanceof String == false){
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationVersion is null or not a String");
                        }
                    }
                    else{
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: applicationVersion does not exist");
                    }

                    //imageVersion
                    if((linkedHashMapDeviceDescription.containsValue("imageVersion"))){

                        if(linkedHashMapDeviceDescription.get("imageVersion") == null || linkedHashMapDeviceDescription.get("imageVersion") instanceof String == false){
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: imageVersion is null or not a String");
                        }
                    }
                    else{
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: imageVersion does not exist");
                    }

                    //os
                    if((linkedHashMapDeviceDescription.containsValue("os"))){

                        if(linkedHashMapDeviceDescription.get("os") == null || linkedHashMapDeviceDescription.get("os") instanceof String == false){
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: os is null or not a String");
                        }
                    }
                    else{
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: os does not exist");
                    }

                    //javaVersion
                    if((linkedHashMapDeviceDescription.containsValue("javaVersion"))){

                        if(linkedHashMapDeviceDescription.get("javaVersion") == null || linkedHashMapDeviceDescription.get("javaVersion") instanceof String == false){
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: javaVersion is null or not a String");
                        }
                    }
                    else{
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: javaVersion does not exist");
                    }

                    //deviceType
                    if((linkedHashMapDeviceDescription.containsValue("deviceType"))){

                        if(linkedHashMapDeviceDescription.get("deviceType") == null || linkedHashMapDeviceDescription.get("deviceType") instanceof String == false){
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: deviceType is null or not a String");
                        }
                    }
                    else{
                        check = false;
                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription: deviceType does not exist");
                    }
                }
            }
            else{
                check = false;
                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "deviceDescription does not exist");
            }

            //Kontrolliert ob bei entities alles passt
            for(Map.Entry e : map.entrySet())
            if (e.getKey().equals("entities")) {

                if(e.getValue() != null){
                    if (e.getValue() instanceof ArrayList) {
                        ArrayList arrayList = (ArrayList) e.getValue(); // entities value: 3 LinkedHashMaps

                        //TODO[OBV]: Funktioniert nicht
                        for (Object linkedHashMapEntities : arrayList) {
                            if (linkedHashMapEntities instanceof LinkedHashMap) {

                                // id
                                if (((LinkedHashMap) linkedHashMapEntities).containsValue("id")) {
                                    //TODO[OBV]: Code für check ob UUID gültig ist
                                } else {
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "entities: id does not exsist");
                                }

                                //_type
                                if (((LinkedHashMap) linkedHashMapEntities).containsValue("_type")) {
                                    if (!((LinkedHashMap) linkedHashMapEntities).get("_type").equals(fileName.getName())) {
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "entities: id is not equal to the filename");
                                    }

                                } else {
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "entities: _type does not exsist");
                                }
                            }
                        }
                    }
                }

            }

            else {
                check = false;
                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "entities does not exist");
            }

            //Speichert check in hashMapCompareJsonSanityCheck
            hashMapCompareJsonSanityCheck.put(fileName.getName(), check);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
