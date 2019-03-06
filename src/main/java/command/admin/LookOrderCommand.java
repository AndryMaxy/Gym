package command.admin;

import command.Command;
import entity.Response;
import entity.Booking;
import entity.Constants;
import entity.User;
import entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.BookingService;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * The class is used for opening the page with the orders of the selected user.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class LookOrderCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Instantiates LookOrderCommand.
     *
     * @param request current http request
     */
    public LookOrderCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
        this.bookingService = BookingServiceImpl.getInstance();
    }

    /**
     * Instantiates LookOrderCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     * @param bookingService booking service instance
     */
    public LookOrderCommand(HttpServletRequest request, UserService userService, BookingService bookingService) {
        super(request);
        this.userService = userService;
        this.bookingService = bookingService;
    }

    /**
     * Opens page with user orders.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the service layer
     * @throws InvalidInputException if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        User user = userService.getUser(userIdStr);
        if (user.getRole() != UserRole.VISITOR) {
            return new Response(Constants.ResponseStatus.NOT_FOUND);
        }
        List<Booking> bookings = bookingService.getBookingList(userIdStr);
        request.setAttribute("user", user);
        request.setAttribute("bookings", bookings);
        return new Response(Constants.URL.ORDER, Constants.ResponseStatus.FORWARD);
    }
}
