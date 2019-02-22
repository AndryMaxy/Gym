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
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebFilter (filterName = "filter3")
public class MainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String[] split = uri.split("/");
        String command;
        if (split.length >= 3) {
            command = split[2];
            RequestWrapper requestWrapper = new RequestWrapper(request);
            requestWrapper.setParameter("command", command);
            filterChain.doFilter(requestWrapper, servletResponse);
        } else if (session.getAttribute(Constants.Parameter.ROLE) != UserRole.GUEST){
            response.sendRedirect(request.getContextPath() + Constants.URL.HOME);
        } else {
            request.getRequestDispatcher(Constants.URL.ROOT).forward(request, servletResponse);
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
