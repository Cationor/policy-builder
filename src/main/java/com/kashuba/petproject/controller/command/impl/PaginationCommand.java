package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.util.ParameterKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Pagination command.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class PaginationCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String paginationDirection = request.getParameter(ParameterKey.PAGINATION_DIRECTION);
        String pageType = request.getParameter(ParameterKey.PAGINATION_SUBJECT);
        int pageNumber = (int) session.getAttribute(pageType);
        String pageName = (String) session.getAttribute(AttributeKey.CURRENT_PAGE);

        if (paginationDirection.equals(ParameterKey.NEXT_PAGE)) {
            session.setAttribute(pageType, ++pageNumber);
        }
        if (paginationDirection.equals(ParameterKey.PREVIOUS_PAGE)) {
            session.setAttribute(pageType, --pageNumber);
        }
        if (pageName.equals(PageName.CAR_CARD.getPath())) {
            pageName = PageName.CLIENT_CARS.getPath();
        }
        if (pageName.equals(PageName.CLIENT_PAYMENT.getPath())) {
            pageName = PageName.CLIENT_ORDERS.getPath();
        }
        if (pageName.equals(PageName.CREATE_CAR.getPath())) {
            pageName = PageName.ADMIN_CARS.getPath();
        }

        return new Router(pageName);
    }
}
