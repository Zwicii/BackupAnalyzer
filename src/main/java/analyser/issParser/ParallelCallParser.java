package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.ParallelCall.json
 */
public class ParallelCallParser implements JsonFileParser {

    private static ParallelCallParser instance = null;

    private ParallelCallParser() {
    }

    public static ParallelCallParser getInstance() {
        if (instance == null) {
            instance = new ParallelCallParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
