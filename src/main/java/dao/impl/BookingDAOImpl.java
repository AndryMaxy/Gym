package dao.impl;

import dao.BookingDAO;
import dao.Executor;
import dao.StatementHandler;
import dao.exception.DAOException;
import dao.exception.ExecutorException;
import entity.Booking;
import entity.Membership;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private static final String ADD = "INSERT INTO Booking ( UserId, MembershipId, VisitCountLeft ) VALUE ( ?, ?, ? )";
    private static final String SELECT_ALL = "SELECT b.BookingId, b.VisitCountLeft, b.Feedback, m.Name FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId WHERE UserId = ?";
    private static final String UPDATE_BALANCE = "UPDATE User SET Balance = ? WHERE UserId = ?";
    //TODO ADD FEEDBACK and count
    private static final String UPDATE = "UPDATE Booking SET VisitCountLeft = ?, Feedback = ? WHERE BookingId = ?";
    private static final String UPDATE_FEEDBACK = "UPDATE User SET Balance = ? WHERE UserId = ?";
    private static final String UPDATE_VISIT_COUNT = "UPDATE Booking SET VisitCountLeft = ? WHERE UserId = ?";

    private static final String SELECT = "SELECT b.BookingId, b.VisitCountLeft, b.Feedback, m.Name FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId WHERE BookingId = ?";
    private static final String SELECT_BY_USER_ID = "SELECT b.BookingId, b.VisitCountLeft, b.Feedback, m.Name FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId WHERE UserId = ?";

    private final Executor executor = Executor.getInstance();


    private static class OrderDAOImplHolder {
        private static final BookingDAOImpl INSTANCE = new BookingDAOImpl();
    }

    private BookingDAOImpl(){}

    public static BookingDAOImpl getInstance() {
        return OrderDAOImplHolder.INSTANCE;
    }

    //TODO TRANSACTION NEEDED  / done?
//    @Override
////    public boolean add(int userId, int balance, Membership membership) throws DAOException {
////        ConnectionPool pool = ConnectionPool.getInstance();
////        ProxyConnection connection = pool.getConnection();
////        try {
////            connection.setAutoCommit(false);
////            try (PreparedStatement addBooking = connection.prepareStatement(ADD)) {
////                try (PreparedStatement updateUser = connection.prepareStatement(UPDATE_BALANCE)) {
////                    addBooking.setInt(1, userId);
////                    addBooking.setInt(2, membership.getId());
////                    addBooking.setInt(3, membership.getCount());
////                    addBooking.executeUpdate();
////                    updateUser.setInt(1, balance);
////                    updateUser.setInt(2, userId);
////                    updateUser.executeUpdate();
////                    connection.commit();
////                    addBooking.close(); //TODO NEEDED?
////                    updateUser.close();
////                    connection.setAutoCommit(true);
////                    connection.close();
////                    return true;
////                }
////            }
////        } catch (SQLException e) {
////            connection.rollback();
////            throw new DAOException(e);
////        }
////    }

    @Override
    public void add(int userId, int balance, Membership membership) throws DAOException {
        String[] query = new String[]{ADD, UPDATE_BALANCE};
        StatementHandler[] statementHandlers = new StatementHandler[query.length];
        statementHandlers[0] = statement -> {
            statement.setInt(1, userId);
            statement.setInt(2, membership.getId());
            statement.setInt(3, membership.getCount());
        };
        statementHandlers[1] = statement -> {
            statement.setInt(1, balance);
            statement.setInt(2, userId);
        };
        try {
            executor.executeTransaction(query, statementHandlers);
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Booking get(int bookingId) throws DAOException {
        return getById(SELECT, bookingId);
    }

    @Override
    public Booking getByUserId(int userId) throws DAOException {
        return getById(SELECT_BY_USER_ID, userId);
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

    @Override
    public void update(Booking booking) throws DAOException {
        try {
            executor.execute(UPDATE, statement -> {
                statement.setInt(1, booking.getVisitCountLeft());
                statement.setString(2, booking.getFeedback());
                statement.setInt(3, booking.getId());
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException();
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

    private Booking getById(String query, int id) throws DAOException {
        try {
            return executor.executeQuery(query, statement -> {
                statement.setInt(1, id);
            }, resultSet -> {
                if (!resultSet.last()) {
                    return null;
                }
                return newBooking(resultSet, id);
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
