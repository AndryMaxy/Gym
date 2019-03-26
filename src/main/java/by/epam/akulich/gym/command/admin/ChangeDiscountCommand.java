package by.epam.akulich.gym.command.admin;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for changing user discount.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class ChangeDiscountCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Instantiates ChangeDiscountCommand.
     *
     * @param request current http request
     */
    public ChangeDiscountCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
    }

    /**
     * Instantiates a new ChangeDiscountCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     */
    public ChangeDiscountCommand(HttpServletRequest request, UserService userService) {
        super(request);
        this.userService = userService;
    }

    /**
     * Changes user discount.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        String discountStr = request.getParameter(Constants.Parameter.DISCOUNT);
        userService.changeDiscount(userIdStr, discountStr);
        return new Response("/order?userId=" + userIdStr, Constants.ResponseStatus.REDIRECT);
    }
}
