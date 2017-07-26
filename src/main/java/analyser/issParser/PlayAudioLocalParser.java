package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.PlayAudioLocal.json
 */
public class PlayAudioLocalParser implements JsonFileParser {

    private static PlayAudioLocalParser instance = null;

    private PlayAudioLocalParser() {
    }

    public static PlayAudioLocalParser getInstance() {
        if (instance == null) {
            instance = new PlayAudioLocalParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
