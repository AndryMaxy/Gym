package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface TrainerDAO {

    List<User> getVisitors() throws DAOException;
    User getVisitor(int userId) throws DAOException;
}
