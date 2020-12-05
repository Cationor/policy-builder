package com.kashuba.petproject.exception;

/**
 * The type Service project exception.
 *
 * Sub class class Exceptions. It's checked, used when exceptions
 * occur in the Service application layer
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class ServiceProjectException extends Exception {
    /**
     * Instantiates a new Service project exception.
     */
    public ServiceProjectException() {
        super();
    }

    /**
     * Instantiates a new Service project exception.
     *
     * @param message the message
     */
    public ServiceProjectException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service project exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Service project exception.
     *
     * @param cause the cause
     */
    public ServiceProjectException(Throwable cause) {
        super(cause);
    }
}
