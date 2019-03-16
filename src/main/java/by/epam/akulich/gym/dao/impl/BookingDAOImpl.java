package by.epam.akulich.gym.dao.impl;

import by.epam.akulich.gym.dao.BookingDAO;
import by.epam.akulich.gym.dao.Executor;
import by.epam.akulich.gym.dao.StatementHandler;
import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.dao.exception.ExecutorException;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Membership;
import by.epam.akulich.gym.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class is used for interacting with booking table of database.
 *
 * @author Andrey Akulich
 */
public class BookingDAOImpl implements BookingDAO {

    private static final String INSERT = "INSERT INTO Booking ( UserId, MembershipId, VisitCountLeft, NeedAppointment ) VALUE ( ?, ?, ?, ? )";
    private static final String SELECT_ALL_BY_USER_ID = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId WHERE b.UserId = ?";
    private static final String UPDATE_BALANCE = "UPDATE User SET Balance = ? WHERE UserId = ?";
    private static final String UPDATE = "UPDATE Booking SET VisitCountLeft = ?, NeedAppointment = ?, Feedback = ? WHERE BookingId = ?";
    private static final String SELECT = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId WHERE BookingId = ?";
    private static final String SELECT_BY_USER_ID = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId WHERE b.UserId = ?";
    private static final String SELECT_ALL = "SELECT b.BookingId, b.UserId, b.VisitCountLeft, b.NeedAppointment, b.Feedback, m.Name AS mName, u.Name AS uName FROM Booking b JOIN Membership m ON b.MembershipId = m.MembershipId JOIN User u ON b.UserId = u.UserId";

    /**
     * Executor instance.
     */
    private Executor executor = Executor.getInstance();

    /**
     * This class represents initialization-on-demand holder idiom for {@link BookingDAOImpl}.
     */
    private static class BookingDAOImplHolder {
        private static final BookingDAOImpl INSTANCE = new BookingDAOImpl();
    }

    /**
     * Constructs BookingDAOImpl.
     */
    private BookingDAOImpl(){}

    /**
     * @return BookingDAOImpl instance.
     */
    public static BookingDAOImpl getInstance() {
        return BookingDAOImplHolder.INSTANCE;
    }

    /**
     * Executes transaction which adds new booking and changes user's balance.
     *
     * @param userId a user identifier
     * @param balance a user balance
     * @param membership a membership which bought
     * @throws DAOException if the query failed
     */
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

    /**
     * Returns {@link Booking} instance by booking identifier.
     *
     * @param bookingId a booking identifier
     * @return {@link Booking} instance by booking identifier
     * @throws DAOException if the query failed.
     */
    @Override
    public Booking get(int bookingId) throws DAOException {
        return getById(SELECT, bookingId);
    }

    /**
     * Returns {@link Booking} instance by user identifier.
     *
     * @param userId a user id
     * @return {@link Booking} instance by user identifier
     * @throws DAOException if the query failed
     */
    @Override
    public Booking getByUserId(int userId) throws DAOException {
        return getById(SELECT_BY_USER_ID, userId);
    }

    /**
     * Returns a list of all bookings.
     *
     * @return a list of all bookings
     * @throws DAOException if the query failed
     */
    @Override
    public List<Booking> getAll() throws DAOException {
        try {
            return executor.executeQuery(SELECT_ALL, this::getBookings);
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Returns a list of booking of user by user identifier.
     *
     * @param userId a user identifier.
     * @return a list of booking of user by user identifier
     * @throws DAOException if the query failed
     */
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

    /**
     * Updates {@link Booking} information in database.
     *
     * @param booking {@link Booking} instance
     * @throws DAOException if the query failed
     */
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

    /**
     * Returns {@link Booking} instance by any identifier.
     *
     * @param id any id
     * @return {@link Booking} instance by user identifier
     * @throws DAOException if the query failed
     */
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

    /**
     * Handles {@link ResultSet} and returns a list of {@link Booking}.
     *
     * @return a list of bookings
     * @throws SQLException if a database access error occurs
     */
    private List<Booking> getBookings(ResultSet resultSet) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (resultSet.next()) {
            Booking booking = parseBooking(resultSet);
            bookings.add(booking);
        }
        return bookings;
    }

    /**
     * Parses {@link ResultSet} and returns {@link Booking}.
     *
     * @param resultSet a table of data representing a database result set, which
     *                  is usually generated by executing a statement that queries the database.
     * @return {@link Booking} parsed from {@link ResultSet}
     * @throws SQLException if a database access error occurs
     */
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
