package com.kashuba.petproject.controller.command.impl.pagecommand;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Order;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.model.service.OrderService;
import com.kashuba.petproject.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The Orders page command.
 * <p>
 * Forwarding a user to the orders page.
 * If the user is an administrator, forwarding occurs to admin version of orders page.
 * If the user is an client, forwarding occurs to client version of orders page and
 * information about his orders is sent.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class OrdersPageCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AttributeKey.USER);
        Router router = new Router(switch (user.getRole()) {
            case CLIENT -> PageName.CLIENT_ORDERS.getPath();
            case ADMIN -> PageName.ADMIN_ORDERS.getPath();
            default -> PageName.ERROR_404.getPath();
        });

        if (user.getRole() == User.Role.CLIENT) {
            OrderService orderService = new OrderServiceImpl();
            try {
                List<Order> orderList = orderService.findClientOrders(user.getUserId());
                session.setAttribute(AttributeKey.ORDER_LIST, orderList);
                session.setAttribute(AttributeKey.ORDERS_PAGE_NUMBER, 1);
                if (orderList == null || orderList.isEmpty()) {
                    request.setAttribute(AttributeKey.ORDERS_FOUND, false);
                }
            } catch (ServiceProjectException e) {
                logger.log(Level.ERROR, "user Id " + user.getUserId(), e);
                router.setPage(PageName.ERROR_500.getPath());
            }
        }

        return router;
    }
}
