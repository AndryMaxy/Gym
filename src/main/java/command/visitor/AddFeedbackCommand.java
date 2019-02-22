package command.visitor;

import command.Command;
import command.Response;
import entity.Booking;
import entity.Constants;
import service.BookingService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddFeedbackCommand extends Command {

    public AddFeedbackCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(Constants.Parameter.USER_ID);
        String feedbackStr = request.getParameter("feedbackArea");
        BookingService bookingService = BookingServiceImpl.getInstance();
        Booking booking = bookingService.getBookingByUserId(userId);
        booking.setFeedback(feedbackStr);
        bookingService.update(booking);
        return new Response("/feedback", true);
    }
}
