package service;

import entity.Booking;
import entity.Membership;
import service.exception.ServiceException;

import java.util.List;

public interface BookingService {

    Booking getBooking(int bookingId) throws ServiceException;
    Booking getBookingByUserId(int userId) throws ServiceException;
    List<Booking> getBookings(int userId) throws ServiceException;
    void buyMembership(int userId, int balance, Membership membership) throws ServiceException;
    void update(Booking booking) throws ServiceException;
}
