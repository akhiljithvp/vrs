package dropwizard.vrs.beans;

import dropwizard.vrs.utils.MovieUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "type", "availableForRent", "rentedBy", "rentedDate", "rentalDays", "rentalPrice"})
public class Movie {

    @XmlElement(name = "availableForRent")
    private boolean availableForRent;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "type")
    private String type;
    @XmlElement(name = "rentedDate")
    private Date rentedDate;
    @XmlElement(name = "rentedBy")
    private String rentedBy;
    @XmlElement(name = "rentalDays")
    private long rentalDays;
    @XmlElement(name = "rentalPrice")
    private long rentalPrice;

    public boolean isAvailableForRent() {
        return availableForRent;
    }

    public void setAvailableForRent(boolean availableForRent) {
        this.availableForRent = availableForRent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(Date rentedDate) {
        this.rentedDate = rentedDate;
    }

    public String getRentedBy() {
        return rentedBy;
    }

    public void setRentedBy(String rentedBy) {
        this.rentedBy = rentedBy;
    }

    public void setRentalPrice(long rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public long getRentalPrice() {
        return rentalPrice;
    }

    public long getSurcharges() {
        long actualRentalDays = getNumberOfDaysRented();
        if (actualRentalDays > rentalDays) {
            return MovieUtils.getPriceForMovieType(actualRentalDays, type) - rentalPrice;
        }
        return 0;
    }

    public long getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(long rentalDays) {
        this.rentalDays = rentalDays;
    }

    public long getNumberOfDaysRented() {
        long diffInDays = MovieUtils.getDiffInDaysFromCurrentDate(rentedDate);
        // if returned the same day it is still considered as rental for 1 day
        return (diffInDays == 0) ? 1 : diffInDays;
    }

    public long getBasicPrice() {
        return MovieUtils.getBasicPrice(type);
    }

    public int getBonus() {
        return MovieUtils.getBonus(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Movie that = (Movie) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }

}
