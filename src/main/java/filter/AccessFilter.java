package filter;

import entity.Constants;
import entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter (filterName = "filter4")
public class AccessFilter implements Filter {

    //TODO CANT LOAD IMG.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String command = request.getParameter(Constants.Command.COMMAND);
        if (command == null) {
            request.getRequestDispatcher(Constants.URL.ROOT).forward(request, response);
            return;
        }
        UserRole role = (UserRole) session.getAttribute(Constants.Parameter.ROLE);
        //TODO MB SEPARATE COMMANDS BY ROLE ? AND DO INSTANCE OF???
        boolean result = command.equals(Constants.Command.LOG_IN)
                || command.equals(Constants.Command.SIGN_IN)
                || command.equals(Constants.Command.CHANGE_LOCALE)
                || command.equals(Constants.Command.REGISTER)
                || command.equals(Constants.Command.FEEDBACK);
        if (role == UserRole.GUEST && result) {
            filterChain.doFilter(request, response);
        } else if (role != UserRole.GUEST) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    public void destroy() {

    }
}
