package by.epam.akulich.gym.command.trainer;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.AppointmentService;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class DoAppointCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private DoAppointCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        Response response1 = new Response(Constants.URL.APPOINT, Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.ResponseStatus.NOT_FOUND);
        User user1 = new User();
        User user2 = new User();
        return new Object[][]{
                {"113", user1, 1, response1},
                {"214", user2, 1, response1},
                {"5437", null, 0, response2},
                {"8975", null, 0, response2}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, User user, int times, Response expected) throws InvalidInputException, ServiceException {
        //given
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getNewVisitor(userId)).thenReturn(user);

        //when
        Response actual = command.execute();

        //then
        verify(userService).getNewVisitor(userId);
        verify(appointmentService, Mockito.times(times)).getAll();
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String userId = "222";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getNewVisitor(userId)).thenThrow(new ServiceException());

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data() {
        Response response1 = new Response(Constants.URL.APPOINT, Constants.ResponseStatus.FORWARD);
        Response response2 = new Response(Constants.ResponseStatus.NOT_FOUND);
        return new Object[][]{
                {"113", 1, response1},
                {"214", 1, response1},
                {"5437", 0, response2},
                {"8975", 0, response2}
        };
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String userId = "222";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(userService.getNewVisitor(userId)).thenThrow(new InvalidInputException());

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}