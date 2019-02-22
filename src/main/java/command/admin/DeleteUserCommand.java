package command.admin;

import command.Command;
import command.Response;
import entity.Constants;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand extends Command {

    public DeleteUserCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        int userId = Integer.parseInt(userIdStr);
        UserService service = UserServiceImpl.getInstance();
        service.delete(userId);
        return new Response(Constants.URL.HOME, true);
    }
}