package com.kashuba.petproject.controller.command.impl.pagecommand;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.PageName;

import javax.servlet.http.HttpServletRequest;

/**
 * The Users page command.
 * <p>
 * Forwarding admin to the users page.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class UsersPageCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PageName.ADMIN_USERS.getPath());
    }
}
