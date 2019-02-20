package dao;

import dao.exception.DAOException;
import entity.Exercise;
import entity.Product;
import entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getAll() throws DAOException;
    User getById(int id) throws DAOException;
    User getByLogin(String login) throws DAOException;
    User getVisitor(int userId) throws DAOException;
    List<User> getVisitors() throws DAOException;
    boolean add(User entity) throws DAOException;
    void update(User user) throws DAOException;
    void delete(int userId) throws DAOException;
}
