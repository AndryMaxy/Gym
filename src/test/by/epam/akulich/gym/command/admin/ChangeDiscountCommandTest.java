package by.epam.akulich.gym.command.admin;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
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
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class ChangeDiscountCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChangeDiscountCommand command;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        return new Object[][]{
                {"5", "15", new Response("/order?userId=" + 5, Constants.ResponseStatus.REDIRECT)},
                {"220", "25", new Response("/order?userId=" + 220, Constants.ResponseStatus.REDIRECT)},
                {"475587", "0", new Response("/order?userId=" + 475587, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String id, String discount, Response expected) throws ServiceException, InvalidInputException {
        //given
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(request.getParameter(Constants.Parameter.DISCOUNT)).thenReturn(discount);

        //when
        Response actual = command.execute();

        //then
        verify(userService).changeDiscount(id,discount);
        assertEquals(expected, actual);
    }

    @Test (expected = ServiceException.class)
    public void execute_Request_ServiceException() throws ServiceException, InvalidInputException {
        //given
        String id = "5";
        String discount = "25";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(request.getParameter(Constants.Parameter.DISCOUNT)).thenReturn(discount);
        doThrow(new ServiceException()).when(userService).changeDiscount(id, discount);

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test (expected = InvalidInputException.class)
    public void execute_Request_InvalidException() throws ServiceException, InvalidInputException {
        //given
        String id = "5";
        String discount = "25";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        when(request.getParameter(Constants.Parameter.DISCOUNT)).thenReturn(discount);
        doThrow(new InvalidInputException()).when(userService).changeDiscount(id, discount);

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}