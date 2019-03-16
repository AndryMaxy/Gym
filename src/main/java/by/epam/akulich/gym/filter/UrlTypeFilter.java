package by.epam.akulich.gym.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class filters all requests.
 * The filter checks url and forward if it has not jsp type otherwise do next filter.
 *
 * @author Andrey Akulich
 * @see Filter
 */
@WebFilter (filterName = "urlTypeFilter")
public class UrlTypeFilter implements Filter {

    /**
     * Initialize this class.
     *
     * @param filterConfig filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Checks url and forward if it has not jsp type otherwise do next filter.
     *
     * @param servletRequest current request
     * @param servletResponse current response
     * @param filterChain next filter
     * @throws IOException if input or output error happens
     * @throws ServletException when servlet has exception situation
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        boolean result = request.getRequestURI().contains("/resources/");
        if (!result) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            request.getRequestDispatcher(request.getServletPath()).forward(request, servletResponse);
        }
    }

    /**
     * Finalize this class.
     */
    @Override
    public void destroy() {
    }
}
