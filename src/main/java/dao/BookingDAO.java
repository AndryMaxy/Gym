package dao;

import dao.exception.DAOException;
import entity.Booking;
import entity.Membership;

public interface BookingDAO {

    boolean add(int userId, int balance, Membership membership) throws DAOException;
    boolean update() throws DAOException;
    boolean delete() throws DAOException;
    Booking get(int userId) throws DAOException;
}
