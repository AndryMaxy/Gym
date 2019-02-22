package command.common;

import command.Command;
import command.Response;
import entity.Constants;
import entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogInCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class.getSimpleName());

    public LogInCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String login = request.getParameter(Constants.Parameter.LOGIN);
        char[] password = request.getParameter(Constants.Parameter.PASSWORD).toCharArray();
        UserService service = UserServiceImpl.getInstance();
        LOGGER.info(login + " tried log in");
        int userId = service.logIn(login, password);
        if (userId != 0) {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.Parameter.ROLE, UserRole.VISITOR);
            session.setAttribute(Constants.Parameter.USER_ID, userId);
            return new Response(Constants.URL.HOME, true);
        } else {
            return new Response(Constants.URL.ROOT, true, false);
        }
    }
}
