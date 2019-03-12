package dao.impl;

import dao.AppointmentDAO;
import entity.ExerciseAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExerciseAppointmentDAOImpl extends AppointmentDAO<ExerciseAppointment> {

    private static final String SELECT_ALL = "SELECT ExerciseId, Name FROM Exercise";
    private static final String SELECT_BY_BOOKING_ID =
            "SELECT e.Name, a.RepCount, a.SetCount, a.Weight FROM Exercise e JOIN ExerciseAppointment a ON e.ExerciseId = a.ExerciseId WHERE a.BookingId = ?";
    private static final String INSERT = "INSERT INTO ExerciseAppointment (BookingId, ExerciseId, SetCount, RepCount, Weight) VALUE (?, ?, ?, ?, ?)";

    /**
     * This class represents initialization-on-demand holder idiom for {@link ExerciseAppointmentDAOImpl}
     */
    private static class ExerciseDAOHolder {
        static final ExerciseAppointmentDAOImpl INSTANCE = new ExerciseAppointmentDAOImpl();
    }

    private ExerciseAppointmentDAOImpl() {}

    public static ExerciseAppointmentDAOImpl getInstance() {
        return ExerciseDAOHolder.INSTANCE;
    }

    @Override
    protected String getAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getByBookingIdQuery() {
        return SELECT_BY_BOOKING_ID;
    }


    @Override
    protected void handleByBookingIdResult(List<ExerciseAppointment> list, ResultSet resultSet) throws SQLException {
        ExerciseAppointment exerciseAppointment = new ExerciseAppointment();
        String name = resultSet.getString("Name");
        int repCount = resultSet.getInt("RepCount");
        int setCount = resultSet.getInt("SetCount");
        int weight = resultSet.getInt("Weight");
        exerciseAppointment.setName(name);
        exerciseAppointment.setRepCount(repCount);
        exerciseAppointment.setSetCount(setCount);
        exerciseAppointment.setWeight(weight);
        list.add(exerciseAppointment);
    }

    @Override
    protected void handleAllResult(List<ExerciseAppointment> list, ResultSet resultSet) throws SQLException {
        int exerciseId = resultSet.getInt("ExerciseId");
        String name = resultSet.getString("Name");
        ExerciseAppointment exerciseAppointment = new ExerciseAppointment();
        exerciseAppointment.setId(exerciseId);
        exerciseAppointment.setName(name);
        list.add(exerciseAppointment);
    }

    @Override
    protected String addAppointmentQuery() {
        return INSERT;
    }

    @Override
    protected void handleAdd(int bookingId, ExerciseAppointment exerciseAppointment, PreparedStatement statement) throws SQLException {
        statement.setInt(1, bookingId);
        statement.setInt(2, exerciseAppointment.getId());
        statement.setInt(3, exerciseAppointment.getSetCount());
        statement.setInt(4, exerciseAppointment.getRepCount());
        statement.setInt(5, exerciseAppointment.getWeight());
        statement.addBatch();
    }
}
