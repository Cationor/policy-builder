package com.kashuba.petproject.model.service;

import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.entity.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Car service.
 * <p>
 * Indicates methods for processing information related to cars.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public interface CarService {

    /**
     * Performs actions to add a new entity Car to the application,
     * the result of the addition is determined by the boolean variable.
     *
     * @param carParameters the car parameters
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean addCar(Map<String, String> carParameters) throws ServiceProjectException;

    /**
     * Finding for a car in the system by its Id with preliminary data validation.
     *
     * @param carId the car id
     * @return the optional
     * @throws ServiceProjectException the service project exception
     */
    Optional<Car> findCarById(long carId) throws ServiceProjectException;

    /**
     * Finding for cars available for order in the system with preliminary
     * check of search parameters.
     *
     * @param carParametersData the car parameters data
     * @return the list
     * @throws ServiceProjectException the service project exception
     */
    List<Car> findAvailableOrderCars(Map<String, String> carParametersData) throws ServiceProjectException;

    /**
     * Finding for cars in the system according to the specified parameters,
     * with their preliminary check for correctness.
     *
     * @param carParametersData the car parameters data
     * @return the list
     * @throws ServiceProjectException the service project exception
     */
    List<Car> findCarsByParameters(Map<String, String> carParametersData) throws ServiceProjectException;

    /**
     * Updates the parameters of a specific car in the system, with a
     * preliminary check of the changed parameters for correctness.
     *
     * @param updatingCar   the updating car
     * @param carParameters the car parameters
     * @return the boolean
     * @throws ServiceProjectException the service project exception
     */
    boolean updateCar(Car updatingCar, Map<String, String> carParameters) throws ServiceProjectException;
}
