package by.epam.akulich.gym.command.visitor;

import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class UpBalanceCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @InjectMocks
    private UpBalanceCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        return new Object[][]{
                {"2", "cash", "200", new Response(Constants.URL.REFILL, Constants.ResponseStatus.REDIRECT)},
                {"434", "card", "550", new Response(Constants.URL.REFILL, Constants.ResponseStatus.REDIRECT)},
                {"23", "card", "1300", new Response(Constants.URL.REFILL, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, String payment, String balance, Response expected) throws InvalidInputException, ServiceException {
        //given
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.PAYMENT)).thenReturn(payment);
        when(request.getParameter(Constants.Parameter.BALANCE)).thenReturn(balance);

        //when
        Response actual = command.execute();

        //then
        verify(userService).upBalance(userId, balance, payment);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String userId = "12";
        String payment = "case";
        String balance = "300";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.PAYMENT)).thenReturn(payment);
        when(request.getParameter(Constants.Parameter.BALANCE)).thenReturn(balance);
        doThrow(new ServiceException()).when(userService).upBalance(userId, balance, payment);

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String userId = "12";
        String payment = "case";
        String balance = "300";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(Constants.Parameter.PAYMENT)).thenReturn(payment);
        when(request.getParameter(Constants.Parameter.BALANCE)).thenReturn(balance);
        doThrow(new InvalidInputException()).when(userService).upBalance(userId, balance, payment);

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}