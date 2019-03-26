package by.epam.akulich.gym.service.impl;

import by.epam.akulich.gym.builder.UserBuilder;
import by.epam.akulich.gym.dao.UserDAO;
import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.dao.impl.UserDAOImpl;
import by.epam.akulich.gym.dto.Appointment;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.entity.UserRole;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.util.Encoder;
import by.epam.akulich.gym.util.exception.EncoderException;
import by.epam.akulich.gym.service.ParameterValidator;

import java.util.List;

/**
 * The class contains methods with business logic for user.
 *
 * @author Andrey Akulich
 */
public class UserServiceImpl implements UserService {

    /**
     * This class represents initialization-on-demand holder idiom for {@link UserServiceImpl}
     */
    private static class UserServiceImplHolder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    /**
     * UserDAO instance.
     */
    private UserDAO userDAO = UserDAOImpl.getInstance();

    /**
     * Encoder instance.
     */
    private Encoder encoder = Encoder.getInstance();

    /**
     * ParameterValidator instance.
     */
    private ParameterValidator validator = ParameterValidator.getInstance();

    /**
     * Constructs UserServiceImpl.
     */
    private UserServiceImpl(){}

    /**
     * Constructs UserServiceImpl.
     * This constructor is used for tests.
     *
     * @param userDAO UserDAO instance
     * @param encoder Encoder instance
     */
    private UserServiceImpl(UserDAO userDAO, Encoder encoder){
        this.userDAO = userDAO;
        this.encoder = encoder;
    }

    /**
     * @return UserServiceImpl instance
     */
    public static UserServiceImpl getInstance() {
        return UserServiceImplHolder.INSTANCE;
    }

    /**
     * Adds new {@link User} to database.
     *
     * @param user an instance of new {@link User}
     * @throws ServiceException if exception in dao layer occurs
     */
    @Override
    public void add(User user) throws ServiceException {
        try {
            userDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns a list of all {@link User}.
     *
     * @return a list of all {@link User}
     * @throws ServiceException if exception in dao layer occurs
     */
    @Override
    public List<User> getAll() throws ServiceException {
        try {
            return userDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns {@link User} according identifier.
     *
     * @param id {@link User} identifier
     * @return {@link User} according identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
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

    /**
     * Checks if such user already exists.
     *
     * @param login a user's login
     * @return {@code true} if user login exist otherwise {@code false}
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
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

    /**
     * User tries to log in to user account. Returns {@link User} identifier or
     * zero if login or password incorrect.
     *
     * @param login user's login
     * @param password user's password
     * @return {@link User} identifier or zero if login or password incorrect.
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
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

    /**
     * Returns {@link User} whose {@link UserRole} is VISITOR
     * and who has no {@link Appointment}.
     *
     * @param id {@link User} identifier
     * @return {@link User} whose {@link UserRole} is VISITOR
     * and who has no {@link Appointment}.
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public User getNewVisitor(String id) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(id)) {
            throw new InvalidInputException();
        }
        int userId = Integer.parseInt(id);
        try {
            return userDAO.getVisitorWithoutApp(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns a list of {@link User} whose {@link UserRole} is VISITOR
     * and who has no {@link Appointment}.
     *
     * @return a list of {@link User} whose {@link UserRole} is VISITOR
     * and who has no {@link Appointment}.
     * @throws ServiceException if exception in dao layer occurs
     */
    @Override
    public List<User> getNewVisitors() throws ServiceException {
        try {
            return userDAO.getVisitorsWithoutApp();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Creates and adds new {@link User} to database.
     *
     * @param login user's login
     * @param password user's password
     * @param name user's name
     * @param surname user's surname
     * @return {@link User} instance
     * @throws ServiceException if exception in dao layer or in {@link Encoder} occurs
     * @throws InvalidInputException if user input invalid data
     */
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

    /**
     * Sets another {@link UserRole} to {@link User}.
     *
     * @param userIdStr {@link User} identifier
     * @param roleStr string of {@link UserRole}
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public void changeRole(String userIdStr, String roleStr) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(userIdStr) || !validator.validateRole(roleStr)) {
            throw new InvalidInputException();
        }
        User user = getUser(userIdStr);
        UserRole userRole = UserRole.valueOf(roleStr);
        user.setRole(userRole);
        update(user);
    }

    /**
     * Sets another discount to {@link User}.
     *
     * @param id {@link User} identifier
     * @param discountStr string of new discount
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
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

    /**
     * Refill user's balance and changes user's
     * discount if the refill meets the conditions.
     *
     * @param userIdStr {@link User} identifier
     * @param addBalanceStr amount to refill balance
     * @param payment payment method
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public void upBalance(String userIdStr, String addBalanceStr, String payment) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(userIdStr) || !validator.validateNumber(addBalanceStr)) {
            throw new InvalidInputException();
        }
        int userId = Integer.parseInt(userIdStr);
        int addBalance = Integer.parseInt(addBalanceStr);
        try {
            User user = userDAO.getById(userId);
            int discount = user.getDiscount();
            if (payment.equals("card") && user.getDiscount() < 20 && addBalance >= 1000) {
                discount += 5;
            }
            int balance = user.getBalance() + addBalance;
            user.setBalance(balance);
            user.setDiscount(discount);
            userDAO.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes {@link User} from database.
     *
     * @param userIdStr {@link User} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public void delete(String userIdStr) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(userIdStr)) {
            throw new InvalidInputException();
        }
        int userId = Integer.parseInt(userIdStr);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates {@link User} in database.
     *
     * @param user {@link User} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    private void update(User user) throws ServiceException {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
