package command.guest;

import command.Command;
import entity.Response;
import entity.Constants;
import entity.UserRole;
import org.apache.commons.text.StringEscapeUtils;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for log in user in his account.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class LogInCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Instantiates a new LogInCommand.
     *
     * @param request current http request
     */
    public LogInCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
    }

    /**
     * Instantiates LogInCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     */
    public LogInCommand(HttpServletRequest request, UserService userService ) {
        super(request);
        this.userService = userService;
    }

    /**
     * Logs in user in his account.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String login = request.getParameter(Constants.Parameter.LOGIN);
        char[] password = request.getParameter(Constants.Parameter.PASSWORD).toCharArray();
        int userId = userService.logIn(login, password);
        if (userId == 0) {
            return new Response(Constants.URL.ROOT, Constants.ResponseStatus.INCORRECT_INPUT);
        }
        session.setAttribute(Constants.Parameter.ROLE, UserRole.VISITOR);
        session.setAttribute(Constants.Parameter.USER_ID, String.valueOf(userId));
        return new Response(Constants.URL.HOME, Constants.ResponseStatus.REDIRECT);
    }
}
