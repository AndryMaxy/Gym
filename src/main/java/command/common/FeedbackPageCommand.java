package command.common;

import command.Command;
import entity.Response;
import entity.Booking;
import entity.Constants;
import entity.UserRole;
import service.BookingService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The class is used for opening feedback page.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class FeedbackPageCommand extends Command {

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Instantiates FeedbackPageCommand.
     *
     * @param request current http request
     */
    public FeedbackPageCommand(HttpServletRequest request) {
        super(request);
        this.bookingService = BookingServiceImpl.getInstance();
    }

    /**
     * Instantiates a new FeedbackPageCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param bookingService booking service instance
     */
    public FeedbackPageCommand(HttpServletRequest request, BookingService bookingService) {
        super(request);
        this.bookingService = bookingService;
    }

    /**
     * Opens feedback page.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        UserRole role = (UserRole) session.getAttribute(Constants.Parameter.ROLE);
        if (role == UserRole.VISITOR) {
            String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
            Booking booking = bookingService.getBookingByUserId(userId);
            if (booking != null) {
                request.setAttribute("booking", booking);
            }
        }
        List<Booking> bookings = bookingService.getAll();
        request.setAttribute("bookings", bookings);
        return new Response(Constants.URL.FEEDBACK_JSP, Constants.ResponseStatus.FORWARD);
    }
}
