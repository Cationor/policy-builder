package com.kashuba.petproject.controller;

import com.kashuba.petproject.controller.command.ActionCommand;
import com.kashuba.petproject.controller.command.CommandProvider;
import com.kashuba.petproject.model.pool.ConnectionPool;
import com.kashuba.petproject.util.ParameterKey;
import com.kashuba.petproject.controller.command.AttributeKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/process_controller")
public class ProcessController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = CommandProvider.defineCommand(request.getParameter(ParameterKey.COMMAND));
        Router router = command.execute(request);
        String page = router.getPage();
        HttpSession session = request.getSession();
        session.setAttribute(AttributeKey.CURRENT_PAGE, page);

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
