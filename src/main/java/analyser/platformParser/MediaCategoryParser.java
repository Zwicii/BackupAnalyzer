package analyser.platformParser;

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
    public static HashMap<String, Integer> hashMapStorage = new HashMap<>();
    public int j =0;


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
                                BackupFileParserImpl.hashMapErrors.put(j+fileName.getName(), "Entity [" + i + "]: name is null");
                                j++;
                            } else {
                                if (!arrayListNames.contains(e.get("name"))) {
                                    arrayListNames.add((String) e.get("name"));
                                }
                            }
                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+fileName.getName(), "Entity [" + i + "]: name does not exist");
                            j++;
                        }

                        //system: ob existiert
                        if (!e.containsKey("system")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+fileName.getName(), "Entity [" + i + "]: system does not exist");
                            j++;
                        }

                        //userMayAdd: ob existiert
                        if (!e.containsKey("userMayAdd")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+fileName.getName(), "Entity [" + i + "]: userMayAdd does not exist");
                            j++;
                        }

                        //userMayEdit: ob existiert
                        if (!e.containsKey("userMayEdit")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: userMayEdit does not exist");
                            j++;
                        }

                        //userMayDelete: ob existiert
                        if (!e.containsKey("userMayDelete")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: userMayDelete does not exist");
                            j++;
                        }

                        //sorting: ob existiert
                        if (e.containsKey("sorting")) {
                            if (!arrayListSortings.contains(e.get("sorting"))) {
                                arrayListSortings.add((Integer) e.get("sorting"));
                            }

                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: sorting does not exist");
                            j++;
                        }

                        //maxSpace: ob existiert und ob useSpace kleiner als maxSpace ist
                        if (e.containsKey("maxSpace")) {
                            if (e.containsKey("usedSpace")) {
                                if (e.get("maxSpace") != null && e.get("usedSpace") != null) {
                                    if ((Integer) e.get("usedSpace") > (Integer) e.get("maxSpace")) {
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: usedSpace has e higher value than maxSpace");
                                        j++;
                                    }
                                }

                                if (e.get("name").equals("sounds")) {
                                    if ((Integer) e.get("usedSpace") != MediaStoreParser.spaceSounds) {
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: usedSpace is not equal with the value of the directory sounds ");
                                        j++;
                                    }

                                    if((Integer) e.get("maxSpace") < (Integer) e.get("usedSpace")){
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: usedSpace has a higher value than maxSpace ");
                                        j++;
                                    }

                                    hashMapStorage.put(e.get("name")+ "_maxSpace", (Integer) e.get("maxSpace"));
                                    hashMapStorage.put(e.get("name")+ "_usedSpace", (Integer) e.get("usedSpace"));
                                }

                                if (e.get("name").equals("snapshots")) {
                                    if ((Integer) e.get("usedSpace") != MediaStoreParser.spaceSnapshots) {
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: usedSpace is not equal with the value of the directory snapshots ");
                                    j++;
                                    }

                                    if((Integer) e.get("maxSpace") < (Integer) e.get("usedSpace")){
                                        check = false;
                                        BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: usedSpace has a higher value than maxSpace ");
                                        j++;
                                    }

                                    hashMapStorage.put(e.get("name")+ "_maxSpace", (Integer) e.get("maxSpace"));
                                    hashMapStorage.put(e.get("name")+ "_usedSpace", (Integer) e.get("usedSpace"));
                                }
                            }
                        } else {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: maxSpace does not exist");
                            j++;
                        }

                        //usedSpace: ob existiert
                        if (!e.containsKey("usedSpace")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: usedSpace does not exist");
                            j++;
                        }

                        //circularBuffer: ob existiert
                        if (!e.containsKey("circularBuffer")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(j+ " " +fileName.getName(), "Entity [" + i + "]: circularBuffer does not exist");
                            j++;
                        }

                        //alle Möglichen MediaCategories (avatar, sounds, snapshots) in arrayListMediaCategory speichern
                        arrayListMediaCategories.add(e);


                    }
                    //ob alle names unterschiedlich sind
                    if (!checkNames(fileName)) {
                        check = false;
                    }

                    //ob alle sortings unterschiedlich sind
                    if (!checkSortings(fileName)) {
                        check = false;
                    }
                }
            }
            Store.hashMapCheckResults.put(fileName.getName(), check);
        }
    }

    public Boolean checkNames(File filename) {

        Boolean check = true;

        for (String name : arrayListNames) {


            for (int i = 0; i < arrayListNames.size(); i++) {
                if (name.equals(arrayListNames.get(i))) {
                    if (i != arrayListNames.indexOf(name)) {
                        BackupFileParserImpl.hashMapErrors.put(j+ " " +filename.getName(), "Entity[" + i + "]: name is equal with the name of Entity[" + arrayListNames.indexOf(name) + "]");
                        j++;
                        check = false;
                    }
                }
            }
        }
        return check;
    }

    public Boolean checkSortings(File filename) {

        boolean check = true;

        for (int sorting : arrayListSortings) {
            for (int i = 0; i < arrayListSortings.size(); i++) {
                if (sorting == arrayListSortings.get(i)) {
                    if (i != arrayListSortings.indexOf(sorting)) {
                        BackupFileParserImpl.hashMapErrors.put(j+ " " +filename.getName(), "Entity[" + i + "]: sorting is equal with the sorting of Entity[" + arrayListNames.indexOf(sorting) + "]");
                        j++;
                        check = false;
                    }
                }
            }
        }
        return check;
    }
}
