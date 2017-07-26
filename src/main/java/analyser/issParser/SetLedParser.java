package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.device.led.SetLed.json
 */
public class SetLedParser implements JsonFileParser {

    private static SetLedParser instance = null;

    private SetLedParser() {
    }

    public static SetLedParser getInstance() {
        if (instance == null) {
            instance = new SetLedParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
