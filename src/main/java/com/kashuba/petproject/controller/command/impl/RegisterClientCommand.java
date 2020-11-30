package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.service.ClientNotificationService;
import com.kashuba.petproject.model.service.UserService;
import com.kashuba.petproject.model.service.impl.ClientNotificationServiceImpl;
import com.kashuba.petproject.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The Register client command.
 * <p>
 * Processes a user's registration request. The parameters necessary for
 * registration are extracted from the request and sent to the service for processing.
 * If the data is correct and the processing has ended successfully, the user is
 * forwarding to the notification page with the appropriate message. The user is
 * also sent an email with a link to activate the account. If the data has
 * not been verified, the user is redirected back to the registration page to re-enter
 * the incorrectly entered parameters.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class RegisterClientCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        ClientNotificationService clientNotificationService;
        Map<String, String> clientParameters = new HashMap();
        clientParameters.put(EMAIL, request.getParameter(EMAIL));
        clientParameters.put(PASSWORD, request.getParameter(PASSWORD));
        clientParameters.put(CONFIRM_PASSWORD, request.getParameter(CONFIRM_PASSWORD));
        clientParameters.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        clientParameters.put(SECOND_NAME, request.getParameter(SECOND_NAME));
        clientParameters.put(DRIVER_LICENSE, request.getParameter(DRIVER_LICENSE));
        clientParameters.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        Router router;

        try {
            if (!userService.existUser(clientParameters.get(EMAIL))) {
                if (userService.add(clientParameters)) {
                    clientNotificationService = new ClientNotificationServiceImpl();
                    clientNotificationService.registerMailNotification(clientParameters.get(EMAIL),
                            clientParameters.get(FIRST_NAME), request.getRequestURL().toString());
                    request.setAttribute(AttributeKey.SUCCESSFUL_REGISTRATION, true);
                    router = new Router(PageName.NOTIFICATION.getPath());
                } else {
                    request.setAttribute(AttributeKey.REGISTER_PARAMETERS, clientParameters);
                    router = new Router(PageName.REGISTER.getPath());
                }
            } else {
                request.setAttribute(AttributeKey.USER_EXIST, true);
                router = new Router(PageName.REGISTER.getPath());
            }
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "User Email" + clientParameters.get(EMAIL), e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
