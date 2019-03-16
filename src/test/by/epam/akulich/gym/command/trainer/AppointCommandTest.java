package by.epam.akulich.gym.command.trainer;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.entity.Appointment;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.AppointmentService;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class AppointCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private AppointCommand command;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] execute_Request_CorrectResponse_Data() {
        Response response = new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
        Booking booking1 = new Booking();
        booking1.setId(10);
        Booking booking2 = new Booking();
        booking2.setId(324);
        Booking booking3 = new Booking();
        booking3.setId(435);
        return new Object[][]{
                {"113", booking1, response},
                {"214", booking2, response},
                {"543", booking3, response}
        };
    }

    @Test
    @UseDataProvider("execute_Request_CorrectResponse_Data")
    public void execute_Request_CorrectResponse(String userId, Booking booking, Response expected) throws InvalidInputException, ServiceException {
        Map<String, String[]> parameterMap = new HashMap<>();
        Enumeration enumeration = mock(Enumeration.class);
        Appointment appointment = new Appointment();
        when(request.getParameterMap()).thenReturn(parameterMap);
        when(request.getParameterNames()).thenReturn(enumeration);
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(userId)).thenReturn(userId);
        when(appointmentService.parseAppointment(parameterMap, enumeration)).thenReturn(appointment);
        when(bookingService.getBookingByUserId(userId)).thenReturn(booking);

        Response actual = command.execute();

        verify(appointmentService).parseAppointment(parameterMap, enumeration);
        verify(bookingService).getBookingByUserId(userId);
        verify(appointmentService).addAppointment(booking.getId(),appointment);
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] execute_Request_ServiceException_Data() {
        Response response = new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
        Booking booking1 = new Booking();
        booking1.setId(10);
        Booking booking2 = new Booking();
        booking2.setId(324);
        Booking booking3 = new Booking();
        booking3.setId(435);
        return new Object[][]{
                {"113", booking1, response},
                {"214", booking2, response},
                {"543", booking3, response}
        };
    }

    @Test(expected = ServiceException.class)
    public void execute_Request_ServiceException() throws InvalidInputException, ServiceException {
        //given
        String userId = "113";
        Map<String, String[]> parameterMap = new HashMap<>();
        Enumeration enumeration = mock(Enumeration.class);
        Appointment appointment = new Appointment();
        when(request.getParameterMap()).thenReturn(parameterMap);
        when(request.getParameterNames()).thenReturn(enumeration);
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(userId)).thenReturn(userId);
        when(appointmentService.parseAppointment(parameterMap, enumeration)).thenReturn(appointment);
        when(bookingService.getBookingByUserId(userId)).thenThrow(new ServiceException());

        //when
        command.execute();

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void execute_Request_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String userId = "113";
        Map<String, String[]> parameterMap = new HashMap<>();
        Enumeration enumeration = mock(Enumeration.class);
        Appointment appointment = new Appointment();
        when(request.getParameterMap()).thenReturn(parameterMap);
        when(request.getParameterNames()).thenReturn(enumeration);
        when(request.getParameter(Constants.Parameter.USER_ID)).thenReturn(userId);
        when(request.getParameter(userId)).thenReturn(userId);
        when(appointmentService.parseAppointment(parameterMap, enumeration)).thenReturn(appointment);
        when(bookingService.getBookingByUserId(userId)).thenThrow(new InvalidInputException());

        //when
        command.execute();

        //then
        //expected InvalidInputException
    }

}