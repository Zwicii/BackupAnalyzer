package analyser.platformParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.platform.db.MigrationScript.json
 */
public class MigrationScriptParser implements JsonFileParser {

    private static MigrationScriptParser instance = null;

    private MigrationScriptParser() {
    }

    public static MigrationScriptParser getInstance() {
        if (instance == null) {
            instance = new MigrationScriptParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
