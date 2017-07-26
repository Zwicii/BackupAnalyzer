package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.led.LedSetting.json
 */
public class LedSettingParser implements JsonFileParser {

    private static LedSettingParser instance = null;

    private LedSettingParser() {
    }

    public static LedSettingParser getInstance() {
        if (instance == null) {
            instance = new LedSettingParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
