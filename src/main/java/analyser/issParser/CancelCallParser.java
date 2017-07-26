package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.CancelCall.json
 */
public class CancelCallParser implements JsonFileParser {

    private static CancelCallParser instance = null;

    private CancelCallParser() {
    }

    public static CancelCallParser getInstance() {
        if (instance == null) {
            instance = new CancelCallParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
