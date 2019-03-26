package by.epam.akulich.gym.command.visitor;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.dto.Response;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class RefuseAppointmentsCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private RefuseAppointmentsCommand command;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        Response response = new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
        return new Object[][]{
                {"34", response},
                {"255", response},
                {"376", response},
                {"43532", response}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String bookingId, Response expected) throws InvalidInputException, ServiceException {
        //given
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingId);

        //when
        Response actual = command.execute();

        //then
        verify(bookingService).refuseAppointments(bookingId);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        Response response = new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
        return new Object[][]{
                {"34", response},
                {"255", response},
                {"376", response},
                {"43532", response}
        };
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String bookingId = "23";
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingId);
        doThrow(new ServiceException()).when(bookingService).refuseAppointments(bookingId);

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String bookingId = "23";
        when(request.getParameter(Constants.Parameter.BOOKING_ID)).thenReturn(bookingId);
        doThrow(new InvalidInputException()).when(bookingService).refuseAppointments(bookingId);

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }
}