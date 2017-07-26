package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.account.Account.json
 */
public class ActionSetParser implements JsonFileParser {

    private static ActionSetParser instance = null;

    private ActionSetParser() {
    }

    public static ActionSetParser getInstance() {
        if (instance == null) {
            instance = new ActionSetParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
