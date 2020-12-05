package com.kashuba.petproject.exception;

/**
 * The Dao project exception.
 *
 * Sub class class Exceptions. It's checked, used when exceptions
 * occur in the DAO application layer
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class DaoProjectException extends Exception {
    /**
     * Instantiates a new Dao project exception.
     */
    public DaoProjectException() {
        super();
    }

    /**
     * Instantiates a new Dao project exception.
     *
     * @param message the message
     */
    public DaoProjectException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Dao project exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DaoProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Dao project exception.
     *
     * @param cause the cause
     */
    public DaoProjectException(Throwable cause) {
        super(cause);
    }
}
