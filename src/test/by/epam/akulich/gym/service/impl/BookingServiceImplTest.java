package by.epam.akulich.gym.service.impl;

import by.epam.akulich.gym.dao.BookingDAO;
import by.epam.akulich.gym.service.ParameterValidator;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Membership;
import by.epam.akulich.gym.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class BookingServiceImplTest {

    @Mock
    private BookingDAO bookingDAO;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] getBooking_String_CorrectBooking_Data(){
        Booking booking1 = new Booking();
        booking1.setId(2);
        booking1.setVisitCountLeft(23);
        Booking booking2 = new Booking();
        booking2.setId(234);
        booking2.setUser(new User());
        Booking booking3 = new Booking();
        booking3.setId(435);
        booking3.setFeedback("some text");
        return new Object[][] {
                {"2", booking1},
                {"234", booking2},
                {"435", booking3}
        };
    }

    @Test
    @UseDataProvider("getBooking_String_CorrectBooking_Data")
    public void getBooking_String_CorrectBooking(String bookingId, Booking expected) throws InvalidInputException, ServiceException, DAOException {
        //given
        when(bookingDAO.get(expected.getId())).thenReturn(expected);

        //when
        Booking actual = bookingService.getBooking(bookingId);

        //then
        verify(bookingDAO).get(expected.getId());
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getBooking_String_ServiceException() throws InvalidInputException, ServiceException, DAOException {
        //given
        Booking expected = new Booking();
        expected.setId(2);
        when(bookingDAO.get(2)).thenThrow(new DAOException());

        //when
        bookingService.getBooking("2");

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void getBooking_String_InvalidInputException() throws InvalidInputException, ServiceException, DAOException {
        //given
        Booking expected = new Booking();
        expected.setId(2);
        when(bookingDAO.get(2)).thenReturn(expected);

        //when
        bookingService.getBooking("2..");

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] getBookingByUserId_String_CorrectBooking_Data(){
        Booking booking1 = new Booking();
        booking1.setId(2);
        booking1.setVisitCountLeft(23);
        Booking booking2 = new Booking();
        booking2.setId(234);
        booking2.setUser(new User());
        Booking booking3 = new Booking();
        booking3.setId(435);
        booking3.setFeedback("some text");
        return new Object[][] {
                {2, "2", booking1},
                {234, "234", booking2},
                {435, "435", booking3}
        };
    }

    @Test
    @UseDataProvider("getBookingByUserId_String_CorrectBooking_Data")
    public void getBookingByUserId_String_CorrectBooking(int userId, String userIdStr, Booking expected) throws DAOException, InvalidInputException, ServiceException {
        //given
        when(bookingDAO.getByUserId(userId)).thenReturn(expected);

        //when
        Booking actual = bookingService.getBookingByUserId(userIdStr);

        //then
        verify(bookingDAO).getByUserId(userId);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getBookingByUserId_String_ServiceException() throws InvalidInputException, ServiceException, DAOException {
        //given
        Booking expected = new Booking();
        expected.setId(243);
        expected.setFeedback("some text");
        when(bookingDAO.getByUserId(2)).thenThrow(new DAOException());

        //when
        bookingService.getBookingByUserId("2");

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void getBookingByUserId_String_InvalidInputException() throws InvalidInputException, ServiceException, DAOException {
        //given
        Booking expected = new Booking();
        expected.setId(243);
        expected.setFeedback("some text");
        when(bookingDAO.getByUserId(2)).thenReturn(expected);

        //when
        bookingService.getBookingByUserId("two");

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] buyMembership_UserString_Boolean_Data(){
        String membership1 = "ONE";
        String membership2 = "STANDARD";
        String membership3 = "ULTRA";
        User user1 = new User();
        user1.setId(2);
        user1.setDiscount(10);
        user1.setBalance(200);
        User user2 = new User();
        user2.setId(45);
        user2.setDiscount(5);
        user2.setBalance(600);
        User user3 = new User();
        user3.setId(848);
        user3.setDiscount(0);
        user3.setBalance(800);
        return new Object[][] {
                {user1, membership1, 1, 110, Membership.ONE, true},
                {user2, membership2, 1, 30, Membership.STANDARD, true},
                {user3, membership3, 0, 0, Membership.ULTRA, false},
        };
    }

    @Test
    @UseDataProvider("buyMembership_UserString_Boolean_Data")
    public void buyMembership_UserString_Boolean(User user, String membershipStr, int times, int newBalance, Membership membership, boolean expected) throws InvalidInputException, ServiceException, DAOException {
        //given
        //from data provider

        //when
        boolean actual = bookingService.buyMembership(user, membershipStr);

        //then
        verify(bookingDAO, times(times)).add(user.getId(), newBalance, membership);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void buyMembership_UserString_ServiceException() throws InvalidInputException, ServiceException, DAOException {
        //given
        User user = new User();
        user.setId(10);
        user.setDiscount(0);
        user.setBalance(1000);
        String membershipStr = "ONE";
        Membership membership = Membership.ONE;
        doThrow(new DAOException()).when(bookingDAO).add(10, 900, membership);

        //when
        bookingService.buyMembership(user, membershipStr);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void buyMembership_UserString_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        User user = new User();
        user.setId(10);
        user.setDiscount(0);
        user.setBalance(1000);
        String membershipStr = "ONEE";

        //when
        bookingService.buyMembership(user, membershipStr);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] getUserBookingList_String_BookingList_Data(){
        Booking booking1 = new Booking();
        booking1.setId(2);
        booking1.setVisitCountLeft(23);
        List<Booking> bookings1 = new ArrayList<>();
        bookings1.add(booking1);
        Booking booking2 = new Booking();
        booking2.setId(234);
        booking2.setNeedAppointment(false);
        List<Booking> bookings2 = new ArrayList<>();
        bookings2.add(booking2);
        Booking booking3 = new Booking();
        booking3.setId(435);
        booking3.setFeedback("some text");
        Booking booking4 = new Booking();
        booking4.setId(895);
        booking4.setUser(new User());
        List<Booking> bookings3 = new ArrayList<>();
        bookings3.add(booking3);
        bookings3.add(booking4);
        return new Object[][] {
                {2,"2", bookings1},
                {34, "34", bookings2},
                {321, "321", bookings3}
        };
    }

    @Test
    @UseDataProvider("getUserBookingList_String_BookingList_Data")
    public void getUserBookingList_String_BookingList(int userId, String userIdStr, List<Booking> expected) throws ServiceException, DAOException, InvalidInputException {
        //given
        when(bookingDAO.getUserBookingList(userId)).thenReturn(expected);

        //when
        List<Booking> actual = bookingService.getBookingList(userIdStr);

        //then
        verify(bookingDAO).getUserBookingList(userId);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getBookingList_String_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        //given
        int userId = 34;
        String userIdStr = "34";
        when(bookingDAO.getUserBookingList(userId)).thenThrow(new DAOException());

        //when
        bookingService.getBookingList(userIdStr);

        //then
        //expected ServiceException

    }

    @Test(expected = InvalidInputException.class)
    public void getBookingList_String_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        //given
        int userId = 34;
        String userIdStr = "34ggr";
        List<Booking> expected = new ArrayList<>();
        when(bookingDAO.getUserBookingList(userId)).thenReturn(expected);

        //when
        bookingService.getBookingList(userIdStr);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] reduceVisits_String_Reduced_Data(){
        Booking oldBooking1 = new Booking();
        oldBooking1.setId(1);
        oldBooking1.setVisitCountLeft(24);
        Booking newBooking1 = new Booking();
        newBooking1.setId(1);
        newBooking1.setVisitCountLeft(23);
        Booking oldBooking2 = new Booking();
        oldBooking2.setId(2);
        oldBooking2.setVisitCountLeft(30);
        Booking newBooking2 = new Booking();
        newBooking2.setId(2);
        newBooking2.setVisitCountLeft(29);
        Booking oldBooking3 = new Booking();
        oldBooking3.setId(3);
        oldBooking3.setVisitCountLeft(15);
        Booking newBooking3 = new Booking();
        newBooking3.setId(3);
        newBooking3.setVisitCountLeft(14);
        return new Object[][] {
                {1, "1", oldBooking1, newBooking1},
                {2, "2", oldBooking2, newBooking2},
                {3, "3", oldBooking3, newBooking3}
        };
    }

    @Test
    @UseDataProvider("reduceVisits_String_Reduced_Data")
    public void reduceVisits_String_Reduced(int bookingId, String bookingIdStr, Booking oldBooking, Booking newBooking) throws InvalidInputException, ServiceException, DAOException {
        //given
        when(bookingDAO.get(bookingId)).thenReturn(oldBooking);

        //when
        bookingService.reduceVisits(bookingIdStr);

        //then
        verify(bookingDAO).update(newBooking);
    }

    @Test(expected = ServiceException.class)
    public void reduceVisits_String_ServiceException() throws InvalidInputException, ServiceException, DAOException {
        //given
        int bookingId = 10;
        String bookingIdStr = "10";
        when(bookingDAO.get(bookingId)).thenThrow(new DAOException());

        //when
        bookingService.reduceVisits(bookingIdStr);

        //then
        //excepted ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void reduceVisits_String_InvalidInputException() throws InvalidInputException, ServiceException, DAOException {
        //given
        int bookingId = 10;
        String bookingIdStr = "10.1";
        when(bookingDAO.get(bookingId)).thenReturn(new Booking());

        //when
        bookingService.reduceVisits(bookingIdStr);

        //then
        //excepted InvalidInputException
    }

    @DataProvider
    public static Object[][] getAll_DAO_BookingList_Data(){
        Booking booking1 = new Booking();
        booking1.setId(2);
        booking1.setVisitCountLeft(23);
        List<Booking> bookings1 = new ArrayList<>();
        bookings1.add(booking1);
        Booking booking2 = new Booking();
        booking2.setId(234);
        booking2.setNeedAppointment(false);
        List<Booking> bookings2 = new ArrayList<>();
        bookings2.add(booking2);
        Booking booking3 = new Booking();
        booking3.setId(435);
        booking3.setFeedback("some text");
        Booking booking4 = new Booking();
        booking4.setId(895);
        booking4.setUser(new User());
        List<Booking> bookings3 = new ArrayList<>();
        bookings3.add(booking3);
        bookings3.add(booking4);
        return new Object[][]{
                {bookings1},
                {bookings2},
                {bookings3}
        };
    }

    @Test
    @UseDataProvider("getAll_DAO_BookingList_Data")
    public void getAll_DAO_BookingList(List<Booking> expected) throws DAOException, ServiceException {
        //given
        when(bookingDAO.getAll()).thenReturn(expected);

        //when
        List<Booking> actual = bookingService.getAll();

        //then
        verify(bookingDAO).getAll();
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getAll_DAO_ServiceException() throws DAOException, ServiceException {
        //given
        when(bookingDAO.getAll()).thenThrow(new DAOException());

        //when
        bookingService.getAll();

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] setFeedback_String_UpdatedFeedback_Data() {
        Booking oldBooking1 = new Booking();
        oldBooking1.setId(1);
        Booking newBooking1 = new Booking();
        newBooking1.setId(1);
        newBooking1.setFeedback("incorrect text");
        Booking oldBooking2 = new Booking();
        oldBooking2.setId(2);
        Booking newBooking2 = new Booking();
        newBooking2.setId(2);
        newBooking2.setFeedback("Nice gym I like it. Of course I come again");
        Booking oldBooking3 = new Booking();
        oldBooking3.setId(3);
        Booking newBooking3 = new Booking();
        newBooking3.setId(3);
        newBooking3.setFeedback("Where is mirrors? I need more mirrors!");
        return new Object[][] {
                {1, "1", 0, false, oldBooking1, newBooking1},
                {2, "2", 1, true, oldBooking2, newBooking2},
                {3, "3", 1, true, oldBooking3, newBooking3}
        };
    }

    @Test
    @UseDataProvider("setFeedback_String_UpdatedFeedback_Data")
    public void setFeedback_String_UpdatedFeedback(int userId, String userIdStr, int times, boolean expected, Booking oldBooking, Booking newBooking) throws DAOException, InvalidInputException, ServiceException {
        //given
        when(bookingDAO.getByUserId(userId)).thenReturn(oldBooking);

        //when
        boolean actual = bookingService.setFeedback(userIdStr, newBooking.getFeedback());

        //then
        verify(bookingDAO, times(times)).update(newBooking);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void setFeedback_String_ServiceException() throws DAOException, InvalidInputException, ServiceException {
        //given
        int userId = 10;
        String userIdStr = "10";
        String feedback = "Nice gym I like it. Of course I come again";
        Booking booking = new Booking();
        booking.setFeedback(feedback);
        when(bookingDAO.getByUserId(userId)).thenThrow(new DAOException());

        //when
        bookingService.setFeedback(userIdStr, feedback);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void setFeedback_String_InvalidInputException() throws DAOException, InvalidInputException, ServiceException {
        //given
        int userId = 10;
        String userIdStr = "10eee";
        String feedback = "Nice gym I like it. Of course I come again";
        Booking booking1 = new Booking();
        booking1.setFeedback(feedback);
        Booking booking2 = new Booking();
        when(bookingDAO.getByUserId(userId)).thenReturn(booking2);

        //when
        bookingService.setFeedback(userIdStr, feedback);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] refuseAppointments_String_Refused_Data(){
        Booking oldBooking1 = new Booking();
        oldBooking1.setId(1);
        oldBooking1.setNeedAppointment(true);
        Booking newBooking1 = new Booking();
        newBooking1.setId(1);
        newBooking1.setNeedAppointment(false);
        Booking oldBooking2 = new Booking();
        oldBooking2.setId(2);
        oldBooking2.setNeedAppointment(true);
        Booking newBooking2 = new Booking();
        newBooking2.setId(2);
        newBooking2.setNeedAppointment(false);
        Booking oldBooking3 = new Booking();
        oldBooking3.setId(3);
        oldBooking3.setNeedAppointment(true);
        Booking newBooking3 = new Booking();
        newBooking3.setId(3);
        newBooking3.setNeedAppointment(false);
        return new Object[][] {
                {1, "1", oldBooking1, newBooking1},
                {2, "2", oldBooking2, newBooking2},
                {3, "3", oldBooking3, newBooking3}
        };
    }

    @Test
    @UseDataProvider("refuseAppointments_String_Refused_Data")
    public void refuseAppointments_String_Refused(int bookingId, String bookingIdStr, Booking oldBooking, Booking newBooking) throws DAOException, InvalidInputException, ServiceException {
        //given
        when(bookingDAO.get(bookingId)).thenReturn(oldBooking);

        //when
        bookingService.refuseAppointments(bookingIdStr);

        //then
        verify(bookingDAO).update(newBooking);
    }

    @Test(expected = ServiceException.class)
    public void refuseAppointments_String_ServiceException() throws DAOException, InvalidInputException, ServiceException {
        //given
        int bookingId = 10;
        String bookingIdStr = "10";
        when(bookingDAO.get(bookingId)).thenThrow(new DAOException());

        //when
        bookingService.refuseAppointments(bookingIdStr);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void refuseAppointments_String_InvalidInputException() throws DAOException, InvalidInputException, ServiceException {
        //given
        int bookingId = 10;
        String bookingIdStr = "10eee";
        Booking booking1 = new Booking();
        when(bookingDAO.get(bookingId)).thenReturn(booking1);

        bookingService.refuseAppointments(bookingIdStr);

        //then
        //expected InvalidInputException
    }

    @Test
    public void getMemberships_Membership_MembershipList() {
        //given
        List<Membership> expected = new ArrayList<>();
        expected.add(Membership.ULTRA);
        expected.add(Membership.SUPER);
        expected.add(Membership.STANDARD);
        expected.add(Membership.EASY);
        expected.add(Membership.ONE);

        //when
        List<Membership> actual = bookingService.getMemberships();

        //then
        assertEquals(expected, actual);
    }
}