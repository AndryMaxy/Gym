package dao.impl;

import builder.UserBuilder;
import dao.Executor;
import dao.VisitorDAO;
import dao.exception.DAOException;
import entity.Constants;
import entity.User;
import entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

//todo CLOSE RESULT SETS
public class VisitorDAOImpl implements VisitorDAO {
    //TODO REPLACE *
    private static final String ADD =
            "INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname, Balance ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    private static final String UPDATE =
            "UPDATE User SET UserRoleId = ?, Login = ?, Hash = ?, Salt = ?, Name = ?, Surname = ?, Discount = ?, Balance = ? WHERE UserId = ?";
    private static final String GET_USER_BY_LOGIN =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId WHERE Login = ?";
    private static final String GET_USER_BY_ID =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId WHERE UserId = ?";

    private final Executor executor = Executor.getInstance();

    private static class UserDAOHolder {
        static final VisitorDAOImpl INSTANCE = new VisitorDAOImpl();
    }

    private VisitorDAOImpl() { }

    public static VisitorDAOImpl getInstance() {
        return UserDAOHolder.INSTANCE;
    }


    @Override
    public boolean isUserLoginExist(String login) throws DAOException {
        try {
            return executor.executeQuery(GET_USER_BY_LOGIN, statement -> {
                statement.setString(1, login);
            }, ResultSet::next);
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public User getById(int id) throws DAOException {
        try {
            return executor.executeQuery(GET_USER_BY_ID, statement -> {
                statement.setInt(1, id);
            }, this::getUser);
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public User getByLogin(String login) throws DAOException {
        try {
            return executor.executeQuery(GET_USER_BY_LOGIN, statement -> {
                statement.setString(1, login);
            }, this::getUser);
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
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

    @Override
    public boolean add(User user) throws DAOException {
        try {
            return executor.execute(ADD, statement -> {
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
    public boolean update(User user) throws DAOException {
        try {
            return executor.execute(UPDATE, statement -> {
                statement.setInt(1, user.getUserRole().getId());
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
}
