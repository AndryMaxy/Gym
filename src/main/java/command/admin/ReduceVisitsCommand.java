package command.admin;

import command.Command;
import entity.Response;
import entity.Booking;
import entity.Constants;
import service.BookingService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class ReduceVisitsCommand extends Command {

    private BookingService bookingService = BookingServiceImpl.getInstance();

    public ReduceVisitsCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        String bookingIdStr = request.getParameter(Constants.Parameter.BOOKING_ID);
        Booking booking = bookingService.getBooking(bookingIdStr);
        bookingService.reduceVisits(booking);
        return new Response("/order?userId=" + userIdStr, true);
    }
}
