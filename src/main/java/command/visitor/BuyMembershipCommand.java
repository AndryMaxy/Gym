package command.visitor;

import command.Command;
import entity.Response;
import entity.Constants;
import entity.User;
import service.BookingService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class BuyMembershipCommand extends Command {

    private UserService userService = UserServiceImpl.getInstance();
    private BookingService bookingService = BookingServiceImpl.getInstance();

    public BuyMembershipCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        String membershipStr = request.getParameter(Constants.Parameter.MEMBERSHIP);
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
        User user = userService.getUser(userId);
        boolean result = bookingService.buyMembership(user, membershipStr);
        if (!result) {
            return new Response(Constants.URL.HOME, true, true);
        }
        return new Response(Constants.URL.HOME, true);
    }
}
