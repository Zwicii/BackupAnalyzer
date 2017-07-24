package server;

import javax.ws.rs.core.Application;
import java.util.LinkedHashSet;
import java.util.Set;

public class ResourceMapper extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<>(); //sowie HashMap nur ohne key
        resources.add(BackupAnalyserResource.class);

        return resources;
    }

}
