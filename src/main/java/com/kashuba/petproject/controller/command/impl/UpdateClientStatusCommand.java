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
import java.util.ArrayList;

/**
 * The  Update client status command.
 * <p>
 * Processes an administrator's request to change the status of a specific client.
 * The parameters of the client's installed status and its index in the list are
 * extracted from the request and sent to the service for processing.
 * After processing, the administrator is forwarding to the users page where
 * he is informed about the successful or unsuccessful status change to the client.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class UpdateClientStatusCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        HttpSession session = request.getSession();
        String clientStatusData = request.getParameter(ParameterKey.CLIENT_STATUS);
        int clientIndex = Integer.parseInt(request.getParameter(ParameterKey.CLIENT_INDEX));
        Client client = ((ArrayList<Client>) session.getAttribute(AttributeKey.CLIENT_LIST)).get(clientIndex);
        Router router;

        try {
            if (userService.updateClientStatus(client, clientStatusData)) {
                request.setAttribute(AttributeKey.CLIENT_STATUS_UPDATED, true);
            } else {
                request.setAttribute(AttributeKey.CLIENT_STATUS_UPDATED, false);
            }
            router = new Router(PageName.ADMIN_USERS.getPath());
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Client Id" + client.getUserId(), e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
