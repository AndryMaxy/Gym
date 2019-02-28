package command.guest;

import builder.UserBuilder;
import command.Command;
import entity.Response;
import entity.Constants;
import entity.User;
import entity.UserRole;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import util.Encoder;
import util.exception.EncoderException;
import validator.ParameterValidator;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand extends Command {

    private static final String SUCCESS_URL = "/controller?command=logIn";
    private final UserService userService = UserServiceImpl.getInstance();
    private final ParameterValidator validator = ParameterValidator.getInstance();

    public SignInCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String login = request.getParameter(Constants.Parameter.LOGIN);
        if (userService.isUserLoginExist(login)) {
            return new Response(Constants.URL.REGISTER, true, true);
        }
        String name = request.getParameter(Constants.Parameter.NAME);
        String surname = request.getParameter(Constants.Parameter.SURNAME);
        char[] password = request.getParameter(Constants.Parameter.PASSWORD).toCharArray();
        if (!validator.validateName(name) || !validator.validateName(surname) || !validator.validatePassword(password)) {
            return new Response(Constants.URL.REGISTER, true, true);
        }
        String[] encoded = Encoder.encode(password);
        String hash = encoded[0];
        String salt = encoded[1];
        UserRole role = UserRole.VISITOR;
        UserBuilder builder = new UserBuilder();
        User user = builder.buildLogin(login)
                .buildName(name)
                .buildSurname(surname)
                .buildUserRole(role)
                .buildHash(hash)
                .buildSalt(salt)
                .build();
        userService.add(user);
        return new Response(SUCCESS_URL, false);
    }
}
