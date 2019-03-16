package by.epam.akulich.gym.command.visitor;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.BookingServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for refusing user appointments.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class RefuseAppointmentsCommand extends Command {

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Instantiates a new RefuseAppointmentsCommand.
     * @param request current http request
     */
    public RefuseAppointmentsCommand(HttpServletRequest request) {
        super(request);
        this.bookingService = BookingServiceImpl.getInstance();
    }

    /**
     * Instantiates RefuseAppointmentsCommand. This constructor uses for tests.
     * @param request current http request
     * @param bookingService booking service instance
     */
    public RefuseAppointmentsCommand(HttpServletRequest request, BookingService bookingService) {
        super(request);
        this.bookingService = bookingService;
    }

    /**
     * Refuses user appointments.
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the service layer
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String bookingIdStr = request.getParameter(Constants.Parameter.BOOKING_ID);
        bookingService.refuseAppointments(bookingIdStr);
        return new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
    }
}
