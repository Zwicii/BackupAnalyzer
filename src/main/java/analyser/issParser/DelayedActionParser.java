package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.activity.DelayedAction.json
 */
public class DelayedActionParser implements JsonFileParser {

    private static DelayedActionParser instance = null;

    private DelayedActionParser() {
    }

    public static DelayedActionParser getInstance() {
        if (instance == null) {
            instance = new DelayedActionParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
