package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.PlayAudioRemote.json
 */
public class PlayAudioRemoteParser implements JsonFileParser {

    private static PlayAudioRemoteParser instance = null;

    private PlayAudioRemoteParser() {
    }

    public static PlayAudioRemoteParser getInstance() {
        if (instance == null) {
            instance = new PlayAudioRemoteParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
