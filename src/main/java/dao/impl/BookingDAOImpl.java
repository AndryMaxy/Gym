package dao.impl;

import connection.ConnectionPool;
import connection.ProxyConnection;
import dao.BookingDAO;
import dao.Executor;
import dao.exception.DAOException;
import entity.Booking;
import entity.Membership;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDAOImpl implements BookingDAO {

    private static final String ADD = "INSERT INTO Booking ( UserId, VisitCountLeft ) VALUE ( ?, ? )";
    private static final String UPDATE_BALANCE = "UPDATE User SET Balance = ? WHERE UserId = ?";
    private static final String SELECT_BY_USER_ID = "SELECT BookingId, VisitCountLeft, Feedback FROM Booking WHERE UserId = ?";

    private final Executor executor = Executor.getInstance();


    private static class OrderDAOImplHolder {
        private static final BookingDAOImpl INSTANCE = new BookingDAOImpl();
    }

    private BookingDAOImpl(){}

    public static BookingDAOImpl getInstance() {
        return OrderDAOImplHolder.INSTANCE;
    }

    //TODO TRANSACTION NEEDED  / done?
    @Override
    public boolean add(int userId, int balance, Membership membership) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement addBooking = connection.prepareStatement(ADD)) {
                try (PreparedStatement updateUser = connection.prepareStatement(UPDATE_BALANCE)) {
                    addBooking.setInt(1, userId);
                    addBooking.setInt(2, membership.getCount());
                    addBooking.executeUpdate();
                    updateUser.setInt(1, balance);
                    updateUser.setInt(2, userId);
                    updateUser.executeUpdate();
                    connection.commit();
                    connection.setAutoCommit(true);
                    connection.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
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
    public Booking get(int userId) throws DAOException {
        try {
            return executor.executeQuery(SELECT_BY_USER_ID, statement -> {
                statement.setInt(1, userId);
            }, resultSet -> {
                if (!resultSet.next()) {
                    return null;
                }
                Booking booking = new Booking();
                int bookingId = resultSet.getInt("BookingId");
                int visitCountLeft = resultSet.getInt("VisitCountLeft");
                String feedback = resultSet.getString("Feedback");
                booking.setUserId(userId);
                booking.setId(bookingId);
                booking.setVisitsLeft(visitCountLeft);
                booking.setFeedback(feedback);
                return booking;
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
