package command.admin;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import entity.Booking;
import entity.Constants;
import entity.Response;
import entity.User;
import entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BookingService;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class LookOrderCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private LookOrderCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        bookings.add(new Booking());
        User user1 = new User();
        user1.setRole(UserRole.VISITOR);
        User user2 = new User();
        user2.setRole(UserRole.VISITOR);
        User user3 = new User();
        user3.setRole(UserRole.ADMIN);
        return new Object[][]{
                {"5", user1, 1, bookings, new Response(Constants.URL.ORDER, Constants.ResponseStatus.FORWARD)},
                {"220", user2, 1, bookings, new Response(Constants.URL.ORDER, Constants.ResponseStatus.FORWARD)},
                {"475587", user3, 0, bookings, new Response(Constants.ResponseStatus.NOT_FOUND)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String id, User user, int times, List<Booking> bookings, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(userService.getUser(id)).thenReturn(user);
        when(bookingService.getBookingList(id)).thenReturn(bookings);
        Response actual = command.execute();

        verify(userService).getUser(id);
        verify(bookingService, times(times)).getBookingList(id);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        bookings.add(new Booking());
        User user1 = new User();
        user1.setRole(UserRole.VISITOR);
        User user2 = new User();
        user2.setRole(UserRole.VISITOR);
        User user3 = new User();
        user3.setRole(UserRole.ADMIN);
        return new Object[][]{
                {"5", 1, bookings, new Response(Constants.URL.ORDER, Constants.ResponseStatus.FORWARD)},
                {"220", 1, bookings, new Response(Constants.URL.ORDER, Constants.ResponseStatus.FORWARD)},
                {"475587",  0, bookings, new Response(Constants.ResponseStatus.NOT_FOUND)}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(String id, int times, List<Booking> bookings, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new ServiceException()).when(userService).getUser(id);
        when(bookingService.getBookingList(id)).thenReturn(bookings);
        Response actual = command.execute();

        verify(userService).getUser(id);
        verify(bookingService, times(times)).getBookingList(id);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        bookings.add(new Booking());
        User user1 = new User();
        user1.setRole(UserRole.VISITOR);
        User user2 = new User();
        user2.setRole(UserRole.VISITOR);
        User user3 = new User();
        user3.setRole(UserRole.ADMIN);
        return new Object[][]{
                {"five", user1, 1, bookings, new Response(Constants.URL.ORDER, Constants.ResponseStatus.FORWARD)},
                {"220g", user2, 1, bookings, new Response(Constants.URL.ORDER, Constants.ResponseStatus.FORWARD)},
                {"47.5587", user3, 0, bookings, new Response(Constants.ResponseStatus.NOT_FOUND)}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputException_Data")
    public void execute_Request_InvalidInputException(String id, User user, int times, List<Booking> bookings, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new InvalidInputException()).when(userService).getUser(id);
        when(bookingService.getBookingList(id)).thenReturn(bookings);
        Response actual = command.execute();

        verify(userService).getUser(id);
        verify(bookingService, times(times)).getBookingList(id);
        assertEquals(expected, actual);
    }
}