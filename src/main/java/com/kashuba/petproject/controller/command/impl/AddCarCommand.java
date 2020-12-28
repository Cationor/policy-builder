package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.service.CarService;
import com.kashuba.petproject.model.service.impl.CarServiceImpl;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * The Add car command.
 * <p>
 * Processes the received request from the administrator to add a new car to the service database.
 * Parameters are extracted from the request and sent to the service for processing.
 * If the car is successfully added to the database, the stored car parameters necessary for adding
 * a car are deleted from the session and the administrator is forwarding to the cars page
 * (version for the administrator). If the car was not added, the administrator is forwarding back
 * to the create car page.
 *
 * @author Kiryl Kashubaa
 * @version 1.0
 */
public class AddCarCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        CarService carService = new CarServiceImpl();
        HttpSession session = request.getSession();
        Map<String, String> carParameters = new HashMap<>();
        carParameters.put(ParameterKey.MODEL, request.getParameter(ParameterKey.MODEL));
        carParameters.put(ParameterKey.CAR_TYPE, request.getParameter(ParameterKey.CAR_TYPE));
        carParameters.put(ParameterKey.NUMBER_SEATS, request.getParameter(ParameterKey.NUMBER_SEATS));
        carParameters.put(ParameterKey.RENT_COST, request.getParameter(ParameterKey.RENT_COST));
        carParameters.put(ParameterKey.FUEL_TYPE, request.getParameter(ParameterKey.FUEL_TYPE));
        carParameters.put(ParameterKey.FUEL_CONSUMPTION, request.getParameter(ParameterKey.FUEL_CONSUMPTION));
        carParameters.put(ParameterKey.CAR_AVAILABLE, request.getParameter(ParameterKey.CAR_AVAILABLE));
        carParameters.put(ParameterKey.EXTERIOR_SMALL, (String) session.getAttribute(ParameterKey.EXTERIOR_SMALL));
        carParameters.put(ParameterKey.EXTERIOR, (String) session.getAttribute(ParameterKey.EXTERIOR));
        carParameters.put(ParameterKey.INTERIOR, (String) session.getAttribute(ParameterKey.INTERIOR));
        Router router;

        try {
            if (carService.addCar(carParameters)) {
                session.removeAttribute(AttributeKey.CAR_LIST);
                session.removeAttribute(ParameterKey.EXTERIOR_SMALL);
                session.removeAttribute(ParameterKey.EXTERIOR);
                session.removeAttribute(ParameterKey.INTERIOR);
                request.setAttribute(AttributeKey.CAR_ADDED, true);
                router = new Router(PageName.ADMIN_CARS.getPath());
            } else {
                request.setAttribute(AttributeKey.CAR_PARAMETERS, carParameters);
                router = new Router(PageName.CREATE_CAR.getPath());
            }
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Car model " + carParameters.get(ParameterKey.MODEL), e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
