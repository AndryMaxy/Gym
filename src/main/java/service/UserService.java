package service;

import connection.ConnectionPool;
import dao.exception.DuplicateInsertException;
import entity.User;
import service.exception.ServiceException;

public interface UserService {

    ConnectionPool POOL = ConnectionPool.getInstance();

    User getByLogin(String login) throws ServiceException;
    User getById(int id) throws ServiceException;
    void add(User entity) throws ServiceException, DuplicateInsertException;
}
