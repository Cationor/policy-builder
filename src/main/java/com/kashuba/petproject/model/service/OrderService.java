package com.kashuba.petproject.model.service;

import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * The interface Order service.
 * <p>
 * Indicates methods for processing information related to orders.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public interface OrderService {

    /**
     * Performs actions to add a new entity order to the system, also checks
     * the correctness of the received parameters, the result of adding
     * is presented by a boolean.
     *
     * @param orderParameters the order parameters
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean add(Map<String, String> orderParameters) throws ServiceProjectException;

    /**
     * Deletes an order from the system, the result of the
     * deletion is represented by a boolean variable.
     *
     * @param decliningOrder the declining order
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean declineOrder(Order decliningOrder) throws ServiceProjectException;

    /**
     * Carries out actions to manage existing orders in the system, controls
     * the timing of updating order statuses. Returned value - number
     * of changed orders.
     *
     * @return the int
     * @throws ServiceProjectException the service project exception
     */
    int manageOrders() throws ServiceProjectException;

    /**
     * Updates the status of an order existing in the system.
     *
     * @param updatingOrder the updating order
     * @param statusData    the status data
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean updateOrderStatus(Order updatingOrder, String statusData) throws ServiceProjectException;

    /**
     * Carries out an operation to pay for an existing order.
     * To make a payment, the order must have a status
     * AWAITING_PAYMENT.
     *
     * @param payableOrder      the payable order
     * @param paymentParameters the payment parameters
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean orderPayment(Order payableOrder, Map<String, String> paymentParameters) throws ServiceProjectException;

    /**
     * Based on the car order period, this method calculates the order amount.
     *
     * @param costPerDay   the cost per day
     * @param dateFromData the date from
     * @param dateToData   the date to
     * @return the int
     */
    int calculateOrderAmount(int costPerDay, String dateFromData, String dateToData);

    /**
     * Finding for orders in the system according to the specified parameters.
     * The parameters are pre-checked for correctness.
     *
     * @param orderParameters the order parameters
     * @return the list
     * @throws ServiceProjectException the service project exception
     */
    List<Order> findOrdersByParameters(Map<String, String> orderParameters) throws ServiceProjectException;

    /**
     * Finding for all orders in the system of a specific client.
     *
     * @param clientId the client id
     * @return the list
     * @throws ServiceProjectException the service project exception
     */
    List<Order> findClientOrders(long clientId) throws ServiceProjectException;
}
