package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.buttons.VirtualButton.json
 */
public class VirtualButtonParser implements JsonFileParser {

    private static VirtualButtonParser instance = null;

    private VirtualButtonParser() {
    }

    public static VirtualButtonParser getInstance() {
        if (instance == null) {
            instance = new VirtualButtonParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
