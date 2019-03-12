package command.admin;

import command.Command;
import entity.Response;
import entity.Booking;
import entity.Constants;
import service.BookingService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * The class is used for reducing user visits count.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class ReduceVisitsCommand extends Command {

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Instantiates ReduceVisitsCommand.
     *
     * @param request current http request
     */
    public ReduceVisitsCommand(HttpServletRequest request) {
        super(request);
        this.bookingService = BookingServiceImpl.getInstance();
    }

    /**
     * Instantiates a new ReduceVisitsCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param bookingService booking service instance
     */
    public ReduceVisitsCommand(HttpServletRequest request, BookingService bookingService) {
        super(request);
        this.bookingService = bookingService;
    }

    /**
     * Reduces user visits count.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the service layer
     * @throws InvalidInputException if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        String bookingIdStr = request.getParameter(Constants.Parameter.BOOKING_ID);
        bookingService.reduceVisits(bookingIdStr);
        return new Response("/order?userId=" + userIdStr, Constants.ResponseStatus.REDIRECT);
    }
}
