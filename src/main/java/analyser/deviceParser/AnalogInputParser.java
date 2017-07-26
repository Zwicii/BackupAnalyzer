package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.config.AnalogInput.json
 */
public class AnalogInputParser implements JsonFileParser {

    private static AnalogInputParser instance = null;

    private AnalogInputParser() {
    }

    public static AnalogInputParser getInstance() {
        if (instance == null) {
            instance = new AnalogInputParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
