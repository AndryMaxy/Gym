package command.visitor;

import command.Command;
import entity.Response;
import entity.Booking;
import entity.Constants;
import service.BookingService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddFeedbackCommand extends Command {

    public AddFeedbackCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
        String feedbackStr = request.getParameter(Constants.Parameter.FEEDBACK_AREA);
        BookingService bookingService = BookingServiceImpl.getInstance();
        Booking booking = bookingService.getBookingByUserId(userId);
        booking.setFeedback(feedbackStr);
        bookingService.update(booking);
        return new Response(Constants.URL.FEEDBACK, true);
    }
}
