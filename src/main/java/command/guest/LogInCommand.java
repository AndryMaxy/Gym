package command.guest;

import command.Command;
import entity.Response;
import entity.Constants;
import entity.UserRole;
import org.apache.commons.text.StringEscapeUtils;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;

public class LogInCommand extends Command {

    private UserService userService = UserServiceImpl.getInstance();

    public LogInCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String login = request.getParameter(Constants.Parameter.LOGIN);
        String escapedLogin = StringEscapeUtils.escapeHtml4(login);
        char[] password = request.getParameter(Constants.Parameter.PASSWORD).toCharArray();
        int userId = userService.logIn(escapedLogin, password);
        if (userId == 0) {
            return new Response(Constants.URL.ROOT, true, true);
        }
        session.setAttribute(Constants.Parameter.ROLE, UserRole.VISITOR);
        session.setAttribute(Constants.Parameter.USER_ID, String.valueOf(userId));
        return new Response(Constants.URL.HOME, true);
    }
}
