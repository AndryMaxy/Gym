package by.epam.akulich.gym.service;

import by.epam.akulich.gym.entity.Appointment;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.entity.UserRole;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.util.Encoder;

import java.util.List;

/**
 * Service layer interface which contains methods with business logic for user.
 *
 * @author Andrey Akulich
 */
public interface UserService {

    /**
     * Returns a list of all {@link User}.
     *
     * @return a list of all {@link User}
     * @throws ServiceException if exception in dao layer occurs
     */
    List<User> getAll() throws ServiceException;

    /**
     * Returns {@link User} according identifier.
     *
     * @param id {@link User} identifier
     * @return {@link User} according identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    User getUser(String id) throws ServiceException, InvalidInputException;

    /**
     * Adds new {@link User} to database.
     *
     * @param user an instance of new {@link User}
     * @throws ServiceException if exception in dao layer occurs
     */
    void add(User user) throws ServiceException;

    /**
     * Checks if such user already exists.
     *
     * @param login a user's login
     * @return {@code true} if user login exist otherwise {@code false}
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    boolean isUserLoginExist(String login) throws ServiceException, InvalidInputException;

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
    int logIn(String login, char[] password) throws ServiceException, InvalidInputException;

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
    User getNewVisitor(String id) throws ServiceException, InvalidInputException;

    /**
     * Returns a list of {@link User} whose {@link UserRole} is VISITOR
     * and who has no {@link Appointment}.
     *
     * @return a list of {@link User} whose {@link UserRole} is VISITOR
     * and who has no {@link Appointment}.
     * @throws ServiceException if exception in dao layer occurs
     */
    List<User> getNewVisitors() throws ServiceException;

    /**
     * Sets another {@link UserRole} to {@link User}.
     *
     * @param id {@link User} identifier
     * @param roleStr string of {@link UserRole}
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    void changeRole(String id, String roleStr) throws ServiceException, InvalidInputException;

    /**
     * Sets another discount to {@link User}.
     *
     * @param id {@link User} identifier
     * @param discount string of new discount
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    void changeDiscount(String id, String discount) throws ServiceException, InvalidInputException;

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
    User createUser(String login, char[] password, String name, String surname) throws ServiceException, InvalidInputException;

    /**
     * Deletes {@link User} from database.
     *
     * @param userId {@link User} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    void delete(String userId) throws ServiceException, InvalidInputException;
}
