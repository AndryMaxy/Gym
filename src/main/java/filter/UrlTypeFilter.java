package filter;

import entity.Constants;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter (filterName = "filter1")
public class UrlTypeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

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

    @Override
    public void destroy() {

    }
}
