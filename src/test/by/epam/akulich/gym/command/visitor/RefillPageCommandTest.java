package by.epam.akulich.gym.command.visitor;

import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.entity.UserRole;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class RefillPageCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @InjectMocks
    private RefillPageCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new RefillPageCommand(request, userService);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        User user1 = new User();
        user1.setName("valera");
        User user2 = new User();
        user2.setBalance(100000);
        User user3 = new User();
        user3.setRole(UserRole.ADMIN);
        return new Object[][]{
                {"5", user1, new Response(Constants.URL.REFILL_JSP, Constants.ResponseStatus.FORWARD)},
                {"220", user2, new Response(Constants.URL.REFILL_JSP, Constants.ResponseStatus.FORWARD)},
                {"475587", user3, new Response(Constants.URL.REFILL_JSP, Constants.ResponseStatus.FORWARD)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userIdStr, User user, Response expected) throws InvalidInputException, ServiceException {
        //given
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userIdStr);
        when(userService.getUser(userIdStr)).thenReturn(user);

        //when
        Response actual = command.execute();

        //then
        verify(request).setAttribute(Constants.Parameter.USER, user);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String userIdStr = "12";
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userIdStr);
        when(userService.getUser(userIdStr)).thenThrow(new ServiceException());

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String userIdStr = "12";
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userIdStr);
        when(userService.getUser(userIdStr)).thenThrow(new InvalidInputException());

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}