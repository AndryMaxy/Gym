package command;

import builder.UserBuilder;
import entity.User;
import entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import util.Encoder;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class.getSimpleName());
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private String url = "/controller?command=logIn";
    //private String url = "/main/";
    private UserService service;

    public SignInCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.service = UserServiceImpl.getInstance();
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String login = request.getParameter(LOGIN);

        if (service.isUserLoginExist(login)) {
            //TODO !
            throw new Error();
        }

        char[] password = request.getParameter(PASSWORD).toCharArray();
        String[] encoded;
        encoded = Encoder.encode(password);
        String hash = encoded[0];
        String salt = encoded[1]; //TODO Спросить как записывать соль и хеш в базу. Сохранять ли в объекте?
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        UserRole role = UserRole.VISITOR;
        UserBuilder builder = new UserBuilder();
        User user = builder.buildLogin(login)
                .buildName(name)
                .buildSurname(surname)
                .buildUserRole(role)
                .buildHash(hash)
                .buildSalt(salt)
                .build();
        service.add(user);
        return new Response(url, false);
    }
}
