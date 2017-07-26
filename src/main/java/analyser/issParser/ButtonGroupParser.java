package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.buttons.ButtonGroup.json
 */
public class ButtonGroupParser implements JsonFileParser {

    private static ButtonGroupParser instance = null;

    private ButtonGroupParser() {
    }

    public static ButtonGroupParser getInstance() {
        if (instance == null) {
            instance = new ButtonGroupParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
