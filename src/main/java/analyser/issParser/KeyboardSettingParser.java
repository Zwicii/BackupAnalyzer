package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.keyboard.KeyboardSetting.json
 */
public class KeyboardSettingParser implements JsonFileParser {

    private static KeyboardSettingParser instance = null;

    private KeyboardSettingParser() {
    }

    public static KeyboardSettingParser getInstance() {
        if (instance == null) {
            instance = new KeyboardSettingParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
