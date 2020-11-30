package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.service.CarService;
import com.kashuba.petproject.model.service.impl.CarServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The Update car property command.
 * <p>
 * Processes the administrator's request to change the parameters (rentCost, available)
 * of a specific car. The parameters extracted from the request are sent to the service
 * for processing. After processing, the administrator is forwarding to the cars page
 * (version for the administrator) with a corresponding message about the successful
 * or unsuccessful change of the car parameters.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class UpdateCarPropertyCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        CarService carService = new CarServiceImpl();
        Map<String, String> carParameters = new HashMap<>();
        carParameters.put(RENT_COST, request.getParameter(RENT_COST));
        carParameters.put(CAR_AVAILABLE, request.getParameter(CAR_AVAILABLE));
        int carIndex = Integer.parseInt(request.getParameter(CAR_INDEX));
        HttpSession session = request.getSession();
        Car updatingCar = ((ArrayList<Car>) session.getAttribute(AttributeKey.CAR_LIST)).get(carIndex);
        Router router;

        try {
            if (carService.updateCar(updatingCar, carParameters)) {
                request.setAttribute(AttributeKey.CAR_UPDATED, true);
            } else {
                session.removeAttribute(AttributeKey.CAR_LIST);
                request.setAttribute(AttributeKey.CAR_UPDATED, false);
            }
            router = new Router(PageName.ADMIN_CARS.getPath());
        } catch (ServiceProjectException e) {
            session.removeAttribute(AttributeKey.CAR_LIST);
            logger.log(Level.ERROR, "Number of parameters" + carParameters.size(), e);
            router = new Router(PageName.ERROR_500.getPath());
        }

        return router;
    }
}
