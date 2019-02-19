package service;

import entity.Booking;
import entity.Membership;
import service.exception.ServiceException;

public interface BookingService {

    Booking getBooking(int userId) throws ServiceException;
    boolean buyMembership(int userId, int balance, Membership membership) throws ServiceException;
}
