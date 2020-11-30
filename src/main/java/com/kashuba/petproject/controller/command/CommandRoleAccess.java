package com.kashuba.petproject.controller.command;

import java.util.EnumSet;
import java.util.Set;

import static com.kashuba.petproject.controller.command.CommandType.*;

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
    GUEST(EnumSet.of(MOVE_LOGIN_PAGE,
            MOVE_REGISTER_PAGE,
            LOG_IN_USER,
            REGISTER_CLIENT,
            MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            SWITCH_LOCALE,
            ACTIVATE_CLIENT,
            FIND_AVAILABLE_CARS,
            PAGINATION)),

    /**
     * Available commands for user role CLIENT.
     */
    CLIENT(EnumSet.of(MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            MOVE_CAR_CARD_PAGE,
            MOVE_ORDERS_PAGE,
            MOVE_PAYMENT_PAGE,
            LOG_OUT_USER,
            SWITCH_LOCALE,
            ACTIVATE_CLIENT,
            FIND_AVAILABLE_CARS,
            ORDER_CAR,
            MAKE_ORDER_PAYMENT,
            UPDATE_ORDER_STATUS,
            PAGINATION)),

    /**
     * Available commands for user role ADMIN.
     */
    ADMIN(EnumSet.of(MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            MOVE_USERS_PAGE,
            MOVE_ORDERS_PAGE,
            MOVE_CREATE_CAR_PAGE,
            LOG_OUT_USER,
            SWITCH_LOCALE,
            FILTER_CARS,
            UPDATE_CAR_PROPERTY,
            ADD_CAR,
            FILTER_ORDERS,
            FILTER_USERS,
            DECLINE_ORDER,
            UPDATE_CLIENT_STATUS,
            UPDATE_ORDER_STATUS,
            PAGINATION));

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
