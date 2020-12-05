package com.kashuba.petproject.model.dao.impl;

import com.kashuba.petproject.builder.OrderBuilder;
import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.model.dao.OrderDao;
import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.Order;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.model.pool.ConnectionPool;
import com.kashuba.petproject.util.DateConverter;
import com.kashuba.petproject.util.ParameterKey;

import java.sql.*;
import java.util.*;

/**
 * The Order dao.
 *
 * {@code OrderDao} interface implementation
 *
 * @author Kiryl Kashuba
 * @version 1.0
 * @see OrderDao
 */
public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl orderDao;
    private static final String ORDER_BY_DATE = " ORDER BY date_from DESC";
    private static final String ADD_ORDER = "INSERT INTO orders(date_from, date_to, amount, order_status, order_car_id, " +
            "order_client_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String REMOVE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String UPDATE_STATUS = "UPDATE orders SET order_status = (?) WHERE order_id = ?";
    private static final String FIND_ALL = "SELECT order_id, date_from, date_to, amount, order_status," +
            "order_car_id, order_client_id, cars.model, cars.car_type, cars.number_seats, cars.rent_cost," +
            "cars.fuel_type, cars.fuel_consumption, cars.is_available, car_views.exterior_small, car_views.exterior," +
            "car_views.interior, users.email, users.password, users.user_role, users.first_name, users.second_name," +
            "users.driver_license, users.phone_number, users.client_status FROM orders JOIN cars ON order_car_id = cars.car_id " +
            "JOIN car_views ON order_car_id = car_views.cars_id JOIN users ON order_client_id = users.user_id";
    private static final String FIND_AWAITING_ACTION = FIND_ALL + " WHERE order_status != 2";
    private static final String FIND_CLIENT_ORDERS = FIND_ALL + " WHERE order_client_id = ? " + ORDER_BY_DATE;
    private static final String CHECK_STATUS = " order_status = ?";
    private static final String CHECK_CLIENT_EMAIL = " users.email = ?";
    private static final String CHECK_CAR_MODEL = " cars.model = ?";
    private static final String WHERE_KEYWORD = " WHERE";
    private static final String AND_KEYWORD = " AND";
    private static final String DOT = ".";

    private OrderDaoImpl() {
    }

    /**
     * Gets instance.
     * Returns a class object {@code OrderDaoImpl}
     *
     * @return the instance
     */
    public static OrderDaoImpl getInstance() {
        if (orderDao == null) {
            orderDao = new OrderDaoImpl();
        }

        return orderDao;
    }

    @Override
    public boolean add(Map<String, Object> orderParameters) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isOrderAdded;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ORDER)) {
            statement.setLong(1, (long) orderParameters.get(ParameterKey.DATE_FROM));
            statement.setLong(2, (long) orderParameters.get(ParameterKey.DATE_TO));
            statement.setInt(3, (int) orderParameters.get(ParameterKey.AMOUNT));
            statement.setInt(4, ((Order.Status) orderParameters.get(ParameterKey.ORDER_STATUS)).ordinal());
            statement.setLong(5, (long) orderParameters.get(ParameterKey.CAR_ID));
            statement.setLong(6, (long) orderParameters.get(ParameterKey.USER_ID));
            isOrderAdded = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to add an order", e);
        }

        return isOrderAdded;
    }

    @Override
    public boolean remove(Order order) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isOrderRemoved;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_ORDER)) {
            statement.setLong(1, order.getOrderId());
            isOrderRemoved = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to remove an order", e);
        }

        return isOrderRemoved;
    }

    @Override
    public boolean update(Order order) {
        throw new UnsupportedOperationException("Operation Update not allowed with order");
    }

    @Override
    public boolean updateOrderStatus(long orderId, Order.Status status) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isStatusUpdated;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)) {
            statement.setInt(1, status.ordinal());
            statement.setLong(2, orderId);
            isStatusUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to update an order status", e);
        }
        return isStatusUpdated;
    }

    @Override
    public Optional<Order> findById(long id) {
        throw new UnsupportedOperationException("Operation FindById not allowed with order");
    }

    @Override
    public List<Order> findAll() throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        List<Order> targetOrders = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + ORDER_BY_DATE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                targetOrders.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to search all orders", e);
        }

        return targetOrders;
    }

    @Override
    public List<Order> findWaitingActionOrders() throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        List<Order> targetOrders = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_AWAITING_ACTION)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                targetOrders.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to search all waiting action orders", e);
        }

        return targetOrders;
    }

    @Override
    public List<Order> findOrdersByParameters(Map<String, Object> orderParameters) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        List<Order> targetOrders = new ArrayList<>();
        StringBuilder findByParametersQuery = new StringBuilder(FIND_ALL);
        if (!orderParameters.isEmpty()) {
            findByParametersQuery.append(WHERE_KEYWORD);
            Iterator<Map.Entry<String, Object>> entries = orderParameters.entrySet().iterator();
            while (entries.hasNext()) {
                String key = entries.next().getKey();
                if (key.equals(ParameterKey.MODEL)) {
                    findByParametersQuery.append(CHECK_CAR_MODEL);
                }
                if (key.equals(ParameterKey.EMAIL)) {
                    findByParametersQuery.append(CHECK_CLIENT_EMAIL);
                }
                if (key.equals(ParameterKey.ORDER_STATUS)) {
                    findByParametersQuery.append(CHECK_STATUS);
                }
                if (entries.hasNext()) {
                    findByParametersQuery.append(AND_KEYWORD);
                }
            }
        }
        findByParametersQuery.append(ORDER_BY_DATE);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByParametersQuery.toString())) {
            int columnIndex = 0;
            if (orderParameters.containsKey(ParameterKey.MODEL)) {
                statement.setString(++columnIndex, (String) orderParameters.get(ParameterKey.MODEL));
            }
            if (orderParameters.containsKey(ParameterKey.EMAIL)) {
                statement.setString(++columnIndex, (String) orderParameters.get(ParameterKey.EMAIL));
            }
            if (orderParameters.containsKey(ParameterKey.ORDER_STATUS)) {
                statement.setInt(++columnIndex, ((Order.Status) orderParameters.get(ParameterKey.ORDER_STATUS)).ordinal());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                targetOrders.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to search orders by parameters", e);
        }

        return targetOrders;
    }

    @Override
    public List<Order> findClientOrders(long clientId) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        List<Order> targetOrders = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_CLIENT_ORDERS)) {
            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                targetOrders.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to search orders by client Id", e);
        }

        return targetOrders;
    }

    private Order createOrder(ResultSet resultSet) throws SQLException {
        Map<String, Object> orderParameters = new HashMap<>();
        orderParameters.put(ParameterKey.ORDER_ID, resultSet.getLong(ParameterKey.ORDER_ID));
        orderParameters.put(ParameterKey.DATE_FROM, DateConverter.convertToDate(resultSet.getLong(ParameterKey.DATE_FROM)));
        orderParameters.put(ParameterKey.DATE_TO, DateConverter.convertToDate(resultSet.getLong(ParameterKey.DATE_TO)));
        orderParameters.put(ParameterKey.AMOUNT, resultSet.getInt(ParameterKey.AMOUNT));
        orderParameters.put(ParameterKey.ORDER_STATUS, Order.Status.getStatus(resultSet.getInt(ParameterKey.ORDER_STATUS)));

        orderParameters.put(ParameterKey.CAR_ID, resultSet.getLong(ParameterKey.ORDER_CAR_ID));
        orderParameters.put(ParameterKey.MODEL, resultSet.getString(ParameterKey.CARS + DOT + ParameterKey.MODEL));
        orderParameters.put(ParameterKey.CAR_TYPE, Car.Type.getType(resultSet.getInt(ParameterKey.CARS + DOT + ParameterKey.CAR_TYPE)));
        orderParameters.put(ParameterKey.NUMBER_SEATS, resultSet.getInt(ParameterKey.CARS + DOT + ParameterKey.NUMBER_SEATS));
        orderParameters.put(ParameterKey.RENT_COST, resultSet.getInt(ParameterKey.CARS + DOT + ParameterKey.RENT_COST));
        orderParameters.put(ParameterKey.FUEL_TYPE, Car.FuelType.getFuelType(resultSet.getInt(ParameterKey.CARS + DOT + ParameterKey.FUEL_TYPE)));
        orderParameters.put(ParameterKey.FUEL_CONSUMPTION, resultSet.getInt(ParameterKey.CARS + DOT + ParameterKey.FUEL_CONSUMPTION));
        orderParameters.put(ParameterKey.CAR_AVAILABLE, resultSet.getBoolean(ParameterKey.CARS + DOT + ParameterKey.CAR_AVAILABLE));

        orderParameters.put(ParameterKey.EXTERIOR, resultSet.getString(ParameterKey.CAR_VIEWS + DOT + ParameterKey.EXTERIOR));
        orderParameters.put(ParameterKey.EXTERIOR_SMALL, resultSet.getString(ParameterKey.CAR_VIEWS + DOT + ParameterKey.EXTERIOR_SMALL));
        orderParameters.put(ParameterKey.INTERIOR, resultSet.getString(ParameterKey.CAR_VIEWS + DOT + ParameterKey.INTERIOR));

        orderParameters.put(ParameterKey.USER_ID, resultSet.getLong(ParameterKey.ORDER_CLIENT_ID));
        orderParameters.put(ParameterKey.EMAIL, resultSet.getString(ParameterKey.USERS + DOT + ParameterKey.EMAIL));
        orderParameters.put(ParameterKey.ROLE, User.Role.getUserRole(resultSet.getInt(ParameterKey.USERS + DOT + ParameterKey.ROLE)));
        orderParameters.put(ParameterKey.FIRST_NAME, resultSet.getString(ParameterKey.USERS + DOT + ParameterKey.FIRST_NAME));
        orderParameters.put(ParameterKey.SECOND_NAME, resultSet.getString(ParameterKey.USERS + DOT + ParameterKey.SECOND_NAME));
        orderParameters.put(ParameterKey.DRIVER_LICENSE, resultSet.getString(ParameterKey.USERS + DOT + ParameterKey.DRIVER_LICENSE));
        orderParameters.put(ParameterKey.PHONE_NUMBER, resultSet.getLong(ParameterKey.USERS + DOT + ParameterKey.PHONE_NUMBER));
        orderParameters.put(ParameterKey.CLIENT_STATUS, Client.Status.getClientStatus(resultSet.getInt(ParameterKey.USERS + DOT + ParameterKey.CLIENT_STATUS)));

        return OrderBuilder.buildOrder(orderParameters);
    }
}
