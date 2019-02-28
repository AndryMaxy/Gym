package command.guest;

import command.Command;
import entity.Response;
import entity.Constants;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand extends Command {

    public RegisterCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() {
        return new Response(Constants.URL.REGISTRATION, false);
    }
}
