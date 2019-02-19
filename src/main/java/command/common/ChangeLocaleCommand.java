package command.common;

import command.Command;
import command.Response;
import entity.Constants;
import service.exception.ServiceException;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLocaleCommand extends Command {

    private static final String LOCALE = "locale";

    public ChangeLocaleCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException, EncoderException {
        String localeStr = request.getParameter(LOCALE);
        HttpSession session = request.getSession();
        Locale locale = Locale.forLanguageTag(localeStr);
        session.setAttribute(LOCALE, locale);
        return new Response(Constants.URL.REDIRECT, true);
    }
}
