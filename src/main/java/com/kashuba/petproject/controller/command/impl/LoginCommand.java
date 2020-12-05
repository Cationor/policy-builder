package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.model.service.UserService;
import com.kashuba.petproject.model.service.impl.UserServiceImpl;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The Login command.
 * <p>
 * Processes a request from a user with the guest role for authorization.
 * The entered authorization parameters are sent to the service for processing.
 * If the data is correct, the user object is retrieved from the database,
 * his role is determined and the role and data of the user are recorded in the session,
 * the already authorized user is forwarding to the home page.
 * If authorization was not successful, the user is forwarding to the login page with
 * a message about unsuccessful authorization.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class LoginCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        String userEmail = request.getParameter(ParameterKey.EMAIL);
        String userPassword = request.getParameter(ParameterKey.PASSWORD);
        HttpSession session = request.getSession();
        Router router;

        try {
            if (userService.authorizeUser(userEmail, userPassword)) {
                User authorizedUser = userService.findUserByEmail(userEmail).get();
                if (authorizedUser.getRole() == User.Role.CLIENT) {
                    Client authorizedClient = (Client) authorizedUser;
                    session.setAttribute(AttributeKey.USER, authorizedClient);
                } else {
                    session.setAttribute(AttributeKey.USER, authorizedUser);
                }
                session.setAttribute(AttributeKey.USER_ROLE, authorizedUser.getRole());
                router = new Router(PageName.HOME.getPath());
            } else {
                router = new Router(PageName.LOGIN.getPath());
                request.setAttribute(AttributeKey.SUCCESSFUL_AUTHORIZATION, false);
            }
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "User Email " + userEmail, e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
