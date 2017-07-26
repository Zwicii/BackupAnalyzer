package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.config.Device.json
 */
public class DeviceParser implements JsonFileParser {

    private static DeviceParser instance = null;

    private DeviceParser() {
    }

    public DeviceParser getInstance() {
        if (instance == null) {
            instance = new DeviceParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
