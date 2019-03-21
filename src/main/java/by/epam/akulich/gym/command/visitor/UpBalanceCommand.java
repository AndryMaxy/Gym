package by.epam.akulich.gym.command.visitor;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for refilling balance.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class UpBalanceCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Instantiates a new UpBalanceCommand.
     * @param request current http request
     */
    public UpBalanceCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
    }

    /**
     * Instantiates UpBalanceCommand. This constructor uses for tests.
     * @param request current http request
     * @param userService booking service instance
     */
    public UpBalanceCommand(HttpServletRequest request, UserService userService) {
        super(request);
        this.userService = userService;
    }

    /**
     * Refilling user's balance.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String payment = request.getParameter(Constants.Parameter.PAYMENT);
        String userId = request.getParameter(Constants.Parameter.USER_ID);
        String balance = request.getParameter(Constants.Parameter.BALANCE);
        userService.upBalance(userId, balance, payment);
        return new Response(Constants.URL.REFILL, Constants.ResponseStatus.REDIRECT);
    }
}
