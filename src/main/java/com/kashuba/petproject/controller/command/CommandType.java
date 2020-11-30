package com.kashuba.petproject.controller.command;

import com.kashuba.petproject.controller.command.impl.*;
import com.kashuba.petproject.controller.command.impl.pagecommand.*;

/**
 * The enum Command type.
 * <p>
 * Describes all commands available for processing by the application.
 * Used by the {@code CommandProvider}
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public enum CommandType {
    MOVE_LOGIN_PAGE(new LoginPageCommand()),
    MOVE_REGISTER_PAGE(new RegisterPageCommand()),
    MOVE_HOME_PAGE(new HomePageCommand()),
    MOVE_CARS_PAGE(new CarsPageCommand()),
    MOVE_CAR_CARD_PAGE(new CarCardPageCommand()),
    MOVE_USERS_PAGE(new UsersPageCommand()),
    MOVE_ORDERS_PAGE(new OrdersPageCommand()),
    MOVE_CREATE_CAR_PAGE(new CreateCarPageCommand()),
    MOVE_PAYMENT_PAGE(new PaymentPageCommand()),
    LOG_IN_USER(new LoginCommand()),
    LOG_OUT_USER(new LogoutCommand()),
    REGISTER_CLIENT(new RegisterClientCommand()),
    SWITCH_LOCALE(new SwitchLocaleCommand()),
    ACTIVATE_CLIENT(new ActivateClientCommand()),
    FIND_AVAILABLE_CARS(new FindAvailableCarsCommand()),
    FILTER_CARS(new FilterCarsCommand()),
    ORDER_CAR(new OrderCarCommand()),
    UPDATE_CAR_PROPERTY(new UpdateCarPropertyCommand()),
    ADD_CAR(new AddCarCommand()),
    FILTER_ORDERS(new FilterOrdersCommand()),
    FILTER_USERS(new FilterUsersCommand()),
    DECLINE_ORDER(new DeclineOrderCommand()),
    MAKE_ORDER_PAYMENT(new MakeOrderPaymentCommand()),
    UPDATE_CLIENT_STATUS(new UpdateClientStatusCommand()),
    UPDATE_ORDER_STATUS(new UpdateOrderStatusCommand()),
    PAGINATION(new PaginationCommand()),
    EMPTY(new EmptyCommand());

    private com.kashuba.petproject.controller.command.ActionCommand command;

    CommandType(com.kashuba.petproject.controller.command.ActionCommand command) {
        this.command = command;
    }

    /**
     * Returns the {@code ActionCommand} object corresponding to the enum object
     *
     * @return the command
     */
    public com.kashuba.petproject.controller.command.ActionCommand getCommand() {
        return command;
    }
}
