package command.admin;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import entity.Constants;
import entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BookingService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class ReduceVisitsCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private ReduceVisitsCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        return new Object[][]{
                {"5", "10", new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"220", "244", new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"475587", "515881", new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, String bookingIdStr, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingIdStr);

        Response actual = command.execute();

        verify(bookingService).reduceVisits(bookingIdStr);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data(){
        return new Object[][]{
                {"5", "10", new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"220", "244", new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"475587", "515881", new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(String userId, String bookingIdStr, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingIdStr);
        doThrow(new ServiceException()).when(bookingService).reduceVisits(bookingIdStr);

        Response actual = command.execute();

        verify(bookingService).reduceVisits(bookingIdStr);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data(){
        return new Object[][]{
                {"5", "1,0", new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"220", "244fds", new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"ten", "515881", new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputException_Data")
    public void execute_Request_InvalidInputException(String userId, String bookingIdStr, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingIdStr);
        doThrow(new InvalidInputException()).when(bookingService).reduceVisits(bookingIdStr);

        Response actual = command.execute();

        verify(bookingService).reduceVisits(bookingIdStr);
        assertEquals(expected, actual);
    }
}