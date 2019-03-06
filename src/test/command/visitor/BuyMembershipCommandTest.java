package command.visitor;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import entity.Constants;
import entity.Response;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BookingService;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class BuyMembershipCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @Mock
    private BookingService bookingService;

    private BuyMembershipCommand command;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new BuyMembershipCommand(request, userService, bookingService);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        Response response1 = new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
        Response response2 = new Response(Constants.URL.HOME, Constants.ResponseStatus.INCORRECT_INPUT);
        User user1 = new User();
        user1.setName("valera");
        User user2 = new User();
        user1.setName("katy");
        User user3 = new User();
        user1.setName("Petr");
        User user4 = new User();
        user1.setName("Vanessa");
        return new Object[][]{
                {"34", "SUPER", user1, true, response1},
                {"255", "ONE", user2, true, response1},
                {"376", "ULTRA", user3, false, response2},
                {"43532", "EASY", user4, false, response2}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, String membership, User user, boolean canBuy, Response expected) throws InvalidInputException, ServiceException {
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.MEMBERSHIP)).thenReturn(membership);
        when(userService.getUser(userId)).thenReturn(user);
        when(bookingService.buyMembership(user, membership)).thenReturn(canBuy);

        Response actual = command.execute();

        verify(userService).getUser(userId);
        verify(bookingService).buyMembership(user, membership);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        Response response1 = new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
        Response response2 = new Response(Constants.URL.HOME, Constants.ResponseStatus.INCORRECT_INPUT);
        User user1 = new User();
        user1.setName("valera");
        User user2 = new User();
        user1.setName("katy");
        User user3 = new User();
        user1.setName("Petr");
        User user4 = new User();
        user1.setName("Vanessa");
        return new Object[][]{
                {"34", "SUPER", user1, response1},
                {"255", "ONE", user2, response1},
                {"376", "ULTRA", user3, response2},
                {"43532", "EASY", user4, response2}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(String userId, String membership, User user, Response expected) throws InvalidInputException, ServiceException {
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.MEMBERSHIP)).thenReturn(membership);
        when(userService.getUser(userId)).thenReturn(user);
        when(bookingService.buyMembership(user, membership)).thenThrow(new ServiceException());

        Response actual = command.execute();

        verify(userService).getUser(userId);
        verify(bookingService).buyMembership(user, membership);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data() {
        Response response1 = new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
        Response response2 = new Response(Constants.URL.HOME, Constants.ResponseStatus.INCORRECT_INPUT);
        User user1 = new User();
        user1.setName("valera");
        User user2 = new User();
        user1.setName("katy");
        User user3 = new User();
        user1.setName("Petr");
        User user4 = new User();
        user1.setName("Vanessa");
        return new Object[][]{
                {"34", "SUPER", user1, response1},
                {"255", "ONE", user2, response1},
                {"376", "ULTRA", user3, response2},
                {"43532", "EASY", user4, response2}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputException_Data")
    public void execute_Request_InvalidInputException_Data(String userId, String membership, User user, Response expected) throws InvalidInputException, ServiceException {
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.MEMBERSHIP)).thenReturn(membership);
        when(userService.getUser(userId)).thenReturn(user);
        when(bookingService.buyMembership(user, membership)).thenThrow(new InvalidInputException());

        Response actual = command.execute();

        verify(userService).getUser(userId);
        verify(bookingService).buyMembership(user, membership);
        assertEquals(expected, actual);
    }
}