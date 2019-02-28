package service.impl;

import dao.BookingDAO;
import dao.exception.DAOException;
import dao.impl.BookingDAOImpl;
import entity.Booking;
import entity.Membership;
import entity.User;
import service.BookingService;
import service.exception.ServiceException;
import validator.ParameterValidator;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private static class BookingServiceImplHolder {
        static final BookingServiceImpl INSTANCE = new BookingServiceImpl();
    }

    public static BookingServiceImpl getInstance(){
        return BookingServiceImplHolder.INSTANCE;
    }

    private final BookingDAO bookingDAO = BookingDAOImpl.getInstance(); //TODO MB STATIC FINAL
    private final ParameterValidator validator = ParameterValidator.getInstance();

    private BookingServiceImpl(){}

    @Override
    public Booking getBooking(String bookingIdStr) throws ServiceException {
        if (!validator.validateNumber(bookingIdStr)) {
            return null;
        }
        int bookingId = Integer.parseInt(bookingIdStr);
        try {
            return bookingDAO.get(bookingId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public Booking getBookingByUserId(String userIdStr) throws ServiceException {
        if (!validator.validateNumber(userIdStr)) {
            return null;
        }
        int userId = Integer.parseInt(userIdStr);
        try {
            return bookingDAO.getByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean buyMembership(User user, String membershipStr) throws ServiceException {
        if (!validator.validateMembership(membershipStr)) {
            return false;
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
        BookingDAO bookingDAO = BookingDAOImpl.getInstance();
        try {
            bookingDAO.add(user.getId(), newBalance, membership);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> getBookingList(String userIdStr) throws ServiceException {
        if (!validator.validateNumber(userIdStr)) {
            return null;
        }
        int userId = Integer.parseInt(userIdStr);
        try {
            return bookingDAO.getBookingList(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Booking booking) throws ServiceException {
        try {
            bookingDAO.update(booking);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void reduceVisits(Booking booking) throws ServiceException {
        int newCount = booking.getVisitCountLeft() - 1;
        booking.setVisitCountLeft(newCount);
        try {
            bookingDAO.update(booking);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> getAll() throws ServiceException {
        try {
            return bookingDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
