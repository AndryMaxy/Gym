package command;

import util.exception.IncorrectURIException;
import entity.Response;
import service.exception.ServiceException;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This abstract class designed by Command pattern.
 * @author Andrey Akulich
 */
public abstract class Command {
//TODO WHAT SHOULD BE TESTED? DOC ALL CLASSES, METHODS AND FIELDS? AND TESTS???
    /**
     * Current http request
     */
    protected HttpServletRequest request;
    protected HttpSession session;

    public Command(HttpServletRequest request) {
        this.request = request;
        session = request.getSession();
    }

    /**
     * This abstract method should be override for implementation any user command
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the service layer
     * @throws EncoderException when encode has't algorithm or get incorrect specification key
     * @throws IncorrectURIException when
     */
    public abstract Response execute() throws ServiceException, EncoderException, IncorrectURIException;
}
