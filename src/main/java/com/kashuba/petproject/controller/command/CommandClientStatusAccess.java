package com.kashuba.petproject.controller.command;

import java.util.EnumSet;
import java.util.Set;

/**
 * The enum Command client status access.
 * <p>
 * This enum is described to differentiate the client's access levels to the elements
 * of the application, depending on its status. Today the client has one of the
 * statuses: PENDING, ACTIVE, BLOCKED ({@code Client.Status}). Depending on the status,
 * the web filter {@code ClientStatusSecurityFilter} can pass requests to execute a particular
 * command {@code CommandType} or block access.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public enum CommandClientStatusAccess {
    /**
     * Available commands for client status PENDING
     */
    PENDING(EnumSet.of(CommandType.MOVE_HOME_PAGE,
            CommandType.MOVE_CARS_PAGE,
            CommandType.LOG_OUT_USER,
            CommandType.SWITCH_LOCALE,
            CommandType.ACTIVATE_CLIENT,
            CommandType.FIND_AVAILABLE_CARS,
            CommandType.PAGINATION)),
    /**
     * Available commands for client status ACTIVE
     */
    ACTIVE(EnumSet.of(CommandType.MOVE_HOME_PAGE,
            CommandType.MOVE_CARS_PAGE,
            CommandType.MOVE_CAR_CARD_PAGE,
            CommandType.MOVE_ORDERS_PAGE,
            CommandType.MOVE_PAYMENT_PAGE,
            CommandType.LOG_OUT_USER,
            CommandType.SWITCH_LOCALE,
            CommandType.FIND_AVAILABLE_CARS,
            CommandType.ORDER_CAR,
            CommandType.MAKE_ORDER_PAYMENT,
            CommandType.UPDATE_ORDER_STATUS,
            CommandType.PAGINATION)),
    /**
     * Available commands for client status BLOCKED
     */
    BLOCKED(EnumSet.of(CommandType.MOVE_HOME_PAGE,
            CommandType.LOG_OUT_USER,
            CommandType.SWITCH_LOCALE));

    private Set<CommandType> accessCommands;

    CommandClientStatusAccess(Set<CommandType> accessCommands) {
        this.accessCommands = accessCommands;
    }

    /**
     * Returns the set of available commands according to the corresponding status value
     *
     * @return the access commands
     */
    public Set<CommandType> getAccessCommands() {
        return this.accessCommands;
    }
}
