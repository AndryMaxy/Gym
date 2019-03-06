package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getAll() throws DAOException;
    User getById(int id) throws DAOException;
    User getByLogin(String login) throws DAOException;
    User getVisitor(int userId) throws DAOException;
    List<User> getVisitorsWithoutApp() throws DAOException;
    void add(User user) throws DAOException;
    void update(User user) throws DAOException;
    void delete(int userId) throws DAOException;
}
