package com.kashuba.petproject.tag;

import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.model.entity.Client;
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
 * The Admin clients pagination tag.
 * <p>
 * Custom tag. Processes a list of clients for displaying them to the administrator,
 * adds a pagination view for more convenient viewing of the list.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminClientsPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_ENTRIES = 5;
    private static final String CONTENT_PAGE = "/prop/contentpage";
    private static final String BLOCK_BUTTON_TITLE = "admin_users.action.block";
    private static final String UNBLOCK_BUTTON_TITLE = "admin_users.action.unblock";

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        Locale locale = new Locale((String) session.getAttribute(AttributeKey.LOCALE));
        ResourceBundle bundle = ResourceBundle.getBundle(CONTENT_PAGE, locale);
        List<Client> clientList = ((ArrayList<Client>) session.getAttribute(AttributeKey.CLIENT_LIST));
        int pageNumber = (int) (session.getAttribute(AttributeKey.CLIENTS_PAGE_NUMBER));
        int fromIndex = pageNumber * PAGE_ENTRIES - PAGE_ENTRIES;
        int toIndex = Math.min(pageNumber * PAGE_ENTRIES, clientList.size());

        try {
            JspWriter out = pageContext.getOut();
            for (int i = fromIndex; i < toIndex; i++) {
                Client client = clientList.get(i);
                out.write("<tr>");
                out.write("<td>" + client.getEmail() + "</td>");
                out.write("<td>" + client.getFirstName() + "</td>");
                out.write("<td>" + client.getSecondName() + "</td>");
                out.write("<td>" + client.getPhoneNumber() + "</td>");
                out.write("<td>" + client.getDriverLicense() + "</td>");
                out.write("<td>" + client.getStatus().name() + "</td>");
                Client.Status status = client.getStatus();
                if (status == Client.Status.ACTIVE || status == Client.Status.BLOCKED) {
                    out.write("<form action=\"CarBook\" method=\"post\" class=\"shadow-lg custom-form\">");
                    out.write("<td>");
                    out.write("<label class=\"custom-form\">");
                    out.write("<input type=\"hidden\" name=\"client_index\" value=" + i + ">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"update_client_status\">");
                    if (status == Client.Status.ACTIVE) {
                        out.write("<input type=\"hidden\" name=\"client_status\" value=" + Client.Status.BLOCKED.name() + ">");
                        out.write("<button class=\"submit-button\" type=\"submit\" id=\"blockUser\">");
                        out.write(bundle.getString(BLOCK_BUTTON_TITLE));
                    } else {
                        out.write("<input type=\"hidden\" name=\"client_status\" value=" + Client.Status.ACTIVE.name() + ">");
                        out.write("<button class=\"submit-button\" type=\"submit\" id=\"unblockUser\">");
                        out.write(bundle.getString(UNBLOCK_BUTTON_TITLE));
                    }
                    out.write("</label>");
                    out.write("</td>");
                    out.write("</form>");
                    out.write("</tr>");
                } else {
                    out.write("<td></td>");
                }
            }
            out.write("<tr>");
            out.write("<td colspan=\"7\" align=\"center\" id=\"pagination\">");
            if (fromIndex >= PAGE_ENTRIES) {
                out.write("<a href=\"CarBook?command=pagination&" +
                        "pagination_subject=clientsPageNumber&pagination_direction=previous_page\">&lt; </a>");
            }
            out.write("<label>" + pageNumber + "</label>");
            if (toIndex < clientList.size()) {
                out.write("<a href=\"CarBook?command=pagination&" +
                        "pagination_subject=clientsPageNumber&pagination_direction=next_page\"> &gt;</a>");
            }
            out.write("</td>");
            out.write("</tr>");
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while writing data on jsp page", e);
        }

        return SKIP_BODY;
    }
}
