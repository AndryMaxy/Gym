package dao.impl;

import builder.UserBuilder;
import dao.AbstractUserDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import entity.Constants;
import entity.User;
import entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends AbstractUserDAO implements UserDAO {
    private static final String INSERT =
            "INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname, Balance ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    private static final String SELECT_USER_BY_LOGIN =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId WHERE Login = ?";
    private static final String SELECT_USER_BY_ID =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId WHERE UserId = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId";
    private static final String SELECT_VISITORS =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId LEFT JOIN ExerciseAppointment e on u.UserId = e.UserId WHERE u.UserRoleId = 3 && e.UserId IS NULL";
    private static final String SELECT_VISITOR =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId LEFT JOIN ExerciseAppointment e on u.UserId = e.UserId WHERE u.UserRoleId = 3 && e.UserId IS NULL && u.UserId = ?";
    private static final String UPDATE =
            "UPDATE User SET UserRoleId = ?, Login = ?, Hash = ?, Salt = ?, Name = ?, Surname = ?, Discount = ?, Balance = ? WHERE UserId = ?";
    private static final String DELETE = "DELETE FROM User WHERE UserId = ?";


    private static class UserDAOHolder {
        static final UserDAOImpl INSTANCE = new UserDAOImpl();
    }

    private UserDAOImpl() { }

    public static UserDAOImpl getInstance() {
        return UserDAOHolder.INSTANCE;
    }

    @Override
    public List<User> getAll() throws DAOException {
        return getUserList(SELECT_ALL);
    }


    public User getById(int userId) throws DAOException {
        return getUser(SELECT_USER_BY_ID, userId);
    }

    public User getByLogin(String login) throws DAOException {
        try {
            return executor.executeQuery(SELECT_USER_BY_LOGIN, statement -> {
                statement.setString(1, login);
            }, resultSet -> {
                if (!resultSet.next()) {
                    return null;
                }
                return newUser(resultSet);
            });
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    public User getVisitor(int userId) throws DAOException {
        return getUser(SELECT_VISITOR, userId);
    }

    public List<User> getVisitors() throws DAOException {
        return getUserList(SELECT_VISITORS);
    }

    @Override
    public boolean add(User user) throws DAOException {
        try {
            return executor.execute(INSERT, statement -> {
                statement.setInt(1, UserRole.VISITOR.getId());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getHash());
                statement.setString(4, user.getSalt());
                statement.setString(5, user.getName());
                statement.setString(6, user.getSurname());
                statement.setInt(7, user.getBalance());
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try {
            executor.execute(UPDATE, statement -> {
                statement.setInt(1, user.getRole().getId());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getHash());
                statement.setString(4, user.getSalt());
                statement.setString(5, user.getName());
                statement.setString(6, user.getSurname());
                statement.setInt(7, user.getDiscount());
                statement.setInt(8, user.getBalance());
                statement.setInt(9, user.getId());
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int userId) throws DAOException {
        try {
            executor.execute(DELETE, statement -> {
                statement.setInt(1, userId);
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private List<User> getUserList(String query) throws DAOException {
        try {
            return executor.executeQuery(query, statement -> {}, resultSet -> {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = newUser(resultSet);
                    users.add(user);
                }
                return users;
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private User getUser(String query, int userId) throws DAOException {
        try {
            return executor.executeQuery(query, statement -> {
                statement.setInt(1, userId);
            }, resultSet -> {
                if (!resultSet.next()) {
                    return null;
                }
                return newUser(resultSet);
            });
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    private User newUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(Constants.User.ID);
        String login = resultSet.getString(Constants.User.LOGIN);
        String name = resultSet.getString(Constants.User.NAME);
        String surname = resultSet.getString(Constants.User.SURNAME);
        String hash = resultSet.getString(Constants.User.HASH);
        String salt = resultSet.getString(Constants.User.SALT);
        String roleString = resultSet.getString(Constants.User.ROLE);
        int discount = resultSet.getInt(Constants.User.DISCOUNT);
        int balance = resultSet.getInt(Constants.User.BALANCE);
        UserRole userRole = UserRole.valueOf(roleString.toUpperCase());
        UserBuilder builder = new UserBuilder();
        return builder.buildId(id)
                .buildLogin(login)
                .buildName(name)
                .buildHash(hash)
                .buildSurname(surname)
                .buildSalt(salt)
                .buildUserRole(userRole)
                .buildDiscount(discount)
                .buildBalance(balance)
                .build();
    }
}
