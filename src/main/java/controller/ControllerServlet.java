package controller;

import command.Command;
import command.CommandFactory;
import entity.Response;
import util.exception.IncorrectURIException;
import command.exception.NoCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exception.ServiceException;
import util.exception.EncoderException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = {
        "/controller", "/home", "/doAppoint", "/register", "/order" , "/feedback", "/about"})
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 6723792333495373893L;
    private static final String COMMAND = "command";
    private static final Logger LOGGER = LogManager.getLogger(ControllerServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request,response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        LOGGER.debug(COMMAND + ": " + commandName);
        CommandFactory factory = CommandFactory.INSTANCE;
        Command command;
        try {
            command = factory.getCommand(commandName, request);
        } catch (NoCommandException e) {
            String errorMsg = String.format("Command %s does not exist", commandName);
            LOGGER.error(errorMsg, e);
            return;
        }
        Response myResponse;
        try {
            myResponse = command.execute();
        } catch (ServiceException e) {
            LOGGER.error("Service side trouble", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (EncoderException e) {
            LOGGER.error("Can't encode data", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (IncorrectURIException e) {
            LOGGER.error("You have an error in uri syntax", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        String url = myResponse.getUrl();
        if (myResponse.isRedirect()) {
            String contextPath = getServletContext().getContextPath();
            response.sendRedirect(contextPath + url);
        } else {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}
