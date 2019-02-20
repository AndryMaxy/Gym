package dao.impl;

import connection.ConnectionPool;
import connection.ProxyConnection;
import dao.BookingDAO;
import dao.Executor;
import dao.exception.DAOException;
import entity.Booking;
import entity.Membership;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private static final String ADD = "INSERT INTO Booking ( UserId, MembershipId, VisitCountLeft ) VALUE ( ?, ?, ? )";
    private static final String SELECT_ALL = "SELECT b.BookingId, b.VisitCountLeft, b.Feedback, m.Name FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId WHERE UserId = ?";
    private static final String UPDATE_BALANCE = "UPDATE User SET Balance = ? WHERE UserId = ?";
    //TODO ADD FEEDBACK and count
    private static final String UPDATE_FEEDBACK = "UPDATE User SET Balance = ? WHERE UserId = ?";
    private static final String UPDATE_VISIT_COUNT = "UPDATE Booking SET VisitCountLeft = ? WHERE UserId = ?";

    private static final String SELECT = "SELECT b.BookingId, b.VisitCountLeft, b.Feedback, m.Name FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId WHERE UserId = ?";

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
                    addBooking.setInt(2, membership.getId());
                    addBooking.setInt(3, membership.getCount());
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
            return executor.executeQuery(SELECT, statement -> {
                statement.setInt(1, userId);
            }, resultSet -> {
                if (!resultSet.last()) {
                    return null;
                }
                return newBooking(resultSet, userId);
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Booking> getAll(int userId) throws DAOException {
        try {
            return executor.executeQuery(SELECT_ALL, statement -> {
                statement.setInt(1, userId);
            }, resultSet -> {
                List<Booking> bookings = new ArrayList<>();
                while (resultSet.next()) {
                    Booking booking = newBooking(resultSet, userId);
                    bookings.add(booking);
                }
                return bookings;
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Booking newBooking(ResultSet resultSet, int userId) throws SQLException {
        Booking booking = new Booking();
        int bookingId = resultSet.getInt("BookingId");
        String membershipSte = resultSet.getString("Name").toUpperCase();
        int visitCountLeft = resultSet.getInt("VisitCountLeft");
        String feedback = resultSet.getString("Feedback");
        Membership membership = Membership.valueOf(membershipSte);
        booking.setUserId(userId);
        booking.setId(bookingId);
        booking.setMembership(membership);
        booking.setVisitCountLeft(visitCountLeft);
        booking.setFeedback(feedback);
        return booking;
    }
}
