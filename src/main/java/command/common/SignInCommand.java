package command.common;

import builder.UserBuilder;
import command.Command;
import command.Response;
import entity.Constants;
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

public class SignInCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class.getSimpleName());

    public SignInCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String login = request.getParameter(Constants.Parameter.LOGIN);
        UserService service = UserServiceImpl.getInstance();
        if (service.isUserLoginExist(login)) {
            //TODO !
            throw new Error("exist");
        }
        char[] password = request.getParameter(Constants.Parameter.PASSWORD).toCharArray();
        String[] encoded = Encoder.encode(password);
        String hash = encoded[0];
        String salt = encoded[1]; //TODO Спросить как записывать соль и хеш в базу. Сохранять ли в объекте?
        String name = request.getParameter(Constants.Parameter.NAME);
        String surname = request.getParameter(Constants.Parameter.SURNAME);
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
        return new Response("/controller?command=logIn", false);
    }
}
