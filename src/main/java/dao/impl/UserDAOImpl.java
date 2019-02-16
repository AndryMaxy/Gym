package dao.impl;

import builder.UserBuilder;
import connection.ConnectionPool;
import connection.ProxyConnection;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.exception.GeneratedKeyException;
import entity.Exercise;
import entity.Product;
import entity.User;
import entity.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String ADD =
            "INSERT INTO User ( UserRoleId, Login, Hash, Salt, Name, Surname, Balance ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    private static final String UPDATE =
            "UPDATE User SET UserRoleId = ?, Login = ?, Hash = ?, Salt = ?, Name = ?, Surname = ?, Discount = ?, Balance = ? WHERE UserId = ?";
    private static final String GER_USER_BY_LOGIN =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId WHERE Login = ?";
    private static final String GER_USER_BY_ID =
            "SELECT * FROM User u JOIN UserRole r on u.UserRoleId = r.UserRoleId WHERE UserId = ?";
    private static final String GET_EXERCISES =
            "SELECT e.Name, a.RepCount, a.SetCount, a.Weight FROM Exercise e JOIN ExerciseAppointment a on e.ExerciseId = a.ExerciseId WHERE a.UserId = ?";
    private static final String GET_PRODUCTS =
            "SELECT p.Name, a.GramInDay FROM Product p JOIN ProductAppointment a on p.ProductId = a.ProductId WHERE a.UserId = ?";
    private static final String DELETE = "DELETE FROM User WHERE UserId = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static class UserDAOHolder {
        static final UserDAOImpl INSTANCE = new UserDAOImpl();
    }

    private UserDAOImpl(){}

    public static UserDAOImpl getInstance(){
        return UserDAOHolder.INSTANCE;
    }

    @Override
    public boolean isUserLoginExist(String login) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GER_USER_BY_LOGIN)) {
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User getById(int id) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GER_USER_BY_ID)) {
                statement.setInt(1, id);
                return getUser(statement);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User getByLogin(String login) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GER_USER_BY_LOGIN)) {
                statement.setString(1, login);
                return getUser(statement);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private User getUser(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
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
    @Override
    public boolean add(User user) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(ADD)) {
                statement.setInt(1, UserRole.VISITOR.getId());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getHash());
                statement.setString(4, user.getSalt());
                statement.setString(5, user.getName());
                statement.setString(6, user.getSurname());
                statement.setInt(7, user.getBalance());
                return statement.execute();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private int getLastInsertId(PreparedStatement statement) throws SQLException, GeneratedKeyException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }
        throw new GeneratedKeyException("No generated keys.");
    }

    @Override
    public List<Exercise> getExercises(int id) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_EXERCISES)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                List<Exercise> exercises = new ArrayList<>();
                while (resultSet.next()) {
                    Exercise exercise = new Exercise();
                    String name = resultSet.getString("Name");
                    int repCount = resultSet.getInt("RepCount");
                    int setCount = resultSet.getInt("SetCount");
                    int weight = resultSet.getInt("Weight");
                    exercise.setName(name);
                    exercise.setRepCount(repCount);
                    exercise.setSetCount(setCount);
                    exercise.setWeight(weight);
                    exercises.add(exercise);
                }
                return exercises;
            } catch (SQLException e) {
                throw new DAOException();
            }
        }
    }

    @Override
    public List<Product> getProducts(int id) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    Product product = new Product();
                    String name = resultSet.getString("Name");
                    int gramInDay = resultSet.getInt("GramInDay");
                    product.setName(name);
                    product.setGramInDay(gramInDay);
                    products.add(product);
                }
                return products;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    private void hh() throws DAOException{
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_EXERCISES)) {

            } catch (SQLException e) {
                throw new DAOException();
            }
        }
    }

    @Override
    public boolean update(User user) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                statement.setInt(1, user.getUserRole().getId());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getHash());
                statement.setString(4, user.getSalt());
                statement.setString(5, user.getName());
                statement.setString(6, user.getSurname());
                statement.setInt(7, user.getDiscount());
                statement.setInt(8, user.getBalance());
                statement.setInt(9, user.getId());
                return statement.execute();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean delete(int id) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
                statement.setInt(1, id);
                return statement.execute();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
