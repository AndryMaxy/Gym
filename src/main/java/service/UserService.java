package service;

import entity.User;
import service.exception.ServiceException;
import util.exception.EncoderException;

import java.util.List;

public interface UserService {

    boolean isUserLoginExist(String login) throws ServiceException;
    List<User> getAll() throws ServiceException;
    User getUser(int id) throws ServiceException;
    int logIn(String login, char[] password) throws ServiceException, EncoderException;
    User getVisitor(int id) throws ServiceException;
    List<User> getVisitors() throws ServiceException;
    boolean add(User user) throws ServiceException;
    void update(User user) throws ServiceException;
    void delete(int userId) throws ServiceException;
}
