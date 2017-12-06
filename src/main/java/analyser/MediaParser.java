package analyser;

import impl.BackupFileParserImpl;
import interfaces.JsonFileParser;
import org.codehaus.jackson.JsonGenerationException;
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
 * parse com.commend.platform.mediastore.Media.json
 */
public class MediaParser implements JsonFileParser {

    // TODO [STC]: Diese Listen entfernen (nicht nötig)
    // Anstattdessen in compareMedia(...) direkt die entsprechenden Sub-Listen aus Store.hashMapOriginalData verwenden;
    private static HashMap<String, Boolean> hashMapCompareMedia = new HashMap<>();
    private static String[] displayNames = new String[1000];

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

            //NEU
            Boolean check = true;

            //Kontrolliert ob bei entities alles passt
            if (map.containsKey("entities")) {

                //Arraylist von Entities
                if (map.get("entities") instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                    if (arrayList.size() != 0) {

                        for (int i = 0; i < arrayList.size(); i++) {
                            LinkedHashMap<Object, Object> a = (LinkedHashMap<Object, Object>) arrayList.get(i);

                            //displayName: ob existiert und Wert nicht null ist
                            if (!a.containsKey("displayName")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: displayName does not exist");
                            }
                            else{
                                if(a.get("displayName") == null){
                                    check = false;
                                    BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: displayName is null");
                                }
                                else if(!checkDisplayName(fileName)){
                                    check = false;
                                }

                            }

                            //mimeType: ob existiert
                            if (!a.containsKey("mimeType")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: mimeType does not exist");
                            }

                            //lastModified: ob existiert
                            if (!a.containsKey("lastModified")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: lastModified does not exist");
                            }

                            //category: ob existiert
                            if (!a.containsKey("category")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: category does not exist");
                            }

                            //size: ob existiert
                            if (!a.containsKey("size")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: size does not exist");
                            }

                            //mediaCategory: ob existiert
                            if (!a.containsKey("mediaCategory")) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: mediaCategory does not exist");
                            }
                            else{
                                //TODO[OBV]: MediaCategory speichern
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

    public Boolean checkDisplayName(File filename){

        String value = null;

        for (int k = 0; displayNames[k] != null; k ++ ) {
            value = displayNames[k];

            for(int i = 0; displayNames[i] != null; i++) {
                if (value.equals(displayNames[i])) {
                    BackupFileParserImpl.hashMapErrors.put(filename.getName(), "Entitie[" + i + "]: displayName is equal with the displayName of Entitie[" + k + "]");
                    return false;
                }
            }
        }
        return true;
    }



    public static void compareMedia() {

        String[] arrMedia = Store.getMedia(); //Data which will be compared from Media
        Object[] arrMediaCategory = Store.getMediaCategory(); //Data which will be compared from MediaCategory

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
                Main.logger.error(arrMedia[i] + ": MediaCategory or Data in MediaStore doesn't exist");
                hashMapCompareMedia.put(arrMedia[i], found);
                BackupFileParserImpl.hashMapErrors.put(arrMedia[i], "MediaCategory or Data in MediaStore does not exist");
            }
        }
    }
}
