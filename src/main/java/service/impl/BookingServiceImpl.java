package service.impl;

import dao.BookingDAO;
import dao.exception.DAOException;
import dao.impl.BookingDAOImpl;
import entity.Booking;
import entity.Membership;
import service.BookingService;
import service.exception.ServiceException;

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
    public Booking getBooking(int userId) throws ServiceException {
        try {
            return bookingDAO.get(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean buyMembership(int userId, int balance, Membership membership) throws ServiceException {
        try {
            BookingDAO bookingDAO = BookingDAOImpl.getInstance();
            return bookingDAO.add(userId, balance, membership);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
