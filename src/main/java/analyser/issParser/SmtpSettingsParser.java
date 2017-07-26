package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.email.SmtpSettings.json
 */
public class SmtpSettingsParser implements JsonFileParser {

    private static SmtpSettingsParser instance = null;

    private SmtpSettingsParser() {
    }

    public static SmtpSettingsParser getInstance() {
        if (instance == null) {
            instance = new SmtpSettingsParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
