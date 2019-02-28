package util;

import util.exception.IncorrectURIException;

import java.net.URI;
import java.net.URISyntaxException;
//TODO NOT GOOD CLASS
public class URLFormatter {

    private static final String REPLACE = "/gym";

    public static String toURL(String uriStr) throws IncorrectURIException {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException e) {
            throw new IncorrectURIException(e);
        }
        String path = uri.getPath();
        String query = uri.getQuery();
        String url = path.replace(REPLACE, "");
        if (query == null) {
            return url;
        }
        return url + "?" + query;
    }
}
