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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class DeleteUserCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserService userService;

    @InjectMocks
    private DeleteUserCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data(){
        return new Object[][]{
                {"5", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"220", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)},
                {"475587", new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT)}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String id, Response expected) throws InvalidInputException, ServiceException {
        //given
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);

        //when
        Response actual = command.execute();

        //then
        verify(userService).delete(id);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String id = "5";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new ServiceException()).when(userService).delete(id);

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String id = "5";
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(id);
        doThrow(new InvalidInputException()).when(userService).delete(id);

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}