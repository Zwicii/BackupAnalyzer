package analyser.platformParser;

import analyser.Main;
import analyser.MediaStoreParser;
import analyser.Store;
import implementaions.BackupFileParserImpl;
import interfaces.JsonFileParser;

import java.io.File;
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
    public static HashMap<String, Boolean> hashMapCompareMedia = new HashMap<>();
    private ArrayList<String> arrayListDisplayNames = new ArrayList<>();

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
        Map<Object, Object> map = (Map<Object, Object>) Store.hashMapOriginalData.get("com.commend.platform.mediastore.Media.json");

        Main.logger.info(map.get("entities"));

        Store.storeOriginalData(fileName.getName(), map);
        compareMedia();
        Store.storeCheckResults(fileName.getName(), hashMapCompareMedia);

        //Neu
        Boolean check = true;

        //Kontrolliert ob bei entities alles passt
        if (map.containsKey("entities")) {

            //Arraylist von Entities
            if (map.get("entities") instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                if (arrayList.size() != 0) {

                    for (int i = 0; i < arrayList.size(); i++) {
                        LinkedHashMap<Object, Object> e = (LinkedHashMap<Object, Object>) arrayList.get(i);

                        //displayName: ob existiert und Wert nicht null ist
                        if (e.containsKey("displayName")) {
                            if (e.get("displayName") == null) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: displayName is null");
                                BackupFileParserImpl.errorNumber++;
                            } else{
                                if (!arrayListDisplayNames.contains(e.get("displayName"))) { //TODO[OBV]: speichert irgendwie displaynames öfter hinein
                                    arrayListDisplayNames.add((String) e.get("displayName"));
                                }
                            }

                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: displayName does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //mimeType: ob existiert
                        if (!e.containsKey("mimeType")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: mimeType does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //lastModified: ob existiert
                        if (!e.containsKey("lastModified")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: lastModified does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //category: ob existiert
                        if (e.containsKey("category")) {
                            if(!MediaCategoryParser.arrayListNames.contains(e.get("category"))){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: category has not the same value as name in MediaCategory");
                                BackupFileParserImpl.errorNumber++;
                            }
                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: category does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //size: ob existiert
                        if (!e.containsKey("size")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: size does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //mediaCategory: ob existiert
                        if (e.containsKey("mediaCategory")) {
                            if (!MediaCategoryParser.arrayListMediaCategories.contains(e.get("mediaCategory"))) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: mediaCategory does not exist in com.commend.platform.mediastore.MediaCategory");
                                BackupFileParserImpl.errorNumber++;
                            }
                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "]: mediaCategory does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }

                        //ob id gleich ist wie name von datei in mediastore
                        if (e.containsKey("id")) {

                            if(!MediaStoreParser.arrayListMediaStore.contains(e.get("id"))){
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity [" + i + "] in the directory mediastore is no file with the same name as the id: " + e.get("id"));
                                BackupFileParserImpl.errorNumber++;
                            }
                        }
                    }

                    if(!checkDisplayNames(fileName)){
                        check = false;
                    }
                }
            }
        }
        Store.hashMapCheckResults.put(fileName.getName(), check);
    }

    public Boolean checkDisplayNames(File filename) {

        boolean check = true;

        for(String displayName: arrayListDisplayNames){

            for(int i = 0; i< arrayListDisplayNames.size(); i++){
                if (displayName.equals(arrayListDisplayNames.get(i))) {
                    if (i != arrayListDisplayNames.indexOf(displayName)) {
                        BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + filename.getName(), "Entity[" + i + "]: displayName is equal with the displayName of Entity[" + arrayListDisplayNames.indexOf(displayName) + "]");
                        BackupFileParserImpl.errorNumber++;
                        check = false;
                    }

                }
            }
        }
        return check;
    }


    public static void compareMedia() {

        ArrayList<Object> arrayListMedia = Store.getMedia();
        ArrayList<Object> arrayListMediaCategory = Store.getMediaCategory();

        for(Object o : arrayListMedia){
            boolean found = false;

            if(arrayListMediaCategory.contains(o) || MediaStoreParser.arrayListMediaStore.contains(o)){
                found = true;

            }
            else {
                Main.logger.error(o + ": Data in MediaStore or MediaCategory doesn't exist");
                BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + o.toString(), "Data in MediaStore or MediaCategory does not exist");
                BackupFileParserImpl.errorNumber++;
            }
            hashMapCompareMedia.put(o.toString(), found);
        }
    }
}
