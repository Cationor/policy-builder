package com.kashuba.petproject.model.dao.impl;

import com.kashuba.petproject.builder.CarBuilder;
import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.model.dao.CarDao;
import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.pool.ConnectionPool;
import com.kashuba.petproject.util.DateConverter;
import com.kashuba.petproject.util.ParameterKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * The Car dao.
 * {@code CarDao} interface implementation
 *
 * @author Balashevich Gleb
 * @version 1.0
 * @see CarDao
 */
public class CarDaoImpl implements CarDao {
    private static CarDaoImpl carDao;
    private static final String ADD_CAR = "INSERT INTO cars (model, car_type, number_seats, rent_cost," +
            "fuel_type, fuel_consumption, is_available) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String ADD_CAR_VIEW = "INSERT INTO car_views(exterior_small, exterior, interior, cars_id)" +
            " VALUES (?, ?, ?, LAST_INSERT_ID())";
    private static final String FIND_ALL = "SELECT car_id, model, car_type, number_seats, rent_cost, " +
            "fuel_type, fuel_consumption, car_views.exterior, car_views.exterior_small, car_views.interior," +
            " is_available FROM cars LEFT OUTER JOIN car_views ON car_id = car_views.cars_id";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE car_id = ?";
    private static final String CHECK_CAR_TYPE = " car_type=?";
    private static final String CHECK_CAR_AVAILABLE = " is_available=?";
    private static final String CHECK_PRICE_RANGE = " (rent_cost BETWEEN ? AND ?)";
    private static final String CHECK_ORDERS_DATE_RANGE = " NOT EXISTS (SELECT date_from, date_to, " +
            "cars_id FROM orders WHERE cars.car_id = orders.order_car_id AND " +
            "((? BETWEEN date_from AND date_to) OR (? BETWEEN date_from AND date_to)))";
    private static final String FIND_AVAILABLE_ORDER_CAR = FIND_ALL + " WHERE is_available=true";
    private static final String UPDATE_CAR = "UPDATE cars SET model = ?, car_type = ?, number_seats = ?," +
            "rent_cost = ?, fuel_type = ?, fuel_consumption = ?, is_available = ? WHERE car_id = ?";
    private static final String WHERE_KEYWORD = " WHERE";
    private static final String AND_KEYWORD = " AND";
    private static final String DOT = ".";

    private CarDaoImpl() {
    }

    /**
     * Gets instance.
     * Returns a class object {@code CarDaoImpl}
     *
     * @return the instance
     */
    public static CarDaoImpl getInstance() {
        if (carDao == null) {
            carDao = new CarDaoImpl();
        }

        return carDao;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoProjectException {
        boolean isCarAdded;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement carStatement = null;
        PreparedStatement carViewStatement = null;

        try {
            autocommit(connection, false);
            carStatement = connection.prepareStatement(ADD_CAR);
            carViewStatement = connection.prepareStatement(ADD_CAR_VIEW);
            carStatement.setString(1, (String) parameters.get(ParameterKey.MODEL));
            carStatement.setInt(2, ((Car.Type) parameters.get(ParameterKey.CAR_TYPE)).ordinal());
            carStatement.setInt(3, (int) parameters.get(ParameterKey.NUMBER_SEATS));
            carStatement.setInt(4, (int) parameters.get(ParameterKey.RENT_COST));
            carStatement.setInt(5, ((Car.FuelType) parameters.get(ParameterKey.FUEL_TYPE)).ordinal());
            carStatement.setInt(6, (int) parameters.get(ParameterKey.FUEL_CONSUMPTION));
            carStatement.setBoolean(7, (boolean) parameters.get(ParameterKey.CAR_AVAILABLE));
            isCarAdded = carStatement.executeUpdate() > 0;
            if (isCarAdded) {
                carViewStatement.setString(1, (String) parameters.get(ParameterKey.EXTERIOR_SMALL));
                carViewStatement.setString(2, (String) parameters.get(ParameterKey.EXTERIOR));
                carViewStatement.setString(3, (String) parameters.get(ParameterKey.INTERIOR));
                isCarAdded = carViewStatement.executeUpdate() > 0;
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DaoProjectException("Error executing the query when adding a car", e);
        } finally {
            autocommit(connection, true);
            close(carStatement);
            close(carViewStatement);
            close(connection);
        }

        return isCarAdded;
    }

    @Override
    public boolean remove(Car car) {
        throw new UnsupportedOperationException("Operation remove not allowed for a car");
    }

    @Override
    public boolean update(Car updatingCar) throws DaoProjectException {
        boolean isCarUpdated;
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CAR)) {
            statement.setString(1, updatingCar.getModel());
            statement.setInt(2, updatingCar.getType().ordinal());
            statement.setInt(3, updatingCar.getNumberSeats());
            statement.setInt(4, updatingCar.getRentCost());
            statement.setInt(5, updatingCar.getFuelType().ordinal());
            statement.setInt(6, updatingCar.getFuelConsumption());
            statement.setBoolean(7, updatingCar.isAvailable());
            statement.setLong(8, updatingCar.getCarId());
            isCarUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a car update query", e);
        }

        return isCarUpdated;
    }

    @Override
    public List<Car> findAll() throws DaoProjectException {
        ConnectionPool pool = ConnectionPool.getInstance();
        List<Car> carList = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carList.add(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a cars search query", e);
        }

        return carList;
    }

    @Override
    public Optional<Car> findById(long id) throws DaoProjectException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Optional<Car> targetCar = Optional.empty();

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                targetCar = Optional.of(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find a car by Id", e);
        }

