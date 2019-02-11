package dao.impl;

import builder.VisitorBuilder;
import connection.ProxyConnection;
import connection.exception.ConnectionException;
import dao.DAO;
import dao.exception.DAOException;
import entity.Visitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VisitorDao extends DAO<Integer, Visitor> {

    private static final String ADD = "INSERT INTO Visitor ( UserId, Discount, Vip, Regular, Balance) VALUES ( LAST_INSERT_ID(), ?, ?, ?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM Visitor v join User u on v.UserId = u.UserId where v.UserId = ?";
    private static final String GET_BY_LOGIN = "SELECT * FROM Visitor v join User u on v.UserId = u.UserId where v.Login = ?";

    public VisitorDao(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Visitor> getAll() {
        return null;
    }

    public Visitor getByLogin(String login) throws DAOException {
        Visitor visitor;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_LOGIN)) {
            statement.setString(1, login);
            visitor = getVisitor(statement);
        } catch (ConnectionException | SQLException e) {
            throw new DAOException(e);
        }
        return visitor;
    }

    private Visitor getVisitor(PreparedStatement statement) throws SQLException {
        VisitorBuilder builder = new VisitorBuilder();
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String name = resultSet.getString("Name");
        String surname = resultSet.getString("Surname");
        int balance = resultSet.getInt("Balance");
        return builder.buildName(name)
                .buildSurname(surname)
                .buildBalance(balance)
                .build();
    }

    @Override
    public Visitor getById(Integer id) throws DAOException {
        Visitor visitor;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            visitor = getVisitor(statement);
            visitor.setId(id);
        } catch (ConnectionException | SQLException e) {
            throw new DAOException(e);
        }
        return visitor;
    }

    @Override
    public int add(Visitor visitor) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(ADD)) {
            statement.setInt(1, visitor.getDiscount());
            statement.setBoolean(2, visitor.isVip());
            statement.setBoolean(3, visitor.isRegular());
            statement.setInt(4, visitor.getBalance());
            statement.execute();
        } catch (ConnectionException | SQLException e) {
            throw new DAOException(e);
        }
        return 1;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Visitor entity) {
        return false;
    }

    @Override
    public boolean update(Visitor entity) {
        return false;
    }
}
