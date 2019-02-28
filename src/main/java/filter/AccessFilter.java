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

        if (checkFullAccess(command)) {
            filterChain.doFilter(request, response);
        } else if (role == UserRole.GUEST && checkGuestAccess(command)) {
            filterChain.doFilter(request, response);
        } else if (role != UserRole.GUEST && checkCommonAccess(command)) {
            filterChain.doFilter(request, response);
        } else if (role == UserRole.VISITOR && checkVisitorAccess(command)) {
            filterChain.doFilter(request, response);
        } else if (role == UserRole.TRAINER && checkTrainerAccess(command)) {
            filterChain.doFilter(request, response);
        } else if (role == UserRole.ADMIN && checkAdminAccess(command)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean checkFullAccess(String command){
        return command.equals(Constants.Command.FEEDBACK)
                || command.equals(Constants.Command.CHANGE_LOCALE)
                || command.equals(Constants.Command.ABOUT);
    }

    private boolean checkGuestAccess(String command){
        return command.equals(Constants.Command.LOG_IN)
                || command.equals(Constants.Command.SIGN_IN)
                || command.equals(Constants.Command.REGISTER);
    }

    private boolean checkCommonAccess(String command) {
        return command.equals(Constants.Command.HOME)
                || command.equals(Constants.Command.LOG_OUT);
    }

    private boolean checkAdminAccess(String command) {
        return command.equals(Constants.Command.CHANGE_DISCOUNT)
                || command.equals(Constants.Command.CHANGE_ROLE)
                || command.equals(Constants.Command.OPEN_ORDER)
                || command.equals(Constants.Command.REDUCE_VISITS)
                || command.equals(Constants.Command.DELETE_USER);
    }

    private boolean checkTrainerAccess(String command) {
        return command.equals(Constants.Command.APPOINT)
                || command.equals(Constants.Command.DO_APPOINT);
    }

    private boolean checkVisitorAccess(String command) {
        return command.equals(Constants.Command.ADD_FEEDBACK)
                || command.equals(Constants.Command.BUY_MEMBERSHIP);
    }
}
