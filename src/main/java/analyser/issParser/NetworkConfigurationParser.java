package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.network.NetworkConfiguration.json
 */
public class NetworkConfigurationParser implements JsonFileParser {

    private static NetworkConfigurationParser instance = null;

    private NetworkConfigurationParser() {
    }

    public static NetworkConfigurationParser getInstance() {
        if (instance == null) {
            instance = new NetworkConfigurationParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
