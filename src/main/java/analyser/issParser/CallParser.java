package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.Call.json
 */
public class CallParser implements JsonFileParser {

    private static CallParser instance = null;

    private CallParser() {
    }

    public static CallParser getInstance() {
        if (instance == null) {
            instance = new CallParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
