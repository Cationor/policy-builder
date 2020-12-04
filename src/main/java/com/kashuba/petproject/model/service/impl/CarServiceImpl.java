package com.kashuba.petproject.model.service.impl;

import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.dao.CarDao;
import com.kashuba.petproject.model.dao.impl.CarDaoImpl;
import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.service.CarService;
import com.kashuba.petproject.validator.CarValidator;
import com.kashuba.petproject.validator.OrderValidator;

import java.time.LocalDate;
import java.util.*;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The Car service.
 * <p>
 * Implements interface {@code CarService} handles car data processing.
 *
 * @author Balashevich Gleb
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
                carParametersChecked.put(MODEL, carParameters.get(MODEL));
                carParametersChecked.put(CAR_TYPE, Car.Type.valueOf(carParameters.get(CAR_TYPE).toUpperCase()));
                carParametersChecked.put(NUMBER_SEATS, Integer.parseInt(carParameters.get(NUMBER_SEATS)));
                carParametersChecked.put(RENT_COST, Integer.parseInt(carParameters.get(RENT_COST)));
                carParametersChecked.put(FUEL_TYPE, Car.FuelType.valueOf(carParameters.get(FUEL_TYPE).toUpperCase()));
                carParametersChecked.put(FUEL_CONSUMPTION, Integer.parseInt(carParameters.get(FUEL_CONSUMPTION)));
                carParametersChecked.put(CAR_AVAILABLE, Boolean.parseBoolean(carParameters.get(CAR_AVAILABLE)));
                String exteriorSmall = carParameters.get(EXTERIOR_SMALL);
                String exterior = carParameters.get(EXTERIOR);
                String interior = carParameters.get(INTERIOR);
                if (exteriorSmall != null && !exteriorSmall.isEmpty()) {
                    carParametersChecked.put(EXTERIOR_SMALL, carParameters.get(EXTERIOR_SMALL));
                } else {
                    carParametersChecked.put(EXTERIOR_SMALL, DEFAULT_EXTERIOR_SMALL);
                }
                if (exterior != null && !exterior.isEmpty()) {
                    carParametersChecked.put(EXTERIOR, carParameters.get(EXTERIOR));
                } else {
                    carParametersChecked.put(EXTERIOR, DEFAULT_EXTERIOR);
                }
                if (interior != null && !interior.isEmpty()) {
                    carParametersChecked.put(INTERIOR, carParameters.get(INTERIOR));
                } else {
                    carParametersChecked.put(INTERIOR, DEFAULT_INTERIOR);
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
        String carRentCostData = carParameters.get(RENT_COST);
        String carAvailableData = carParameters.get(CAR_AVAILABLE);

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
        String dateFromData = carParametersData.get(DATE_FROM);
        String dateToData = carParametersData.get(DATE_TO);
        String priceRangeData = carParametersData.get(PRICE_RANGE);
        String carTypeData = carParametersData.get(CAR_TYPE);
        List<Car> targetCars = null;

        try {
            if (OrderValidator.validateDate(dateFromData) && OrderValidator.validateDate(dateToData)) {
                Map<String, Object> carParametersChecked = new HashMap<>();
                LocalDate dateFrom = LocalDate.parse(dateFromData);
                LocalDate dateTo = LocalDate.parse(dateToData);
                LocalDate today = LocalDate.now();
                if (dateFrom.toEpochDay() <= dateTo.toEpochDay() && dateFrom.toEpochDay() > today.toEpochDay()) {
                    carParametersChecked.put(DATE_FROM, dateFrom);
                    carParametersChecked.put(DATE_TO, dateTo);
                    if (CarValidator.validatePriceRangeData(priceRangeData)) {
                        String[] prices = priceRangeData.split(PRICE_DELIMITER);
                        carParametersChecked.put(PRICE_FROM, Integer.parseInt(prices[0]));
                        carParametersChecked.put(PRICE_TO, Integer.parseInt(prices[1]));
                    }
                    if (CarValidator.validateType(carTypeData)) {
                        carParametersChecked.put(CAR_TYPE, Car.Type.valueOf(carTypeData.toUpperCase()));
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
        String carTypeData = carParametersData.get(CAR_TYPE);
        String carAvailableData = carParametersData.get(CAR_AVAILABLE);
        Map<String, Object> carParametersChecked = new HashMap<>();
        List<Car> targetCars;

        try {
            if (CarValidator.validateType(carTypeData)) {
                carParametersChecked.put(CAR_TYPE, Car.Type.valueOf(carTypeData.toUpperCase()));
            }
            if (CarValidator.validateAvailable(carAvailableData)) {
                carParametersChecked.put(CAR_AVAILABLE, Boolean.valueOf(carAvailableData));
            }
            targetCars = carDao.findCarsByParameters(carParametersChecked);

        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetCars;
    }
}
