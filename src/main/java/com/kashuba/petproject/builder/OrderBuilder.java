package com.kashuba.petproject.builder;

import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.Order;

import java.time.LocalDate;
import java.util.Map;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The type Order builder.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class OrderBuilder {
    private OrderBuilder() {
    }

    /**
     * Build order order.
     *
     * @param orderParameters the order parameters
     * @return the order
     * @author Balashevich Gleb
     */
    public static Order buildOrder(Map<String, Object> orderParameters) {
        Order order = new Order();
        order.setOrderId((long) orderParameters.get(ORDER_ID));
        order.setDateFrom((LocalDate) orderParameters.get(DATE_FROM));
        order.setDateTo((LocalDate) orderParameters.get(DATE_TO));
        order.setAmount((int) orderParameters.get(AMOUNT));
        order.setStatus((Order.Status) orderParameters.get(ORDER_STATUS));
        order.setCar(CarBuilder.buildCar(orderParameters));
        order.setClient((Client) UserBuilder.buildUser(orderParameters));

        return order;
    }
}
