package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.service.CarService;
import com.kashuba.petproject.model.service.impl.CarServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.kashuba.petproject.util.ParameterKey.*;

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
 * @author Balashevich Gleb
 * @version 1.0
 */
public class AddCarCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        CarService carService = new CarServiceImpl();
        HttpSession session = request.getSession();
        Map<String, String> carParameters = new HashMap<>();
        carParameters.put(MODEL, request.getParameter(MODEL));
        carParameters.put(CAR_TYPE, request.getParameter(CAR_TYPE));
        carParameters.put(NUMBER_SEATS, request.getParameter(NUMBER_SEATS));
        carParameters.put(RENT_COST, request.getParameter(RENT_COST));
        carParameters.put(FUEL_TYPE, request.getParameter(FUEL_TYPE));
        carParameters.put(FUEL_CONSUMPTION, request.getParameter(FUEL_CONSUMPTION));
        carParameters.put(CAR_AVAILABLE, request.getParameter(CAR_AVAILABLE));
        carParameters.put(EXTERIOR_SMALL, (String) session.getAttribute(EXTERIOR_SMALL));
        carParameters.put(EXTERIOR, (String) session.getAttribute(EXTERIOR));
        carParameters.put(INTERIOR, (String) session.getAttribute(INTERIOR));
        Router router;

        try {
            if (carService.addCar(carParameters)) {
                session.removeAttribute(AttributeKey.CAR_LIST);
                session.removeAttribute(EXTERIOR_SMALL);
                session.removeAttribute(EXTERIOR);
                session.removeAttribute(INTERIOR);
                request.setAttribute(AttributeKey.CAR_ADDED, true);
                router = new Router(PageName.ADMIN_CARS.getPath());
            } else {
                request.setAttribute(AttributeKey.CAR_PARAMETERS, carParameters);
                router = new Router(PageName.CREATE_CAR.getPath());
            }
        } catch (ServiceProjectException e) {
            logger.log(Level.ERROR, "Car model " + carParameters.get(MODEL), e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
