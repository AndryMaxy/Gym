package dao;

import dao.exception.DAOException;
import entity.Booking;
import entity.Membership;

import java.util.List;

public interface BookingDAO {

    void add(int userId, int balance, Membership membership) throws DAOException;
    List<Booking> getAll() throws DAOException;
    List<Booking> getUserBookingList(int userId) throws DAOException;
    void update(Booking booking) throws DAOException;
    Booking get(int bookingId) throws DAOException;
    Booking getByUserId(int userId) throws DAOException;
}
