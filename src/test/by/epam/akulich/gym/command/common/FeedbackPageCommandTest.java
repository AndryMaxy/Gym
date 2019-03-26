package by.epam.akulich.gym.command.common;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(DataProviderRunner.class)
public class FeedbackPageCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private FeedbackPageCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new FeedbackPageCommand(request, bookingService);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        return new Object[][]{
                {null, UserRole.ADMIN, 0, new Response(Constants.URL.FEEDBACK_JSP, Constants.ResponseStatus.FORWARD)},
                {"220", UserRole.VISITOR, 1, new Response(Constants.URL.FEEDBACK_JSP, Constants.ResponseStatus.FORWARD)},
                {null, UserRole.TRAINER, 0, new Response(Constants.URL.FEEDBACK_JSP, Constants.ResponseStatus.FORWARD)},
                {null, UserRole.GUEST, 0, new Response(Constants.URL.FEEDBACK_JSP, Constants.ResponseStatus.FORWARD)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String id, UserRole role, int times, Response expected) throws ServiceException, InvalidInputException {
        //given
        when(session.getAttribute(Constants.Parameter.ROLE)).thenReturn(role);
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(id);

        //when
        Response actual = command.execute();

        //then
        verify(bookingService, times(times)).getBookingByUserId(id);
        verify(bookingService).getAll();
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws ServiceException, InvalidInputException {
        //given
        String id = "220";
        UserRole role = UserRole.VISITOR;
        when(session.getAttribute(Constants.Parameter.ROLE)).thenReturn(role);
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(id);
        when(bookingService.getAll()).thenThrow(new ServiceException());

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws ServiceException, InvalidInputException {
        //given
        String id = "220";
        UserRole role = UserRole.VISITOR;
        when(session.getAttribute(Constants.Parameter.ROLE)).thenReturn(role);
        when(session.getAttribute(Constants.Parameter.USER_ID)).thenReturn(id);
        when(bookingService.getBookingByUserId(id)).thenThrow(new InvalidInputException());

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}
