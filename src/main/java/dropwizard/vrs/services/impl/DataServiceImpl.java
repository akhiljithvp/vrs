package dropwizard.vrs.services.impl;

import dropwizard.vrs.beans.Customer;
import dropwizard.vrs.beans.Customers;
import dropwizard.vrs.beans.Movie;
import dropwizard.vrs.beans.Movies;
import dropwizard.vrs.services.DataServices;
import dropwizard.vrs.utils.MovieUtils;
import dropwizard.vrs.utils.XmlUtils;
import dropwizard.vrs.views.SummaryView;
import com.sun.jersey.spi.resource.Singleton;
import com.yammer.dropwizard.views.View;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class DataServiceImpl implements DataServices {

    private static Set<Movie> movies;
    private static Set<Customer> customers;

    @Override
    public Set<Movie> getAvailableMovies(String customer) {
        Set<Movie> availableMovies = new LinkedHashSet<>();
        for (Movie movie : XmlUtils.getMovies()) {
            if (movie.isAvailableForRent()) {
                availableMovies.add(movie);
            } else if (customer.equals(movie.getRentedBy())) {
                availableMovies.add(movie);
            }
        }
        return availableMovies;
    }

    @Override
    public Set<Movie> getAllMovies() {
        return XmlUtils.getMovies();
    }

    @Override
    public Customer getCustomer(String customerName) {
        for (Customer customer : getCustomers()) {
            if (customer.getName().equals(customerName)) {
                return customer;
            }
        }
        return createNewCustomerAndReturn(customerName);
    }

    @Override
    public void addMovie(String name, String type) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setType(type);
        movie.setAvailableForRent(true);
        XmlUtils.addMovieToXml(movie);
    }

    @Override
    public View saveMovies(String customerName, List<String> selectedMovies, int rentalDays) {
        Customer customer = getCustomer(customerName);
        SummaryView summaryView = processMovies(customerName, selectedMovies, rentalDays, customer.getBonus());
        processCustomer(customer, summaryView.getBonus());
        return summaryView;
    }

    private SummaryView processMovies(String customerName, List<String> selectedMovies, int rentalDays, int bonus) {
        Movies moviesToFile = new Movies();
        // read movie details from the file
        Set<Movie> moviesFromFile = XmlUtils.getMovies();
        // convert the Set to map for easy updating of the movies
        Map<String, Movie> movieMap = moviesFromFile.stream().collect(Collectors.toMap(Movie::getName, Function.identity()));
        // summary page view which will show the information of the movies which are selected for rent/return
        SummaryView summaryView = new SummaryView("summary.ftl");
        for (Movie movie : getMovies(customerName)) {
            if (selectedMovies.contains(movie.getName())) {
                // if true it means this movie is selected for rent by the current customer
                // else it means this movie is selected for returning by the current customer
                if (movie.isAvailableForRent()) {
                    bonus = bonus + MovieUtils.getBonus(movie.getType());
                    movie.setAvailableForRent(false);
                    movie.setRentedBy(customerName);
                    movie.setRentedDate(new GregorianCalendar(TimeZone.getTimeZone("UTC")).getTime());
                    if (rentalDays < 1) {
                        movie.setRentalDays(MovieUtils.getRentalDaysForMovieType(movie.getType()));
                    } else {
                        movie.setRentalDays(rentalDays);
                    }
                    movie.setRentalPrice(MovieUtils.getPriceForMovieType(movie.getRentalDays(), movie.getType()));
                    summaryView.getMoviesToRent().add(movie);
                } else {
                    movie.setAvailableForRent(true);
                    movie.setRentedBy("");
                    summaryView.getMoviesReturned().add(movie);
                }
            }
            movieMap.put(movie.getName(), movie);
        }
        moviesToFile.setMovies(new HashSet<>(movieMap.values()));
        // save the updated movie information back to xml
        XmlUtils.saveMoviesToFile(moviesToFile);
        summaryView.setTotalRentalPrice(getTotalRentalPrice(summaryView.getMoviesToRent()));
        summaryView.setTotalSurcharges(getTotalSurcharges(summaryView.getMoviesReturned()));
        summaryView.setBonus(bonus);
        return summaryView;
    }

    private void processCustomer(Customer customer, int bonus) {
        customer.setBonus(bonus);
        Customers customersToFile = new Customers();
        // read customer details from the file
        Set<Customer> customersFromFile = XmlUtils.getCustomers().getCustomers();
        // convert the Set to map for easy updating of the customers
        Map<String, Customer> customerMap = customersFromFile.stream().collect(Collectors.toMap(Customer::getName, Function.identity()));
        customerMap.put(customer.getName(), customer);
        customersToFile.setCustomers(new HashSet<>(customerMap.values()));
        // save the updated customer information back to xml
        XmlUtils.saveCustomersToFile(customersToFile);
    }

    private Set<Movie> getMovies(String customerName) {
        if (movies == null) {
            movies = getAvailableMovies(customerName);
        }
        return movies;
    }

    private Set<Customer> getCustomers() {
        if (customers == null) {
            customers = XmlUtils.getCustomers().getCustomers();
        }
        return customers;
    }

    private Customer createNewCustomerAndReturn(String customerName) {
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setBonus(0);
        XmlUtils.addCustomerToXml(customer);
        customers.add(customer);
        return customer;
    }

    private long getTotalRentalPrice(Set<Movie> movies) {
        long totalPrice = 0;
        for (Movie movie : movies) {
            totalPrice = totalPrice + movie.getRentalPrice();
        }
        return totalPrice;
    }

    private long getTotalSurcharges(Set<Movie> movies) {
        long totalSurcharges = 0;
        for (Movie movie : movies) {
            totalSurcharges = totalSurcharges + movie.getSurcharges();
        }
        return totalSurcharges;
    }
}
