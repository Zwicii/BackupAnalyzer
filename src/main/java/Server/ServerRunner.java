package Server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.util.Headers;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import java.io.File;
import java.nio.file.Paths;

import static io.undertow.Handlers.resource;

/**
 * Created by victoria on 14.07.17.
 */
public class ServerRunner {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();

    /**
     * Startet Undertow Server auf übergebenen Port
     */
//    @Override
    public void start(Integer port) {
        //IP Adresse von Rechner kann auch verwendet werden
        Undertow.Builder serverBuilder = Undertow.builder()
                .addHttpListener(port, "0.0.0.0"); //listen to all IP adresses

        server.deploy(ResourceMapper.class, "/api");

        server.addResourcePrefixPath("/",
                resource(new PathResourceManager(Paths.get("/"),100))
                        .addWelcomeFiles("index.html"));

        server.start(serverBuilder);

//        UndertowJaxrsServer server = new UndertowJaxrsServer();
//
//        ResteasyDeployment deployment = new ResteasyDeployment();
//
//        deployment.setApplicationClass(ResourceMapper.class.getName());
//
//        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
//        deploymentInfo.setDeploymentName("Test");
//        deploymentInfo.setClassLoader(ServerRunner.class.getClassLoader());
//
//        deploymentInfo.setContextPath("/api");
//
//        server.deploy(deploymentInfo);
//
//        server.addResourcePrefixPath("/",
//                resource(new PathResourceManager(Paths.get("/index.html"),100)).
//                        addWelcomeFiles("index.html"));
//
//        server.start();
    }


    //    @Override
    public void stop() {
        server.stop();
    }
}
