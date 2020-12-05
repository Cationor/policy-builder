package com.kashuba.petproject.tag;

import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.model.entity.Car;
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
 * The Admin cars pagination tag.
 * <p>
 * Custom tag. Processes a list of cars for displaying them to the administrator,
 * adds a pagination view for more convenient viewing of the list.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminCarsPaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final int PAGE_ENTRIES = 5;
    private static final String CONTENT_PAGE = "/prop/contentpage";
    private static final String CAR_MODEL = "admin_cars.model";
    private static final String CAR_TYPE = "admin_cars.type";
    private static final String NUMBER_SEATS = "admin_cars.num_seats";
    private static final String FUEL_CONSUMPTION = "admin_cars.consumption";
    private static final String RENT_COST = "admin_cars.cost";
    private static final String IS_AVAILABLE = "admin_cars.is_available";
    private static final String IS_AVAILABLE_NOW = "admin_cars.avail_now";
    private static final String INVALID_PRICE = "admin_cars.invalid_price";
    private static final String AVAILABLE_TRUE = "admin_cars.avail_change_to_true";
    private static final String AVAILABLE_FALSE = "admin_cars.avail_change_to_false";
    private static final String EDIT_CAR_BUTTON_TITLE = "admin_cars.edit";

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        Locale locale = new Locale((String) session.getAttribute(AttributeKey.LOCALE));
        ResourceBundle bundle = ResourceBundle.getBundle(CONTENT_PAGE, locale);
        List<Car> carList = ((ArrayList<Car>) session.getAttribute(AttributeKey.CAR_LIST));
        int pageNumber = (int) (session.getAttribute(AttributeKey.CARS_PAGE_NUMBER));
        int fromIndex = pageNumber * PAGE_ENTRIES - PAGE_ENTRIES;
        int toIndex = Math.min(pageNumber * PAGE_ENTRIES, carList.size());

        try {
            JspWriter out = pageContext.getOut();
            for (int i = fromIndex; i < toIndex; i++) {
                Car car = carList.get(i);
                out.write("<form action=\"CarBook\" method=\"post\" class=\"shadow-lg custom-form\">");
                out.write("<tr><td>");
                out.write("<img src=\"/image/" + car.getCarView().getExteriorSmall() + "\"/>");
                out.write("</td><td>");
                out.write("<table class=\"table table-sm\" id=\"carsListTable\">");
                out.write("<tbody><tr>");
                out.write("<td>" + bundle.getString(CAR_MODEL) + "</td>");
                out.write("<td colspan=\"2\">" + car.getModel() + "</td>");
                out.write("<td>" + bundle.getString(CAR_TYPE) + "</td>");
                out.write("<td>" + car.getType().name() + "</td>");
                out.write("</tr><tr>");
                out.write("<td colspan=\"2\">" + bundle.getString(NUMBER_SEATS) + "</td>");
                out.write("<td>" + car.getNumberSeats() + "</td>");
                out.write("<td>" + bundle.getString(FUEL_CONSUMPTION) + "</td>");
                out.write("<td>" + car.getFuelConsumption() + "</td>");
                out.write("</tr><tr>");
                out.write("<td>" + bundle.getString(RENT_COST) + "</td>");
                out.write("<td>");
                out.write("<input class=\"form-control\" name=\"rent_cost\" type=\"number\" " +
                        "minlength=\"1\" maxlength=\"2\" min=\"10\" max=\"200\" " +
                        "value=\"" + car.getRentCost() + "\" oninvalid=\"this.setCustomValidity('" +
                        bundle.getString(INVALID_PRICE) + "')\" onchange=\"this.setCustomValidity('')\"/>");
                out.write("</td>");
                out.write("<td>" + bundle.getString(IS_AVAILABLE) + "</td>");
                out.write("<td colspan=\"2\">" +
                        "<div class=\"form-group\">");
                out.write("<select id=\"select\" size=\"1\" class=\"form-control\" " +
                        "name=\"is_available\">");
                out.write("<option value=\"" + car.isAvailable() + "\">");
                out.write(bundle.getString(IS_AVAILABLE_NOW) + car.isAvailable());
                out.write("</option>");
                out.write("<option value=\"true\">" + bundle.getString(AVAILABLE_TRUE) + "</option>");
                out.write("</option>");
                out.write("<option value=\"false\">" + bundle.getString(AVAILABLE_FALSE) + "</option>");
                out.write("</option>");
                out.write("</select></div></td></tr></tbody></table></td>");
                out.write("<td><label>");
                out.write("<input type=\"hidden\" name=\"car_index\" value=\"" + i + "\">");
                out.write("<input type=\"hidden\" name=\"command\" value=\"update_car_property\">");
                out.write("<button class=\"submit-button\" type=\"submit\" id=\"buttOrd\">" + bundle.getString(EDIT_CAR_BUTTON_TITLE));
                out.write("</label></td></tr></form>");
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
