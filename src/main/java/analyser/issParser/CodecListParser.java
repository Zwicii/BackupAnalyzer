package analyser.issParser;

import analyser.Main;
import interfaces.JsonFileParser;

/**
 * parse com.commend.iss.codecs.CodecList.json
 */
public class CodecListParser implements JsonFileParser {

    private static CodecListParser instance = null;

    private CodecListParser() {
    }

    public static CodecListParser getInstance() {
        if (instance == null) {
            instance = new CodecListParser();
        }
        return instance;
    }

    @Override
    public void parse(String filePath) {
        Main.logger.info("Not implemented yet");
    }
}
