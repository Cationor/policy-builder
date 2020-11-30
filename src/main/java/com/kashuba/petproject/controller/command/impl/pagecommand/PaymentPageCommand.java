package com.kashuba.petproject.controller.command.impl.pagecommand;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.model.entity.Order;
import com.kashuba.petproject.util.ParameterKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * The Payment page command.
 * <p>
 * Forwarding the client to the payment page.
 * Before forwarding receive index of paid order from session
 * find order from order list and put it into session for further processing.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class PaymentPageCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int orderIndex = Integer.parseInt(request.getParameter(ParameterKey.ORDER_INDEX));
        Order payableOrder = ((ArrayList<Order>) session.getAttribute(AttributeKey.ORDER_LIST)).get(orderIndex);
        session.setAttribute(AttributeKey.PAYABLE_ORDER, payableOrder);

        return new Router(PageName.CLIENT_PAYMENT.getPath());
    }
}
