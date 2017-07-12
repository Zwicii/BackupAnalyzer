import java.util.HashMap;
import java.util.Map;

/**
 * Created by victoria on 12.07.17.
 */
public class Compare {

    public static HashMap<String, HashMap> hashMapCheck = new HashMap<>();
    public static HashMap<String, Boolean> hashMapMC = new HashMap<>();
    public static HashMap<String, Boolean> hashMapMF = new HashMap<>();


    public static void compare(HashMap<Integer, String> hashMap1, HashMap<Integer, String> hashMap2, HashMap<String, Boolean> hashMap3) {


        for (int i = 0; i < hashMap1.size(); i++) {

            boolean found = false;

            for (int j = 0; j < hashMap2.size(); j++) {
                if (hashMap1.get(i).equals(hashMap2.get(j))) {
                    System.out.println(hashMap1.get(i) + ": OK");
                    found = true;
                    hashMap3.put(hashMap1.get(i), found);

                    break;
                }
            }

            if (found == false) {
                System.out.println(hashMap1.get(i) + ": MediaCategory doest't exist");
//                hashMapCheck.put(k,hashMap1.get(i) +": "+ found);

            }
        }
    }

    public static void check() {

        hashMapCheck.put("com.commend.platform.mediastore.MediaCategory", hashMapMC);
        hashMapCheck.put("com.commend.platform.mediastore.Media", hashMapMF);


        for (HashMap.Entry<String, HashMap> entry : hashMapCheck.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("\nKey: " + key);


            if (key.equals("com.commend.platform.mediastore.MediaCategory")) {
                for (HashMap.Entry<String, Boolean> entryMC : hashMapMC.entrySet()) {

                    String keyMC = entryMC.getKey();
                    Boolean valueMC = entryMC.getValue();
                    System.out.println(keyMC + ": " + valueMC);
                }
            }

            if (key.equals("com.commend.platform.mediastore.Media")) {
                for (HashMap.Entry<String, Boolean> entryMF : hashMapMF.entrySet()) {

                    String keyMF = entryMF.getKey();
                    Boolean valueMF = entryMF.getValue();
                    System.out.println(keyMF + ": " + valueMF);
                }
            }

        }
    }
}
