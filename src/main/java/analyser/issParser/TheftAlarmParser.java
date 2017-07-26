package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.hardware.secureconnector.TheftAlarm.json
 */
public class TheftAlarmParser implements JsonFileParser {

    private static TheftAlarmParser instance = null;

    private TheftAlarmParser() {
    }

    public static TheftAlarmParser getInstance() {
        if (instance == null) {
            instance = new TheftAlarmParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
