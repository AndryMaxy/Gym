package entity;

public class ProductAppointment {

    private String name;
    private int gramInDay;

    public ProductAppointment(String name, int gramInDay) {
        this.name = name;
        this.gramInDay = gramInDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGramInDay() {
        return gramInDay;
    }

    public void setGramInDay(int gramInDay) {
        this.gramInDay = gramInDay;
    }
}
