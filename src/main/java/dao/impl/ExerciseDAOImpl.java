package dao.impl;

import dao.AppointmentDAO;
import entity.Exercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExerciseDAOImpl extends AppointmentDAO<Exercise> {

    private static final String SELECT_ALL = "SELECT ExerciseId, Name FROM Exercise";
    private static final String SELECT_BY_USER_ID =
            "SELECT e.Name, a.RepCount, a.SetCount, a.Weight FROM Exercise e JOIN ExerciseAppointment a on e.ExerciseId = a.ExerciseId WHERE a.UserId = ?";
    private static final String ADD = "INSERT INTO ExerciseAppointment (UserId, ExerciseId, SetCount, RepCount, Weight) VALUE (?, ?, ?, ?, ?)";

    private static class ExerciseDAOHolder {
        static final ExerciseDAOImpl INSTANCE = new ExerciseDAOImpl();
    }

    private ExerciseDAOImpl() {}

    public static ExerciseDAOImpl getInstance() {
        return ExerciseDAOHolder.INSTANCE;
    }

    @Override
    protected String getAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getByUserIdQuery() {
        return SELECT_BY_USER_ID;
    }


    @Override
    protected void handleByUserIdResult(List<Exercise> list, ResultSet resultSet) throws SQLException {
        Exercise exercise = new Exercise();
        String name = resultSet.getString("Name");
        int repCount = resultSet.getInt("RepCount");
        int setCount = resultSet.getInt("SetCount");
        int weight = resultSet.getInt("Weight");
        exercise.setName(name);
        exercise.setRepCount(repCount);
        exercise.setSetCount(setCount);
        exercise.setWeight(weight);
        list.add(exercise);
    }

    @Override
    protected void handleAllResult(List<Exercise> list, ResultSet resultSet) throws SQLException {
        int exerciseId = resultSet.getInt("ExerciseId");
        String name = resultSet.getString("Name");
        Exercise exercise = new Exercise();
        exercise.setId(exerciseId);
        exercise.setName(name);
        list.add(exercise);
    }

    @Override
    protected String addAppointment() {
        return ADD;
    }

    @Override
    protected void handleAdd(int userId, Exercise exercise, PreparedStatement statement) throws SQLException {
        statement.setInt(1, userId);
        statement.setInt(2, exercise.getId());
        statement.setInt(3, exercise.getSetCount());
        statement.setInt(4, exercise.getRepCount());
        statement.setInt(5, exercise.getWeight());
        statement.addBatch();
    }
}
