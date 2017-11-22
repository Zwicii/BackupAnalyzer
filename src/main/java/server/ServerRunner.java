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
    public void start(Integer port) {
        //IP Adresse von Rechner kann auch verwendet werden
        Undertow.Builder serverBuilder = Undertow.builder()
                .addHttpListener(port, "0.0.0.0"); //listen to all IP addresses

        server.deploy(ResourceMapper.class, "/api"); //Creates a web deployment for the jaxrs Application. Will ignore any @ApplicationPath annotation

//        //Startseite index.html
//        server.addResourcePrefixPath("/",
//                resource(new PathResourceManager(Paths.get("/"), 100))
//                        .addWelcomeFiles("index.html")); //?

        server.start(serverBuilder);

        List<URL> urls;
        ClassLoader classLoader = ServerRunner.class.getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            //load classes and resources from a search path of URLs referring to both JAR files and directories
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            urls = asList(urlClassLoader.getURLs());
            // TODO [STC]: Weitere Beispiele/Tutorials zu Streams und Lambda Expressions in Java ansehen
            // z.B.:    https://www.youtube.com/watch?v=Hl5XLAtpRog
            //          https://www.youtube.com/watch?v=9Orn0Pwp3YU
            //          https://www.youtube.com/watch?v=8pDm_kH4YKY
            //          http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
            // TODO [STC]: Diesen Code-Block verstehen und gut kommentieren
            urls.stream()
                    .filter(url -> !url.toString().endsWith(".jar")) //Filtert alles mit .jar heraus
                    .filter(url -> url.toString().contains("resources")) //url: "file:/home/victoria/IdeaProjects/Victoria/build/resources/main"
                    .map(url -> { //Returns a stream consisting of the results of applying the given function to the elements of this stream.
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
    }

    public void stop() {
        server.stop();
    }
}
