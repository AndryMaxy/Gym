package command.admin;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class DeleteUserCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @InjectMocks
    private DeleteUserCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        return new Object[][]{
                {"5", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"220", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"475587", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String id, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);

        Response actual = command.execute();

        verify(userService).delete(id);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data(){
        return new Object[][]{
                {"5", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"220", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"475587", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(String id, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new ServiceException()).when(userService).delete(id);

        Response actual = command.execute();

        verify(userService).delete(id);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputExceptionData(){
        return new Object[][]{
                {"5", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"220", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"475587", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputExceptionData")
    public void execute_Request_InvalidInputException(String id, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new InvalidInputException()).when(userService).delete(id);

        Response actual = command.execute();

        verify(userService).delete(id);
        assertEquals(expected, actual);
    }
}