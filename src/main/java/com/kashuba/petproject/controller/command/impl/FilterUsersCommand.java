package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.service.UserService;
import com.kashuba.petproject.model.service.impl.UserServiceImpl;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The Filter users command.
 * <p>
 * Processes a request from the administrator for the selection of users that
 * meet the requested parameter (status). After processing the request, the administrator
 * is forwarding to the users page, which displays the list of requested users.
 * In the event that no users were found according to these criteria,
 * an appropriate message is displayed to the administrator.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class FilterUsersCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        String clientStatusData = request.getParameter(ParameterKey.CLIENT_STATUS);
        HttpSession session = request.getSession();
        Router router;

        try {
            List<Client> clientsList = userService.findClientsByStatus(clientStatusData);
            session.setAttribute(AttributeKey.CLIENT_LIST, clientsList);
            session.setAttribute(AttributeKey.CLIENTS_PAGE_NUMBER, 1);
            if (clientsList == null || clientsList.isEmpty()) {
                request.setAttribute(AttributeKey.CLIENTS_FOUND, false);
            }
            router = new Router(PageName.ADMIN_USERS.getPath());
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Client status " + clientStatusData, e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
