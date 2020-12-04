package com.kashuba.petproject.model.dao;

import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.model.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * The interface Order dao.
 * <p>
 * Extends the interface of the {@code BaseDao}, supplementing it with specific
 * methods for the interaction of the application with Order entities in the database.
 *
 * @author Balashevich Gleb
 * @version 1.0
 * @see BaseDao
 */
public interface OrderDao extends BaseDao<Order> {

    /**
     * Update specific order status.
     *
     * @param orderId the order id
     * @param status  the status
     * @return the boolean
     * @throws DaoProjectException the dao project exception
     */
    boolean updateOrderStatus(long orderId, Order.Status status) throws DaoProjectException;

    /**
     * Find all orders in the database waiting action
     *
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<Order> findWaitingActionOrders() throws DaoProjectException;

    /**
     * Find orders by parameters.
     *
     * @param orderParameters the order parameters
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<Order> findOrdersByParameters(Map<String, Object> orderParameters) throws DaoProjectException;

    /**
     * Find all specific client orders in database.
     *
     * @param clientId the client id
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<Order> findClientOrders(long clientId) throws DaoProjectException;
}
