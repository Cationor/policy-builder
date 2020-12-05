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
import java.util.HashMap;
import java.util.Map;

/**
 * The Make order payment command.
 * <p>
 * Processes the customer's request to pay for the order. The card data required for
 * making a payment is extracted from the request and sent to the service for processing.
 * In case of successful payment, the status of the client's order is updated to ACTIVE
 * and the client is forwarding to the orders page (version for the client) where he is
 * informed about the successful payment of the order. If the payment fails, the client
 * is redirected back to the payment page with a message about an unsuccessful attempt
 * to make a payment.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class MakeOrderPaymentCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        OrderService orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        Order payableOrder = (Order) session.getAttribute(AttributeKey.PAYABLE_ORDER);
        Map<String, String> paymentParameters = new HashMap<>();
        paymentParameters.put(ParameterKey.CARD_HOLDER, request.getParameter(ParameterKey.CARD_HOLDER));
        paymentParameters.put(ParameterKey.CARD_NUMBER, request.getParameter(ParameterKey.CARD_NUMBER));
        paymentParameters.put(ParameterKey.CARD_EXPIRATION_MONTH, request.getParameter(ParameterKey.CARD_EXPIRATION_MONTH));
        paymentParameters.put(ParameterKey.CARD_EXPIRATION_YEAR, request.getParameter(ParameterKey.CARD_EXPIRATION_YEAR));
        paymentParameters.put(ParameterKey.CARD_CVV_CODE, request.getParameter(ParameterKey.CARD_CVV_CODE));
        Router router;

        try {
            if (orderService.orderPayment(payableOrder, paymentParameters)) {
                session.removeAttribute(AttributeKey.PAYABLE_ORDER);
                request.setAttribute(AttributeKey.ORDER_STATUS_UPDATED, true);
                router = new Router(PageName.CLIENT_ORDERS.getPath());
            } else {
                router = new Router(PageName.CLIENT_PAYMENT.getPath());
                request.setAttribute(AttributeKey.PAYMENT_FAILED, true);
            }
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Order Id" + payableOrder, e);
            session.removeAttribute(AttributeKey.PAYABLE_ORDER);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
