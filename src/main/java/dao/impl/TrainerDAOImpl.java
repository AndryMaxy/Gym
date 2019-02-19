package dao.impl;

import builder.UserBuilder;
import dao.Executor;
import dao.TrainerDAO;
import dao.exception.DAOException;
import entity.Constants;
import entity.User;
import entity.UserRole;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAOImpl implements TrainerDAO {

    private static final String GET_VISITORS =
            "SELECT u.UserId, u.Name, u.Surname FROM User u LEFT JOIN ExerciseAppointment e on u.UserId = e.UserId WHERE u.UserRoleId = 3 && e.UserId IS NULL";

    private static final String GET_VISITOR =
            "SELECT u.Name, u.Surname FROM User u LEFT JOIN ExerciseAppointment e on u.UserId = e.UserId WHERE u.UserRoleId = 3 && e.UserId IS NULL && u.UserId = ?";

    private static class TrainerDAOImplHolder {
        static final TrainerDAOImpl INSTANCE = new TrainerDAOImpl();
    }

    private final Executor executor = Executor.getInstance();

    private TrainerDAOImpl(){};

    public static TrainerDAOImpl getInstance() {
        return TrainerDAOImplHolder.INSTANCE;
    }

    @Override
    public List<User> getVisitors() throws DAOException {
        try {
            return executor.executeQuery(GET_VISITORS, statement -> {}, resultSet -> {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    UserBuilder builder = new UserBuilder();
                    int userId = resultSet.getInt("UserId");
                    String name = resultSet.getString("Name");
                    String surname = resultSet.getString("Surname");
                    User user = builder.buildId(userId)
                            .buildName(name)
                            .buildSurname(surname)
                            .buildUserRole(UserRole.VISITOR)
                            .build();
                    users.add(user);
                }
                return users;
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User getVisitor(int id) throws DAOException {
        try {
            return executor.executeQuery(GET_VISITOR, statement -> {
                statement.setInt(1, id);
            }, resultSet -> {
                if (!resultSet.next()) {
                    return null;
                }
                String name = resultSet.getString(Constants.User.NAME);
                String surname = resultSet.getString(Constants.User.SURNAME);
                UserBuilder userBuilder = new UserBuilder();
                return userBuilder.buildId(id)
                        .buildName(name)
                        .buildSurname(surname)
                        .build();
            });
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

}
