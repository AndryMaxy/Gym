package command.guest;

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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class LogInCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    private LogInCommand command;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new LogInCommand(request, userService);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        return new Object[][]{
                {"valera", "valera1".toCharArray(), 5, new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"1killer", "pet324".toCharArray(), 12, new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"iam321", "iam321".toCharArray(), 0, new Response(Constants.URL.ROOT, Constants.ResponseStatus.INCORRECT_INPUT)},
                {"dogger", "24wr243".toCharArray(), 0, new Response(Constants.URL.ROOT, Constants.ResponseStatus.INCORRECT_INPUT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String login, char[] password, int userId, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(login);
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(new String(password));
        when(userService.logIn(login, password)).thenReturn(userId);

        Response actual = command.execute();

        verify(userService).logIn(login, password);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        return new Object[][]{
                {"valera", "valera1".toCharArray(),new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"1killer", "pet324".toCharArray(), new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"iam321", "iam321".toCharArray(), new Response(Constants.URL.ROOT, Constants.ResponseStatus.INCORRECT_INPUT)},
                {"dogger", "24wr243".toCharArray(), new Response(Constants.URL.ROOT, Constants.ResponseStatus.INCORRECT_INPUT)}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(String login, char[] password, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(login);
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(new String(password));
        when(userService.logIn(login, password)).thenThrow(new ServiceException());

        Response actual = command.execute();

        verify(userService).logIn(login, password);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data() {
        return new Object[][]{
                {"valera", "valera1".toCharArray(), new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"1killer", "pet324".toCharArray(), new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"iam321", "iam321".toCharArray(), new Response(Constants.URL.ROOT, Constants.ResponseStatus.INCORRECT_INPUT)},
                {"dogger", "24wr243".toCharArray(), new Response(Constants.URL.ROOT, Constants.ResponseStatus.INCORRECT_INPUT)}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputException_Data")
    public void execute_Request_InvalidInputException(String login, char[] password, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(login);
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(new String(password));
        when(userService.logIn(login, password)).thenThrow(new InvalidInputException());

        Response actual = command.execute();

        verify(userService).logIn(login, password);
        assertEquals(expected, actual);
    }
}