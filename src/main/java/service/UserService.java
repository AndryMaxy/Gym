package service;

import entity.User;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import java.util.List;

public interface UserService {

    boolean isUserLoginExist(String login) throws ServiceException, InvalidInputException;
    List<User> getAll() throws ServiceException;
    User getUser(String id) throws ServiceException, InvalidInputException;
    int logIn(String login, char[] password) throws ServiceException, InvalidInputException;
    User getNewVisitor(String id) throws ServiceException, InvalidInputException;
    List<User> getVisitors() throws ServiceException;
    void add(User user) throws ServiceException;
    void changeRole(String id, String roleStr) throws ServiceException, InvalidInputException;
    void changeDiscount(String id, String discount) throws ServiceException, InvalidInputException;
    User createUser(String login, char[] password, String name, String surname) throws ServiceException, InvalidInputException;
    void delete(String userId) throws ServiceException, InvalidInputException;
}
