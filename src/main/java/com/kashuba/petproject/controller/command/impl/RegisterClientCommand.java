package com.kashuba.petproject.controller.command.impl;

import com.kashuba.petproject.builder.PolicyBuilder;
import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.AttributeKey;
import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Policy;
import com.kashuba.petproject.model.service.ClientNotificationService;
import com.kashuba.petproject.model.service.UserService;
import com.kashuba.petproject.model.service.impl.ClientNotificationServiceImpl;
import com.kashuba.petproject.model.service.impl.UserServiceImpl;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The Register client command.
 * <p>
 * Processes a user's registration request. The parameters necessary for
 * registration are extracted from the request and sent to the service for processing.
 * If the data is correct and the processing has ended successfully, the user is
 * forwarding to the notification page with the appropriate message. The user is
 * also sent an email with a link to activate the account. If the data has
 * not been verified, the user is redirected back to the registration page to re-enter
 * the incorrectly entered parameters.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class RegisterClientCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Map<String, String> clientParameters = new HashMap<>();
        clientParameters.put(ParameterKey.REGISTERED_OBJECT, request.getParameter(ParameterKey.REGISTERED_OBJECT));
        clientParameters.put(ParameterKey.SUM_INSURED, request.getParameter(ParameterKey.SUM_INSURED));
        clientParameters.put(ParameterKey.CONTRACT_CURRENCY, request.getParameter(ParameterKey.CONTRACT_CURRENCY));
        clientParameters.put(ParameterKey.FIRST_NAME, request.getParameter(ParameterKey.FIRST_NAME));
        clientParameters.put(ParameterKey.SECOND_NAME, request.getParameter(ParameterKey.SECOND_NAME));
        clientParameters.put(ParameterKey.INSURANCE_COVERAGE_AREA, request.getParameter(ParameterKey.INSURANCE_COVERAGE_AREA));
        clientParameters.put(ParameterKey.TERM_OF_VALIDITY, request.getParameter(ParameterKey.TERM_OF_VALIDITY));
        clientParameters.put(ParameterKey.INSURANCE_TYPE, request.getParameter(ParameterKey.INSURANCE_TYPE));
        Router router;



//                    PolicyBuilder.buildPolicy(clientParameters);
//                    request.setAttribute("policyList", buildingPolicy);
                    Policy policy = PolicyBuilder.buildPolicy(clientParameters);
                    request.setAttribute("policyList", policy);
                    router = new Router(PageName.LOGIN.getPath()); // Тут вписать путь на JSP с полисом


        return router;
    }
}
