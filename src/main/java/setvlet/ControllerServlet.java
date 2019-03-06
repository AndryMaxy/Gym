package setvlet;

import command.Command;
import command.CommandFactory;
import command.exception.CommandException;
import entity.Constants;
import entity.Response;
import service.exception.InvalidInputException;
import command.exception.NoCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = {
        "/controller", "/home", "/doAppoint", "/order", "/feedback"})
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
        LOGGER.trace(COMMAND + ": " + commandName);
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
        } catch (CommandException e) {
            LOGGER.error("Exception in command", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (InvalidInputException e) {
            LOGGER.error("Someone try to hack us", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String url = myResponse.getUrl();
        String contextPath = getServletContext().getContextPath();
        switch (myResponse.getStatus()) {
            case Constants.ResponseStatus.FORWARD:
                request.getRequestDispatcher(url).forward(request, response);
                break;
            case Constants.ResponseStatus.REDIRECT:
                response.sendRedirect(contextPath + url);
                break;
            case Constants.ResponseStatus.INCORRECT_INPUT:
                url += "?error=true";
                response.sendRedirect(contextPath + url);
                break;
            case Constants.ResponseStatus.NOT_FOUND:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
