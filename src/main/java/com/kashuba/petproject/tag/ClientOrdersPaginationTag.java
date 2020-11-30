package com.kashuba.petproject.tag;

import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.model.entity.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The type Client orders pagination tag.
 * <p>
 * Custom tag. Processes a list of orders for displaying them to the client,
 * adds a pagination view for more convenient viewing of the list.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ClientOrdersPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_ENTRIES = 5;
    private static final String CONTENT_PAGE = "/prop/contentpage";
    private static final String PAY_BUTTON_TITLE = "client_orders.table.pay";

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        Locale locale = new Locale((String) session.getAttribute(AttributeKey.LOCALE));
        ResourceBundle bundle = ResourceBundle.getBundle(CONTENT_PAGE, locale);
        List<Order> orderList = ((ArrayList<Order>) session.getAttribute(AttributeKey.ORDER_LIST));
        int pageNumber = (int) (session.getAttribute(AttributeKey.ORDERS_PAGE_NUMBER));
        int fromIndex = pageNumber * PAGE_ENTRIES - PAGE_ENTRIES;
        int toIndex = Math.min(pageNumber * PAGE_ENTRIES, orderList.size());

        try {
            JspWriter out = pageContext.getOut();
            for (int i = fromIndex; i < toIndex; i++) {
                Order order = orderList.get(i);
                out.write("<tr>");
                out.write("<td>" + order.getCar().getModel() + "</td>");
                out.write("<td>" + order.getDateFrom() + "</td>");
                out.write("<td>" + order.getDateTo() + "</td>");
                out.write("<td>" + order.getAmount() + "</td>");
                out.write("<td>" + order.getStatus().getTitle() + "</td>");
                if (order.getStatus() == Order.Status.AWAITING_PAYMENT) {
                    out.write("<form action=\"CarBook\" method=\"post\" class=\"shadow-lg custom-form\">");
                    out.write("<th>");
                    out.write("<label class=\"custom-form\" id=\"payBut\">");
                    out.write("<input type=\"hidden\" name=\"order_index\" value=" + i + ">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"move_payment_page\">");
                    out.write("<button class=\"submit-button\" type=\"submit\" id=\"paymentOrder\">");
                    out.write(bundle.getString(PAY_BUTTON_TITLE));
                    out.write("</label>");
                    out.write("</th>");
                    out.write("</form>");
                } else {
                    out.write("<th></th>");
                }
            }
            out.write("<tr>");
            out.write("<td colspan=\"6\" align=\"center\" id=\"pagination\">");
            if (fromIndex >= PAGE_ENTRIES) {
                out.write("<a href=\"CarBook?command=pagination&" +
                        "pagination_subject=ordersPageNumber&pagination_direction=previous_page\">&lt; </a>");
            }
            out.write("<label>" + pageNumber + "</label>");
            if (toIndex < orderList.size()) {
                out.write("<a href=\"CarBook?command=pagination&" +
                        "pagination_subject=ordersPageNumber&pagination_direction=next_page\"> &gt;</a>");
            }
            out.write("</td>");
            out.write("</tr>");
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while writing data on jsp page", e);
        }

        return SKIP_BODY;
    }
}
