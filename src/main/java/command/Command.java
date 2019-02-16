package command;

import service.exception.ServiceException;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public Command(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public abstract Response execute() throws ServiceException, EncoderException;
}
