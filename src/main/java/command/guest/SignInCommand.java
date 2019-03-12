package command.guest;

import command.Command;
import entity.Response;
import entity.Constants;
import entity.User;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for sign in user account.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class SignInCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Instantiates a new SignInCommand.
     *
     * @param request current http request
     */
    public SignInCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
    }

    /**
     * Instantiates SignInCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     */
    public SignInCommand(HttpServletRequest request, UserService userService) {
        super(request);
        this.userService = userService;
    }

    /**
     * Signs in user account.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String login = request.getParameter(Constants.Parameter.LOGIN);
        boolean isExist = userService.isUserLoginExist(login);
        if (isExist) {
            return new Response(Constants.URL.REGISTER, Constants.ResponseStatus.INCORRECT_INPUT);
        }
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        char[] password = request.getParameter(Constants.Parameter.PASSWORD).toCharArray();
        User user = userService.createUser(login, password, name, surname);
        userService.add(user);
        return new Response("/controller?command=logIn", Constants.ResponseStatus.FORWARD);
    }
}
