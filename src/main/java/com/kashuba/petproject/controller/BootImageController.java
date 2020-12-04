package com.kashuba.petproject.controller;

import com.kashuba.petproject.controller.command.PageName;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Boot image controller.
 * <p>
 * Process a request to upload an image to the server
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@WebServlet(urlPatterns = "/boot_image_controller/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class BootImageController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_LOCATION = "upload.location";
    private static final String FILE_PARAMETER = "file";
    private static final String EMPTY = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process request.
     * <p>
     * Gets from the servletContext of the request object the directory for loading the image,
     * forms the path to the image on the server and loads.
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String applicationFileDirectory = request.getServletContext().getInitParameter(UPLOAD_LOCATION);
        HttpSession session = request.getSession();
        String imageType = request.getParameter(ParameterKey.IMAGE_TYPE);
        String fileName = request.getPart(FILE_PARAMETER).getSubmittedFileName();
        Path path = Paths.get(applicationFileDirectory);
        Path fullPath = Paths.get(path + File.separator + fileName);

        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
        if (Files.notExists(fullPath)) {
            List<Part> parts = request.getParts().stream().filter(part -> FILE_PARAMETER.equals(part.getName()))
                    .collect(Collectors.toList());
            parts.forEach(part -> {
                try {
                    part.write(fullPath.toString());
                    session.setAttribute(imageType, fileName);
                } catch (IOException e) {
                    logger.log(Level.ERROR, "FileName " + fileName, e);
                    session.setAttribute(imageType, EMPTY);
                }
            });
        } else {
            session.setAttribute(imageType, EMPTY);
        }
        request.getRequestDispatcher(PageName.CREATE_CAR.getPath()).forward(request, response);
    }
}
