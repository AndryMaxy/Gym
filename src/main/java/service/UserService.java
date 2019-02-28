package service;

import entity.User;
import service.exception.ServiceException;
import util.exception.EncoderException;

import java.util.List;

public interface UserService {

    boolean isUserLoginExist(String login) throws ServiceException;
    List<User> getAll() throws ServiceException;
    User getUser(String id) throws ServiceException;
    int logIn(String login, char[] password) throws ServiceException, EncoderException;
    User getVisitor(String id) throws ServiceException;
    List<User> getVisitors() throws ServiceException;
    void add(User user) throws ServiceException;
    void update(User user) throws ServiceException;
    void changeRole(String id, String roleStr) throws ServiceException;
    void changeDiscount(String id, String discount) throws ServiceException;
    void delete(String userId) throws ServiceException;
}
