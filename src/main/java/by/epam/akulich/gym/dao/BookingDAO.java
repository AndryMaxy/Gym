package by.epam.akulich.gym.dao;

import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Membership;

import java.util.List;

/**
 * DAO interface to interact with booking table of database.
 *
 * @author Andrey Akulich
 */
public interface BookingDAO {

    /**
     * Adds new booking and changes user balance.
     *
     * @param userId a user identifier
     * @param balance a user balance
     * @param membership a membership which bought
     * @throws DAOException if the query failed
     */
    void add(int userId, int balance, Membership membership) throws DAOException;

    /**
     * Returns a list of all bookings.
     *
     * @return a list of all bookings
     * @throws DAOException if the query failed
     */
    List<Booking> getAll() throws DAOException;

    /**
     * Returns a list of booking of user by user identifier.
     *
     * @param userId a user identifier.
     * @return a list of booking of user by user identifier
     * @throws DAOException if the query failed
     */
    List<Booking> getUserBookingList(int userId) throws DAOException;

    /**
     * Returns {@link Booking} instance by booking identifier.
     *
     * @param bookingId a booking identifier
     * @return {@link Booking} instance by booking identifier
     * @throws DAOException if the query failed.
     */
    Booking get(int bookingId) throws DAOException;

    /**
     * Returns {@link Booking} instance by user identifier.
     *
     * @param userId a user id
     * @return {@link Booking} instance by user identifier
     * @throws DAOException if the query failed
     */
    Booking getByUserId(int userId) throws DAOException;

    /**
     * Updates {@link Booking} information in database.
     *
     * @param booking {@link Booking} instance
     * @throws DAOException if the query failed
     */
    void update(Booking booking) throws DAOException;
}
