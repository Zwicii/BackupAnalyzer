package analyser.platformParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.platform.i18n.I18nSettings.json
 */
public class l18nSettingsParser implements JsonFileParser {

    private static l18nSettingsParser instance = null;

    private l18nSettingsParser() {
    }

    public static l18nSettingsParser getInstance() {
        if (instance == null) {
            instance = new l18nSettingsParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
