package dropwizard.vrs.resources;

import dropwizard.vrs.beans.Movie;
import dropwizard.vrs.services.DataServices;
import dropwizard.vrs.services.impl.DataServiceImpl;
import com.sun.jersey.spi.resource.Singleton;
import com.yammer.dropwizard.views.View;

import javax.ws.rs.*;
import java.util.List;
import java.util.Set;

@Singleton
@Path("/data/")
public class MovieResource {

    private DataServices dataServices = new DataServiceImpl();

    @GET
    @Path("movies")
    public Set<Movie> getAvailableMovies(@QueryParam("customer") String customerName) {
        return dataServices.getAvailableMovies(customerName);
    }

    @POST
    @Path("movies/save")
    public View saveMovies(@QueryParam("customer") String customerName,
                           @FormParam("selectedMovies") List<String> selectedMovies,
                           @FormParam("days") int rentalDays) {
        return dataServices.saveMovies(customerName, selectedMovies, rentalDays);
    }

    @POST
    @Path("add/movie")
    public void addMovie(@FormParam("name") String name, @FormParam("type") String type) {
        dataServices.addMovie(name, type);
    }


}
