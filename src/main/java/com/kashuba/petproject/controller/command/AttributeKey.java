package com.kashuba.petproject.controller.command;

/**
 * The Attribute key.
 * <p>
 * The class is used to store the names of the attributes placed in the {@code HttpSession}
 * or in the {@code HttpServletRequest} when processing the request.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class AttributeKey {

    public static final String USER = "user";                                           //session attribute
    public static final String LOCALE = "locale";                                       //session attribute
    public static final String USER_ROLE = "userRole";                                  //session attribute
    public static final String CURRENT_PAGE = "current_page";                           //session attribute
    public static final String CAR_LIST = "carList";                                    //session attribute
    public static final String ORDER_LIST = "orderList";                                //session attribute
    public static final String PAYABLE_ORDER = "payableOrder";                          //session attribute
    public static final String CLIENT_LIST = "clientList";                              //session attribute
    public static final String CAR_PARAMETERS = "carParameters";                        //session attribute
    public static final String CLIENTS_PAGE_NUMBER = "clientsPageNumber";               //session attribute
    public static final String ORDERS_PAGE_NUMBER = "ordersPageNumber";                 //session attribute
    public static final String CARS_PAGE_NUMBER = "carsPageNumber";                     //session attribute

    public static final String SUCCESSFUL_AUTHORIZATION = "successfulAuthorization";    //request attribute
    public static final String SUCCESSFUL_ACTIVATION = "successfulActivation";          //request attribute
    public static final String SUCCESSFUL_ORDERING = "successfulOrdering";              //request attribute
    public static final String SUCCESSFUL_REGISTRATION = "successfulRegistration";      //request attribute
    public static final String ACCESS_DENIED = "accessDenied";                          //request attribute
    public static final String ACTIVATE_ACCOUNT = "activateAccount";                    //request attribute
    public static final String ACCOUNT_BLOCKED = "accountBlocked";                      //request attribute
    public static final String PAYMENT_FAILED = "paymentFailed";                        //request attribute
    public static final String CAR_ADDED = "carAdded";                                  //request attribute
    public static final String CAR_UPDATED = "carUpdated";                              //request attribute
    public static final String CARS_FOUND = "noCars";                                   //request attribute
    public static final String CAR_FOUND = "noCar";                                     //request attribute
    public static final String NEGATIVE_AMOUNT = "negativeAmount";                      //request attribute
    public static final String ORDERS_FOUND = "noOrders";                               //request attribute
    public static final String CLIENTS_FOUND = "noClients";                             //request attribute
    public static final String ORDER_DECLINED = "orderDeclined";                        //request attribute
    public static final String ORDER_STATUS_UPDATED = "orderStatusUpdated";             //request attribute
    public static final String CLIENT_STATUS_UPDATED = "clientStatusUpdated";           //request attribute
    public static final String REGISTER_PARAMETERS = "registerParameters";              //request attribute
    public static final String USER_EXIST = "userExist";                                //request attribute
    public static final String CAR_TYPE = "carType";                                    //request attribute
    public static final String CAR = "car";                                             //request attribute
    public static final String ORDER_AMOUNT = "order_amount";                           //request attribute
    public static final String DATE_FROM = "date_from";                                 //request attribute
    public static final String DATE_TO = "date_to";                                     //request attribute

    private AttributeKey() {
    }
}
