package dao;

import dao.exception.DAOException;
import entity.Exercise;
import entity.Product;
import entity.User;

import java.util.List;

public interface VisitorDAO {

    boolean add(User entity) throws DAOException;
    boolean update(User user) throws DAOException;
    User getByLogin(String login) throws DAOException;
    User getById(int id) throws DAOException;
    boolean isUserLoginExist(String login) throws DAOException;
}
