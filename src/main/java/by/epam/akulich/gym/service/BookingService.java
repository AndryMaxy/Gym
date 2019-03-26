package by.epam.akulich.gym.service;

import by.epam.akulich.gym.dto.Appointment;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Membership;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import java.util.List;

/**
 * Service layer interface which contains methods with business logic for booking.
 *
 * @author Andrey Akulich
 */
public interface BookingService {

    /**
     * Returns {@link Booking} according identifier.
     *
     * @param bookingId {@link Booking} identifier
     * @return {@link Booking} according identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    Booking getBooking(String bookingId) throws ServiceException, InvalidInputException ;

    /**
     * Returns {@link Booking} according {@link User} identifier.
     *
     * @param userId {@link User} identifier
     * @return {@link Booking} according {@link User} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    Booking getBookingByUserId(String userId) throws ServiceException, InvalidInputException ;

    /**
     * Returns a list of {@link Booking} according {@link User} identifier.
     *
     * @param userId {@link User} identifier
     * @return a list of {@link Booking} according {@link User} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    List<Booking> getBookingList(String userId) throws ServiceException, InvalidInputException;

    /**
     * Returns a list of all {@link Booking}.
     *
     * @return a list of all {@link Booking}
     * @throws ServiceException if exception in dao layer occurs
     */
    List<Booking> getAll() throws ServiceException;

    /**
     * User tries to buy {@link Membership}.
     *
     * @param user current {@link User} instance
     * @param membershipStr string of {@link Membership}
     * @return {@code true} if {@link Membership} was bought
     * otherwise {@code false}
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    boolean buyMembership(User user, String membershipStr) throws ServiceException, InvalidInputException ;

    /**
     * User sets feedback to {@link Booking}.
     *
     * @param userId {@link User} identifier
     * @param feedback text from user
     * @return {@code true} if feedback was added
     * otherwise {@code false}
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    boolean setFeedback(String userId, String feedback) throws ServiceException, InvalidInputException;

    /**
     * Reduces user's visits count.
     *
     * @param bookingId {@link Booking} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    void reduceVisits(String bookingId) throws ServiceException, InvalidInputException;

    /**
     * Tells that {@link User} do not need {@link Appointment}.
     *
     * @param bookingId {@link Booking} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    void refuseAppointments(String bookingId) throws ServiceException, InvalidInputException;

    /**
     * @return a list of all {@link Membership}
     */
    List<Membership> getMemberships();
}
