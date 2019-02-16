package command;

import entity.User;
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
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String PAGE = "visitor.jsp";
    private String url = "/controller?command=main&login=";
    //private String url = "/main/";

    public LogInCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String login = request.getParameter(LOGIN);
        char[] password = request.getParameter(PASSWORD).toCharArray();
        UserService service = UserServiceImpl.getInstance();
        LOGGER.info(login + " tried log in");
        boolean isValid = service.checkUser(login, password);
        if (isValid) {
            LOGGER.info("GJ!!!!!!!!!!!!!!!!!!!");
            HttpSession session = request.getSession();
            session.setAttribute("role", UserRole.VISITOR);
            session.setAttribute("login", login);
            return new Response(url + login, true);
        } else {
            LOGGER.info("ALERT!!!!!!!!!!!!!!!!!!!");
            return new Response("/", true, false);
        }
    }
}
