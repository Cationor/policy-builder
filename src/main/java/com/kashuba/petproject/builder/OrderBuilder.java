package com.kashuba.petproject.builder;

import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.Order;
import com.kashuba.petproject.util.ParameterKey;

import java.time.LocalDate;
import java.util.Map;

/**
 * The type Order builder.
 *
 * @author Kiryl Kashuba
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
     * @author Kiryl Kashuba
     */
    public static Order buildOrder(Map<String, Object> orderParameters) {
        Order order = new Order();
        order.setOrderId((long) orderParameters.get(ParameterKey.ORDER_ID));
        order.setDateFrom((LocalDate) orderParameters.get(ParameterKey.DATE_FROM));
        order.setDateTo((LocalDate) orderParameters.get(ParameterKey.DATE_TO));
        order.setAmount((int) orderParameters.get(ParameterKey.AMOUNT));
        order.setStatus((Order.Status) orderParameters.get(ParameterKey.ORDER_STATUS));
        order.setCar(CarBuilder.buildCar(orderParameters));
        order.setClient((Client) UserBuilder.buildUser(orderParameters));

        return order;
    }
}
