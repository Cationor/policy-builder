package com.kashuba.petproject.controller.command;

import java.util.EnumSet;
import java.util.Set;

/**
 * The enum Command role access.
 * <p>
 * This enum is described to differentiate the user's access levels to the elements
 * of the application, depending on its roles. Today the user has one of the
 * roles: GUEST, ADMIN, CLIENT ({@code User.Role}). Depending on the role,
 * the web filter {@code ServletSecurityFilter} can pass requests to execute a particular
 * command {@code CommandType} or block access.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public enum CommandRoleAccess {
    /**
     * Available commands for user role GUEST.
     */
    GUEST(EnumSet.of(CommandType.MOVE_LOGIN_PAGE,
            CommandType.MOVE_REGISTER_PAGE,
            CommandType.LOG_IN_USER,
            CommandType.REGISTER_CLIENT,
            CommandType.MOVE_HOME_PAGE,
            CommandType.MOVE_CARS_PAGE,
            CommandType.SWITCH_LOCALE,
            CommandType.ACTIVATE_CLIENT,
            CommandType.FIND_AVAILABLE_CARS,
            CommandType.PAGINATION)),

    /**
     * Available commands for user role CLIENT.
     */
    CLIENT(EnumSet.of(CommandType.MOVE_HOME_PAGE,
            CommandType.MOVE_CARS_PAGE,
            CommandType.MOVE_CAR_CARD_PAGE,
            CommandType.MOVE_ORDERS_PAGE,
            CommandType.MOVE_PAYMENT_PAGE,
            CommandType.LOG_OUT_USER,
            CommandType.SWITCH_LOCALE,
            CommandType.ACTIVATE_CLIENT,
            CommandType.FIND_AVAILABLE_CARS,
            CommandType.ORDER_CAR,
            CommandType.MAKE_ORDER_PAYMENT,
            CommandType.UPDATE_ORDER_STATUS,
            CommandType.PAGINATION)),

    /**
     * Available commands for user role ADMIN.
     */
    ADMIN(EnumSet.of(CommandType.MOVE_HOME_PAGE,
            CommandType.MOVE_CARS_PAGE,
            CommandType.MOVE_USERS_PAGE,
            CommandType.MOVE_ORDERS_PAGE,
            CommandType.MOVE_CREATE_CAR_PAGE,
            CommandType.LOG_OUT_USER,
            CommandType.SWITCH_LOCALE,
            CommandType.FILTER_CARS,
            CommandType.UPDATE_CAR_PROPERTY,
            CommandType.ADD_CAR,
            CommandType.FILTER_ORDERS,
            CommandType.FILTER_USERS,
            CommandType.DECLINE_ORDER,
            CommandType.UPDATE_CLIENT_STATUS,
            CommandType.UPDATE_ORDER_STATUS,
            CommandType.PAGINATION));

    private Set<CommandType> accessCommands;

    CommandRoleAccess(Set<CommandType> accessCommands) {
        this.accessCommands = accessCommands;
    }

    /**
     * Returns the set of available commands according to the corresponding role value
     *
     * @return the set
     */
    public Set<CommandType> getAccessCommands() {
        return this.accessCommands;
    }
}
