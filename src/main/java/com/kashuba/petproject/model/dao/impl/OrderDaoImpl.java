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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The Order dao.
 *
 * {@code OrderDao} interface implementation
 *
 * @author Balashevich Gleb
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
            statement.setLong(1, (long) orderParameters.get(DATE_FROM));
            statement.setLong(2, (long) orderParameters.get(DATE_TO));
            statement.setInt(3, (int) orderParameters.get(AMOUNT));
            statement.setInt(4, ((Order.Status) orderParameters.get(ORDER_STATUS)).ordinal());
            statement.setLong(5, (long) orderParameters.get(CAR_ID));
            statement.setLong(6, (long) orderParameters.get(USER_ID));
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
                if (key.equals(MODEL)) {
                    findByParametersQuery.append(CHECK_CAR_MODEL);
                }
                if (key.equals(EMAIL)) {
                    findByParametersQuery.append(CHECK_CLIENT_EMAIL);
                }
                if (key.equals(ORDER_STATUS)) {
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
            if (orderParameters.containsKey(MODEL)) {
                statement.setString(++columnIndex, (String) orderParameters.get(MODEL));
            }
            if (orderParameters.containsKey(EMAIL)) {
                statement.setString(++columnIndex, (String) orderParameters.get(EMAIL));
            }
            if (orderParameters.containsKey(ORDER_STATUS)) {
                statement.setInt(++columnIndex, ((Order.Status) orderParameters.get(ORDER_STATUS)).ordinal());
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
        orderParameters.put(ORDER_ID, resultSet.getLong(ORDER_ID));
        orderParameters.put(DATE_FROM, DateConverter.convertToDate(resultSet.getLong(DATE_FROM)));
        orderParameters.put(DATE_TO, DateConverter.convertToDate(resultSet.getLong(DATE_TO)));
        orderParameters.put(AMOUNT, resultSet.getInt(AMOUNT));
        orderParameters.put(ORDER_STATUS, Order.Status.getStatus(resultSet.getInt(ORDER_STATUS)));

        orderParameters.put(CAR_ID, resultSet.getLong(ORDER_CAR_ID));
        orderParameters.put(MODEL, resultSet.getString(CARS + DOT + MODEL));
        orderParameters.put(CAR_TYPE, Car.Type.getType(resultSet.getInt(CARS + DOT + CAR_TYPE)));
        orderParameters.put(NUMBER_SEATS, resultSet.getInt(CARS + DOT + NUMBER_SEATS));
        orderParameters.put(RENT_COST, resultSet.getInt(CARS + DOT + RENT_COST));
        orderParameters.put(FUEL_TYPE, Car.FuelType.getFuelType(resultSet.getInt(CARS + DOT + FUEL_TYPE)));
        orderParameters.put(FUEL_CONSUMPTION, resultSet.getInt(CARS + DOT + FUEL_CONSUMPTION));
        orderParameters.put(CAR_AVAILABLE, resultSet.getBoolean(CARS + DOT + CAR_AVAILABLE));

        orderParameters.put(EXTERIOR, resultSet.getString(CAR_VIEWS + DOT + EXTERIOR));
        orderParameters.put(EXTERIOR_SMALL, resultSet.getString(CAR_VIEWS + DOT + EXTERIOR_SMALL));
        orderParameters.put(INTERIOR, resultSet.getString(CAR_VIEWS + DOT + INTERIOR));

        orderParameters.put(USER_ID, resultSet.getLong(ORDER_CLIENT_ID));
        orderParameters.put(EMAIL, resultSet.getString(USERS + DOT + EMAIL));
        orderParameters.put(ROLE, User.Role.getUserRole(resultSet.getInt(USERS + DOT + ROLE)));
        orderParameters.put(FIRST_NAME, resultSet.getString(USERS + DOT + FIRST_NAME));
        orderParameters.put(SECOND_NAME, resultSet.getString(USERS + DOT + SECOND_NAME));
        orderParameters.put(DRIVER_LICENSE, resultSet.getString(USERS + DOT + DRIVER_LICENSE));
        orderParameters.put(PHONE_NUMBER, resultSet.getLong(USERS + DOT + PHONE_NUMBER));
        orderParameters.put(CLIENT_STATUS, Client.Status.getClientStatus(resultSet.getInt(USERS + DOT + CLIENT_STATUS)));

        return OrderBuilder.buildOrder(orderParameters);
    }
}
