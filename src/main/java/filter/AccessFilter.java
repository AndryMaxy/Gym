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

/**
 * This class filters requests by specific url.
 * The filter checks if the role matches the command entered.
 *
 * @author Andrey Akulich
 * @see Filter
 */
@WebFilter (filterName = "filter4")
public class AccessFilter implements Filter {

    /**
     * Initialize this class.
     * @param filterConfig filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Checks if the role matches the command entered. If user has access then
     * next filer, else send response error 404.
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
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String command = request.getParameter(Constants.Command.COMMAND);
        if (command == null) {
            request.getRequestDispatcher(Constants.URL.ROOT).forward(request, response);
            return;
        }
        UserRole role = (UserRole) session.getAttribute(Constants.Parameter.ROLE);
        boolean access = checkFullAccess(command)
                || (role == UserRole.GUEST && checkGuestAccess(command))
                || (role != UserRole.GUEST && checkCommonAccess(command))
                || (role == UserRole.VISITOR && checkVisitorAccess(command))
                || (role == UserRole.TRAINER && checkTrainerAccess(command))
                || (role == UserRole.ADMIN && checkAdminAccess(command));
        if (access) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Finalize this class.
     */
    @Override
    public void destroy() {

    }

    /**
     * Return true if current user has full access else false
     *
     * @param command command name
     * @return true if current user has full access else false
     */
    private boolean checkFullAccess(String command){
        return command.equals(Constants.Command.FEEDBACK)
                || command.equals(Constants.Command.CHANGE_LOCALE);
    }

    /**
     * Return true if current user has guest access else false
     *
     * @param command command name
     * @return true if current user has guest access else false
     */
    private boolean checkGuestAccess(String command){
        return command.equals(Constants.Command.LOG_IN)
                || command.equals(Constants.Command.SIGN_IN);
    }

    /**
     * Return true if current user has admin, trainer or visitor
     * access else false
     *
     * @param command command name
     * @return true if current user has admin, trainer or visitor
     *          access else false
     */
    private boolean checkCommonAccess(String command) {
        return command.equals(Constants.Command.HOME)
                || command.equals(Constants.Command.LOG_OUT);
    }

    /**
     * Return true if current user has admin access else false
     *
     * @param command command name
     * @return true if current user has admin access else false
     */
    private boolean checkAdminAccess(String command) {
        return command.equals(Constants.Command.CHANGE_DISCOUNT)
                || command.equals(Constants.Command.CHANGE_ROLE)
                || command.equals(Constants.Command.LOOK_ORDER)
                || command.equals(Constants.Command.REDUCE_VISITS)
                || command.equals(Constants.Command.DELETE_USER);
    }

    /**
     * Return true if current user has trainer access else false
     *
     * @param command command name
     * @return true if current user has trainer access else false
     */
    private boolean checkTrainerAccess(String command) {
        return command.equals(Constants.Command.APPOINT)
                || command.equals(Constants.Command.DO_APPOINT);
    }

    /**
     * Return true if current user has visitor access else false
     *
     * @param command command name
     * @return true if current user has visitor access else false
     */
    private boolean checkVisitorAccess(String command) {
        return command.equals(Constants.Command.ADD_FEEDBACK)
                || command.equals(Constants.Command.REFUSE_APPOINTMENT)
                || command.equals(Constants.Command.BUY_MEMBERSHIP);
    }
}
