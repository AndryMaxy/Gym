package command.common;

import command.Command;
import entity.Response;
import entity.Booking;
import entity.Constants;
import entity.UserRole;
import service.BookingService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FeedbackPageCommand extends Command {

    public FeedbackPageCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        UserRole role = (UserRole) session.getAttribute(Constants.Parameter.ROLE);
        BookingService bookingService = BookingServiceImpl.getInstance();
        if (role == UserRole.VISITOR) {
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
            Booking booking = bookingService.getBookingByUserId(userId);
            if (booking != null) {
                request.setAttribute("booking", booking);
            }
        }
        List<Booking> bookings = bookingService.getAll();
        request.setAttribute("bookings", bookings);
        return new Response(Constants.URL.FEEDBACK_JSP, false);
    }
}
