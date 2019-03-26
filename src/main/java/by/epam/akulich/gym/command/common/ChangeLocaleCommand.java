package by.epam.akulich.gym.command.common;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.command.exception.CommandException;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.dto.Response;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

/**
 * The class is used for changing user locale.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class ChangeLocaleCommand extends Command {

    /**
     * The key for giving referrer url.
     */
    private static final String REFERER = "referer";

    /**
     * Instantiates a new ChangeDiscountCommand.
     *
     * @param request current http request
     */
    public ChangeLocaleCommand(HttpServletRequest request) {
        super(request);
    }

    /**
     * Changes user locale.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws CommandException when {@link URISyntaxException} will be thrown
     */
    @Override
    public Response execute() throws CommandException {
        String localeStr = request.getParameter(Constants.Parameter.LOCALE);
        Locale locale = Locale.forLanguageTag(localeStr);
        String referer = request.getHeader(REFERER);
        String url;
        try {
            url = getURL(referer);
        } catch (URISyntaxException e) {
            throw new CommandException(e);
        }
        session.setAttribute(Constants.Parameter.LOCALE, locale);
        return new Response(url, Constants.ResponseStatus.REDIRECT);
    }

    /**
     * Returns previous url.
     *
     * @param uriStr string as uri
     * @return previous url
     * @throws URISyntaxException if uri string has syntax mistake
     */
    private String getURL(String uriStr) throws URISyntaxException {
        URI uri = new URI(uriStr);
        String path = uri.getPath();
        String query = uri.getQuery();
        String url = path.replace("/gym", "");
        if (query == null) {
            return url;
        }
        return url + "?" + query;
    }


}
