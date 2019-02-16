package command.forward;

import command.Command;
import command.Response;
import entity.Appointment;
import entity.Order;
import entity.User;
import entity.UserRole;
import service.OrderService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainPageCommand extends Command {

    private static final String USER_ROLE = "role";
    private UserService userService;

    public MainPageCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String login = request.getParameter("login");
        if (login == null) {
            return new Response("/WEB-INF/jsp/main.jsp", false);
        }
        userService = UserServiceImpl.getInstance();
        User user = userService.getByLogin(login);
        int userId = user.getId();
        request.setAttribute("user", user);
        HttpSession session = request.getSession();
        session.setAttribute(USER_ROLE, user.getUserRole());
        session.setAttribute("userId", user);
        switch (user.getUserRole()) {
            case VISITOR:
                visitorPage(userId);
                break;
            case ADMIN:
                adminPage();
                break;
        }
        return new Response("/WEB-INF/jsp/main.jsp", false);
    }

    private void visitorPage(int userId) throws ServiceException {
        OrderService orderService = OrderServiceImpl.getInstance();
        Order order = orderService.get(userId);
        if (order != null) {
            request.setAttribute("order", order);
            //request.setAttribute("visitCount", order.getVisitCountLeft());
            //request.setAttribute("feedback", order.getFeedback());
        }
        Appointment appointment = userService.getAppointment(userId);
        if (appointment != null) {
            request.setAttribute("appointment", appointment);
        }
    }

    private void adminPage(){
        String order = request.getParameter("order");
    }
}
