package command.common;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import command.exception.CommandException;
import entity.Constants;
import entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class ChangeLocaleCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private ChangeLocaleCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new ChangeLocaleCommand(request);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        String referer1 = "http://localhost:8080/gym/order?userId=5";
        String referer2 = "http://localhost:8080/gym/order?userId=220";
        String referer3 = "http://localhost:8080/gym/order?userId=475587";
        return new Object[][]{
                {"ru-RU", referer1, new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"en-US", referer2, new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"be-BY", referer3, new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String locale, String referer, Response expected) throws CommandException {
        when(request.getParameter(Constants.Parameter.LOCALE)).thenReturn(locale);
        when(request.getHeader("referer")).thenReturn(referer);

        Response actual = command.execute();

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_CommandException_Data(){
        String referer1 = "http:/localho[st:8080/gym/order?userId=5";
        String referer2 = "htp:<//localhost:8080/gym/order?userId=220";
        String referer3 = "http:||localhost8080/gym|order?userId=475587";
        return new Object[][]{
                {"ru-RU", referer1, new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"en-US", referer2, new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"be-BY", referer3, new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test(expected = CommandException.class)
    @UseDataProvider("execute_Request_CommandException_Data")
    public void execute_Request_CommandException(String locale, String referer, Response expected) throws CommandException {
        when(request.getParameter(Constants.Parameter.LOCALE)).thenReturn(locale);
        when(request.getHeader("referer")).thenReturn(referer);

        Response actual = command.execute();

        assertEquals(expected, actual);
    }
}