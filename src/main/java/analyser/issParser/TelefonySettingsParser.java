package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.TelephonySettings.json
 */
public class TelefonySettingsParser implements JsonFileParser {

    private static TelefonySettingsParser instance = null;

    private TelefonySettingsParser() {
    }

    public static TelefonySettingsParser getInstance() {
        if (instance == null) {
            instance = new TelefonySettingsParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
