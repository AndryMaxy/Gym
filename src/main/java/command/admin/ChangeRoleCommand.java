package command.admin;

import command.Command;
import command.Response;
import entity.Constants;
import entity.User;
import entity.UserRole;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeRoleCommand extends Command {

    public ChangeRoleCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        int userId = Integer.parseInt(userIdStr);
        String userRoleStr = request.getParameter(Constants.Parameter.ROLE);
        UserRole userRole = UserRole.valueOf(userRoleStr);
        UserService service = UserServiceImpl.getInstance();
        User user = service.getUser(userId);
        user.setRole(userRole);
        service.update(user);
        return new Response(Constants.URL.HOME, true);
    }
}
