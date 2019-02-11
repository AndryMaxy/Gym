package controller;

import command.Command;
import command.CommandFactory;
import command.Status;
import entity.User;
import entity.Visitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Service;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    private static final String COMMAND = "command";
    private static final Logger LOGGER = LogManager.getLogger(ControllerServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //handle(req, resp);
       // int id = (int) req.getSession().getAttribute("id");
        User visitor = (User) req.getSession().getAttribute("user");
        UserService service = new UserServiceImpl();
            //User visitor = service.getById(id);
        req.setAttribute("visitor", visitor);
        req.getRequestDispatcher("/WEB-INF/jsp/visitor.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commandName = request.getParameter(COMMAND);
        CommandFactory factory = CommandFactory.INSTANCE;
        Command command = factory.getCommand(commandName);
        Status status = command.execute(request);
        LOGGER.info(status.toString());
        if (status == Status.OK) {
            response.sendRedirect(getServletContext().getContextPath() + "/controller");
        }
    }
}
