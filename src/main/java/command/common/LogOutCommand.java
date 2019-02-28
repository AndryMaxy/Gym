package command.common;

import command.Command;
import entity.Response;
import entity.Constants;
import entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LogOutCommand extends Command {

    public LogOutCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() {
        Locale locale = (Locale) session.getAttribute(Constants.Parameter.LOCALE);
        session.invalidate();
        HttpSession session = request.getSession();
        session.setAttribute(Constants.Parameter.LOCALE, locale);
        session.setAttribute(Constants.Parameter.ROLE, UserRole.GUEST);
        return new Response(Constants.URL.ROOT, true);
    }
}
