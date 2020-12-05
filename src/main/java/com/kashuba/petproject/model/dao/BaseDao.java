package com.kashuba.petproject.model.dao;

import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.model.entity.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Base dao.
 * <p>
 * The base interface denoting the main methods of interaction between the
 * application and the database (CRUD). Has default implementations of methods
 * for closing a connection, statement, change autocommit and rolling back a transaction.
 *
 * @param <T> the type parameter
 * @author Kiryl Kashuba
 * @version 1.0
 */
public interface BaseDao<T extends Entity> {
    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger();

    /**
     * Adding a new entity to the database based on the parameters passed to the method.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoProjectException the dao project exception
     */
    boolean add(Map<String, Object> parameters) throws DaoProjectException;

    /**
     * Removes an entity from the database
     *
     * @param t the t
     * @return the boolean
     * @throws DaoProjectException the dao project exception
     */
    boolean remove(T t) throws DaoProjectException;

    /**
     * Updating an entity in the database
     *
     * @param t the t
     * @return the boolean
     * @throws DaoProjectException the dao project exception
     */
    boolean update(T t) throws DaoProjectException;

    /**
     * Find entity by Id in database.
     *
     * @param id the id
     * @return the optional
     * @throws DaoProjectException the dao project exception
     */
    Optional<T> findById(long id) throws DaoProjectException;

    /**
     * Find all entities in database.
     *
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<T> findAll() throws DaoProjectException;

    /**
     * Changing the autocommit value of the Connection object
     *
     * @param connection the connection
     */
    default void autocommit(Connection connection, boolean type) {
        if (connection != null) {
            try {
                connection.setAutoCommit(type);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error occurred while changing autocommit to " + type, e);
            }
        }
    }

    /**
     * Rollback a connection transaction
     *
     * @param connection the connection
     */
    default void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error while rollback committing car data", e);
            }
        }
    }

    /**
     * Close statement.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error occurred while closing the statement", e);
            }
        }
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    default void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error occurred while closing the connection", e);
            }
        }
    }
}
