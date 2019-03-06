package command.admin;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import command.exception.CommandException;
import entity.Constants;
import entity.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class ChangeDiscountCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChangeDiscountCommand command;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        return new Object[][]{
                {"5", "15", new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"220", "25", new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"475587", "0", new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String id, String discount, Response expected) throws ServiceException, InvalidInputException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(request.getParameter(Constants.Parameter.DISCOUNT)).thenReturn(discount);

        Response actual = command.execute();

        verify(userService).changeDiscount(id,discount);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceExceptionData(){
        return new Object[][]{
                {"5", "15", new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"220", "25", new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"475587", "0", new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test (expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceExceptionData")
    public void execute_Request_ServiceException(String id, String discount, Response expected) throws ServiceException, InvalidInputException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(request.getParameter(Constants.Parameter.DISCOUNT)).thenReturn(discount);
        doThrow(new ServiceException()).when(userService).changeDiscount(id, discount);

        Response actual = command.execute();

        verify(userService).changeDiscount(id,discount);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidException_Data(){
        return new Object[][]{
                {"5", "35", new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"nine", "25", new Response("/order?userId=" + "nine", Constants.ResponseStatus.REDIRECT)},
                {"324", "100", new Response("/order?userId=" + "324", Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test (expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidException_Data")
    public void execute_Request_InvalidException(String id, String discount, Response expected) throws ServiceException, InvalidInputException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(request.getParameter(Constants.Parameter.DISCOUNT)).thenReturn(discount);
        doThrow(new InvalidInputException()).when(userService).changeDiscount(id, discount);
        Response actual = command.execute();

        verify(userService).changeDiscount(id,discount);
        assertEquals(expected, actual);
    }
}