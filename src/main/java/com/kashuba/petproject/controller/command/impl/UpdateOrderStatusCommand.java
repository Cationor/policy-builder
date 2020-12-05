package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Order;
import com.kashuba.petproject.model.service.OrderService;
import com.kashuba.petproject.model.service.impl.OrderServiceImpl;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * The Update order status command.
 * <p>
 * Processes a request to change the status of a specific order. The parameters
 * of the order status to be set and its index in the list are extracted from
 * the request and sent to the service for processing. After processing, the user
 * is forwarding to the current page where he is informed about the successful or
 * unsuccessful change of the order status.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class UpdateOrderStatusCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        OrderService orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        String updatingStatusData = request.getParameter(ParameterKey.ORDER_STATUS);
        int orderIndex = Integer.parseInt(request.getParameter(ParameterKey.ORDER_INDEX));
        Order order = ((ArrayList<Order>) session.getAttribute(AttributeKey.ORDER_LIST)).get(orderIndex);
        Router router;

        try {
            if (orderService.updateOrderStatus(order, updatingStatusData)) {
                request.setAttribute(AttributeKey.ORDER_STATUS_UPDATED, true);
            } else {
                request.setAttribute(AttributeKey.ORDER_STATUS_UPDATED, false);
            }
            router = new Router((String) session.getAttribute(AttributeKey.CURRENT_PAGE));
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Order Id" + order.getOrderId(), e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
