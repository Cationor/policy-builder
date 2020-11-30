package com.kashuba.petproject.model.entity;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * The Order.
 * <p>
 * Provided for booking a car for a certain range of dates, monitoring the correctness
 * of booking cars, for financial reporting, to track the work of the service.
 * The main unit of the business logic of the application.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class Order extends Entity {
    /**
     * The enum Status.
     */
    public enum Status {
        PENDING("Pending"),
        AWAITING_PAYMENT("Awaiting Payment"),
        ACTIVE("Active"),
        COMPLETED("Completed");

        private String title;

        Status(String title) {
            this.title = title;
        }

        /**
         * Gets status title.
         *
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Gets status by index.
         *
         * @param index the index
         * @return the status
         */
        public static Status getStatus(int index) {
            return Arrays.stream(Status.values()).filter(o -> o.ordinal() == index).findFirst().get();
        }
    }

    private long orderId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int amount;
    private Status status;
    private Client client;
    private Car car;

    /**
     * Gets orderId.
     *
     * @return the order id
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Sets orderId.
     *
     * @param orderId the order id
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets dateFrom.
     *
     * @return the date from
     */
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets dateFrom.
     *
     * @param dateFrom the date from
     */
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Gets dateTo.
     *
     * @return the date to
     */
    public LocalDate getDateTo() {
        return dateTo;
    }

    /**
     * Sets dateTo.
     *
     * @param dateTo the date to
     */
    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Gets car.
     *
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets car.
     *
     * @param car the car
     */
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        com.kashuba.petproject.model.entity.Order order = (com.kashuba.petproject.model.entity.Order) o;

        return orderId == order.orderId
                && (dateFrom == order.dateFrom) || (dateFrom != null && dateFrom.equals(order.dateFrom))
                && (dateTo == order.dateTo) || (dateTo != null && dateTo.equals(order.dateTo))
                && amount == order.amount
                && status == order.status
                && (client == order.client) || (client != null && client.equals(order.client))
                && (car == order.car) || (car != null && car.equals(order.car));
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 37 * result + Long.hashCode(orderId);
        result += 37 * result + (dateFrom == null ? 0 : dateFrom.hashCode());
        result += 37 * result + (dateTo == null ? 0 : dateTo.hashCode());
        result += 37 * result + amount;
        result += 37 * result + (status == null ? 0 : status.hashCode());
        result += 37 * result + (client == null ? 0 : client.hashCode());
        result += 37 * result + (car == null ? 0 : car.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return String.format("Order %d: dateFrom %s, dateTo %s, amount %d, status %s, " +
                        "client %s, car %s",
                orderId, dateFrom, dateTo, amount, status.name(), client, car);
    }
}
