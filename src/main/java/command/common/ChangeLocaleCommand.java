package command.common;

import command.Command;
import entity.Constants;
import entity.Response;
import util.exception.IncorrectURIException;
import util.URLFormatter;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class ChangeLocaleCommand extends Command {

    private static final String REFERER = "referer";

    public ChangeLocaleCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws IncorrectURIException {
        String localeStr = request.getParameter(Constants.Parameter.LOCALE);
        Locale locale = Locale.forLanguageTag(localeStr);
        String referer = request.getHeader(REFERER);
        String url = URLFormatter.toURL(referer);
        session.setAttribute(Constants.Parameter.LOCALE, locale);
        return new Response(url, true);
    }
}
