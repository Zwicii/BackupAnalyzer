package analyser.platformParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.platform.security.Role.json
 */
public class RoleParser implements JsonFileParser {

    private static RoleParser instance = null;

    private RoleParser() {
    }

    public static RoleParser getInstance() {
        if (instance == null) {
            instance = new RoleParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
