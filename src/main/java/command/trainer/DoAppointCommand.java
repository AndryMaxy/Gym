package command.trainer;

import command.Command;
import command.Response;
import dao.AppointmentDAO;
import dao.exception.DAOException;
import dao.impl.ExerciseDAOImpl;
import entity.Appointment;
import entity.Constants;
import entity.Exercise;
import entity.Product;
import entity.User;
import service.AppointmentService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.AppointmentServiceImpl;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoAppointCommand extends Command {

    public DoAppointCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        UserService userService = UserServiceImpl.getInstance();
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        int userId = Integer.parseInt(userIdStr);
        User user = userService.getVisitor(userId);
        if (user == null) {
            return new Response(Constants.URL.MAIN, false);
        }
        AppointmentService service = AppointmentServiceImpl.getInstance();
        Appointment appointment = service.getAll();
        request.setAttribute("visitor", user);
        request.setAttribute("appointment", appointment);
        return new Response("/app", false);
    }
}
