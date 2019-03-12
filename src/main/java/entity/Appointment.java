package entity;

import java.util.List;
import java.util.Objects;

/**
 * The class represent appointment to the visitor of gym.
 *
 * @author Andrey Akulich
 */
public class Appointment {

    /**
     * List of appointment exercises to the visitor
     */
    private List<ExerciseAppointment> exerciseAppointments;

    /**
     * List of appointment products to the visitor
     */
    private List<ProductAppointment> productAppointments;

    /**
     * Constructs this class
     */
    public Appointment() {
    }

    /**
     * @return list of appointment exercises to the visitor
     */
    public List<ExerciseAppointment> getExerciseAppointments() {
        return exerciseAppointments;
    }

    /**
     * Sets list of appointment exercises to the visitor.
     *
     * @param exerciseAppointments appointment exercises
     */
    public void setExerciseAppointments(List<ExerciseAppointment> exerciseAppointments) {
        this.exerciseAppointments = exerciseAppointments;
    }

    /**
     * @return list of appointment products to the visitor
     */
    public List<ProductAppointment> getProductAppointments() {
        return productAppointments;
    }

    /**
     * Sets list of appointment products to the visitor.
     *
     * @param productAppointments appointment products
     */
    public void setProductAppointments(List<ProductAppointment> productAppointments) {
        this.productAppointments = productAppointments;
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
        if (!(o instanceof Appointment)) {
            return false;
        }
        Appointment that = (Appointment) o;
        return Objects.equals(exerciseAppointments, that.exerciseAppointments) &&
                Objects.equals(productAppointments, that.productAppointments);
    }

    /**
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(exerciseAppointments, productAppointments);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "exerciseAppointments=" + exerciseAppointments +
                ", productAppointments=" + productAppointments +
                '}';
    }
}
