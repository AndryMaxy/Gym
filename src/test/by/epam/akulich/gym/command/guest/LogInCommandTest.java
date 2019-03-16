package by.epam.akulich.gym.command.guest;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
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
    public void setUp() {
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
        //given
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(login);
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(new String(password));
        when(userService.logIn(login, password)).thenReturn(userId);

        //when
        Response actual = command.execute();

        //then
        verify(userService).logIn(login, password);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String login = "valera";
        char[] password = "valera1".toCharArray();
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(login);
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(new String(password));
        when(userService.logIn(login, password)).thenThrow(new ServiceException());

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String login = "valera";
        char[] password = "valera1".toCharArray();
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(login);
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(new String(password));
        when(userService.logIn(login, password)).thenThrow(new InvalidInputException());

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}