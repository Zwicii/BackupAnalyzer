package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.account.Account.json
 */
public class AccountParser implements JsonFileParser {

    private static AccountParser instance = null;

    private AccountParser() {
    }

    public static AccountParser getInstance() {
        if (instance == null) {
            instance = new AccountParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
