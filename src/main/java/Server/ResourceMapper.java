package Server;

import javax.ws.rs.core.Application;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by victoria on 14.07.17.
 */
public class ResourceMapper extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<>(); //sowie HashMap nur ohne key
        resources.add(TestResource.class);

        return resources;
    }

}
