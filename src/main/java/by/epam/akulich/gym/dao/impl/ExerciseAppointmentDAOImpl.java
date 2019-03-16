package by.epam.akulich.gym.dao.impl;

import by.epam.akulich.gym.dao.AppointmentDAO;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.ExerciseAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The class is used for interacting with appointment tables of database.
 *
 * @author Andrey Akulich
 */
public class ExerciseAppointmentDAOImpl extends AppointmentDAO<ExerciseAppointment> {

    private static final String SELECT_ALL = "SELECT ExerciseId, Name FROM Exercise";
    private static final String SELECT_BY_BOOKING_ID = "SELECT e.Name, a.RepCount, a.SetCount, a.Weight FROM Exercise e JOIN ExerciseAppointment a ON e.ExerciseId = a.ExerciseId WHERE a.BookingId = ?";
    private static final String INSERT = "INSERT INTO ExerciseAppointment (BookingId, ExerciseId, SetCount, RepCount, Weight) VALUE (?, ?, ?, ?, ?)";

    /**
     * This class represents initialization-on-demand holder idiom for {@link ExerciseAppointmentDAOImpl}
     */
    private static class ExerciseDAOHolder {
        static final ExerciseAppointmentDAOImpl INSTANCE = new ExerciseAppointmentDAOImpl();
    }

    /**
     * Constructs ExerciseAppointmentDAOImpl.
     */
    private ExerciseAppointmentDAOImpl() {}

    /**
     * @return ExerciseAppointmentDAOImpl instance.
     */
    public static ExerciseAppointmentDAOImpl getInstance() {
        return ExerciseDAOHolder.INSTANCE;
    }

    /**
     * @return SQL query for selecting all {@link ExerciseAppointment}.
     */
    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL;
    }

    /**
     * @return SQL query for selecting all {@link ExerciseAppointment}
     * according {@link Booking} identifier
     */
    @Override
    protected String getSelectByBookingIdQuery() {
        return SELECT_BY_BOOKING_ID;
    }

    /**
     * @return SQL query for inserting new {@link ExerciseAppointment}
     */
    @Override
    protected String addAppointmentQuery() {
        return INSERT;
    }

    /**
     * Handles {@link ResultSet} of {@link ExerciseAppointment} and
     * sets result in param list.
     *
     * @param list a list to set appointment
     * @param resultSet a table of data representing a database result set, which
     *                  is usually generated by executing a statement that queries the database.
     * @throws SQLException if a database access error occurs or this method is
     * called on a closed result set
     */
    @Override
    protected void handleSelectByBookingIdResult(List<ExerciseAppointment> list, ResultSet resultSet) throws SQLException {
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

    /**
     * Handles {@link ResultSet} of {@link ExerciseAppointment} and
     * sets result in param list.
     *
     * @param list a list to set appointment
     * @param resultSet a table of data representing a database result set, which
     *                  is usually generated by executing a statement that queries the database.
     * @throws SQLException if a database access error occurs or this method is
     * called on a closed result set
     */
    @Override
    protected void handleSelectAllResult(List<ExerciseAppointment> list, ResultSet resultSet) throws SQLException {
        int exerciseId = resultSet.getInt("ExerciseId");
        String name = resultSet.getString("Name");
        ExerciseAppointment exerciseAppointment = new ExerciseAppointment();
        exerciseAppointment.setId(exerciseId);
        exerciseAppointment.setName(name);
        list.add(exerciseAppointment);
    }

    /**
     * Sets params to {@link PreparedStatement} and adds to batch.
     *
     * @param bookingId {@link Booking} identifier
     * @param exerciseAppointment a type of appointment
     * @param statement an object that represents a precompiled SQL statement
     * @throws SQLException if a database access error occurs or this method is
     * called on a closed result set
     */
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
