package dao.impl;

import connection.ConnectionPool;
import connection.ProxyConnection;
import dao.OrderDAO;
import dao.exception.DAOException;
import entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    private static final String ADD = "INSERT INTO Booking ( UserId, VisitCountLeft ) VALUE ( ?, ? )";
    private static final String SELECT_BY_USER_ID = "SELECT BookingId, VisitCountLeft, Feedback FROM Booking WHERE UserId = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static class OrderDAOImplHolder {
        private static final OrderDAOImpl INSTANCE = new OrderDAOImpl();
    }

    public static OrderDAOImpl getInstance() {
        return OrderDAOImplHolder.INSTANCE;
    }

    //TODO TRANSACTION NEEDED
    @Override
    public boolean add(int userId, int visitCount) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD);
            statement.setInt(1, userId);
            statement.setInt(2, visitCount);
            Order order = new Order();
            order.setUserId(userId);
            order.setVisitCountLeft(visitCount);
            return true;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public Order get(int userId) throws DAOException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID)) {
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    return null;
                }
                Order order = new Order();
                int orderId = resultSet.getInt("BookingId");
                int visitCountLeft = resultSet.getInt("VisitCountLeft");
                String feedback = resultSet.getString("Feedback");
                order.setUserId(userId);
                order.setId(orderId);
                order.setVisitCountLeft(visitCountLeft);
                order.setFeedback(feedback);
                return order;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
