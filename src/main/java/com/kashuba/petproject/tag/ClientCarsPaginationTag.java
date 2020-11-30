package com.kashuba.petproject.tag;

import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Client cars pagination tag.
 * <p>
 * Custom tag. Processes a list of cars for displaying them to the client,
 * adds a pagination view for more convenient viewing of the list.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ClientCarsPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_ENTRIES = 5;
    private static final String CONTENT_PAGE = "/prop/contentpage";
    private static final String CAR_MODEL = "cars.model";
    private static final String CAR_TYPE = "cars.type";
    private static final String FUEL_TYPE = "cars.fuel_type";
    private static final String FUEL_CONSUMPTION = "cars.fuel_consumption";
    private static final String NUMBER_SEATS = "cars.number_seats";
    private static final String RENT_COST = "cars.cost";
    private static final String ORDER_BUTTON_TITLE = "cars.order";
    private static final String LOGIN_FOR_ORDER = "client_orders.authorize";

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        Locale locale = new Locale((String) session.getAttribute(AttributeKey.LOCALE));
        ResourceBundle bundle = ResourceBundle.getBundle(CONTENT_PAGE, locale);
        List<Car> carList = ((ArrayList<Car>) session.getAttribute(AttributeKey.CAR_LIST));
        User.Role role = ((User.Role) session.getAttribute(AttributeKey.USER_ROLE));
        int pageNumber = (int) (session.getAttribute(AttributeKey.CARS_PAGE_NUMBER));
        int fromIndex = pageNumber * PAGE_ENTRIES - PAGE_ENTRIES;
        int toIndex = Math.min(pageNumber * PAGE_ENTRIES, carList.size());

        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            for (int i = fromIndex; i < toIndex; i++) {
                Car car = carList.get(i);
                out.write("<tr><td>");
                out.write("<img src=\"/image/" + car.getCarView().getExteriorSmall() + "\"/>");
                out.write("</td><td>");
                out.write("<table class=\"table table-sm\" id=\"carsListTable\">");
                out.write("<tbody><tr>");
                out.write("<td>" + bundle.getString(CAR_MODEL) + "</td>");
                out.write("<td>" + car.getModel() + "</td>");
                out.write("<td>" + bundle.getString(CAR_TYPE) + "</td>");
                out.write("<td>" + car.getType().getTitle() + "</td>");
                out.write("</tr><tr>");
                out.write("<td>" + bundle.getString(FUEL_TYPE) + "</td>");
                out.write("<td>" + car.getFuelType().getTitle() + "</td>");
                out.write("<td>" + bundle.getString(FUEL_CONSUMPTION) + "</td>");
                out.write("<td>" + car.getFuelConsumption() + "</td>");
                out.write("</tr><tr>");
                out.write("<td>" + bundle.getString(NUMBER_SEATS) + "</td>");
                out.write("<td>" + car.getNumberSeats() + "</td>");
                out.write("<th>" + bundle.getString(RENT_COST) + "</th>");
                out.write("<th>" + car.getRentCost() + "</th>");
                out.write("</tr></tbody></table></td>");
                out.write("<td>");
                if (role == User.Role.CLIENT) {
                    out.write("<div class=\"form-row form-group\">");
                    out.write("<div class=\"input-column\" >");
                    out.write("<form action=\"CarBook\" method=\"post\" style=\"font-family: Nunito\">");
                    out.write("<input type=\"hidden\" name=\"car_id\" value=\"" + car.getCarId() + "\">");
                    out.write("<input type=\"hidden\" name=\"date_from\" value=\""
                            + request.getParameter(ParameterKey.DATE_FROM) + "\">");
                    out.write("<input type=\"hidden\" name=\"date_to\" value=\""
                            + request.getParameter(ParameterKey.DATE_TO) + "\">");
                    out.write("<input type=\"hidden\" name=\"command\" value=\"move_car_card_page\">");
                    out.write("<button class=\"submit-button\" type=\"submit\" id=\"buttOrd\">" +
                            bundle.getString(ORDER_BUTTON_TITLE));
                    out.write("</form></div></div>");
                } else {
                    out.write(bundle.getString(LOGIN_FOR_ORDER));
                }
                out.write("</td></tr>");
            }
            out.write("<tr>");
            out.write("<td colspan=\"3\" align=\"center\" id=\"pagination\">");
            if (fromIndex >= PAGE_ENTRIES) {
                out.write("<a href=\"CarBook?command=pagination&" +
                        "pagination_subject=carsPageNumber&pagination_direction=previous_page\">&lt; </a>");
            }
            out.write("<label>" + pageNumber + "</label>");
            if (toIndex < carList.size()) {
                out.write("<a href=\"CarBook?command=pagination&" +
                        "pagination_subject=carsPageNumber&pagination_direction=next_page\"> &gt;</a>");
            }
            out.write("</td>");
            out.write("</tr>");
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while writing data on jsp page", e);
        }

        return SKIP_BODY;
    }
}
