package dropwizard.vrs.utils;

import dropwizard.vrs.beans.Customer;
import dropwizard.vrs.beans.Customers;
import dropwizard.vrs.beans.Movie;
import dropwizard.vrs.beans.Movies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public final class XmlUtils {

    private static final String CUSTOMER_XML_FILE = "data/customers.xml";
    private static final String MOVIE_XML_FILE = "data/movies.xml";

    private static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    private XmlUtils() {
        throw new IllegalAccessError("Utility class. Cannot be instantiated");
    }

    public static void addCustomerToXml(Customer customer) {
        Customers customers = getCustomers();
        if (customers == null) {
            customers = new Customers();
        }
        Set<Customer> customerList = customers.getCustomers();
        customerList.add(customer);
        customers.setCustomers(customerList);
        saveCustomersToFile(customers);
    }

    public static void addMovieToXml(Movie movie) {
        Movies movies = getMoviesFromFile();
        if (movies == null) {
            movies = new Movies();
        }
        Set<Movie> movieList = movies.getMovies();
        if (!movieList.contains(movie)) {
            movieList.add(movie);
            movies.setMovies(movieList);
            saveMoviesToFile(movies);
        }
    }

    public static Set<Movie> getMovies() {
        Movies movies = getMoviesFromFile();
        if (movies != null) {
            return movies.getMovies();
        }
        return Collections.emptySet();
    }

    public static Customers getCustomers() {
        return (Customers) unrmarshall(CUSTOMER_XML_FILE, Customers.class);
    }

    public static void saveCustomersToFile(Customers customers) {
        marshall(CUSTOMER_XML_FILE, customers, Customers.class);
    }

    private static Movies getMoviesFromFile() {
        return (Movies) unrmarshall(MOVIE_XML_FILE, Movies.class);
    }

    public static void saveMoviesToFile(Movies movies) {
        marshall(MOVIE_XML_FILE, movies, Movies.class);
    }

    public static void createXmlFiles() {
        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                if (!dataDir.mkdir()) {
                    logger.error("Failed to create data directory.");
                    return;
                }
            }
            createXmlFile(MOVIE_XML_FILE, new Movies(), Movies.class);
            createXmlFile(CUSTOMER_XML_FILE, new Customers(), Customers.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void marshall(String fileName, Object content, Class classType) {
        try {
            File xmlFile = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(classType);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(content, xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static Object unrmarshall(String fileName, Class classType) {
        try {
            File xmlFile = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(classType);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void createXmlFile(String fileName, Object content, Class classType) throws IOException {
        File xmlFile = new File(fileName);
        if (xmlFile.createNewFile()) {
            marshall(fileName, content, classType);
        }
    }
}
