package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.service.UserService;
import com.kashuba.petproject.model.service.impl.UserServiceImpl;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The Activate client command.
 * <p>
 * Processes a request from registered users to activate an account.
 * After the activation request has been processed by the service method,
 * the user is redirected to the notifications page where he is informed
 * about the successful or not activation of the account.
 * Account activation provides the user with full use of the resource.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class ActivateClientCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        String clientEmail = request.getParameter(ParameterKey.EMAIL);
        Router router;

        try {
            if (userService.activateClient(clientEmail)) {
                request.setAttribute(AttributeKey.SUCCESSFUL_ACTIVATION, true);
            } else {
                request.setAttribute(AttributeKey.SUCCESSFUL_ACTIVATION, false);
            }
            router = new Router(PageName.NOTIFICATION.getPath());
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Client Email " + clientEmail, e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
