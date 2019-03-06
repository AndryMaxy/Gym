package command.trainer;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import entity.Appointment;
import entity.Booking;
import entity.Constants;
import entity.Response;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import service.AppointmentService;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class DoAppointCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private DoAppointCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        Response response1 = new Response(Constants.URL.APPOINT, Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.ResponseStatus.NOT_FOUND);
        User user1 = new User();
        User user2 = new User();
        return new Object[][]{
                {"113", user1, 1, response1},
                {"214", user2, 1, response1},
                {"5437", null, 0, response2},
                {"8975", null, 0, response2}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, User user, int times, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getNewVisitor(userId)).thenReturn(user);

        Response actual = command.execute();

        verify(userService).getNewVisitor(userId);
        verify(appointmentService, Mockito.times(times)).getAll();
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        Response response1 = new Response(Constants.URL.APPOINT, Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.ResponseStatus.NOT_FOUND);
        return new Object[][]{
                {"113", 1, response1},
                {"214", 1, response1},
                {"5437", 0, response2},
                {"8975", 0, response2}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(String userId, int times, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getNewVisitor(userId)).thenThrow(new ServiceException());

        Response actual = command.execute();

        verify(userService).getNewVisitor(userId);
        verify(appointmentService, Mockito.times(times)).getAll();
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data() {
        Response response1 = new Response(Constants.URL.APPOINT, Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.ResponseStatus.NOT_FOUND);
        return new Object[][]{
                {"113", 1, response1},
                {"214", 1, response1},
                {"5437", 0, response2},
                {"8975", 0, response2}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputException_Data")
    public void execute_Request_InvalidInputException(String userId, int times, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getNewVisitor(userId)).thenThrow(new InvalidInputException());

        Response actual = command.execute();

        verify(userService).getNewVisitor(userId);
        verify(appointmentService, Mockito.times(times)).getAll();
        assertEquals(expected, actual);
    }
}