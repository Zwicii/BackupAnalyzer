package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.DeviceSettings.json
 */
public class DeviceSettingsParser implements JsonFileParser {

    private static DeviceSettingsParser instance = null;

    private DeviceSettingsParser() {
    }

    public static DeviceSettingsParser getInstance() {
        if (instance == null) {
            instance = new DeviceSettingsParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
