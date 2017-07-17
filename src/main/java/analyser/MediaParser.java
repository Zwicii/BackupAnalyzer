package analyser;

import interfaces.JsonFileParser;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by victoria on 17.07.17.
 */
public class MediaParser implements JsonFileParser {

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
        JSONObject obj = new JSONObject(filePath);
        int j = 0;
        int k = 0;
//        String deviceDescription = obj.getJSONObject("deviceDescription").getString("applicationName");
//        Main.logger.info("deviceDescription: " + deviceDescription);


        JSONArray arr = obj.getJSONArray("entities");
        for (int i = 0; i < arr.length(); i++) {

            JSONObject objMediaCategory = arr.getJSONObject(i).getJSONObject("mediaCategory");
            String idmediaCategory = objMediaCategory.getString("id");
            Main.logger.info(i + ": mediaCategory-id: " + idmediaCategory);

            String namemediaCategory = objMediaCategory.getString("name");
            Main.logger.info(i + ": mediaCategory-name: " + namemediaCategory);

            String id = arr.getJSONObject(i).getString("id");
            Main.logger.info(i + ": entities-id: " + id);

            String displayName = arr.getJSONObject(i).getString("displayName");
            Main.logger.info(i + ": entities-displayName: " + displayName);

            Store.storeDataMediaC(j, idmediaCategory);
            j++;
            Store.storeDataMediaC(j, namemediaCategory);
            j++;
            Store.storeDataMediaS(k, id);
            k++;

        }
    }
}

