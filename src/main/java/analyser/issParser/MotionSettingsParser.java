package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.video.MotionSettings.json
 */
public class MotionSettingsParser implements JsonFileParser {

    private static MotionSettingsParser instance = null;

    private MotionSettingsParser() {
    }

    public static MotionSettingsParser getInstance() {
        if (instance == null) {
            instance = new MotionSettingsParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
