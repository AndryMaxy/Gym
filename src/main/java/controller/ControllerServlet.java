package controller;

import command.Command;
import command.CommandFactory;
import command.Response;
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

@WebServlet(name = "controller", urlPatterns = {"/controller", "/main/*"})
public class ControllerServlet extends HttpServlet {

    private static final String COMMAND = "command";
    private static final Logger LOGGER = LogManager.getLogger(ControllerServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request, response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        LOGGER.info("command: " + commandName);
        CommandFactory factory = CommandFactory.INSTANCE;
        Command command = factory.getCommand(commandName, request,  response);
        Response resp;
        try {
            resp = command.execute();
        } catch (ServiceException e) {
            LOGGER.error("not good", e);
            //TODO ERROR MESSAGE
            return;
        } catch (EncoderException e) {
            LOGGER.error("cant encode", e);
            //TODO ERROR MESSAGE
            return;
        }
        LOGGER.info("message: " + resp.isGoodMessage());
        String uri = resp.getUrl();
        if (resp.isRedirect()) {
            redirect(response, uri);
        } else {
            request.setAttribute("message", resp.isGoodMessage());
            forward(request, response, uri);
        }
    }

    private void redirect(HttpServletResponse response, String uri) throws IOException {
        String contextPath = getServletContext().getContextPath();
        System.out.println("redirect: " + contextPath + uri);
        response.sendRedirect(contextPath + uri);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String uri) throws ServletException, IOException {
        System.out.println("forward: " + uri);
        request.getRequestDispatcher(uri).forward(request,response);
    }
}
