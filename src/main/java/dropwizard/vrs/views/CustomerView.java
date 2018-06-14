package dropwizard.vrs.views;

import dropwizard.vrs.beans.Customer;
import dropwizard.vrs.beans.Movie;
import com.yammer.dropwizard.views.View;

import java.util.Set;

public class CustomerView extends View {

    private Set<Movie> movies;
    private Customer customer;

    public CustomerView(String templateName) {
        super(templateName);
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
