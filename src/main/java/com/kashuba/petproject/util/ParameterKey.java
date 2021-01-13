package com.kashuba.petproject.util;

/**
 * The Parameter key.
 * <p>
 * Describes the parameter names of entity objects
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class ParameterKey {

    public static final String REGISTERED_OBJECT = "registered_object";                         //table users
    public static final String SUM_INSURED = "sum_insured";                         //table users
    public static final String CONTRACT_CURRENCY = "contract_currency";                     //table users
    public static final String INSURANCE_COVERAGE_AREA = "insurance_coverage_area";                   //table users
    public static final String TERM_OF_VALIDITY = "term_of_validity";   //table users
    public static final String INSURANCE_TYPE = "insurance_type";           //table users
    public static final String FIRST_NAME = "first_name";               //table users
    public static final String SECOND_NAME = "second_name";              //table users
    public static final String DATE_TO = "date_to";                     //table orders
    public static final String ROLE = "user_role";                      //table users
    public static final String DRIVER_LICENSE = "driver_license";       //table users
    public static final String EMAIL = "email";                         //table users
    public static final String PASSWORD = "password";                         //table users
    public static final String CLIENT_STATUS = "client_status";         //table users
    public static final String USERS = "users";                         //table users
    public static final String USER_ID = "user_id";                     //table users
    public static final String CONFIRM_PASSWORD = "confirm_password";   //table users
    public static final String PHONE_NUMBER = "phone_number";           //table users


    public static final String CARS = "cars";                           //table cars
    public static final String CAR_ID = "car_id";                       //table cars
    public static final String MODEL = "model";                         //table cars
    public static final String CAR_TYPE = "car_type";                   //table cars
    public static final String NUMBER_SEATS = "number_seats";           //table cars
    public static final String RENT_COST = "rent_cost";                 //table cars
    public static final String FUEL_TYPE = "fuel_type";                 //table cars
    public static final String FUEL_CONSUMPTION = "fuel_consumption";   //table cars
    public static final String CAR_AVAILABLE = "is_available";          //table cars
    public static final String PRICE_RANGE = "price_range";             //table cars
    public static final String PRICE_FROM = "price_from";               //table cars
    public static final String PRICE_TO = "price_to";                   //table cars

    public static final String CAR_VIEWS = "car_views";                 //table car_views
    public static final String EXTERIOR = "exterior";                   //table car_views
    public static final String EXTERIOR_SMALL = "exterior_small";       //table car_views
    public static final String INTERIOR = "interior";                   //table car_views

    public static final String ORDER_ID = "order_id";                   //table orders
    public static final String DATE_FROM = "date_from";                 //table orders
    public static final String AMOUNT = "amount";                       //table orders
    public static final String ORDER_STATUS = "order_status";           //table orders
    public static final String ORDER_CAR_ID = "order_car_id";           //table orders
    public static final String ORDER_CLIENT_ID = "order_client_id";     //table orders

    public static final String CARD_HOLDER = "card_holder";             //payment data
    public static final String CARD_NUMBER = "card_number";             //payment data
    public static final String CARD_EXPIRATION_MONTH = "card_exp_month";//payment data
    public static final String CARD_EXPIRATION_YEAR = "card_exp_year";  //payment data
    public static final String CARD_CVV_CODE = "card_cvv";              //payment data

    public static final String CAR_INDEX = "car_index";                   //car index in car list
    public static final String CLIENT_INDEX = "client_index";             //client index in client list
    public static final String ORDER_INDEX = "order_index";               //order index in order list
    public static final String IMAGE_TYPE = "image_type";                 //boot image controller

    public static final String PAGINATION_DIRECTION = "pagination_direction";       //pagination
    public static final String PAGINATION_SUBJECT = "pagination_subject";           //pagination
    public static final String NEXT_PAGE = "next_page";                             //pagination direction
    public static final String PREVIOUS_PAGE = "previous_page";                     //pagination direction

    public static final String COMMAND = "command";                                 //command

    private ParameterKey() {
    }
}
