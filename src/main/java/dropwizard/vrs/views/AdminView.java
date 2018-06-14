package dropwizard.vrs.views;

import dropwizard.vrs.beans.Movie;
import com.yammer.dropwizard.views.View;

import java.util.Set;

public class AdminView extends View {

    private Set<Movie> movies;

    public AdminView(String templateName) {
        super(templateName);
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
