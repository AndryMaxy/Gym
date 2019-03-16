package by.epam.akulich.gym.command.admin;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for changing user role.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class ChangeRoleCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Instantiates ChangeRoleCommand.
     *
     * @param request current http request
     */
    public ChangeRoleCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
    }

    /**
     * Instantiates a new ChangeRoleCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     */
    public ChangeRoleCommand(HttpServletRequest request, UserService userService) {
        super(request);
        this.userService = userService;
    }

    /**
     * Changes user role.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the service layer
     * @throws InvalidInputException if user enter invalid data
     */
    @Override
    public Response execute() throws InvalidInputException, ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        String userRoleStr = request.getParameter(Constants.Parameter.ROLE);
        userService.changeRole(userIdStr, userRoleStr);
        return new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
    }
}
