package service.impl;

import dao.BookingDAO;
import dao.exception.DAOException;
import dao.impl.BookingDAOImpl;
import entity.Booking;
import entity.Membership;
import service.BookingService;
import service.exception.ServiceException;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private static class BookingServiceImplHolder {
        static final BookingServiceImpl INSTANCE = new BookingServiceImpl();
    }

    public static BookingServiceImpl getInstance(){
        return BookingServiceImplHolder.INSTANCE;
    }

    private BookingDAO bookingDAO = BookingDAOImpl.getInstance(); //TODO MB STATIC FINAL

    private BookingServiceImpl(){}

    @Override
    public Booking getBooking(int bookingId) throws ServiceException {
        try {
            return bookingDAO.get(bookingId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public Booking getBookingByUserId(int userId) throws ServiceException {
        try {
            return bookingDAO.getByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void buyMembership(int userId, int balance, Membership membership) throws ServiceException {
        try {
            BookingDAO bookingDAO = BookingDAOImpl.getInstance();
            bookingDAO.add(userId, balance, membership);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> getBookings(int userId) throws ServiceException {
        try {
            return bookingDAO.getAll(userId);
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
}
