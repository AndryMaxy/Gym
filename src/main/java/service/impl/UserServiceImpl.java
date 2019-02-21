package service.impl;

import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.UserDAOImpl;
import entity.User;
import service.UserService;
import service.exception.ServiceException;
import util.Encoder;
import util.exception.EncoderException;

import java.util.List;
//TODO MB SEPARATE BY ROLE
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = UserDAOImpl.getInstance();

    private static class UserServiceImplHolder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return UserServiceImplHolder.INSTANCE;
    }

    private UserServiceImpl(){}

    @Override
    public List<User> getAll() throws ServiceException {
        try {
            return userDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserLoginExist(String login) throws ServiceException {
        try {
            User user = userDAO.getByLogin(login);
            return user != null;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(int id) throws ServiceException {
        try {
            return userDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int logIn(String login, char[] password) throws ServiceException, EncoderException {
        try {
            User user = userDAO.getByLogin(login);
            if (user == null) {
                return 0;
            }
            String hash = user.getHash();
            String salt = user.getSalt();
            boolean correct = Encoder.check(password, hash.getBytes(), salt.getBytes());
            if (correct){
                return user.getId();
            } else {
                return 0;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getVisitors() throws ServiceException {
        try {
            return userDAO.getVisitors();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getVisitor(int id) throws ServiceException {
        try {
            return userDAO.getVisitor(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void add(User user) throws ServiceException {
        try {
            userDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int userId) throws ServiceException {
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
