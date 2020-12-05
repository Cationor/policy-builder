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
 * The Admin orders pagination tag.
 * <p>
 * Custom tag. Processes a list of orders for displaying them to the administrator,
 * adds a pagination view for more convenient viewing of the list.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminOrdersPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_ENTRIES = 10;
    private static final String CONTENT_PAGE = "/prop/contentpage";
    private static final String DECLINE_BUTTON_TITLE = "admin_orders.table.decline";
    private static final String CONFIRM_BUTTON_TITLE = "admin_orders.table.confirm";

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
                out.write("<td name=\"email\" onclick=\"test(this);\">" + order.getClient().getEmail() + "</td>");
                out.write("<td>" + order.getClient().getFirstName() + " " + order.getClient().getSecondName() + "</td>");
                out.write("<td name=\"car_model\" onclick=\"cartest(this);\">" + order.getCar().getModel() + "</td>");
                out.write("<td>" + order.getDateFrom() + "</td>");
                out.write("<td>" + order.getDateTo() + "</td>");
                out.write("<td>" + order.getAmount() + "</td>");
                out.write("<td>" + order.getStatus().getTitle() + "</td>");
                Order.Status status = order.getStatus();
                if (status == Order.Status.PENDING) {
                    out.write("<form action=\"CarBook\" method=\"post\" class=\"shadow-lg custom-form\">");
                    out.write("<td>");
                    out.write("<label class=\"custom-form\">");
                    out.write("<input type=\"hidden\" name=\"order_index\" value=" + i + ">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"decline_order\">");
                    out.write("<button class=\"submit-button\" type=\"submit\" id=\"declineOrder\">");
                    out.write(bundle.getString(DECLINE_BUTTON_TITLE));
                    out.write("</label>");
                    out.write("</td>");
                    out.write("</form>");
                    out.write("<form action=\"CarBook\" method=\"post\" class=\"shadow-lg custom-form\">");
                    out.write("<td>");
                    out.write("<label class=\"custom-form\">");
                    out.write("<input type=\"hidden\" name=\"order_index\" value=" + i + ">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"update_order_status\">");
                    out.write("<input type=\"hidden\" name=\"order_status\"value=" + Order.Status.AWAITING_PAYMENT.name() + ">");
                    out.write("<button class=\"submit-button\" type=\"submit\" id=\"activateOrder\">");
                    out.write(bundle.getString(CONFIRM_BUTTON_TITLE));
                    out.write("</label>");
                    out.write("</td>");
                    out.write("</form>");
                    out.write("</tr>");
                } else {
                    out.write("<td></td>");
                    out.write("<td></td>");
                }
            }
            out.write("<tr>");
            out.write("<td colspan=\"9\" align=\"center\" id=\"pagination\">");
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
