package by.epam.akulich.gym.filter;

import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * This class filters requests by specific url.
 * The filter gets command name from url string and sets as request parameter.
 *
 * @author Andrey Akulich
 * @see Filter
 */
@WebFilter (filterName = "commandFilter")
public class CommandFilter implements Filter {

    /**
     * Initialize this class.
     * @param filterConfig filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Gets command name from url string and sets as request parameter.
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
        String uri = request.getRequestURI();
        String[] split = uri.split("/");
        String command;
        command = split[2];
        RequestWrapper requestWrapper = new RequestWrapper(request);
        requestWrapper.setParameter(Constants.Command.COMMAND, command);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    /**
     * Finalize this class.
     */
    @Override
    public void destroy() {

    }

    /**
     * This private class is wrapper for {@link HttpServletRequest}.
     * It is needed to add method for setting a parameter to the request.
     */
    private class RequestWrapper extends HttpServletRequestWrapper {

        /**
         * A parameter map.
         */
        private Map<String, String[]> map;

        /**
         * Constructs instance of this class
         *
         * @param request current request
         */
        RequestWrapper(HttpServletRequest request) {
            super(request);
            map = new HashMap(request.getParameterMap());
        }

        /**
         * Sets the key and value to the map.
         *
         * @param key the key for the map
         * @param value the value for the map
         */
        void setParameter(String key, String value){
            map.put(key, new String[]{value});
        }

        /**
         * Returns the value according the key.
         *
         * @param name the key for the map
         * @return the value according the key
         */
        @Override
        public String getParameter(String name) {
            return map.get(name) == null ? null : map.get(name)[0];
        }

        /**
         * Returns parameter map.
         *
         * @return parameter map
         */
        @Override
        public Map<String, String[]> getParameterMap() {
            return map;
        }

        /**
         * Return enumeration which contains parameter names.
         *
         * @return enumeration which contains parameter names
         */
        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(map.keySet());
        }

        /**
         * Returns the values according the key.
         *
         * @param name the key for the map
         * @return the values according the key
         */
        @Override
        public String[] getParameterValues(String name) {
            return map.get(name);
        }
    }
}
