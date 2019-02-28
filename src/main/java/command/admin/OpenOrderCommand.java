package command.admin;

import command.Command;
import entity.Response;
import entity.Booking;
import entity.Constants;
import entity.User;
import entity.UserRole;
import service.BookingService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenOrderCommand extends Command {

    private UserService userService = UserServiceImpl.getInstance();
    private BookingService bookingService = BookingServiceImpl.getInstance();

    public OpenOrderCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        User user = userService.getUser(userIdStr);
        UserRole userRole = user.getRole();
        if ((userRole == UserRole.ADMIN) || (userRole == UserRole.TRAINER)) {
            return new Response(Constants.URL.ROOT, true);
        }
        List<Booking> bookings = bookingService.getBookingList(userIdStr);
        request.setAttribute("user", user);
        request.setAttribute("bookings", bookings);
        return new Response(Constants.URL.ORDER, false);
    }
}
