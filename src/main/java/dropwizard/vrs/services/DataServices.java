package dropwizard.vrs.services;

import dropwizard.vrs.beans.Customer;
import dropwizard.vrs.beans.Movie;
import com.yammer.dropwizard.views.View;

import java.util.List;
import java.util.Set;

public interface DataServices {

    void addMovie(String name, String type);

    Set<Movie> getAvailableMovies(String customer);

    Set<Movie> getAllMovies();

    View saveMovies(String customerName, List<String> selectedMovies, int rentalDays);

    Customer getCustomer(String customerName);

}
