package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.email.SendMail.json
 */
public class SendMailParser implements JsonFileParser {

    private static SendMailParser instance = null;

    private SendMailParser() {
    }

    public static SendMailParser getInstance() {
        if (instance == null) {
            instance = new SendMailParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
