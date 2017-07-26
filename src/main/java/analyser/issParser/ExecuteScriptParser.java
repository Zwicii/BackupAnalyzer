package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse
 */
public class ExecuteScriptParser implements JsonFileParser {

    private static ExecuteScriptParser instance = null;

    private ExecuteScriptParser() {
    }

    public static ExecuteScriptParser getInstance() {
        if (instance == null) {
            instance = new ExecuteScriptParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
