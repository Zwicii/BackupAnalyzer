package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.AnswerCall.json
 */
public class AnswerCallParser implements JsonFileParser {

    private static AnswerCallParser instance = null;

    private AnswerCallParser() {
    }

    public static AnswerCallParser getInstance() {
        if (instance == null) {
            instance = new AnswerCallParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
