package analyser.issParser;

import analyser.Store;
import implementaions.BackupFileParserImpl;
import interfaces.JsonFileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * parse com.commend.iss.account.Account.json
 */
public class ActionSetParser implements JsonFileParser {

    private static ActionSetParser instance = null;

    private ActionSetParser() {
    }

    public static ActionSetParser getInstance() {
        if (instance == null) {
            instance = new ActionSetParser();
        }
        return instance;
    }

    private ArrayList<String> arrayListDisplayNames = new ArrayList<>();

    @Override
    public void parse(String filePath) {

        File fileName = new File(filePath);
        Map<Object, Object> map = (Map<Object, Object>) Store.hashMapOriginalData.get("com.commend.iss.activity.ActionSet.json");
        Boolean check = true;

        //Kontrolliert ob bei entities alles passt
        if (map.containsKey("entities")) {

            //Arraylist von Entities
            if (map.get("entities") instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                if (arrayList.size() != 0) {

                    for (int i = 0; i < arrayList.size(); i++) {
                        LinkedHashMap<Object, Object> a = (LinkedHashMap<Object, Object>) arrayList.get(i);

                        //displayName: ob existiert
                        if (!a.containsKey("displayName")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" + i + "]: displayName doesn not exist");
                        }
                        if (a.get("displayName") == null) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" + i + "]: displayName is null");
                        }

                        //system: ob existiert
                        if (!a.containsKey("system")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(fileName.getName(), "Entiti[" + i + "]: system doesn not exist");
                        }
                    }
                    //ob alle DisplayNames unterschiedlich sind
                    if(!checkDisplayNames(fileName)){
                        check = false;
                    }
                }
            }
        }
        Store.hashMapCheckResults.put(fileName.getName(), check);
    }

    public Boolean checkDisplayNames(File filename) {

        String value = null;

        for(String displayName: arrayListDisplayNames){

            value = displayName;

            for(int i = 0; i < arrayListDisplayNames.size(); i++){
                if(value.equals(arrayListDisplayNames.get(i))){
                    BackupFileParserImpl.hashMapErrors.put(filename.getName(), "Entitie[" + i + "]: displayName is equal with the displayName of Entitie[" + arrayListDisplayNames.indexOf(displayName) + "]");
                    return false;
                }
            }
        }return true;
    }
}
