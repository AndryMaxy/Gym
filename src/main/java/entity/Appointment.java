package entity;
//TODO PACKAGE?
import java.util.List;

public class Appointment {

    private List<ExerciseAppointment> exerciseAppointments;
    private List<ProductAppointment> productAppointments;

    public List<ExerciseAppointment> getExerciseAppointments() {
        return exerciseAppointments;
    }

    public void setExerciseAppointments(List<ExerciseAppointment> exerciseAppointments) {
        this.exerciseAppointments = exerciseAppointments;
    }

    public List<ProductAppointment> getProductAppointments() {
        return productAppointments;
    }

    public void setProductAppointments(List<ProductAppointment> productAppointments) {
        this.productAppointments = productAppointments;
    }
}
