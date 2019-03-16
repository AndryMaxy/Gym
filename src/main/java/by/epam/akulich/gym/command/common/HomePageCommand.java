package by.epam.akulich.gym.command.common;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.entity.Appointment;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Membership;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.entity.UserRole;
import by.epam.akulich.gym.service.AppointmentService;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.UserService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.AppointmentServiceImpl;
import by.epam.akulich.gym.service.impl.BookingServiceImpl;
import by.epam.akulich.gym.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The class is used for opening user home page.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class HomePageCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Appointment service instance.
     */
    private AppointmentService appointmentService;

    /**
     * Instantiates a new HomePageCommand.
     *
     * @param request current http request
     */
    public HomePageCommand(HttpServletRequest request) {
        super(request);
        userService = UserServiceImpl.getInstance();
        bookingService = BookingServiceImpl.getInstance();
        appointmentService = AppointmentServiceImpl.getInstance();
    }

    /**
     * Instantiates ChangeDiscountCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     * @param bookingService booking service instance
     * @param appointmentService appointment service instance
     */
    public HomePageCommand(HttpServletRequest request, UserService userService, BookingService bookingService, AppointmentService appointmentService) {
        super(request);
        this.userService = userService;
        this.bookingService = bookingService;
        this.appointmentService = appointmentService;
    }

    /**
     * Opens home page depending on the user role.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
        User user = userService.getUser(userId);
        UserRole userRole = user.getRole();
        session.setAttribute(Constants.Parameter.ROLE, userRole);
        request.setAttribute(Constants.Parameter.USER, user);
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
        return new Response(Constants.URL.MAIN, Constants.ResponseStatus.FORWARD);
    }

    /**
     * Sets attributes in request for visitor
     * @param userId user identifier
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException if user enter invalid data
     */
    private void visitorPage(String userId) throws ServiceException, InvalidInputException {
        Booking booking = bookingService.getBookingByUserId(userId);
        if (booking != null && booking.getVisitCountLeft() != 0) {
            request.setAttribute("booking", booking);
            Appointment appointment = appointmentService.getAppointment(booking.getId());
            if (appointment != null) {
                request.setAttribute("appointment", appointment);
            }
        } else {
            List<Membership> memberships = bookingService.getMemberships();
            request.setAttribute("memberships", memberships);
        }
    }

    /**
     * Sets attributes in request for admin
     * @throws ServiceException from the userService layer
     */
    private void adminPage() throws ServiceException {
        List<User> users = userService.getAll();
        request.setAttribute("users", users);
    }

    /**
     * Sets attributes in request for trainer
     * @throws ServiceException from the userService layer
     */
    private void trainerPage() throws ServiceException {
        List<User> users = userService.getNewVisitors();
        request.setAttribute("users", users);
    }
}
