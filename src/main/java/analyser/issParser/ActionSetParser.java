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
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: displayName does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }
                        else if (a.get("displayName") == null) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: displayName is null");
                            BackupFileParserImpl.errorNumber++;
                        }
                        else{
                            arrayListDisplayNames.add(a.get("displayName").toString());
                        }

                        //system: ob existiert
                        if (!a.containsKey("system")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: system does not exist");
                            BackupFileParserImpl.errorNumber++;
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

        for(int i = 0; i < arrayListDisplayNames.size(); i++){

            String value = arrayListDisplayNames.get(i);

            for(int l = 0; l< arrayListDisplayNames.size(); l++){
                if(l != i && value.equals(arrayListDisplayNames.get(l))){
                    BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + filename.getName(), "Entity[" + i + "]: displayName is equal with the displayName of Entity[" + l + "]");
                    BackupFileParserImpl.errorNumber++;
                    return false;
                }
            }
        }return true;
    }
}
