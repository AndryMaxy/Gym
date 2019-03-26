package by.epam.akulich.gym.command.guest;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
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
        //given
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(user.getLogin());
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(password);
        when(request.getParameter("name")).thenReturn(user.getName());
        when(request.getParameter("surname")).thenReturn(user.getSurname());
        when(userService.isUserLoginExist(user.getLogin())).thenReturn(isExist);
        when(userService.createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname())).thenReturn(user);

        //when
        Response actual = command.execute();

        //then
        verify(userService).isUserLoginExist(user.getLogin());
        verify(userService, times(times)).createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname());
        verify(userService, times(times)).add(user);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        User user = new User();
        user.setName("valera");
        user.setSurname("valerii");
        user.setLogin("valera");
        String password = "valera1";
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(user.getLogin());
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(password);
        when(request.getParameter("name")).thenReturn(user.getName());
        when(request.getParameter("surname")).thenReturn(user.getSurname());
        when(userService.isUserLoginExist(user.getLogin())).thenThrow(new ServiceException());
        when(userService.createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname())).thenReturn(user);

        //then
        command.execute();

        //then
        // expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        User user = new User();
        user.setName("valera");
        user.setSurname("valerii");
        user.setLogin("valera");
        String password = "valera1";
        when(request.getParameter(Constants.Parameter.LOGIN)).thenReturn(user.getLogin());
        when(request.getParameter(Constants.Parameter.PASSWORD)).thenReturn(password);
        when(request.getParameter("name")).thenReturn(user.getName());
        when(request.getParameter("surname")).thenReturn(user.getSurname());
        when(userService.isUserLoginExist(user.getLogin())).thenThrow(new InvalidInputException());
        when(userService.createUser(user.getLogin(), password.toCharArray(), user.getName(), user.getSurname())).thenReturn(user);

        //then
        command.execute();

        //then
        // expected InvalidInputException
    }
}