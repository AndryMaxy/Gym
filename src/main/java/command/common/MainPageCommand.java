package command.common;

import command.Command;
import command.Response;
import entity.Appointment;
import entity.Booking;
import entity.Constants;
import entity.Membership;
import entity.User;
import service.AppointmentService;
import service.BookingService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.AppointmentServiceImpl;
import service.impl.BookingServiceImpl;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class MainPageCommand extends Command {

    private static final String USERS = "users";
    private UserService userService;

    public MainPageCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        userService = UserServiceImpl.getInstance();
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(Constants.Parameter.USER_ID);
        User user = userService.getUser(userId);
        request.setAttribute(Constants.Parameter.USER, user);
        session.setAttribute(Constants.Parameter.ROLE, user.getRole());
        switch (user.getRole()) {
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

    private void visitorPage(int userId) throws ServiceException {
        BookingService bookingService = BookingServiceImpl.getInstance();
        Booking booking = bookingService.getBooking(userId);
        if (booking != null) {
            request.setAttribute("booking", booking);
            AppointmentService appointmentService = AppointmentServiceImpl.getInstance();
            Appointment appointment = appointmentService.getAppointment(userId);
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
