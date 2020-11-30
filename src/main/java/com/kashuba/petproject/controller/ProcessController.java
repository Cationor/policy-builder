package com.kashuba.petproject.controller;

import com.kashuba.petproject.controller.Router;
import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.CommandProvider;
import com.kashuba.petproject.model.pool.ConnectionPool;
import com.kashuba.petproject.util.ParameterKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.kashuba.petproject.controller.command.AttributeKey.*;

/**
 * The Process controller.
 * <p>
 * Controller for processing requests coming from the client side.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@WebServlet(urlPatterns = "/process_controller")
public class ProcessController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process request.
     * Receives a request from the client, retrieves the name of the requested command,
     * searches for this command from the list of existing ones, and redirects the
     * request to the command for processing. Based on the processing results, it
     * generates a response and redirects or forwards to the required page.
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = CommandProvider.defineCommand(request.getParameter(ParameterKey.COMMAND));
        Router router = command.execute(request);
        String page = router.getPage();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, page);

        if (router.getTransition() == Router.Transition.FORWARD) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            response.sendRedirect(page);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
