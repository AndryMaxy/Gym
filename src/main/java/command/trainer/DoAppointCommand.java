package command.trainer;

import command.Command;
import entity.Response;
import entity.Appointment;
import entity.Constants;
import entity.User;
import service.AppointmentService;
import service.UserService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import service.impl.AppointmentServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for opening appoint page.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class DoAppointCommand extends Command {

    /**
     * User service instance.
     */
    private UserService userService;

    /**
     * Appointment service instance.
     */
    private AppointmentService appointmentService;

    /**
     * Instantiates a new DoAppointCommand.
     *
     * @param request current http request
     */
    public DoAppointCommand(HttpServletRequest request) {
        super(request);
        this.userService = UserServiceImpl.getInstance();
        this.appointmentService = AppointmentServiceImpl.getInstance();
    }

    /**
     * Instantiates DoAppointCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param userService user service instance
     * @param appointmentService appointment service instance
     */
    public DoAppointCommand(HttpServletRequest request, UserService userService, AppointmentService appointmentService) {
        super(request);
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    /**
     * Opens appoint page.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        User user = userService.getNewVisitor(userIdStr);
        if (user == null) {
            return new Response(Constants.ResponseStatus.NOT_FOUND);
        }
        Appointment appointment = appointmentService.getAll();
        request.setAttribute("visitor", user);
        request.setAttribute("appointment", appointment);
        return new Response(Constants.URL.APPOINT, Constants.ResponseStatus.FORWARD);
    }
}
