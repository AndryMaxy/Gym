package command;

import builder.VisitorBuilder;
import dao.exception.DuplicateInsertException;
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

public class SignInCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class.getSimpleName());
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public Status execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String[] encoded;
        try {
            encoded = Encoder.encode(password);
        } catch (EncoderException e) {
            LOGGER.error("Can't encode password.", e);
            return Status.EXCEPTION;
        }
        String hash = encoded[0];
        String salt = encoded[1]; //TODO Спросить как записывать соль и хеш в базу. Сохранять ли в объекте?
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        UserRole role = UserRole.VISITOR;
        VisitorBuilder builder = new VisitorBuilder();
        Visitor visitor = builder.buildLogin(login)
                .buildHash(hash)
                .buildSalt(salt)
                .buildName(name)
                .buildSurname(surname)
                .buildUserRole(role)
                .build();
        UserService service = new UserServiceImpl();
        try {
            service.add(visitor);
        } catch (ServiceException e) {
            LOGGER.error("Can't sign in user.", e);
            return Status.EXCEPTION;
        } catch (DuplicateInsertException e
        ) {
            return Status.EXCEPTION;
        }
        int id = visitor.getId();
        HttpSession session = request.getSession();
        session.setAttribute("id", id);
        return Status.OK;
    }
}
