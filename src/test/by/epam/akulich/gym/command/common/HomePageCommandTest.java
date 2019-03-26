package by.epam.akulich.gym.command.common;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.AppointmentService;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(DataProviderRunner.class)
public class HomePageCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @Mock
    private BookingService bookingService;

    @Mock
    private AppointmentService appointmentService;

    private HomePageCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new HomePageCommand(request, userService, bookingService, appointmentService);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        Response response = new Response(Constants.URL.MAIN, Constants.ResponseStatus.FORWARD);
        User user1 = new User();
        user1.setRole(UserRole.VISITOR);
        User user2 = new User();
        user2.setRole(UserRole.VISITOR);
        User user3 = new User();
        user3.setRole(UserRole.ADMIN);
        User user4 = new User();
        user4.setRole(UserRole.TRAINER);
        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setVisitCountLeft(20);
        Booking booking2 = new Booking();
        booking2.setId(2);
        booking2.setVisitCountLeft(0);
        int[] times1 = {1, 1, 1, 0, 0, 0};
        int[] times2 = {1, 1, 0, 1, 0, 0};
        int[] times3 = {1, 0, 0, 0, 1, 0};
        int[] times4 = {1, 0, 0, 0, 0, 1};
        return new Object[][]{
                {"121", user1, times1, booking1, response},
                {"225", user2, times2, booking2, response},
                {"188", user3, times3, booking1, response},
                {"1897", user4, times4, booking1, response}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, User user, int[] times, Booking booking, Response expected) throws ServiceException, InvalidInputException {
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getUser(userId)).thenReturn(user);
        when(bookingService.getBookingByUserId(userId)).thenReturn(booking);

        Response actual = command.execute();

        verify(userService, times(times[0])).getUser(userId);
        verify(bookingService, times(times[1])).getBookingByUserId(userId);
        verify(appointmentService, times(times[2])).getAppointment(booking.getId());
        verify(bookingService, times(times[3])).getMemberships();
        verify(userService, times(times[4])).getAll();
        verify(userService, times(times[5])).getNewVisitors();
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data(){
        Response response = new Response(Constants.URL.MAIN, Constants.ResponseStatus.FORWARD);
        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setVisitCountLeft(20);
        Booking booking2 = new Booking();
        booking2.setId(2);
        booking2.setVisitCountLeft(0);
        return new Object[][]{
                {"121", booking1, response},
                {"225", booking2, response},
                {"188", booking1, response},
                {"1897", booking1, response}
        };
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws ServiceException, InvalidInputException {
        //given
        String userId = "121";
        Booking booking = new Booking();
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getUser(userId)).thenThrow(new ServiceException());
        when(bookingService.getBookingByUserId(userId)).thenReturn(booking);

        ///when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws ServiceException, InvalidInputException {
        //given
        String userId = "121";
        Booking booking = new Booking();
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getUser(userId)).thenThrow(new InvalidInputException());
        when(bookingService.getBookingByUserId(userId)).thenReturn(booking);

        ///when
        command.execute();

        //then
        //expected InvalidInputException
    }
}