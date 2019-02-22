package command.common;

import command.Command;
import command.Response;
import entity.Booking;
import entity.Constants;
import entity.Feedback;
import entity.UserRole;
import service.BookingService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FeedbackPageCommand extends Command {

    public FeedbackPageCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute(Constants.Parameter.ROLE);
        BookingService bookingService = BookingServiceImpl.getInstance();
        if (role == UserRole.VISITOR) {
        int userId = (int) session.getAttribute(Constants.Parameter.USER_ID);
            Booking booking = bookingService.getBookingByUserId(userId);
            if (booking != null) {
                request.setAttribute("booking", booking);
            }
        }
        List<Feedback> feedbackList = bookingService.getFeedbackList();
        request.setAttribute("feedbackList", feedbackList);
        return new Response("/feedback.jsp", false);
    }
}
