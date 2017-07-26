package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.audio.AudioConfiguration.json
 */
public class AudioConfigurationParser implements JsonFileParser {

    private static AudioConfigurationParser instance = null;

    private AudioConfigurationParser() {
    }

    public static AudioConfigurationParser getInstance() {
        if (instance == null) {
            instance = new AudioConfigurationParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
