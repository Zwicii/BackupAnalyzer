package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.config.Input.json
 */
public class InputParser implements JsonFileParser {

    private static InputParser instance = null;

    private InputParser() {
    }

    public InputParser getInstance() {
        if (instance == null) {
            instance = new InputParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
