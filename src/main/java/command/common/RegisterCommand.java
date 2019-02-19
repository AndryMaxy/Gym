package command.common;

import command.Command;
import command.Response;
import entity.Constants;
import service.exception.ServiceException;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand extends Command {

    public RegisterCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        return new Response(Constants.URL.REGISTER, false);
    }
}
