package com.kashuba.petproject.model.service;

import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface User service.
 * <p>
 * Indicates methods for processing information related to users.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public interface UserService {

    /**
     * Based on the received data, it adds a new user to the system,
     * the received data is checked for correctness before adding,
     * the result of the addition is represented by the boolean variable.
     *
     * @param user the user
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean add(Map<String, String> user) throws ServiceProjectException;

    /**
     * Updates the client status to ACTIVE newly registered user,
     * upon confirmation of registration.
     *
     * @param email the email
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean activateClient(String email) throws ServiceProjectException;

    /**
     * Updates the status of an existing client, the result of
     * the update is represented by a boolean variable.
     *
     * @param updatingClient the updating client
     * @param statusData     the status data
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean updateClientStatus(Client updatingClient, String statusData) throws ServiceProjectException;

    /**
     * Finding for a user in the system at the specified email address
     *
     * @param email the email
     * @return the optional
     * @throws ServiceProjectException the service project exception
     */
    Optional<User> findUserByEmail(String email) throws ServiceProjectException;

    /**
     * Carries out the selection of system clients according to a certain status.
     *
     * @param clientStatusData the client status data
     * @return the list
     * @throws ServiceProjectException the service project exception
     */
    List<Client> findClientsByStatus(String clientStatusData) throws ServiceProjectException;

    /**
     * Authorizes a user in the system, the result of authorization
     * is represented by a boolean variable.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean authorizeUser(String email, String password) throws ServiceProjectException;

    /**
     * Checks if a user exists on the system with the specified email address.
     * The check result is represented by a boolean variable.
     *
     * @param email the email
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean existUser(String email) throws ServiceProjectException;
}
