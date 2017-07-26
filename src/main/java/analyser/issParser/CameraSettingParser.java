package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.camera.CameraSetting.json
 */
public class CameraSettingParser implements JsonFileParser {

    private static CameraSettingParser instance = null;

    private CameraSettingParser() {
    }

    public static CameraSettingParser getInstance() {
        if (instance == null) {
            instance = new CameraSettingParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
