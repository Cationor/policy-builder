package com.kashuba.petproject.controller.filter;

import com.kashuba.petproject.controller.command.PageName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Jsp security filter.
 * <p>
 * The filter's task is to redirect direct calls to jsp pages located in the package
 * 'jsp' of webapp to the index page.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
@WebFilter(urlPatterns = "/jsp/*")
public class JspSecurityFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        response.sendRedirect(request.getContextPath() + PageName.INDEX.getPath());
    }

    public void init(FilterConfig config) {
    }
}