        return targetCar;
    }

    @Override
    public List<Car> findAvailableOrderCars(Map<String, Object> carParameters) throws DaoProjectException {
        ConnectionPool pool = ConnectionPool.getInstance();
        List<Car> carList = new ArrayList<>();
        StringBuilder filteringAvailableCarsQuery = new StringBuilder();
        filteringAvailableCarsQuery.append(FIND_AVAILABLE_ORDER_CAR);
        if (carParameters.containsKey(ParameterKey.PRICE_FROM) && carParameters.containsKey(ParameterKey.PRICE_TO)) {
            filteringAvailableCarsQuery.append(AND_KEYWORD).append(CHECK_PRICE_RANGE);
        }
        if (carParameters.containsKey(ParameterKey.CAR_TYPE)) {
            filteringAvailableCarsQuery.append(AND_KEYWORD).append(CHECK_CAR_TYPE);
        }
        filteringAvailableCarsQuery.append(AND_KEYWORD).append(CHECK_ORDERS_DATE_RANGE);

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(filteringAvailableCarsQuery.toString())) {
            int parameterIndex = 0;
            if (carParameters.containsKey(ParameterKey.PRICE_FROM) && carParameters.containsKey(ParameterKey.PRICE_TO)) {
                statement.setInt(++parameterIndex, (int) carParameters.get(ParameterKey.PRICE_FROM));
                statement.setInt(++parameterIndex, (int) carParameters.get(ParameterKey.PRICE_TO));
            }
            if (carParameters.containsKey(ParameterKey.CAR_TYPE)) {
                statement.setInt(++parameterIndex, ((Car.Type) carParameters.get(ParameterKey.CAR_TYPE)).ordinal());
            }
            statement.setLong(++parameterIndex, DateConverter.convertToLong((LocalDate) carParameters.get(ParameterKey.DATE_FROM)));
            statement.setLong(++parameterIndex, DateConverter.convertToLong((LocalDate) carParameters.get(ParameterKey.DATE_TO)));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carList.add(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find all available cars", e);
        }

        return carList;
    }

    @Override
    public List<Car> findCarsByParameters(Map<String, Object> carParameters) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        List<Car> targetCars = new ArrayList<>();
        StringBuilder findCheckCarsQuery = new StringBuilder(FIND_ALL);
        if (carParameters.containsKey(ParameterKey.CAR_TYPE) || carParameters.containsKey(ParameterKey.CAR_AVAILABLE)) {
            findCheckCarsQuery.append(WHERE_KEYWORD);
            if (carParameters.containsKey(ParameterKey.CAR_TYPE)) {
                findCheckCarsQuery.append(CHECK_CAR_TYPE);
            }
            if (carParameters.containsKey(ParameterKey.CAR_AVAILABLE)) {
                if (carParameters.containsKey(ParameterKey.CAR_TYPE)) {
                    findCheckCarsQuery.append(AND_KEYWORD);
                }
                findCheckCarsQuery.append(CHECK_CAR_AVAILABLE);
            }
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findCheckCarsQuery.toString())) {
            int parameterIndex = 0;
            if (carParameters.containsKey(ParameterKey.CAR_TYPE)) {
                statement.setInt(++parameterIndex, ((Car.Type) carParameters.get(ParameterKey.CAR_TYPE)).ordinal());
            }
            if (carParameters.containsKey(ParameterKey.CAR_AVAILABLE)) {
                statement.setBoolean(++parameterIndex, (boolean) carParameters.get(ParameterKey.CAR_AVAILABLE));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                targetCars.add(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find a cars by parameters", e);
        }

        return targetCars;
    }

    private Car createCar(ResultSet resultSet) throws SQLException {
        Map<String, Object> carParameters = new HashMap<>();
        carParameters.put(ParameterKey.CAR_ID, resultSet.getLong(ParameterKey.CAR_ID));
        carParameters.put(ParameterKey.MODEL, resultSet.getString(ParameterKey.MODEL));
        carParameters.put(ParameterKey.CAR_TYPE, Car.Type.getType(resultSet.getInt(ParameterKey.CAR_TYPE)));
        carParameters.put(ParameterKey.NUMBER_SEATS, resultSet.getInt(ParameterKey.NUMBER_SEATS));
        carParameters.put(ParameterKey.RENT_COST, resultSet.getInt(ParameterKey.RENT_COST));
        carParameters.put(ParameterKey.FUEL_TYPE, Car.FuelType.getFuelType(resultSet.getInt(ParameterKey.FUEL_TYPE)));
        carParameters.put(ParameterKey.FUEL_CONSUMPTION, resultSet.getInt(ParameterKey.FUEL_CONSUMPTION));
        carParameters.put(ParameterKey.CAR_AVAILABLE, resultSet.getBoolean(ParameterKey.CAR_AVAILABLE));
        carParameters.put(ParameterKey.EXTERIOR, resultSet.getString(ParameterKey.CAR_VIEWS + DOT + ParameterKey.EXTERIOR));
        carParameters.put(ParameterKey.EXTERIOR_SMALL, resultSet.getString(ParameterKey.CAR_VIEWS + DOT + ParameterKey.EXTERIOR_SMALL));
        carParameters.put(ParameterKey.INTERIOR, resultSet.getString(ParameterKey.CAR_VIEWS + DOT + ParameterKey.INTERIOR));

        return CarBuilder.buildCar(carParameters);
    }
}
