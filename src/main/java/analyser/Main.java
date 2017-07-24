package analyser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ServerRunner;

import java.io.IOException;



public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        //Start server
        ServerRunner serverRunner = new ServerRunner();
        serverRunner.start(8090);

        //Test Pull

    }
}






