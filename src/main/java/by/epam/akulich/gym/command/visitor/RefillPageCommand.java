package by.epam.akulich.gym.command.visitor;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for opening refill page.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class RefillPageCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Instantiates a new RefillPageCommand.
     *
     * @param request current http request
     */
    public RefillPageCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
    }

    /**
     * Instantiates RefillPageCommand. This constructor uses for tests.
     * @param request current http request
     * @param userService booking service instance
     */
    public RefillPageCommand(HttpServletRequest request, UserService userService) {
        super(request);
        this.userService = userService;
    }

    /**
     * Opens refill page.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
        User user = userService.getUser(userId);
        request.setAttribute(Constants.Parameter.USER, user);
        return new Response(Constants.URL.REFILL_JSP, Constants.ResponseStatus.FORWARD);
    }
}
