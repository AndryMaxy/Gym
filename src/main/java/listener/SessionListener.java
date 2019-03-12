package listener;

import entity.Constants;
import entity.UserRole;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

/**
 * This listener is used for set user role to session.
 *
 * @author Andrey Akulich
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Sets user role to session.
     * @param httpSessionEvent event which invoke when http session created
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(Constants.Parameter.ROLE, UserRole.GUEST);
    }

    /**
     * Finalize session listener
     * @param httpSessionEvent event which invoke when http session created
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {}
}
