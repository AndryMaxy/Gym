package command.trainer;

import command.Command;
import entity.Response;
import entity.Appointment;
import entity.Constants;
import entity.User;
import service.AppointmentService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.AppointmentServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class DoAppointCommand extends Command {

    public DoAppointCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        UserService userService = UserServiceImpl.getInstance();
        User user = userService.getVisitor(userIdStr);
        if (user == null) {
            return new Response(Constants.URL.ROOT, true);
        }
        AppointmentService service = AppointmentServiceImpl.getInstance();
        Appointment appointment = service.getAll();
        request.setAttribute("visitor", user);
        request.setAttribute("appointment", appointment);
        return new Response(Constants.URL.APPOINT, false);
    }
}
