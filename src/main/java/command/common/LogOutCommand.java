package command.common;

import command.Command;
import command.Response;
import entity.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand extends Command {

    public LogOutCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Response(Constants.URL.REDIRECT, true);
    }
}
