package by.epam.akulich.gym.entity;

import java.util.Objects;

/**
 * The class represents exercise which trainer appoint to visitor.
 *
 * @author Andrey Akulich
 */
public class ExerciseAppointment {

    /**
     * A booking identifier in database.
     */
    private int id;

    /**
     * A exercise name.
     */
    private String name;

    /**
     * Sets count of exercise.
     */
    private int setCount;

    /**
     * Repetition count of exercise.
     */
    private int repCount;

    /**
     * Weight for exercise.
     */
    private int weight;

    /**
     * Constructs this class.
     */
    public ExerciseAppointment(){}

    /**
     * Constructs this class.
     *
     * @param id a booking identifier in database
     * @param name a exercise name
     * @param setCount sets count of exercise.
     * @param repCount repetition count of exercise
     * @param weight weight for exercise
     */
    public ExerciseAppointment(int id, String name, int setCount, int repCount, int weight) {
        this.id = id;
        this.name = name;
        this.setCount = setCount;
        this.repCount = repCount;
        this.weight = weight;
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
     * @return a set count of exercise.
     */
    public int getSetCount() {
        return setCount;
    }

    /**
     * Sets a set count of exercise.
     *
     * @param setCount a set count of exercise.
     */
    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    /**
     * @return a repetition count of exercise.
     */
    public int getRepCount() {
        return repCount;
    }

    /**
     * Sets a repetition count of exercise.
     *
     * @param repCount a repetition count of exercise.
     */
    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }

    /**
     * @return a weight for exercise
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets a weight for exercise.
     *
     * @param weight a weight for exercise
     */
    public void setWeight(int weight) {
        this.weight = weight;
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
        if (!(o instanceof ExerciseAppointment)) {
            return false;
        }
        ExerciseAppointment that = (ExerciseAppointment) o;
        return id == that.id &&
                setCount == that.setCount &&
                repCount == that.repCount &&
                weight == that.weight &&
                Objects.equals(name, that.name);
    }

    /**
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, setCount, repCount, weight);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "ExerciseAppointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", setCount=" + setCount +
                ", repCount=" + repCount +
                ", weight=" + weight +
                '}';
    }
}
