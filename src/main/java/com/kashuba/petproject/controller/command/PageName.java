package com.kashuba.petproject.controller.command;

/**
 * The enum Page name.
 * <p>
 * An enumeration that describes all pages used by the application to
 * provide forwarding as a result of request processing
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public enum PageName {
    INDEX("/index.jsp"),                                                //general page
    LOGIN("/jsp/login.jsp"),                                            //general page
    REGISTER("/jsp/register.jsp"),                                      //general page
    HOME("/jsp/home.jsp"),                                              //general page
    NOTIFICATION("/jsp/notification.jsp"),                              //general page
    CLIENT_CARS("/jsp/cars.jsp"),                                       //client page
    CLIENT_PAYMENT("/jsp/payment.jsp"),                                 //client page
    CLIENT_ORDERS("/jsp/orders.jsp"),                                   //client page
    CAR_CARD("/jsp/car_card.jsp"),                                      //client page
    ADMIN_USERS("/jsp/admin/users.jsp"),                                //admin page
    ADMIN_ORDERS("/jsp/admin/orders.jsp"),                              //admin page
    ADMIN_CARS("/jsp/admin/cars.jsp"),                                  //admin page
    CREATE_CAR("/jsp/admin/create_car.jsp"),                            //admin page
    ERROR_404("/jsp/error/error404.jsp"),                               //error page
    ERROR_500("/jsp/error/error500.jsp");                               //error page

    private String path;

    PageName(String path) {
        this.path = path;
    }

    /**
     * Returns the path of the page
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }
}
