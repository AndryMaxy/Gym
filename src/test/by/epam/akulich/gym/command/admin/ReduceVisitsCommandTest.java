package by.epam.akulich.gym.command.admin;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
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
        //given
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingIdStr);

        //when
        Response actual = command.execute();

        //then
        verify(bookingService).reduceVisits(bookingIdStr);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String userId = "5";
        String bookingId = "10";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingId);
        doThrow(new ServiceException()).when(bookingService).reduceVisits(bookingId);

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String userId = "5";
        String bookingId = "10";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingId);
        doThrow(new InvalidInputException()).when(bookingService).reduceVisits(bookingId);

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}