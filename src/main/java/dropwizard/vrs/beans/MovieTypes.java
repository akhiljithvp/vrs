package dropwizard.vrs.beans;

public enum MovieTypes {

    NEW("New", 40, 2, 1),
    OLD("Old", 30, 1, 5),
    REGULAR("Regular", 30, 1, 3);

    private long price;
    private int bonus;
    private int rentalDays;
    private String code;

    MovieTypes(String code, long price, int bonus, int rentalDays) {
        this.code = code;
        this.price = price;
        this.bonus = bonus;
        this.rentalDays = rentalDays;
    }

    public String getCode() {
        return code;
    }

    public long getPrice() {
        return price;
    }

    public int getBonus() {
        return bonus;
    }

    public int getRentalDays() {
        return rentalDays;
    }
}
