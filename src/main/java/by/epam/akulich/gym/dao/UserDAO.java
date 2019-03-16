package by.epam.akulich.gym.dao;

import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.entity.Appointment;
import by.epam.akulich.gym.entity.User;

import java.util.List;

/**
 *  DAO interface to interact with user table of database.
 *
 * @author Andrey Akulich
 */
public interface UserDAO {

    /**
     * Returns list of all users.
     *
     * @return list of all users
     * @throws DAOException if the query failed.
     */
    List<User> getAll() throws DAOException;

    /**
     * Returns {@link User} instance by identifier.
     *
     * @param id identifier in database
     * @return {@link User} instance by id
     * @throws DAOException if the query failed.
     */
    User getById(int id) throws DAOException;

    /**
     * Returns {@link User} instance by user's login.
     *
     * @param login user's login
     * @return {@link User} instance by login
     * @throws DAOException if the query failed.
     */
    User getByLogin(String login) throws DAOException;

    /**
     * Returns {@link User} instance by id whose role is visitor
     * and has no {@link Appointment}.
     *
     * @param userId identifier in database
     * @return {@link User} instance by id with visitor role
     * @throws DAOException if the query failed.
     */
    User getVisitorWithoutApp(int userId) throws DAOException;

    /**
     * Returns list of users whose role is visitor and
     * has no {@link Appointment}.
     *
     * @return list of users
     * @throws DAOException if the query failed.
     */
    List<User> getVisitorsWithoutApp() throws DAOException;

    /**
     * Adds {@link User} to database.
     *
     * @param user new {@link User} object
     * @throws DAOException if the query failed.
     */
    void add(User user) throws DAOException;

    /**
     * Updates {@link User} information in database.
     *
     * @param user {@link User} object
     * @throws DAOException if the query failed.
     */
    void update(User user) throws DAOException;

    /**
     * Deletes {@link User} information from database by user identifier.
     *
     * @param userId user identifier
     * @throws DAOException if the query failed.
     */
    void delete(int userId) throws DAOException;
}
