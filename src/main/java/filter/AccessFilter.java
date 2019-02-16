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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter (filterName = "filter2", urlPatterns = "/main/*")
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        //String roleStr = (String) session.getAttribute("role");
//        UserRole role = (UserRole) session.getAttribute("role");
//        //UserRole role = UserRole.valueOf(roleStr);
//        if (role == UserRole.VISITOR) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            HttpServletResponse response = (HttpServletResponse) servletResponse;
//            response.sendRedirect(request.getContextPath());
//        }
    }

    @Override
    public void destroy() {

    }
}
