package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.config.MultiStateInput.json
 */
public class MultiStateInputParser implements JsonFileParser {

    private static MultiStateInputParser instance = null;

    private MultiStateInputParser() {
    }

    public MultiStateInputParser getInstance() {
        if (instance == null) {
            instance = new MultiStateInputParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
