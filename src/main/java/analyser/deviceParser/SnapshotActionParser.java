package analyser.deviceParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.device.video.SnapshotAction.json
 */
public class SnapshotActionParser implements JsonFileParser {

    private static SnapshotActionParser instance = null;

    private SnapshotActionParser() {
    }

    public SnapshotActionParser getInstance() {
        if (instance == null) {
            instance = new SnapshotActionParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {

        Main.logger.info("Not implemented yet");
    }
}
