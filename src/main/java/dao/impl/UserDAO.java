package dao.impl;

import builder.UserBuilder;
import connection.ProxyConnection;
import dao.DAO;
import dao.exception.DAOException;
import dao.exception.DuplicateInsertException;
import dao.exception.GeneratedKeyException;
import entity.User;
import connection.exception.ConnectionException;
import entity.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;

public class UserDAO extends DAO<Integer, User> {
    private static final String ADD_USER = "INSERT INTO User " +
            "( UserRoleId, Login, Hash, Salt, Name, Surname ) " +
            "VALUES ( ?, ?, ?, ?, ?, ?)";
    private static final String GER_USER_LOGIN = "SELECT * FROM USER WHERE Login = ?";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    public User getByLogin(String login) throws DAOException {
        User user;
        try (PreparedStatement statement = connection.prepareStatement(GER_USER_LOGIN)) {
            statement.setString(1, login);
            user = getUser(statement);
        } catch (ConnectionException | SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    private User getUser(PreparedStatement statement) throws SQLException {
        UserBuilder builder = new UserBuilder();
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String name = resultSet.getString("Name");
        String surname = resultSet.getString("Surname");
        String hash = resultSet.getString("Hash");
        String salt = resultSet.getString("Salt");
        return builder.buildName(name)
                .buildSurname(surname)
                .buildHash(hash)
                .buildSalt(salt)
                .build();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(Integer id) {
        return null;
    }
    //TODO спросить нормально ли возвращать lastInsertId
    @Override
    public int add(User user) throws DAOException, DuplicateInsertException {
        int id;
        try (PreparedStatement statement = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, UserRole.VISITOR.getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getHash());
            statement.setString(4, user.getSalt());
            statement.setString(5, user.getName());
            statement.setString(6, user.getSurname());
            statement.execute();
            id = getLastInsertId(statement);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateInsertException(e);
        } catch (ConnectionException | SQLException | GeneratedKeyException e) {
            throw new DAOException(e);
        }
        return id;
    }

    private int getLastInsertId(PreparedStatement statement) throws SQLException, GeneratedKeyException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }
        throw new GeneratedKeyException("No generated keys.");
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }
}
