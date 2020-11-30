package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Order;
import com.kashuba.petproject.model.service.OrderService;
import com.kashuba.petproject.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The Filter orders command.
 * <p>
 * Processes a request from the administrator for the selection of orders that
 * meet the requested parameters (email, model, status). After processing the
 * request, the administrator is forwarding to the orders page (version for
 * the administrator), which displays the list of requested orders. In the
 * event that no orders were found according to these criteria, an appropriate
 * message is displayed to the administrator.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class FilterOrdersCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        OrderService orderService = new OrderServiceImpl();
        Map<String, String> orderParameters = new HashMap<>();
        orderParameters.put(MODEL, request.getParameter(MODEL));
        orderParameters.put(EMAIL, request.getParameter(EMAIL));
        orderParameters.put(ORDER_STATUS, request.getParameter(ORDER_STATUS));
        HttpSession session = request.getSession();
        Router router;

        try {
            orderService.manageOrders();
            List<Order> orderList = orderService.findOrdersByParameters(orderParameters);
            session.setAttribute(AttributeKey.ORDER_LIST, orderList);
            session.setAttribute(AttributeKey.ORDERS_PAGE_NUMBER, 1);
            if (orderList == null || orderList.isEmpty()) {
                request.setAttribute(AttributeKey.ORDERS_FOUND, false);
            }
            router = new Router(PageName.ADMIN_ORDERS.getPath());
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Number of parameters" + orderParameters.size(), e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
