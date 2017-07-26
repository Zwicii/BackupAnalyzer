package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.activity.TimeDefinition.json
 */
public class TimeDefinitionParser implements JsonFileParser {

    private static TimeDefinitionParser instance = null;

    private TimeDefinitionParser() {
    }

    public static TimeDefinitionParser getInstance() {
        if (instance == null) {
            instance = new TimeDefinitionParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
