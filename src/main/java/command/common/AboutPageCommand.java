package command.common;

import command.Command;
import entity.Response;
import entity.Constants;

import javax.servlet.http.HttpServletRequest;

public class AboutPageCommand extends Command {

    public AboutPageCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() {
        return new Response(Constants.URL.ABOUT_JSP, false);
    }
}
