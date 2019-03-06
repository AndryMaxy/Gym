package command.guest;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import entity.Constants;
import entity.Response;
import entity.User;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class SignInCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @InjectMocks
    private SignInCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        Response response1 = new Response("/controller?command=logIn", Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.URL.REGISTER, Constants.ResponseStatus.INCORRECT_INPUT);
        User user1 = new User();
        user1.setName("valera");
        user1.setSurname("valerii");
        user1.setLogin("valera");
        User user2 = new User();
        user1.setName("pety");
        user1.setSurname("petrov");
        user1.setLogin("1killer");
        return new Object[][]{
                {user1, "valera1", 1, false, response1},
                {user2, "24wr243", 0, true, response2}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(User user, String password, int times, boolean isExist, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(user.getLogin());
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(password);
        when(request.getParameter("name")).thenReturn(user.getName());
        when(request.getParameter("surname")).thenReturn(user.getSurname());
        when(userService.isUserLoginExist(user.getLogin())).thenReturn(isExist);
        when(userService.createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname())).thenReturn(user);

        Response actual = command.execute();

        verify(userService).isUserLoginExist(user.getLogin());
        verify(userService, times(times)).createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname());
        verify(userService, times(times)).add(user);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        Response response1 = new Response("/controller?command=logIn", Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.URL.REGISTER, Constants.ResponseStatus.INCORRECT_INPUT);
        User user1 = new User();
        User user2 = new User();
        return new Object[][]{
                {user1, "valera1", 1, false, response1},
                {user2, "24wr243", 0, true, response2}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(User user, String password, int times, boolean isExist, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(user.getLogin());
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(password);
        when(request.getParameter("name")).thenReturn(user.getName());
        when(request.getParameter("surname")).thenReturn(user.getSurname());
        when(userService.isUserLoginExist(user.getLogin())).thenThrow(new ServiceException());
        when(userService.createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname())).thenReturn(user);

        Response actual = command.execute();

        verify(userService).isUserLoginExist(user.getLogin());
        verify(userService, times(times)).createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname());
        verify(userService, times(times)).add(user);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data() {
        Response response1 = new Response("/controller?command=logIn", Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.URL.REGISTER, Constants.ResponseStatus.INCORRECT_INPUT);
        User user1 = new User();
        User user2 = new User();
        return new Object[][]{
                {user1, "valera1", 1, false, response1},
                {user2, "24wr243", 0, true, response2}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputException_Data")
    public void execute_Request_InvalidInputException(User user, String password, int times, boolean isExist, Response expected) throws InvalidInputException, ServiceException {
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(user.getLogin());
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(password);
        when(request.getParameter("name")).thenReturn(user.getName());
        when(request.getParameter("surname")).thenReturn(user.getSurname());
        when(userService.isUserLoginExist(user.getLogin())).thenThrow(new InvalidInputException());
        when(userService.createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname())).thenReturn(user);

        Response actual = command.execute();

        verify(userService).isUserLoginExist(user.getLogin());
        verify(userService, times(times)).createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname());
        verify(userService, times(times)).add(user);
        assertEquals(expected, actual);
    }
}