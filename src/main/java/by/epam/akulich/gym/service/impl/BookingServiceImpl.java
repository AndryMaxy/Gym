package by.epam.akulich.gym.service.impl;

import by.epam.akulich.gym.dao.BookingDAO;
import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.dao.impl.BookingDAOImpl;
import by.epam.akulich.gym.entity.Appointment;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Membership;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.ParameterValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains methods with business logic for booking.
 *
 * @author Andrey Akulich
 */
public class BookingServiceImpl implements BookingService {

    /**
     * This class represents initialization-on-demand holder idiom for {@link BookingServiceImpl}
     */
    private static class BookingServiceImplHolder {
        static final BookingServiceImpl INSTANCE = new BookingServiceImpl();
    }

    /**
     * BookingDAO instance.
     */
    private BookingDAO bookingDAO = BookingDAOImpl.getInstance();

    /**
     * ParameterValidator instance.
     */
    private ParameterValidator validator = ParameterValidator.getInstance();

    /**
     * Constructs BookingServiceImpl.
     */
    public BookingServiceImpl() {
    }

    /**
     * Constructs BookingServiceImpl.
     * This constructor is used for tests.
     *
     * @param bookingDAO {@link BookingDAO} instance
     */
    private BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    /**
     * @return BookingServiceImpl instance
     */
    public static BookingServiceImpl getInstance(){
        return BookingServiceImplHolder.INSTANCE;
    }

    /**
     * Returns {@link Booking} according identifier.
     *
     * @param bookingIdStr {@link Booking} identifier
     * @return {@link Booking} according identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public Booking getBooking(String bookingIdStr) throws ServiceException, InvalidInputException  {
        if (!validator.validateNumber(bookingIdStr)) {
            throw new InvalidInputException();
        }
        int bookingId = Integer.parseInt(bookingIdStr);
        try {
            return bookingDAO.get(bookingId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns {@link Booking} according {@link User} identifier.
     *
     * @param userIdStr {@link User} identifier
     * @return {@link Booking} according {@link User} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public Booking getBookingByUserId(String userIdStr) throws ServiceException, InvalidInputException  {
        if (!validator.validateNumber(userIdStr)) {
            throw new InvalidInputException();
        }
        int userId = Integer.parseInt(userIdStr);
        try {
            return bookingDAO.getByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns a list of all {@link Booking}.
     *
     * @return a list of all {@link Booking}
     * @throws ServiceException if exception in dao layer occurs
     */
    @Override
    public List<Booking> getAll() throws ServiceException {
        try {
            return bookingDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns a list of {@link Booking} according {@link User} identifier.
     *
     * @param userIdStr {@link User} identifier
     * @return a list of {@link Booking} according {@link User} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public List<Booking> getBookingList(String userIdStr) throws ServiceException, InvalidInputException {
        if (!validator.validateNumber(userIdStr)) {
            throw new InvalidInputException();
        }
        int userId = Integer.parseInt(userIdStr);
        try {
            return bookingDAO.getUserBookingList(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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
    @Override
    public boolean buyMembership(User user, String membershipStr) throws ServiceException, InvalidInputException  {
        if (!validator.validateMembership(membershipStr)) {
            throw new InvalidInputException();
        }
        Membership membership = Membership.valueOf(membershipStr);
        int discount = user.getDiscount();
        int balance = user.getBalance();
        int price = membership.getPrice();
        int cost = price / 100 * (100 - discount);
        if (balance < cost) {
            return false;
        }
        int newBalance = balance - cost;
        try {
            bookingDAO.add(user.getId(), newBalance, membership);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Reduces user's visits count.
     *
     * @param bookingId {@link Booking} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public void reduceVisits(String bookingId) throws ServiceException, InvalidInputException {
        Booking booking = getBooking(bookingId);
        int newCount = booking.getVisitCountLeft() - 1;
        booking.setVisitCountLeft(newCount);
        update(booking);
    }

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
    @Override
    public boolean setFeedback(String userId, String feedback) throws ServiceException, InvalidInputException {
        Booking booking = getBookingByUserId(userId);
        if (!validator.validateText(feedback)) {
            return false;
        }
        booking.setFeedback(feedback);
        update(booking);
        return true;
    }

    /**
     * Tells that {@link User} do not need {@link Appointment}.
     *
     * @param bookingId {@link Booking} identifier
     * @throws ServiceException if exception in dao layer occurs
     * @throws InvalidInputException if user input invalid data
     */
    @Override
    public void refuseAppointments(String bookingId) throws ServiceException, InvalidInputException {
        Booking booking = getBooking(bookingId);
        booking.setNeedAppointment(false);
        update(booking);
    }

    /**
     * @return a list of all {@link Membership}
     */
    @Override
    public List<Membership> getMemberships(){
        List<Membership> memberships = new ArrayList<>();
        memberships.add(Membership.ULTRA);
        memberships.add(Membership.SUPER);
        memberships.add(Membership.STANDARD);
        memberships.add(Membership.EASY);
        memberships.add(Membership.ONE);
        return memberships;
    }

    /**
     * Updates {@link Booking} in database.
     *
     * @param booking {@link Booking} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    private void update(Booking booking) throws ServiceException {
        try {
            bookingDAO.update(booking);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
