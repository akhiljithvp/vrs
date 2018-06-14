package dropwizard.vrs.views;

import dropwizard.vrs.beans.Movie;
import com.yammer.dropwizard.views.View;

import java.util.LinkedHashSet;
import java.util.Set;

public class SummaryView extends View {

    private long totalRentalPrice;
    private long totalSurcharges;
    private int bonus;
    private Set<Movie> moviesReturned;
    private Set<Movie> moviesToRent;

    public SummaryView(String templateName) {
        super(templateName);
    }

    public Set<Movie> getMoviesReturned() {
        if (moviesReturned == null) {
            moviesReturned = new LinkedHashSet<>();
        }
        return moviesReturned;
    }

    public void setMoviesReturned(Set<Movie> moviesReturned) {
        this.moviesReturned = moviesReturned;
    }

    public Set<Movie> getMoviesToRent() {
        if (moviesToRent == null) {
            moviesToRent = new LinkedHashSet<>();
        }
        return moviesToRent;
    }

    public void setMoviesToRent(Set<Movie> moviesToRent) {
        this.moviesToRent = moviesToRent;
    }

    public long getTotalRentalPrice() {
        return totalRentalPrice;
    }

    public void setTotalRentalPrice(long totalRentalPrice) {
        this.totalRentalPrice = totalRentalPrice;
    }

    public long getTotalSurcharges() {
        return totalSurcharges;
    }

    public void setTotalSurcharges(long totalSurcharges) {
        this.totalSurcharges = totalSurcharges;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}



