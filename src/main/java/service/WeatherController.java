package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/weather")
public class WeatherController {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getWeatherInfoByCityID(@PathParam("id") String id) {
        int httpStatus = 200;
        return Response.status(httpStatus).entity(new Object()).build();
    }
}
