package dropwizard.vrs.utils;

import dropwizard.vrs.beans.MovieTypes;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class MovieUtils {

    private MovieUtils() {
        throw new IllegalAccessError("Utility class. Cannot be instantiated");
    }

    public static int getBonus(String type) {
        if (MovieTypes.NEW.getCode().equals(type)) {
            return MovieTypes.NEW.getBonus();
        } else if (MovieTypes.OLD.getCode().equals(type)) {
            return MovieTypes.OLD.getBonus();
        } else if (MovieTypes.REGULAR.getCode().equals(type)) {
            return MovieTypes.REGULAR.getBonus();
        }
        return 0;
    }

    public static long getBasicPrice(String type) {
        if (MovieTypes.NEW.getCode().equals(type)) {
            return MovieTypes.NEW.getPrice();
        } else if (MovieTypes.OLD.getCode().equals(type)) {
            return MovieTypes.OLD.getPrice();
        } else if (MovieTypes.REGULAR.getCode().equals(type)) {
            return MovieTypes.REGULAR.getPrice();
        }
        return 0;
    }

    public static int getRentalDaysForMovieType(String type) {
        if (MovieTypes.NEW.getCode().equals(type)) {
            return MovieTypes.NEW.getRentalDays();
        } else if (MovieTypes.OLD.getCode().equals(type)) {
            return MovieTypes.OLD.getRentalDays();
        } else if (MovieTypes.REGULAR.getCode().equals(type)) {
            return MovieTypes.REGULAR.getRentalDays();
        }
        return 0;
    }

    public static long getPriceForMovieType(long diffInDays, String type) {
        if (MovieTypes.NEW.getCode().equals(type)) {
            return diffInDays * MovieTypes.NEW.getPrice();
        } else if (MovieTypes.REGULAR.getCode().equals(type)) {
            if (diffInDays < 3) {
                return MovieTypes.REGULAR.getPrice();
            } else {
                return MovieTypes.REGULAR.getPrice() + (MovieTypes.REGULAR.getPrice() * (diffInDays - 3));
            }
        } else if (MovieTypes.OLD.getCode().equals(type)) {
            if (diffInDays < 5) {
                return MovieTypes.OLD.getPrice();
            } else {
                return MovieTypes.OLD.getPrice() + (MovieTypes.OLD.getPrice() * (diffInDays - 5));
            }
        }
        return 0;
    }

    public static long getDiffInDaysFromCurrentDate(Date dateToCompare) {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - dateToCompare.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


}
