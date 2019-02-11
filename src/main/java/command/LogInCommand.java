package command;

import entity.User;
import entity.UserRole;
import entity.Visitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Service;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import util.Encoder;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogInCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class.getSimpleName());
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public Status execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserService service = new UserServiceImpl();
        System.out.println(login);
        User user;
        try {
            user = service.getByLogin(login);
        } catch (ServiceException e) {
            return Status.EXCEPTION;
        }
        String hash = user.getHash();
        String salt = user.getSalt();
        System.out.println(hash);
        System.out.println(salt);
        boolean isValid = false;
        try {
            isValid = Encoder.check(password, hash.getBytes(), salt.getBytes());
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        if (isValid) {
            HttpSession session = request.getSession();
            if (user.getUserRole() == UserRole.VISITOR) {
                session.setAttribute("visitor", user);
            } else {
                session.setAttribute("user", user);
            }
        } else {
            LOGGER.info("ALERT!!!!!!!!!!!!!!!!!!!");
            return Status.EXCEPTION;
        }
        return Status.OK;
    }
}
