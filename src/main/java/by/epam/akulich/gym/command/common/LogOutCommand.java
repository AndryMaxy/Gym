package by.epam.akulich.gym.command.common;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * The class is used for user log out from account.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class LogOutCommand extends Command {

    /**
     * Instantiates a new LogOutCommand.
     *
     * @param request current http request
     */
    public LogOutCommand(HttpServletRequest request) {
        super(request);
    }

    /**
     * Logs out of user account.
     *
     * @return the {@link Response} instance which contains information about next page
     */
    @Override
    public Response execute() {
        Locale locale = (Locale) session.getAttribute(Constants.Parameter.LOCALE);
        session.invalidate();
        HttpSession session = request.getSession();
        session.setAttribute(Constants.Parameter.LOCALE, locale);
        session.setAttribute(Constants.Parameter.ROLE, UserRole.GUEST);
        return new Response(Constants.URL.ROOT, Constants.ResponseStatus.REDIRECT);
    }
}
