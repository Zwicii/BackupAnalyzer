package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.config.Led.json
 */
public class LedParser implements JsonFileParser {

    private static LedParser instance = null;

    private LedParser() {
    }

    public LedParser getInstance() {
        if (instance == null) {
            instance = new LedParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
