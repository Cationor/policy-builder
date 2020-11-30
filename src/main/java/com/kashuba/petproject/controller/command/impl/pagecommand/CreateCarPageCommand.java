package com.kashuba.petproject.controller.command.impl.pagecommand;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.PageName;

import javax.servlet.http.HttpServletRequest;

/**
 * The Create car page command.
 * <p>
 * forwarding a user with the admin role to the create car page
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class CreateCarPageCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PageName.CREATE_CAR.getPath());
    }
}
