package dao.impl;

import dao.BookingDAO;
import dao.Executor;
import dao.StatementHandler;
import dao.exception.DAOException;
import dao.exception.ExecutorException;
import entity.Booking;
import entity.Membership;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private static final String INSERT = "INSERT INTO Booking ( UserId, MembershipId, VisitCountLeft, NeedAppointment ) VALUE ( ?, ?, ?, ? )";
    private static final String SELECT_ALL_BY_USER_ID = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId WHERE b.UserId = ?";
    private static final String UPDATE_BALANCE = "UPDATE User SET Balance = ? WHERE UserId = ?";
    private static final String UPDATE = "UPDATE Booking SET VisitCountLeft = ?, NeedAppointment = ?, Feedback = ? WHERE BookingId = ?";
    private static final String SELECT = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId WHERE BookingId = ?";
    private static final String SELECT_BY_USER_ID = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId WHERE b.UserId = ?";
    private static final String SELECT_ALL = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId";

    private final Executor executor = Executor.getInstance();

    private static class OrderDAOImplHolder {
        private static final BookingDAOImpl INSTANCE = new BookingDAOImpl();
    }

    private BookingDAOImpl(){}

    public static BookingDAOImpl getInstance() {
        return OrderDAOImplHolder.INSTANCE;
    }

    @Override
    public void add(int userId, int balance, Membership membership) throws DAOException {
        String[] query = new String[]{INSERT, UPDATE_BALANCE};
        StatementHandler[] statementHandlers = new StatementHandler[query.length];
        statementHandlers[0] = statement -> {
            statement.setInt(1, userId);
            statement.setInt(2, membership.getId());
            statement.setInt(3, membership.getCount());
            statement.setBoolean(4, true);
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
    public List<Booking> getAll() throws DAOException {
        try {
            return executor.executeQuery(SELECT_ALL, this::getBookings);
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Booking> getUserBookingList(int userId) throws DAOException {
        try {
            return executor.executeQuery(SELECT_ALL_BY_USER_ID, statement -> {
                statement.setInt(1, userId);
            }, this::getBookings);
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Booking booking) throws DAOException {
        try {
            executor.execute(UPDATE, statement -> {
                statement.setInt(1, booking.getVisitCountLeft());
                statement.setInt(2, booking.isNeedAppointment() ? 1 : 0);
                statement.setString(3, booking.getFeedback());
                statement.setInt(4, booking.getId());
            });
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    private Booking getById(String query, int id) throws DAOException {
        try {
            return executor.executeQuery(query, statement -> {
                statement.setInt(1, id);
            }, resultSet -> {
                if (!resultSet.last()) {
                    return null;
                }
                return parseBooking(resultSet);
            });
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    private List<Booking> getBookings(ResultSet resultSet) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (resultSet.next()) {
            Booking booking = parseBooking(resultSet);
            bookings.add(booking);
        }
        return bookings;
    }

    private Booking parseBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        int bookingId = resultSet.getInt("BookingId");
        int visitCountLeft = resultSet.getInt("VisitCountLeft");
        String feedback = resultSet.getString("Feedback");
        String membershipSte = resultSet.getString("mName").toUpperCase();
        boolean needAppointment = resultSet.getInt("NeedAppointment") == 1;
        int userId = resultSet.getInt("UserId");
        String userName = resultSet.getString("uName");
        Membership membership = Membership.valueOf(membershipSte);
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        booking.setUser(user);
        booking.setId(bookingId);
        booking.setMembership(membership);
        booking.setVisitCountLeft(visitCountLeft);
        booking.setNeedAppointment(needAppointment);
        booking.setFeedback(feedback);
        return booking;
    }
}
