package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.buttons.HardwareButton.json
 */
public class HardwareButtonParser implements JsonFileParser {

    private static HardwareButtonParser instance = null;

    private HardwareButtonParser() {
    }

    public static HardwareButtonParser getInstance() {
        if (instance == null) {
            instance = new HardwareButtonParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
