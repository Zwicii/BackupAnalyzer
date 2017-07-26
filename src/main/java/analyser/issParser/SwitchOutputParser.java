package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.hardware.output.SwitchOutput.json
 */
public class SwitchOutputParser implements JsonFileParser {

    private static SwitchOutputParser instance = null;

    private SwitchOutputParser() {
    }

    public static SwitchOutputParser getInstance() {
        if (instance == null) {
            instance = new SwitchOutputParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
