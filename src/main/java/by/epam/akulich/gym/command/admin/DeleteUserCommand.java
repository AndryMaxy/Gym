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
 * The class is used for deleting user.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class DeleteUserCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Instantiates DeleteUserCommand.
     *
     * @param request current http request
     */
    public DeleteUserCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
    }

    /**
     * Instantiates a new DeleteUserCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     */
    public DeleteUserCommand(HttpServletRequest request, UserService userService) {
        super(request);
        this.userService = userService;
    }

    /**
     * Deletes user.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        userService.delete(userIdStr);
        return new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
    }
}
