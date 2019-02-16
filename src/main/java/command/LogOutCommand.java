package command;

import entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand extends Command {

    private static final String REDIRECT = "/controller?command=main";
    private static final String USER_ROLE = "role";

    public LogOutCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() {
        HttpSession session = request.getSession();
        session.setAttribute(USER_ROLE, UserRole.GUEST);
        return new Response(REDIRECT, true);
    }
}
