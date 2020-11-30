package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The Switch locale command.
 * <p>
 * Processes a request to change the site locale. After changing the locale,
 * a forwarding to the current page occurs.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class SwitchLocaleCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = request.getParameter(AttributeKey.LOCALE);
        session.setAttribute(AttributeKey.LOCALE, locale);

        return new Router((String) session.getAttribute(AttributeKey.CURRENT_PAGE));
    }
}
