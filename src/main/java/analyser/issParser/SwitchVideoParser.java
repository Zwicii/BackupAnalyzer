package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.SwitchVideo.json
 */
public class SwitchVideoParser implements JsonFileParser {

    private static SwitchVideoParser instance = null;

    private SwitchVideoParser() {
    }

    public static SwitchVideoParser getInstance() {
        if (instance == null) {
            instance = new SwitchVideoParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
