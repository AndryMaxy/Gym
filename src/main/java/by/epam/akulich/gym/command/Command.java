package by.epam.akulich.gym.command;

import by.epam.akulich.gym.command.exception.CommandException;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.dto.Response;
import by.epam.akulich.gym.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The abstract class designed by Command pattern.
 * @author Andrey Akulich
 */
public abstract class Command {

    /**
     * Current http servlet request
     */
    protected HttpServletRequest request;

    /**
     * Last request session
     */
    protected HttpSession session;

    /**
     * Instantiates a new Command
     * @param request current http servlet request
     */
    public Command(HttpServletRequest request) {
        this.request = request;
        session = request.getSession();
    }

    /**
     * This abstract method should be override for implementation any user command
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the service layer
     * @throws InvalidInputException when input data can't to pass validation
     */
    public abstract Response execute() throws ServiceException, InvalidInputException, CommandException;
}
