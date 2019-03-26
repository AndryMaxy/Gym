package by.epam.akulich.gym.command.visitor;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
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
    public void setUp() {
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
        //given
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.MEMBERSHIP)).thenReturn(membership);
        when(userService.getUser(userId)).thenReturn(user);
        when(bookingService.buyMembership(user, membership)).thenReturn(canBuy);

        //when
        Response actual = command.execute();

        //then
        verify(userService).getUser(userId);
        verify(bookingService).buyMembership(user, membership);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String userId = "222";
        String membership = "ULTRA";
        User user = new User();
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.MEMBERSHIP)).thenReturn(membership);
        when(userService.getUser(userId)).thenReturn(user);
        when(bookingService.buyMembership(user, membership)).thenThrow(new ServiceException());

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String userId = "222";
        String membership = "ULTRA";
        User user = new User();
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.MEMBERSHIP)).thenReturn(membership);
        when(userService.getUser(userId)).thenReturn(user);
        when(bookingService.buyMembership(user, membership)).thenThrow(new InvalidInputException());

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}