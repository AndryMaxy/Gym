package command.admin;

import command.Command;
import entity.Response;
import entity.Constants;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
     * Instantiates DeleteUserCommand. This constructor uses for tests.
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
