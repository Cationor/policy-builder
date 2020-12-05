package com.kashuba.petproject.controller.filter;

import com.kashuba.petproject.controller.command.*;
import com.kashuba.petproject.controller.command.impl.EmptyCommand;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.util.ParameterKey;
import com.kashuba.petproject.controller.command.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * The Client status security filter.
 * <p>
 * Filter of the client's access level to the command sent to the
 * controller based on the current status. The filter intercepts
 * the request sent to {@code ProcessController}.
 * The user's role and command name are retrieved from the {@code HttpServletRequest}.
 * If the user's role is defined and the user is a client, then the client object is retrieved
 * from the {@code HttpSession}. The command that the request wants to call is determined.
 * The status is retrieved from the client, and based on it, a set of commands available
 * for processing by the client with this status is obtained. The command received from
 * the request is searched for in the received set, if such a command is found, control
 * is passed to the next filter, if not, it is redirected to the notification page.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
@WebFilter(urlPatterns = "/CarBook")
public class ClientStatusSecurityFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User.Role role = (User.Role) session.getAttribute(AttributeKey.USER_ROLE);
        String commandName = request.getParameter(ParameterKey.COMMAND);
        Set<CommandType> commandTypeSet = null;

        if (role != null) {
            if (role == User.Role.CLIENT) {
                Client client = (Client) session.getAttribute(AttributeKey.USER);
                ActionCommand command = CommandProvider.defineCommand(commandName);
                if (command.getClass() != EmptyCommand.class) {
                    commandTypeSet = switch (client.getStatus()) {
                        case PENDING -> CommandClientStatusAccess.PENDING.getAccessCommands();
                        case ACTIVE -> CommandClientStatusAccess.ACTIVE.getAccessCommands();
                        case BLOCKED -> CommandClientStatusAccess.BLOCKED.getAccessCommands();
                    };
                }
                if (commandTypeSet != null && !commandTypeSet.contains(CommandType.valueOf(commandName.toUpperCase()))) {
                    if (client.getStatus() == Client.Status.PENDING) {
                        request.setAttribute(AttributeKey.ACTIVATE_ACCOUNT, true);
                    }
                    if (client.getStatus() == Client.Status.BLOCKED) {
                        request.setAttribute(AttributeKey.ACCOUNT_BLOCKED, true);
                    }
                    request.getRequestDispatcher(PageName.NOTIFICATION.getPath()).forward(request, response);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config){
    }
}
