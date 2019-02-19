package service;

import entity.User;
import service.exception.ServiceException;
import util.exception.EncoderException;

import java.util.List;

public interface UserService {

    boolean isUserLoginExist(String login) throws ServiceException;
    User getUserByLogin(String login) throws ServiceException;
    User getUser(int id) throws ServiceException;
    int logIn(String login, char[] password) throws ServiceException, EncoderException;
    User getVisitor(int id) throws ServiceException;
    List<User> getVisitors() throws ServiceException;
    boolean add(User entity) throws ServiceException;
}
