package service.impl;

import dao.BookingDAO;
import dao.exception.DAOException;
import dao.impl.BookingDAOImpl;
import entity.Booking;
import entity.Membership;
import entity.User;
import service.BookingService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.ParameterValidator;

import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private static class BookingServiceImplHolder {
        static final BookingServiceImpl INSTANCE = new BookingServiceImpl();
    }

    public static BookingServiceImpl getInstance(){
        return BookingServiceImplHolder.INSTANCE;
    }

    private BookingDAO bookingDAO = BookingDAOImpl.getInstance();
    private ParameterValidator validator = ParameterValidator.getInstance();

    private BookingServiceImpl(){}

    private BookingServiceImpl(BookingDAO bookingDAO){
        this.bookingDAO = bookingDAO;
    }

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

    @Override
    public void reduceVisits(String bookingId) throws ServiceException, InvalidInputException {
        Booking booking = getBooking(bookingId);
        int newCount = booking.getVisitCountLeft() - 1;
        booking.setVisitCountLeft(newCount);
        update(booking);
    }

    @Override
    public List<Booking> getAll() throws ServiceException {
        try {
            return bookingDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public void refuseAppointments(String bookingId) throws ServiceException, InvalidInputException {
        Booking booking = getBooking(bookingId);
        booking.setNeedAppointment(false);
        update(booking);
    }

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

    private void update(Booking booking) throws ServiceException {
        try {
            bookingDAO.update(booking);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
