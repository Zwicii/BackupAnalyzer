package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.config.Output.json
 */
public class OutputParser implements JsonFileParser {

    private static OutputParser instance = null;

    private OutputParser() {
    }

    public OutputParser getInstance() {
        if (instance == null) {
            instance = new OutputParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
