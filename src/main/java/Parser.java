import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by victoria on 11.07.17.
 */
public class Parser {

    public static void parseBackupFile(String directoryName, String jsonData){

        File directory = new File(directoryName);

        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList){

//            System.out.println(file.getName());

            if(file.getName().endsWith(".Media.json")) {

                System.out.println("found Media");
                Parser.parseJsonFile(jsonData);
            }
        }
    }

    public static void parseJsonFile(String jsonData)
    {
        JSONObject obj = new JSONObject(jsonData);
        String deviceDescription = obj.getJSONObject("deviceDescription").getString("applicationName");
        System.out.println("deviceDescription: " + deviceDescription);


        JSONArray arr = obj.getJSONArray("entities");
        for (int i = 0; i < arr.length(); i++)
        {
            String id = arr.getJSONObject(i).getString("id");
            System.out.println(i + ". entities-id: " + id);
//            Store.storeData(id);

            String displayName = arr.getJSONObject(i).getString("displayName");
            System.out.println(i + ". entities-displayname: " + displayName);
            Store.storeData(i, id +" " + displayName);

        }


    }


}
