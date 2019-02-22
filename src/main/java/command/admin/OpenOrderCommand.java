package command.admin;

import command.Command;
import command.Response;
import entity.Booking;
import entity.Constants;
import entity.User;
import entity.UserRole;
import service.BookingService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenOrderCommand extends Command {

    public OpenOrderCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        int userId = Integer.parseInt(userIdStr);
        UserService userService = UserServiceImpl.getInstance();
        User user = userService.getUser(userId);
        UserRole userRole = user.getRole();
        if ((userRole == UserRole.ADMIN) || (userRole == UserRole.TRAINER)) {
            return new Response(Constants.URL.ROOT, true);
        }
        BookingService service = BookingServiceImpl.getInstance();
        List<Booking> bookings = service.getBookings(userId);
        request.setAttribute("user", user);
        request.setAttribute("bookings", bookings);
        return new Response(Constants.URL.ORDER, false);
    }
}
