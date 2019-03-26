package by.epam.akulich.gym.command.trainer;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.dto.Appointment;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.service.AppointmentService;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.AppointmentServiceImpl;
import by.epam.akulich.gym.service.impl.BookingServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * The class is used for appointing appointments for visitors.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class AppointCommand extends Command {

    /**
     * Appointment service instance.
     */
    private AppointmentService appointmentService;

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Instantiates a new AppointCommand.
     *
     * @param request current http request
     */
    public AppointCommand(HttpServletRequest request) {
        super(request);
        this.appointmentService = AppointmentServiceImpl.getInstance();
        this.bookingService = BookingServiceImpl.getInstance();
    }

    /**
     * Instantiates AppointCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param appointmentService appointment service instance
     * @param bookingService booking service instance
     */
    public AppointCommand(HttpServletRequest request, AppointmentService appointmentService, BookingService bookingService) {
        super(request);
        this.appointmentService = appointmentService;
        this.bookingService = bookingService;
    }

    /**
     * Appoints appointments for visitors.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
        Enumeration enumeration = request.getParameterNames();
        Appointment appointment = appointmentService.parseAppointment(parameterMap, enumeration);
        Booking booking = bookingService.getBookingByUserId(userIdStr);
        appointmentService.addAppointment(booking.getId(), appointment);
        return new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
    }
}
