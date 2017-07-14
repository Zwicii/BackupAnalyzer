package Server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.List;

/**REST Interface für Nutzer um über HTTP auf Daten zugreifen zu können*/
@Path("/test")
public class TestResource {

//    UserService userService = UserServiceImpl.getInstance();
//    ObjectMapper mapper = new ObjectMapper();

    /**Test-Methode um funktionierende Kommunikation aufbauen zu können*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String test(@QueryParam("name") String name) { //?name=Victoria
        System.out.println(name);
        return "{}";
    }

    @GET
    @Path("/asdf")
    @Produces(MediaType.APPLICATION_JSON)
    public String asdf() {
        System.out.println("asdf");
        return "{\"asdf\": true}";
    }
}
