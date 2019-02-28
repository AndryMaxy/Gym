package service.impl;

import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.UserDAOImpl;
import entity.User;
import entity.UserRole;
import service.UserService;
import service.exception.ServiceException;
import util.Encoder;
import util.exception.EncoderException;
import validator.ParameterValidator;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = UserDAOImpl.getInstance();

    private static class UserServiceImplHolder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return UserServiceImplHolder.INSTANCE;
    }

    private final ParameterValidator validator = ParameterValidator.getInstance();

    private UserServiceImpl(){}

    @Override
    public void add(User user) throws ServiceException {
        try {
            userDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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
        if (!validator.validateLogin(login)) {
            return false;
        }
        try {
            User user = userDAO.getByLogin(login);
            return user != null;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(String id) throws ServiceException {
        if (!validator.validateNumber(id)) {
            return null;
        }
        try {
            int userId = Integer.parseInt(id);
            return userDAO.getById(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int logIn(String login, char[] password) throws ServiceException, EncoderException {
        if (!validator.validateLogin(login) || !validator.validatePassword(password)) {
            return 0;
        }
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
    public User getVisitor(String id) throws ServiceException {
        if (!validator.validateNumber(id)) {
            return null;
        }
        int userId = Integer.parseInt(id);
        try {
            return userDAO.getVisitor(userId);
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
    public void delete(String id) throws ServiceException {
        if (!validator.validateNumber(id)) {
            return;
        }
        int userId = Integer.parseInt(id);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeRole(String id, String roleStr) throws ServiceException {
        if (!validator.validateNumber(id) || !validator.validateRole(roleStr)) {
            return;
        }
        User user = getUser(id);
        UserRole userRole = UserRole.valueOf(roleStr);
        user.setRole(userRole);
        update(user);
    }

    @Override
    public void changeDiscount(String id, String discountStr) throws ServiceException {
        if (!validator.validateNumber(id) || !validator.validateDiscount(discountStr)) {
            return;
        }
        User user = getUser(id);
        int discount = Integer.parseInt(discountStr);
        user.setDiscount(discount);
        update(user);
    }
}
