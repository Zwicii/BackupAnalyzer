package analyser.platformParser;

import analyser.MediaStoreParser;
import analyser.Store;
import impl.BackupFileParserImpl;
import interfaces.JsonFileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public static ArrayList<String> arrayListNames = new ArrayList<>();
    private ArrayList<Integer> arrayListSortings = new ArrayList<>();
    public static ArrayList<HashMap<Object, Object>> arrayListMediaCategories = new ArrayList<>();


    @Override
    public void parse(String filePath) {

        File fileName = new File(filePath);
        Boolean check = true;
        Map<Object, Object> map = (Map<Object, Object>) Store.hashMapOriginalData.get("com.commend.platform.mediastore.MediaCategory.json");

        //Kontrolliert ob bei entities alles passt
        if (map.containsKey("entities")) {

            //Arraylist von Entities
            if (map.get("entities") instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                if (arrayList.size() != 0) {

                    for (int i = 0; i < arrayList.size(); i++) {
                        LinkedHashMap<Object, Object> e = (LinkedHashMap<Object, Object>) arrayList.get(i); // e => entity

                        //name: ob existiert und Wert nicht null ist
                        //Wenn alles ok dann wir value von name in arrayListNames gespeichert
                        if (e.containsKey("name")) {
                            if (e.get("name") == null) {
                                check = false;
                                BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: name is null");
                            }else{
                                arrayListNames.add((String)e.get("name"));
                            }
                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: name does not exist");
                        }

                        //system: ob existiert
                        if (!e.containsKey("system")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: system does not exist");
                        }

                        //userMayAdd: ob existiert
                        if (!e.containsKey("userMayAdd")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: userMayAdd does not exist");
                        }

                        //userMayEdit: ob existiert
                        if (!e.containsKey("userMayEdit")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: userMayEdit does not exist");
                        }

                        //userMayDelete: ob existiert
                        if (!e.containsKey("userMayDelete")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: userMayDelete does not exist");
                        }

                        //sorting: ob existiert
                        if (e.containsKey("sorting")) {
                            arrayListSortings.add((Integer)e.get("sorting"));
                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: sorting does not exist");
                        }

                        //maxSpace: ob existiert und ob useSpace kleiner als maxSpace ist
                        if (e.containsKey("maxSpace")) {
                            if(e.containsKey("usedSpace")){
                                if(e.get("maxSpace") != null && e.get("usedSpace") != null){
                                    if((Integer) e.get("usedSpace") > (Integer) e.get("maxSpace")){
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: usedSpace has e higher value than maxSpace");
                                    }
                                }

                                if(e.get("name") == "sounds"){
                                    if((long)e.get("usedSpace") != MediaStoreParser.spaceSounds){
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: usedSpace is not equal with the value of the directory sounds ");
                                    }
                                }

                                if(e.get("name") == "snapshots"){
                                    if((long)e.get("usedSpace") != MediaStoreParser.spaceSnapshots){
                                        //check = false;
                                        //BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: usedSpace is not equal with the value of the directory snapshots ");
                                    }
                                }
                            }
                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: maxSpace does not exist");
                        }

                        //usedSpace: ob existiert
                        if (!e.containsKey("usedSpace")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: usedSpace does not exist");
                        }

                        //circularBuffer: ob existiert
                        if (!e.containsKey("circularBuffer")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entitie [" + i + "]: circularBuffer does not exist");
                        }

                        //alle Möglichen MediaCategories (avatar, sounds, snapshots) in arrayListMediaCategory speichern
                        arrayListMediaCategories.add(e);


                    }
                    //ob alle names unterschiedlich sind
                    if(!checkNames(fileName)){
                        check = false;
                    }

                    //ob alle sortings unterschiedlich sind
                    if(!checkSortings(fileName)){
                        check = false;
                    }
                }
            }
            Store.hashMapCheckResults.put(fileName.getName(), check);
        }
    }

    public Boolean checkNames(File filename) {

        String value = null;

        for(String name : arrayListNames){

            value = name;

            for(int i = 0; i < arrayListNames.size(); i++){
                if(value.equals(arrayListNames.get(i))){
                    BackupFileParserImpl.hashMapErrors.put(filename.getName(), "Entitie[" + i + "]: name is equal with the name of Entitie[" + arrayListNames.indexOf(name) + "]");
                    return false;
                }
            }
        }return true;
    }

    public Boolean checkSortings(File filename) {

        int value = 0;

        for(int sorting : arrayListSortings){

            value = sorting;

            for(int i = 0; i < arrayListSortings.size(); i++){
                if(value == arrayListSortings.get(i)){
                    BackupFileParserImpl.hashMapErrors.put(filename.getName(), "Entitie[" + i + "]: sorting is equal with the sorting of Entitie[" + arrayListNames.indexOf(sorting) + "]");
                    return false;
                }
            }
        }return true;
    }
}
