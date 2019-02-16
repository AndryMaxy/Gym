package filter;

import entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebFilter (filterName = "filter3", urlPatterns = "/controller")
public class MainCommandFilter implements Filter {

    private static final String USER_ROLE = "role";
    private static final String LOGIN = "login";
    private static final String COMMAND = "command";
    private static final String MAIN_COMMAND = "main";
    private static final String LOG_IN_COMMAND = "logIn";
    private static final String SIGN_IN_COMMAND = "signIn";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String userLogin = request.getParameter(LOGIN);
        String command = request.getParameter(COMMAND);
        if (command == null) {
            request.getRequestDispatcher("/main").forward(request, response);
            return;
        }
        //filterChain.doFilter(new RequestWrapper(request), response);
//        String url = request.getRequestURI();
//        String[] split = url.split("/");
//        String userLogin = split[3];
        UserRole role = (UserRole) session.getAttribute(USER_ROLE);
        String cashedUserLogin = (String) session.getAttribute(LOGIN);
        if (role != UserRole.GUEST) {
            if (cashedUserLogin != null && cashedUserLogin.equals(userLogin)) {
                filterChain.doFilter(request, response);
            } else {
                RequestWrapper requestWrapper = new RequestWrapper(request);
                requestWrapper.setParameter(LOGIN, cashedUserLogin);
                filterChain.doFilter(requestWrapper, response);
            }
        } else if (!command.equals(LOG_IN_COMMAND) && !command.equals(SIGN_IN_COMMAND)) {
            response.sendRedirect(request.getContextPath());
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private class RequestWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> map;

        public RequestWrapper(HttpServletRequest request) {
            super(request);
            map = new HashMap(request.getParameterMap());
        }


        public void setParameter(String key, String value){
            map.put(key, new String[]{value});
        }

        @Override
        public String getParameter(String name) {
            return map.get(name) == null ? null : map.get(name)[0];
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return map;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(map.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return map.get(name);
        }
    }
}
