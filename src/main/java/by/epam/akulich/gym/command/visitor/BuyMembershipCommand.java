package by.epam.akulich.gym.command.visitor;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.BookingServiceImpl;
import by.epam.akulich.gym.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for buying membership.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class BuyMembershipCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Instantiates a new BuyMembershipCommand.
     *
     * @param request current http request
     */
    public BuyMembershipCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
        this.bookingService = BookingServiceImpl.getInstance();
    }

    /**
     * Instantiates BuyMembershipCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     * @param bookingService booking service instance
     */
    public BuyMembershipCommand(HttpServletRequest request, UserService userService, BookingService bookingService) {
        super(request);
        this.userService = userService;
        this.bookingService = bookingService;
    }

    /**
     * Visitor buys membership.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
        String membershipStr = request.getParameter(Constants.Parameter.MEMBERSHIP);
        User user = userService.getUser(userId);
        boolean result = bookingService.buyMembership(user, membershipStr);
        if (!result) {
            return new Response(Constants.URL.HOME, Constants.ResponseStatus.INCORRECT_INPUT);
        }
        return new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
    }
}
