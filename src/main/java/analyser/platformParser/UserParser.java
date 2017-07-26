package analyser.platformParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.platform.security.User.json
 */
public class UserParser implements JsonFileParser {

    private static UserParser instance = null;

    private UserParser() {
    }

    public static UserParser getInstance() {
        if (instance == null) {
            instance = new UserParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
