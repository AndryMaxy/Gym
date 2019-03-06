package service;

import entity.Booking;
import entity.Membership;
import entity.User;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import java.util.List;

public interface BookingService {

    Booking getBooking(String bookingId) throws ServiceException, InvalidInputException ;
    Booking getBookingByUserId(String userId) throws ServiceException, InvalidInputException ;
    List<Booking> getBookingList(String userId) throws ServiceException, InvalidInputException;
    List<Booking> getAll() throws ServiceException;
    boolean buyMembership(User user, String membershipStr) throws ServiceException, InvalidInputException ;
    boolean setFeedback(String userId, String feedback) throws ServiceException, InvalidInputException;
    List<Membership> getMemberships();
    void reduceVisits(String bookingId) throws ServiceException, InvalidInputException;
    void refuseAppointments(String bookingId) throws ServiceException, InvalidInputException;
}
