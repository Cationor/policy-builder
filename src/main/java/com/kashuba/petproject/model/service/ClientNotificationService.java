package com.kashuba.petproject.model.service;

import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Order;

/**
 * The interface Client notification service.
 * <p>
 * Indicates methods for sending notifications to users.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public interface ClientNotificationService {

    /**
     * Create a notification about user registration and sends it
     * to the email address specified in the registration data.
     *
     * @param clientEmail     the client email
     * @param clientFirstName the client first name
     * @param link            the link
     * @throws ServiceProjectException the service project exception
     */
    void registerMailNotification(String clientEmail, String clientFirstName, String link) throws ServiceProjectException;

    /**
     * Creates a notification that the client has successfully created
     * a new order in the system.
     *
     * @param clientEmail the client email
     * @throws ServiceProjectException the service project exception
     */
    void createOrderNotification(String clientEmail) throws ServiceProjectException;

    /**
     * Creates a declining notification for a client order previously
     * created by him.
     *
     * @param decliningOrder the declining order
     * @throws ServiceProjectException the service project exception
     */
    void declineOrderNotification(Order decliningOrder) throws ServiceProjectException;

    /**
     * Creates a notification about a expiration in order confirmation
     * by the administrator or payment of a confirmed order.
     *
     * @param expiredOrder the expired order
     * @throws ServiceProjectException the service project exception
     */
    void expiredOrderNotification(Order expiredOrder) throws ServiceProjectException;

    /**
     * Creates a notification about the successful completion of a
     * client's car rental order.
     *
     * @param completedOrder the completed order
     * @throws ServiceProjectException the service project exception
     */
    void completeOrderNotification(Order completedOrder) throws ServiceProjectException;
}
