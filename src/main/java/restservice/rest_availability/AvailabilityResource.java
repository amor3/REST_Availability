package restservice.rest_availability;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import restservice.model.Route;

/**
 * REST Web Service
 *
 * @author AMore
 */
@Stateless
@Path("availability")
public class AvailabilityResource {

    @Context
    private UriInfo context;

    @PersistenceContext(unitName = "WebServices_PU")
    private EntityManager em;

    public AvailabilityResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/searchpath/{username}/{password}/{from}/{to}")
    public Response getAvail(
            @PathParam("username") String username,
            @PathParam("password") String password,
            @PathParam("from") String from,
            @PathParam("to") String to) {
        JsonObject value;

        String path = from + ":" + to;

        Route route;

        try {
            route = em.createNamedQuery("Route.findByPath", Route.class).setParameter("path", path).getSingleResult();
        } catch (NoResultException e) {
            route = null;
        }

        if (authorizeUser(username, password) && route != null) {
            return Response.status(200).entity(route).build();
        } else {
            value = Json.createObjectBuilder()
                    .add("found", "false")
                    .add("reason", "User not authorized or path dont exist")
                    .build();

            return Response.status(200).entity(value.toString()).build();
        }
    }

    @POST
    @Path("/createroute")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Route route) {
        JsonObject value;

        em.persist(route);

        value = Json.createObjectBuilder()
                .add("addedroute", "true")
                .build();

        return Response.status(200).entity(value).build();
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean authorizeUser(String username, String password) {
        try {
            Client client = Client.create();

            WebResource webResource = client.resource("http://localhost:8080/REST_Authorization/webresources/auth/login/" + username + "/" + password);

            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            JSONObject jsonObj = new JSONObject(response.getEntity(String.class));

            return Boolean.valueOf((String) jsonObj.get("authorized"));

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

    }

}
