package service;

import entity.Booking;
import entity.User;
import service.exception.ServiceException;

import java.util.List;

public interface BookingService {

    Booking getBooking(String bookingId) throws ServiceException;
    Booking getBookingByUserId(String userId) throws ServiceException;
    List<Booking> getBookingList(String userId) throws ServiceException;
    List<Booking> getAll() throws ServiceException;
    boolean buyMembership(User user, String membershipStr) throws ServiceException;
    void update(Booking booking) throws ServiceException;
    void reduceVisits(Booking booking) throws ServiceException;
}
