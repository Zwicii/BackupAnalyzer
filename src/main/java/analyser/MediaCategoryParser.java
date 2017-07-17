package analyser;

import interfaces.JsonFileParser;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by victoria on 17.07.17.
 */
public class MediaCategoryParser implements JsonFileParser {

    private static MediaCategoryParser instance = null;

    private MediaCategoryParser() {
    }

    public static MediaCategoryParser getInstance() {
        if (instance == null) {
            instance = new MediaCategoryParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        JSONObject obj = new JSONObject(filePath);
        int j = 0;

        JSONArray arr = obj.getJSONArray("entities");
        for (int i = 0; i < arr.length(); i++) {

            String id = arr.getJSONObject(i).getString("id");
            Main.logger.info(i + ": entities-id: " + id);
            String name = arr.getJSONObject(i).getString("name");
            Main.logger.info(i + ": entities-name: " + name);

            Store.storeDataMediaCategory(j, id);
            j++;
            Store.storeDataMediaCategory(j, name);
            j++;
        }
    }
}
