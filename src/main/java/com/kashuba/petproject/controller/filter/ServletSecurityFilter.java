package com.kashuba.petproject.controller.filter;

import com.kashuba.petproject.controller.command.*;
import com.kashuba.petproject.controller.command.*;
import com.kashuba.petproject.controller.command.impl.EmptyCommand;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.util.ParameterKey;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * The Servlet security filter.
 * <p>
 * Filter of the user's access level to the command sent to the
 * controller based on the current role. The filter intercepts
 * the request sent to {@code ProcessController}.
 * The user's role and command name are retrieved from the {@code HttpServletRequest}.
 * If the user's role is null then the session is invalidated and the user is redirected
 * to the index page. If not then the command that the request wants to call is determined.
 * The role is retrieved from the {@code HttpSession}, and based on it, a set of commands available
 * for processing by the user with this role is obtained. The command, received from
 * the request is searched for in the received set, if such a command is found, control
 * is passed to the next filter, if not, it is redirected to the notification page.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@WebFilter(urlPatterns = "/CarBook")
public class ServletSecurityFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User.Role role = (User.Role) session.getAttribute(AttributeKey.USER_ROLE);
        String commandName = request.getParameter(ParameterKey.COMMAND);

        if (role == null) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + PageName.INDEX.getPath());
            return;
        }
        ActionCommand command = CommandProvider.defineCommand(commandName);
        if (command.getClass() != EmptyCommand.class) {
            Set<CommandType> commandTypeSet = switch (role) {
                case GUEST -> CommandRoleAccess.GUEST.getAccessCommands();
                case CLIENT -> CommandRoleAccess.CLIENT.getAccessCommands();
                case ADMIN -> CommandRoleAccess.ADMIN.getAccessCommands();
            };
            if (commandTypeSet != null && !commandTypeSet.contains(CommandType.valueOf(commandName.toUpperCase()))) {
                request.setAttribute(AttributeKey.ACCESS_DENIED, true);
                request.getRequestDispatcher(PageName.NOTIFICATION.getPath()).forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) {
    }
}
