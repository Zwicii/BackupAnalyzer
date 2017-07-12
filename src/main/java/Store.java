import java.util.HashMap;

/**
 * Created by victoria on 11.07.17.
 */
public class Store {

    public static HashMap<Integer, String> hashMap = new HashMap<Integer, String>();


    public static void storeData(Integer key, String value){

        hashMap.put(key, value);
        System.out.println("Key: " + key + " " + "Value: " + hashMap.get(key));

    }
}
