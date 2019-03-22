package by.epam.akulich.gym.entity;

import java.util.Objects;

/**
 * The class represents product which trainer appoint to visitor.
 *
 * @author Andrey Akulich
 */
public class ProductAppointment {

    /**
     * A product identifier in database.
     */
    private int id;

    /**
     * A product name.
     */
    private String name;

    /**
     * A number of grams of product consumed per day.
     */
    private int gramInDay;

    /**
     * Constructs this class.
     */
    public ProductAppointment() {
    }

    /**
     * Constructs this class.
     *
     * @param id a product identifier
     * @param name a product name
     * @param gramInDay a number of grams of product consumed per day
     */
    public ProductAppointment(int id, String name, int gramInDay) {
        this.id = id;
        this.name = name;
        this.gramInDay = gramInDay;
    }

    /**
     * @return a exercise identifier in database
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a exercise identifier in database.
     *
     * @param id a exercise identifier in database
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return a name of exercise
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a name of exercise.
     *
     * @param name a of exercise
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return a number of grams of product consumed per day
     */
    public int getGramInDay() {
        return gramInDay;
    }

    /**
     * Sets a number of grams of product consumed per day
     * @param gramInDay a number of grams of product
     */
    public void setGramInDay(int gramInDay) {
        this.gramInDay = gramInDay;
    }

    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     *
     * @param o an object to be compared with this object
     * @return {@code true} if the arguments are equal to each other
     * and {@code false} otherwise
     */
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
                Objects.equals(name, that.name);
    }

    /**
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, gramInDay);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "ProductAppointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gramInDay=" + gramInDay +
                '}';
    }
}
