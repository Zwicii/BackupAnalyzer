package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.Barcode.json
 */
public class BarcodeParser implements JsonFileParser {

    private static BarcodeParser instance = null;

    private BarcodeParser() {
    }

    public BarcodeParser getInstance() {
        if (instance == null) {
            instance = new BarcodeParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
