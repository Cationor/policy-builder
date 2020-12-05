package com.kashuba.petproject.model.service.impl;

import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.dao.CarDao;
import com.kashuba.petproject.model.dao.impl.CarDaoImpl;
import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.service.CarService;
import com.kashuba.petproject.validator.CarValidator;
import com.kashuba.petproject.validator.OrderValidator;
import com.kashuba.petproject.util.ParameterKey;

import java.time.LocalDate;
import java.util.*;

/**
 * The Car service.
 * <p>
 * Implements interface {@code CarService} handles car data processing.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 * @see CarService
 */
public class CarServiceImpl implements CarService {
    private CarDao carDao = CarDaoImpl.getInstance();
    private static final String PRICE_DELIMITER = ";";
    private static final String DEFAULT_EXTERIOR_SMALL = "default_exterior_small.png";
    private static final String DEFAULT_EXTERIOR = "default_exterior.png";
    private static final String DEFAULT_INTERIOR = "default_interior.png";

    @Override
    public boolean addCar(Map<String, String> carParameters) throws ServiceProjectException {
        boolean isCarAdded = false;

        try {
            if (CarValidator.validateCarParameters(carParameters)) {
                Map<String, Object> carParametersChecked = new HashMap<>();
                carParametersChecked.put(ParameterKey.MODEL, carParameters.get(ParameterKey.MODEL));
                carParametersChecked.put(ParameterKey.CAR_TYPE, Car.Type.valueOf(carParameters.get(ParameterKey.CAR_TYPE).toUpperCase()));
                carParametersChecked.put(ParameterKey.NUMBER_SEATS, Integer.parseInt(carParameters.get(ParameterKey.NUMBER_SEATS)));
                carParametersChecked.put(ParameterKey.RENT_COST, Integer.parseInt(carParameters.get(ParameterKey.RENT_COST)));
                carParametersChecked.put(ParameterKey.FUEL_TYPE, Car.FuelType.valueOf(carParameters.get(ParameterKey.FUEL_TYPE).toUpperCase()));
                carParametersChecked.put(ParameterKey.FUEL_CONSUMPTION, Integer.parseInt(carParameters.get(ParameterKey.FUEL_CONSUMPTION)));
                carParametersChecked.put(ParameterKey.CAR_AVAILABLE, Boolean.parseBoolean(carParameters.get(ParameterKey.CAR_AVAILABLE)));
                String exteriorSmall = carParameters.get(ParameterKey.EXTERIOR_SMALL);
                String exterior = carParameters.get(ParameterKey.EXTERIOR);
                String interior = carParameters.get(ParameterKey.INTERIOR);
                if (exteriorSmall != null && !exteriorSmall.isEmpty()) {
                    carParametersChecked.put(ParameterKey.EXTERIOR_SMALL, carParameters.get(ParameterKey.EXTERIOR_SMALL));
                } else {
                    carParametersChecked.put(ParameterKey.EXTERIOR_SMALL, DEFAULT_EXTERIOR_SMALL);
                }
                if (exterior != null && !exterior.isEmpty()) {
                    carParametersChecked.put(ParameterKey.EXTERIOR, carParameters.get(ParameterKey.EXTERIOR));
                } else {
                    carParametersChecked.put(ParameterKey.EXTERIOR, DEFAULT_EXTERIOR);
                }
                if (interior != null && !interior.isEmpty()) {
                    carParametersChecked.put(ParameterKey.INTERIOR, carParameters.get(ParameterKey.INTERIOR));
                } else {
                    carParametersChecked.put(ParameterKey.INTERIOR, DEFAULT_INTERIOR);
                }
                isCarAdded = carDao.add(carParametersChecked);
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isCarAdded;
    }

    @Override
    public boolean updateCar(Car updatingCar, Map<String, String> carParameters) throws ServiceProjectException {
        boolean isCarUpdated;
        String carRentCostData = carParameters.get(ParameterKey.RENT_COST);
        String carAvailableData = carParameters.get(ParameterKey.CAR_AVAILABLE);

        if (CarValidator.validateRentCost(carRentCostData)) {
            updatingCar.setRentCost(Integer.parseInt(carRentCostData));
        }
        if (CarValidator.validateAvailable(carAvailableData)) {
            updatingCar.setAvailable(Boolean.parseBoolean(carAvailableData));
        }

        try {
            isCarUpdated = carDao.update(updatingCar);
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isCarUpdated;
    }

    @Override
    public Optional<Car> findCarById(long carId) throws ServiceProjectException {
        Optional<Car> targetCar;

        try {
            targetCar = carDao.findById(carId);
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetCar;
    }

    @Override
    public List<Car> findAvailableOrderCars(Map<String, String> carParametersData) throws ServiceProjectException {
        String dateFromData = carParametersData.get(ParameterKey.DATE_FROM);
        String dateToData = carParametersData.get(ParameterKey.DATE_TO);
        String priceRangeData = carParametersData.get(ParameterKey.PRICE_RANGE);
        String carTypeData = carParametersData.get(ParameterKey.CAR_TYPE);
        List<Car> targetCars = null;

        try {
            if (OrderValidator.validateDate(dateFromData) && OrderValidator.validateDate(dateToData)) {
                Map<String, Object> carParametersChecked = new HashMap<>();
                LocalDate dateFrom = LocalDate.parse(dateFromData);
                LocalDate dateTo = LocalDate.parse(dateToData);
                LocalDate today = LocalDate.now();
                if (dateFrom.toEpochDay() <= dateTo.toEpochDay() && dateFrom.toEpochDay() > today.toEpochDay()) {
                    carParametersChecked.put(ParameterKey.DATE_FROM, dateFrom);
                    carParametersChecked.put(ParameterKey.DATE_TO, dateTo);
                    if (CarValidator.validatePriceRangeData(priceRangeData)) {
                        String[] prices = priceRangeData.split(PRICE_DELIMITER);
                        carParametersChecked.put(ParameterKey.PRICE_FROM, Integer.parseInt(prices[0]));
                        carParametersChecked.put(ParameterKey.PRICE_TO, Integer.parseInt(prices[1]));
                    }
                    if (CarValidator.validateType(carTypeData)) {
                        carParametersChecked.put(ParameterKey.CAR_TYPE, Car.Type.valueOf(carTypeData.toUpperCase()));
                    }
                    targetCars = carDao.findAvailableOrderCars(carParametersChecked);
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetCars;
    }

    @Override
    public List<Car> findCarsByParameters(Map<String, String> carParametersData) throws ServiceProjectException {
        String carTypeData = carParametersData.get(ParameterKey.CAR_TYPE);
        String carAvailableData = carParametersData.get(ParameterKey.CAR_AVAILABLE);
        Map<String, Object> carParametersChecked = new HashMap<>();
        List<Car> targetCars;

        try {
            if (CarValidator.validateType(carTypeData)) {
                carParametersChecked.put(ParameterKey.CAR_TYPE, Car.Type.valueOf(carTypeData.toUpperCase()));
            }
            if (CarValidator.validateAvailable(carAvailableData)) {
                carParametersChecked.put(ParameterKey.CAR_AVAILABLE, Boolean.valueOf(carAvailableData));
            }
            targetCars = carDao.findCarsByParameters(carParametersChecked);

        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetCars;
    }
}
