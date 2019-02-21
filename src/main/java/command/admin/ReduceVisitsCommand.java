package command.admin;

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

public class ReduceVisitsCommand extends Command {

    public ReduceVisitsCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        String bookingIdStr = request.getParameter("bookingId");
        int bookingId = Integer.parseInt(bookingIdStr);
        BookingService service = BookingServiceImpl.getInstance();
        Booking booking = service.getBooking(bookingId);
        int newCount = booking.getVisitCountLeft() - 1;
        booking.setVisitCountLeft(newCount);
        service.update(booking);
        return new Response("/order?userId=" + userIdStr, true);
    }
}
