package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.video.VideoConfiguration.json
 */
public class VideoConfigurationParser implements JsonFileParser {

    private static VideoConfigurationParser instance = null;

    private VideoConfigurationParser() {
    }

    public static VideoConfigurationParser getInstance() {
        if (instance == null) {
            instance = new VideoConfigurationParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
