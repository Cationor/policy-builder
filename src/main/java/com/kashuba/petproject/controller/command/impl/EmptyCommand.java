package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.PageName;

import javax.servlet.http.HttpServletRequest;

/**
 * The Empty command.
 * <p>
 * It is called if it was not possible to determine the command
 * received in the request. User is forwarding to the error404 page.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class EmptyCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PageName.ERROR_404.getPath());
    }
}
