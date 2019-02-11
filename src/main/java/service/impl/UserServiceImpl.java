package service.impl;

import connection.ProxyConnection;
import connection.exception.ConnectionException;
import dao.DAO;
import dao.exception.DAOException;
import dao.exception.DuplicateInsertException;
import dao.impl.UserDAO;
import dao.impl.VisitorDao;
import entity.User;
import entity.UserRole;
import entity.Visitor;
import service.Service;
import service.UserService;
import service.exception.ServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {
    //TODO СПРОСИТЬ МОЖНО ЛИ ВЫНЕСТИ DAO КАК ПЕРЕМЕННУЮ КЛАССА?
    // БУДЕТ ЛИ ПРОБЛЕМА МНОГОПОТОЧНОСТИ? И ВАЩЕ НОРМ ЛИ СТРУКТУРА



    public Visitor getByLogin2(String login) throws ServiceException {
        DAO<Integer, Visitor> dao = new VisitorDao(POOL.getConnection());
        Visitor visitor;
        try {
            visitor = dao.getByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    public User getByLogin(String login) throws ServiceException {
        DAO<Integer, User> dao = new UserDAO(POOL.getConnection());
        User user;
        try {
            user = dao.getByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        DAO<Integer, Visitor> visitorDAO = new VisitorDao(POOL.getConnection());
        if (user.getUserRole() == UserRole.VISITOR) {
            try {
                user = visitorDAO.getByLogin(login);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return user;
    }

    @Override
    public User getById(int id) throws ServiceException {
        DAO<Integer, Visitor> dao = new VisitorDao(POOL.getConnection());
        Visitor visitor;
        try {
            visitor = dao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return visitor;
    }

    @Override
    public void add(User visitor) throws ServiceException, DuplicateInsertException {
        ProxyConnection connection = null;
        try {
            connection = POOL.getConnection();
            connection.setAutoCommit(false);
            DAO<Integer, User> userDAO = new UserDAO(connection);
            int id = userDAO.add(visitor);
            visitor.setId(id);
            DAO<Integer, Visitor> visitorDAO = new VisitorDao(connection);
            visitorDAO.add((Visitor) visitor);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (DAOException | ConnectionException e) {
            try {
                connection.rollback(); //TODO КАК СДЕЛАТЬ КРАСИВЕЕ
            } catch (ConnectionException e2) {
                e.printStackTrace();
            }
            throw new ServiceException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
