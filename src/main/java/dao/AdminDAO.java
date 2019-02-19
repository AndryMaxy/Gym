package dao;

import dao.exception.DAOException;

public interface AdminDAO {

    boolean delete(int userId) throws DAOException;
}
