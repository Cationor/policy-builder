package com.kashuba.petproject.controller.command.impl.pagecommand;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.PageName;

import javax.servlet.http.HttpServletRequest;

/**
 * The Login page command.
 * <p>
 * Forwarding a user to the login page.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class LoginPageCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PageName.LOGIN.getPath());
    }
}
