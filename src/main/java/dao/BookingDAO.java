package dao;

import dao.exception.DAOException;
import entity.Booking;
import entity.Membership;

import java.util.List;

public interface BookingDAO {

    boolean add(int userId, int balance, Membership membership) throws DAOException;
    List<Booking> getAll(int userId) throws DAOException;
    boolean update() throws DAOException;
    boolean delete() throws DAOException;
    Booking get(int userId) throws DAOException;
}
