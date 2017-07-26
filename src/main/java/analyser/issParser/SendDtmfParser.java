package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.telephony.SendDtmf.json
 */
public class SendDtmfParser implements JsonFileParser {

    private static SendDtmfParser instance = null;

    private SendDtmfParser() {
    }

    public static SendDtmfParser getInstance() {
        if (instance == null) {
            instance = new SendDtmfParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
