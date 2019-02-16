package listener;

import entity.UserRole;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        Locale locale = Locale.getDefault();
        //Locale locale = Locale.US;
        String languageTag = locale.toLanguageTag();
        //httpSessionEvent.getSession().setAttribute("locale", languageTag);
        httpSessionEvent.getSession().setAttribute("role", UserRole.GUEST);
        System.out.println(languageTag);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("ses destr");
    }
}
