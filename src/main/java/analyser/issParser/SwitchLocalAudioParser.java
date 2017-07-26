package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.SwitchLocalAudio.json
 */
public class SwitchLocalAudioParser implements JsonFileParser {

    private static SwitchLocalAudioParser instance = null;

    private SwitchLocalAudioParser() {
    }

    public static SwitchLocalAudioParser getInstance() {
        if (instance == null) {
            instance = new SwitchLocalAudioParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
