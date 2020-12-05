package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.PageName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Logout command.
 * <p>
 * Processes a request to terminate a session with a user. The session is
 * no longer valid, the user is forwarding to the home page.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class LogoutCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return new Router(PageName.INDEX.getPath());
    }
}
