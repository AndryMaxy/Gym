package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class filters all requests.
 * The filter sets charset and content type to request and response.
 *
 * @author Andrey Akulich
 * @see Filter
 */
@WebFilter(filterName = "filter2",
        initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class EncodingFilter implements Filter {

    /**
     * Charset encoding.
     */
    private String code;

    /**
     * Initialize this class.
     *
     * @param filterConfig filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {
        code = filterConfig.getInitParameter("encoding");
    }

    /**
     * Sets charset and content type to request and response.
     *
     * @param servletRequest current request
     * @param servletResponse current response
     * @param filterChain next filter
     * @throws IOException if input or output error happens
     * @throws ServletException when servlet has exception situation
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String codeRequest = servletRequest.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }
        servletResponse.setContentType("txt/html");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    /**
     * Finalize this class.
     */
    @Override
    public void destroy() {}
}
