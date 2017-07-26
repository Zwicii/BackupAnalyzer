package analyser.platformParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.platform.theft.Theft.json
 */
public class TheftParser implements JsonFileParser {

    private static TheftParser instance = null;

    private TheftParser() {
    }

    public static TheftParser getInstance() {
        if (instance == null) {
            instance = new TheftParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
