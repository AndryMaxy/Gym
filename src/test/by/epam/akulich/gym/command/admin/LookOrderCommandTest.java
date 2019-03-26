package by.epam.akulich.gym.command.admin;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        //given
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(userService.getUser(id)).thenReturn(user);
        when(bookingService.getBookingList(id)).thenReturn(bookings);

        //when
        Response actual = command.execute();

        //then
        verify(userService).getUser(id);
        verify(bookingService, times(times)).getBookingList(id);
        assertEquals(expected, actual);
    }


    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String id = "5";
        List<Booking> bookings = new ArrayList<>();
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new ServiceException()).when(userService).getUser(id);
        when(bookingService.getBookingList(id)).thenReturn(bookings);

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String id = "5";
        List<Booking> bookings = new ArrayList<>();
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new InvalidInputException()).when(userService).getUser(id);
        when(bookingService.getBookingList(id)).thenReturn(bookings);

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}