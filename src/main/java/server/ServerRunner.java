package server;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.List;

import static io.undertow.Handlers.resource;
import static java.util.Arrays.asList;


public class ServerRunner {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();

    /**
     * Startet Undertow server auf übergebenen Port
     */
    // TODO [STC]: Auskommentierten (alten) Code entfernen
//    @Override
    public void start(Integer port) {
        //IP Adresse von Rechner kann auch verwendet werden
        Undertow.Builder serverBuilder = Undertow.builder()
                .addHttpListener(port, "0.0.0.0"); //listen to all IP adresses

        server.deploy(ResourceMapper.class, "/api");

        server.addResourcePrefixPath("/",
                resource(new PathResourceManager(Paths.get("/"), 100))
                        .addWelcomeFiles("index.html"));

        server.start(serverBuilder);

        List<URL> urls;
        ClassLoader classLoader = ServerRunner.class.getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            urls = asList(urlClassLoader.getURLs());
            // TODO [STC]: Weitere Beispiele/Tutorials zu Streams und Lambda Expressions in Java ansehen
            // z.B.:    https://www.youtube.com/watch?v=Hl5XLAtpRog
            //          https://www.youtube.com/watch?v=9Orn0Pwp3YU
            //          https://www.youtube.com/watch?v=8pDm_kH4YKY
            //          http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
            // TODO [STC]: Diesen Code-Block verstehen und gut kommentieren
            urls.stream()
                    .filter(url -> !url.toString().endsWith(".jar"))
                    .filter(url -> url.toString().contains("resources"))
                    .map(url -> {
                        try {
                            return Paths.get(url.toURI());
                        } catch (URISyntaxException e) {
                            return null;
                        }
                    })
                    .findFirst()
                    .ifPresent(path -> {

                        server.addResourcePrefixPath("/",
                                resource(new PathResourceManager(Paths.get(path.toString()), 100))
                                        .addWelcomeFiles("index.html"));
                    });
        }

        // TODO [STC]: Auskommentierten (alten) Code entfernen
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


    // TODO [STC]: Auskommentierten (alten) Code entfernen
    //    @Override
    public void stop() {
        server.stop();
    }
}
