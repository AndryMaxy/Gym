package service.impl;

import builder.UserBuilder;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.UserDAOImpl;
import entity.User;
import entity.UserRole;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import util.Encoder;
import util.exception.EncoderException;
import service.ParameterValidator;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = UserDAOImpl.getInstance();
    private Encoder encoder = Encoder.getInstance();
    private ParameterValidator validator = ParameterValidator.getInstance();

    private static class UserServiceImplHolder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return UserServiceImplHolder.INSTANCE;
    }

    private UserServiceImpl(){}

    private UserServiceImpl(UserDAO userDAO, Encoder encoder){
        this.userDAO = userDAO;
        this.encoder = encoder;
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
    public List<User> getAll() throws ServiceException {
        try {
            return userDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserLoginExist(String login) throws ServiceException, InvalidInputException {
        if (!validator.validateLogin(login)) {
            throw new InvalidInputException();
        }
        try {
            User user = userDAO.getByLogin(login);
            return user != null;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(String id) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(id)) {
            throw new InvalidInputException();
        }
        try {
            int userId = Integer.parseInt(id);
            return userDAO.getById(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int logIn(String login, char[] password) throws ServiceException, InvalidInputException {
        if (!validator.validateLogin(login) || !validator.validatePassword(password)) {
            throw new InvalidInputException();
        }
        try {
            User user = userDAO.getByLogin(login);
            if (user == null) {
                return 0;
            }
            String hash = user.getHash();
            String salt = user.getSalt();
            boolean correct;
            try {
                correct = encoder.check(password, hash.getBytes(), salt.getBytes());
            } catch (EncoderException e) {
                throw new ServiceException(e);
            }
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
            return userDAO.getVisitorsWithoutApp();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getNewVisitor(String id) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(id)) {
            throw new InvalidInputException();
        }
        int userId = Integer.parseInt(id);
        try {
            return userDAO.getVisitor(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String id) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(id)) {
            throw new InvalidInputException();
        }
        int userId = Integer.parseInt(id);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User createUser(String login, char[] password, String name, String surname) throws ServiceException, InvalidInputException {
        if (!validator.validateName(name) || !validator.validateName(surname) || !validator.validatePassword(password)) {
            throw new InvalidInputException();
        }
        String[] encoded;
        try {
            encoded = encoder.encode(password);
        } catch (EncoderException e) {
            throw new ServiceException(e);
        }
        String hash = encoded[0];
        String salt = encoded[1];
        UserRole role = UserRole.VISITOR;
        UserBuilder builder = new UserBuilder();
        return builder.buildLogin(login)
                .buildName(name)
                .buildSurname(surname)
                .buildUserRole(role)
                .buildHash(hash)
                .buildSalt(salt)
                .build();
    }

    @Override
    public void changeRole(String id, String roleStr) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(id) || !validator.validateRole(roleStr)) {
            throw new InvalidInputException();
        }
        User user = getUser(id);
        UserRole userRole = UserRole.valueOf(roleStr);
        user.setRole(userRole);
        update(user);
    }

    @Override
    public void changeDiscount(String id, String discountStr) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(id) || !validator.validateDiscount(discountStr)) {
            throw new InvalidInputException();
        }
        User user = getUser(id);
        int discount = Integer.parseInt(discountStr);
        user.setDiscount(discount);
        update(user);
    }

    private void update(User user) throws ServiceException {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
