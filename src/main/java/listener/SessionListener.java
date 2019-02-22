package listener;

import entity.UserRole;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute("role", UserRole.GUEST);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {}
}
