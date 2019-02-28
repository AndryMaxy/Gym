package entity;

import java.util.Objects;

public class ProductAppointment {

    private int id;
    private String name;
    private int gramInDay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAppointment)) {
            return false;
        }
        ProductAppointment that = (ProductAppointment) o;
        return id == that.id &&
                gramInDay == that.gramInDay &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gramInDay);
    }

    @Override
    public String toString() {
        return "ProductAppointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gramInDay=" + gramInDay +
                '}';
    }
}
