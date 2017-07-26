package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.activity.ActivityCard.json
 */
public class ActivityCardParser implements JsonFileParser {

    private static ActivityCardParser instance = null;

    private ActivityCardParser() {
    }

    public static ActivityCardParser getInstance() {
        if (instance == null) {
            instance = new ActivityCardParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
