package dao.impl;

import builder.UserBuilder;
import dao.Executor;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.exception.ExecutorException;
import entity.User;
import entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String INSERT =
            "INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname, Balance ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    private static final String SELECT_USER_BY_LOGIN =
            "SELECT u.UserId, u.Login, u.Name, u.Surname, u.Hash, u.Salt, u.Discount, u.Balance, r.Role FROM User u JOIN UserRole r ON u.UserRoleId = r.UserRoleId WHERE Login = ?";
    private static final String SELECT_USER_BY_ID =
            "SELECT u.UserId, u.Login, u.Name, u.Surname, u.Hash, u.Salt, u.Discount, u.Balance, r.Role FROM User u JOIN UserRole r ON u.UserRoleId = r.UserRoleId WHERE UserId = ?";
    private static final String SELECT_ALL =
            "SELECT u.UserId, u.Login, u.Name, u.Surname, u.Hash, u.Salt, u.Discount, u.Balance, r.Role FROM User u JOIN UserRole r ON u.UserRoleId = r.UserRoleId";
    private static final String SELECT_WITHOUT_APPOINTMENT =
            "SELECT u.UserId, u.Login, u.Name, u.Surname, u.Hash, u.Salt, u.Discount, u.Balance, r.Role FROM User u JOIN UserRole r ON u.UserRoleId = r.UserRoleId JOIN Booking b ON u.UserId = b.UserId LEFT JOIN ExerciseAppointment e ON b.BookingId = e.BookingId WHERE u.UserRoleId = 3 && e.BookingId IS NULL && b.NeedAppointment = 1";
    private static final String SELECT_VISITOR =
            "SELECT u.UserId, u.Login, u.Name, u.Surname, u.Hash, u.Salt, u.Discount, u.Balance, r.Role FROM User u JOIN UserRole r ON u.UserRoleId = r.UserRoleId JOIN Booking b ON u.UserId = b.UserId LEFT JOIN ExerciseAppointment e ON b.BookingId = e.BookingId WHERE u.UserRoleId = 3 && e.BookingId IS NULL && u.UserId = ?";
    private static final String UPDATE =
            "UPDATE User SET UserRoleId = ?, Login = ?, Hash = ?, Salt = ?, Name = ?, Surname = ?, Discount = ?, Balance = ? WHERE UserId = ?";
    private static final String DELETE = "DELETE FROM User WHERE UserId = ?";

    private Executor executor = Executor.getInstance();

    /**
     * This class represents initialization-on-demand holder idiom for {@link UserDAOImpl}
     */
    private static class UserDAOHolder {
        static final UserDAOImpl INSTANCE = new UserDAOImpl();
    }

    private UserDAOImpl() {}

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
                return parseUser(resultSet);
            });
        } catch (ExecutorException e) {
            throw new DAOException();
        }
    }

    public User getVisitor(int userId) throws DAOException {
        return getUser(SELECT_VISITOR, userId);
    }

    public List<User> getVisitorsWithoutApp() throws DAOException {
        return getUserList(SELECT_WITHOUT_APPOINTMENT);
    }

    @Override
    public void add(User user) throws DAOException {
        try {
            executor.execute(INSERT, statement -> {
                statement.setInt(1, UserRole.VISITOR.getId());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getHash());
                statement.setString(4, user.getSalt());
                statement.setString(5, user.getName());
                statement.setString(6, user.getSurname());
                statement.setInt(7, user.getBalance());
            });
        } catch (ExecutorException e) {
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
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int userId) throws DAOException {
        try {
            executor.execute(DELETE, statement -> {
                statement.setInt(1, userId);
            });
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    private List<User> getUserList(String query) throws DAOException {
        try {
            return executor.executeQuery(query, resultSet -> {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = parseUser(resultSet);
                    users.add(user);
                }
                return users;
            });
        } catch (ExecutorException e) {
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
                return parseUser(resultSet);
            });
        } catch (ExecutorException e) {
            throw new DAOException();
        }
    }

    private User parseUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("UserId");
        String login = resultSet.getString("Login");
        String name = resultSet.getString("Name");
        String surname = resultSet.getString("Surname");
        String hash = resultSet.getString("Hash");
        String salt = resultSet.getString("Salt");
        String roleString = resultSet.getString("Role");
        int discount = resultSet.getInt("Discount");
        int balance = resultSet.getInt("Balance");
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
