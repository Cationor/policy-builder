package com.kashuba.petproject.model.dao;

import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 * <p>
 * Extends the interface of the {@code BaseDao}, supplementing it with specific
 * methods for the interaction of the application with User entities in the database.
 *
 * @version 1.0
 * @see BaseDao * @author Balashevich Gleb
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Update the user's status with a specific email address.
     *
     * @param targetEmail the target email
     * @param status      the status
     * @return the boolean
     * @throws DaoProjectException the dao project exception
     */
    boolean updateClientStatus(String targetEmail, Client.Status status) throws DaoProjectException;

    /**
     * Find a user in the database by email address
     *
     * @param targetEmail the target email
     * @return the optional
     * @throws DaoProjectException the dao project exception
     */
    Optional<User> findByEmail(String targetEmail) throws DaoProjectException;

    /**
     * Find all clients in database.
     *
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<Client> findAllClients() throws DaoProjectException;

    /**
     * Find a client status in the database by email address.
     *
     * @param targetEmail the target email
     * @return the client . status
     * @throws DaoProjectException the dao project exception
     */
    Client.Status findStatusByEmail(String targetEmail) throws DaoProjectException;

    /**
     * Find clients by specific status.
     *
     * @param clientStatus the client status
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<Client> findClientsByStatus(Client.Status clientStatus) throws DaoProjectException;

    /**
     * Find user password by email.
     *
     * @param targetEmail the target email
     * @return the string
     * @throws DaoProjectException the dao project exception
     */
    String findPasswordByEmail(String targetEmail) throws DaoProjectException;

    /**
     * Determines the presence of a specific email address in the database.
     *
     * @param targetEmail the target email
     * @return the string
     * @throws DaoProjectException the dao project exception
     */
    String existEmail(String targetEmail) throws DaoProjectException;
}
