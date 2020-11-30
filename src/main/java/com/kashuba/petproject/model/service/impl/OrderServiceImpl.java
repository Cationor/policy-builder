package com.kashuba.petproject.model.service.impl;

import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.dao.OrderDao;
import com.kashuba.petproject.model.dao.impl.OrderDaoImpl;
import com.kashuba.petproject.model.entity.Order;
import com.kashuba.petproject.model.service.OrderService;
import com.kashuba.petproject.util.DateConverter;
import com.kashuba.petproject.validator.CarValidator;
import com.kashuba.petproject.validator.OrderValidator;
import com.kashuba.petproject.validator.PaymentValidator;
import com.kashuba.petproject.validator.UserValidator;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The Order service.
 * <p>
 * Implements an interface OrderService for processing order-related data.
 *
 * @author Balashevich Gleb
 * @version 1.0
 * @see OrderService
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = OrderDaoImpl.getInstance();
    private static final int DURING_DAY = 1;

    @Override
    public boolean add(Map<String, String> orderParameters) throws ServiceProjectException {
        boolean isOrderAdded;
        Map<String, Object> preparedOrderParameters = new HashMap<>();
        LocalDate dateFrom = LocalDate.parse(orderParameters.get(DATE_FROM));
        LocalDate dateTo = LocalDate.parse(orderParameters.get(DATE_TO));
        preparedOrderParameters.put(DATE_FROM, DateConverter.convertToLong(dateFrom));
        preparedOrderParameters.put(DATE_TO, DateConverter.convertToLong(dateTo));
        preparedOrderParameters.put(CAR_ID, Long.parseLong(orderParameters.get(CAR_ID)));
        preparedOrderParameters.put(USER_ID, Long.parseLong(orderParameters.get(USER_ID)));
        preparedOrderParameters.put(AMOUNT, Integer.parseInt(orderParameters.get(AMOUNT)));
        preparedOrderParameters.put(ORDER_STATUS, Order.Status.PENDING);

        try {
            isOrderAdded = orderDao.add(preparedOrderParameters);
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isOrderAdded;
    }

    @Override
    public boolean declineOrder(Order decliningOrder) throws ServiceProjectException {
        boolean isOrderDeclined;

        try {
            isOrderDeclined = orderDao.remove(decliningOrder);
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isOrderDeclined;
    }

    @Override
    public int manageOrders() throws ServiceProjectException {
        ClientNotificationServiceImpl notificationService = new ClientNotificationServiceImpl();
        List<Order> inspectingOrders;
        int numberManagedOrders = 0;

        try {
            inspectingOrders = orderDao.findWaitingActionOrders();
            if (inspectingOrders != null && !inspectingOrders.isEmpty()) {
                LocalDate today = LocalDate.now();
                for (Order inspectingOrder : inspectingOrders) {
                    if (inspectingOrder.getStatus() == Order.Status.PENDING
                            || inspectingOrder.getStatus() == Order.Status.AWAITING_PAYMENT) {
                        if (today.toEpochDay() >= inspectingOrder.getDateFrom().toEpochDay()) {
                            if (orderDao.remove(inspectingOrder)) {
                                notificationService.expiredOrderNotification(inspectingOrder);
                                numberManagedOrders++;
                            }
                        }
                    }
                    if (inspectingOrder.getStatus() == Order.Status.ACTIVE) {
                        if (today.toEpochDay() > inspectingOrder.getDateTo().toEpochDay()) {
                            if (orderDao.updateOrderStatus(inspectingOrder.getOrderId(), Order.Status.COMPLETED)) {
                                notificationService.completeOrderNotification(inspectingOrder);
                                numberManagedOrders++;
                            }
                        }
                    }
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return numberManagedOrders;
    }

    @Override
    public boolean updateOrderStatus(Order updatingOrder, String statusData) throws ServiceProjectException {
        boolean isOrderStatusUpdated = false;

        try {
            if (OrderValidator.validateStatus(statusData)) {
                Order.Status status = Order.Status.valueOf(statusData);
                isOrderStatusUpdated = orderDao.updateOrderStatus(updatingOrder.getOrderId(), status);
                if (isOrderStatusUpdated) {
                    updatingOrder.setStatus(status);
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isOrderStatusUpdated;
    }

    @Override
    public boolean orderPayment(Order payableOrder, Map<String, String> paymentParameters) throws ServiceProjectException {
        boolean isPaymentComplete = false;

        try {
            if (PaymentValidator.validatePaymentParameters(paymentParameters)) {
                Order.Status status = Order.Status.ACTIVE;
                isPaymentComplete = orderDao.updateOrderStatus(payableOrder.getOrderId(), status);
                if (isPaymentComplete) {
                    payableOrder.setStatus(status);
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isPaymentComplete;
    }

    @Override
    public List<Order> findOrdersByParameters(Map<String, String> orderParameters) throws ServiceProjectException {
        List<Order> targetOrders;
        Map<String, Object> orderParametersChecked = new HashMap<>();
        String statusData = orderParameters.get(ORDER_STATUS);
        String clientEmailData = orderParameters.get(EMAIL);
        String carModelData = orderParameters.get(MODEL);

        try {
            if (OrderValidator.validateStatus(statusData)) {
                orderParametersChecked.put(ORDER_STATUS, Order.Status.valueOf(statusData.toUpperCase()));
            }
            if (UserValidator.validateEmail(clientEmailData)) {
                orderParametersChecked.put(EMAIL, clientEmailData);
            }
            if (CarValidator.validateModel(carModelData)) {
                orderParametersChecked.put(MODEL, carModelData);
            }
            if (!orderParametersChecked.isEmpty()) {
                targetOrders = orderDao.findOrdersByParameters(orderParametersChecked);
            } else {
                targetOrders = orderDao.findAll();
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetOrders;
    }

    @Override
    public List<Order> findClientOrders(long clientId) throws ServiceProjectException {
        List<Order> targetOrders;

        try {
            targetOrders = orderDao.findClientOrders(clientId);
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetOrders;
    }

    @Override
    public int calculateOrderAmount(int costPerDay, String dateFromData, String dateToData) {
        int rentCost = -1;

        if (OrderValidator.validateDate(dateFromData) && OrderValidator.validateDate(dateToData)) {
            LocalDate dateFrom = LocalDate.parse(dateFromData);
            LocalDate dateTo = LocalDate.parse(dateToData);
            Period period = Period.between(dateFrom, dateTo);
            int rentDays = period.getDays() + DURING_DAY;
            rentCost = (rentDays > 0) ? costPerDay * rentDays : -1;
        }

        return rentCost;
    }
}
