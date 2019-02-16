package dao;

import dao.exception.DAOException;
import entity.Order;

public interface OrderDAO {

    boolean add(int userId, int visitCount) throws DAOException;
    boolean update() throws DAOException;
    boolean delete() throws DAOException;
    Order get(int userId) throws DAOException;
}
