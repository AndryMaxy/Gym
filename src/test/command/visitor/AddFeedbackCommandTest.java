package command.visitor;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import entity.Constants;
import entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BookingService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class AddFeedbackCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private BookingService bookingService;

    private AddFeedbackCommand command;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new AddFeedbackCommand(request, bookingService);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        Response response1 = new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.REDIRECT);
        Response response2 = new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.INCORRECT_INPUT);
        return new Object[][]{
                {"34", "Nice gym and site. I like it!", true, response1},
                {"255", "Все круто. Приду ещё и друзей позаву.", true, response1},
                {"376", "Thank you. Alexei best trainer. I recommend.", false, response2},
                {"43532", "Нужно больше зеркал, я хочу видеть себя когда занимаюсь", false, response2}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, String feedback, boolean canSetFeedBack, Response expected) throws InvalidInputException, ServiceException {
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.FEEDBACK_AREA)).thenReturn(feedback);
        when(bookingService.setFeedback(userId, feedback)).thenReturn(canSetFeedBack);

        Response actual = command.execute();

        verify(bookingService).setFeedback(userId, feedback);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        Response response1 = new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.REDIRECT);
        Response response2 = new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.INCORRECT_INPUT);
        return new Object[][]{
                {"34", "Nice gym and site. I like it!", response1},
                {"255", "Все круто. Приду ещё и друзей позаву.", response1},
                {"376", "Thank you. Alexei best trainer. I recommend.", response2},
                {"43532", "Нужно больше зеркал, я хочу видеть себя когда занимаюсь", response2}
        };
    }

    @Test(expected = ServiceException.class)
    @UseDataProvider("execute_Request_ServiceException_Data")
    public void execute_Request_ServiceException(String userId, String feedback, Response expected) throws InvalidInputException, ServiceException {
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.FEEDBACK_AREA)).thenReturn(feedback);
        when(bookingService.setFeedback(userId, feedback)).thenThrow(new ServiceException());

        Response actual = command.execute();

        verify(bookingService).setFeedback(userId, feedback);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_InvalidInputException_Data() {
        Response response1 = new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.REDIRECT);
        Response response2 = new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.INCORRECT_INPUT);
        return new Object[][]{
                {"34", "Nice gym and site. I like it!", response1},
                {"255", "Все круто. Приду ещё и друзей позаву.", response1},
                {"376", "Thank you. Alexei best trainer. I recommend.", response2},
                {"43532", "Нужно больше зеркал, я хочу видеть себя когда занимаюсь", response2}
        };
    }

    @Test(expected = InvalidInputException.class)
    @UseDataProvider("execute_Request_InvalidInputException_Data")
    public void execute_Request_InvalidInputException(String userId, String feedback, Response expected) throws InvalidInputException, ServiceException {
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.FEEDBACK_AREA)).thenReturn(feedback);
        when(bookingService.setFeedback(userId, feedback)).thenThrow(new InvalidInputException());

        Response actual = command.execute();

        verify(bookingService).setFeedback(userId, feedback);
        assertEquals(expected, actual);
    }
}