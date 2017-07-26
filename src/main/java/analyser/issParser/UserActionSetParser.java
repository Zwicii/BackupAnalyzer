package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.activity.UserActionSet.json
 */
public class UserActionSetParser implements JsonFileParser {

    private static UserActionSetParser instance = null;

    private UserActionSetParser() {
    }

    public static UserActionSetParser getInstance() {
        if (instance == null) {
            instance = new UserActionSetParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
