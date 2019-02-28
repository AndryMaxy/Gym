package command.common;

import command.Command;
import entity.Response;
import entity.Appointment;
import entity.Booking;
import entity.Constants;
import entity.Membership;
import entity.User;
import entity.UserRole;
import service.AppointmentService;
import service.BookingService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.AppointmentServiceImpl;
import service.impl.BookingServiceImpl;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class HomePageCommand extends Command {

    private static final String USERS = "users";
    private UserService userService = UserServiceImpl.getInstance();

    public HomePageCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
        User user = userService.getUser(userId);
        UserRole userRole = user.getRole();
        session.setAttribute(Constants.Parameter.ROLE, userRole);
        request.setAttribute("user", user);
        switch (userRole) {
            case VISITOR:
                visitorPage(userId);
                break;
            case ADMIN:
                adminPage();
                break;
            case TRAINER:
                trainerPage();
        }
        return new Response(Constants.URL.MAIN, false);
    }

    private void visitorPage(String userId) throws ServiceException {
        BookingService bookingService = BookingServiceImpl.getInstance();
        Booking booking = bookingService.getBookingByUserId(userId);
        if (booking != null && booking.getVisitCountLeft() != 0) {
            request.setAttribute("booking", booking);
            AppointmentService appointmentService = AppointmentServiceImpl.getInstance();
            Appointment appointment = appointmentService.getAppointment(booking.getId());
            if (appointment != null) {
                request.setAttribute("appointment", appointment);
            }
        } else {
            List<Membership> memberships = new ArrayList<>();
            memberships.add(Membership.ULTRA);
            memberships.add(Membership.SUPER);
            memberships.add(Membership.STANDARD);
            memberships.add(Membership.EASY);
            memberships.add(Membership.ONE);
            request.setAttribute("memberships", memberships);
        }
    }

    private void adminPage() throws ServiceException {
        List<User> users = userService.getAll();
        request.setAttribute(USERS, users);
    }

    private void trainerPage() throws ServiceException {
        List<User> users = userService.getVisitors();
        request.setAttribute(USERS, users);
    }
}
