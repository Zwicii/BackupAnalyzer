package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.config.KeyboardButton.json
 */
public class KeyboardButtonParser implements JsonFileParser {

    private static KeyboardButtonParser instance = null;

    private KeyboardButtonParser() {
    }

    public KeyboardButtonParser getInstance() {
        if (instance == null) {
            instance = new KeyboardButtonParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
