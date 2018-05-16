package analyser.platformParser;

import analyser.Store;
import implementaions.BackupFileParserImpl;
import interfaces.JsonFileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * parse com.commend.platform.db.MigrationScript.json
 */
public class MigrationScriptParser implements JsonFileParser {

    private static MigrationScriptParser instance = null;

    private MigrationScriptParser() {
    }

    public static MigrationScriptParser getInstance() {
        if (instance == null) {
            instance = new MigrationScriptParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        File fileName = new File(filePath);
        Map<Object, Object> map = (Map<Object, Object>) Store.hashMapOriginalData.get("com.commend.platform.db.MigrationScript.json");
        Boolean check = true;

        //Kontrolliert ob bei entities alles passt
        if (map.containsKey("entities")) {

            //Arraylist von Entities
            if (map.get("entities") instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) map.get("entities"); // entities value: 3 LinkedHashMaps

                if (arrayList.size() != 0) {

                    for (int i = 0; i < arrayList.size(); i++) {
                        LinkedHashMap<Object, Object> a = (LinkedHashMap<Object, Object>) arrayList.get(i);

                        //name: ob existiert und nicht null ist
                        if (!a.containsKey("name")) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: name does not exist");
                            BackupFileParserImpl.errorNumber++;
                        }
                        if (a.get("name") == null) {
                            check = false;
                            BackupFileParserImpl.hashMapErrors.put(BackupFileParserImpl.errorNumber + " " + fileName.getName(), "Entity[" + i + "]: name is null");
                            BackupFileParserImpl.errorNumber++;
                        }
                    }
                }
            }
        }
        Store.hashMapCheckResults.put(fileName.getName(), check);
    }
}
