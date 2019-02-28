package command.admin;

import command.Command;
import entity.Response;
import entity.Constants;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class ChangeRoleCommand extends Command {

    private UserService userService = UserServiceImpl.getInstance();

    public ChangeRoleCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        String userRoleStr = request.getParameter(Constants.Parameter.ROLE);
        userService.changeRole(userIdStr, userRoleStr);
        return new Response(Constants.URL.HOME, true);
    }
}
