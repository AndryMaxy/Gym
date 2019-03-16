package by.epam.akulich.gym.setvlet;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.command.CommandFactory;
import by.epam.akulich.gym.command.exception.CommandException;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.command.exception.NoCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class represents servlet which handle all requests to specific urls.
 * Acts as controller in MVC pattern.
 *
 * @author Andrey Akulch
 * @see HttpServlet
 */
@WebServlet(name = "controller", urlPatterns = {
        "/controller", "/home", "/doAppoint", "/order", "/feedback"})
public class ControllerServlet extends HttpServlet {

    /**
     * SerialVersionUID is used for interoperability.
     */
    private static final long serialVersionUID = 6723792333495373893L;

    /**
     * Parameter key
     */
    private static final String COMMAND = "command";

    /**
     * A constant has an instance of {@link Logger} to logs this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(ControllerServlet.class.getSimpleName());

    /**
     * Handle get requests the send a response depending on the request.
     * @param request current http request
     * @param response my http response
     * @throws ServletException from the service layer
     * @throws IOException if servlet has input or output errors
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request,response);
    }

    /**
     * Handle post requests the send a response depending on the request.
     * @param request current http request
     * @param response my http response
     * @throws ServletException from the service layer
     * @throws IOException if servlet has input or output errors
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request,response);
    }

    /**
     * Handle get and post requests the send a response depending on the request.
     * @param request current http request
     * @param response my http response
     * @throws ServletException from the service layer
     * @throws IOException if servlet has input or output errors
     */
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
            LOGGER.error("Service side exception", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (CommandException e) {
            LOGGER.error("The exception in command", e);
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
