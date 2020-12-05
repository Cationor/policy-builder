package com.kashuba.petproject.builder;

import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.entity.CarView;
import com.kashuba.petproject.util.ParameterKey;

import java.util.Map;

/**
 * The type Car builder.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class CarBuilder {
    private CarBuilder() {
    }

    /**
     * Build car car.
     *
     * @param carParameters the car parameters
     * @return the car
     */
    public static Car buildCar(Map<String, Object> carParameters) {
        Car buildingCar = new Car();

        if (carParameters.containsKey(ParameterKey.CAR_ID)) {
            buildingCar.setCarId((long) carParameters.get(ParameterKey.CAR_ID));
        }
        buildingCar.setModel((String) carParameters.get(ParameterKey.MODEL));
        buildingCar.setType((Car.Type) carParameters.get(ParameterKey.CAR_TYPE));
        buildingCar.setNumberSeats((int) carParameters.get(ParameterKey.NUMBER_SEATS));
        buildingCar.setRentCost((int) carParameters.get(ParameterKey.RENT_COST));
        buildingCar.setFuelType((Car.FuelType) carParameters.get(ParameterKey.FUEL_TYPE));
        buildingCar.setFuelConsumption((int) carParameters.get(ParameterKey.FUEL_CONSUMPTION));
        buildingCar.setAvailable((boolean) carParameters.get(ParameterKey.CAR_AVAILABLE));
        buildingCar.setCarView(buildCarView(carParameters));

        return buildingCar;
    }

    private static CarView buildCarView(Map<String, Object> carParameters) {
        CarView carView = new CarView();
        carView.setExterior((String) carParameters.get(ParameterKey.EXTERIOR));
        carView.setExteriorSmall((String) carParameters.get(ParameterKey.EXTERIOR_SMALL));
        carView.setInterior((String) carParameters.get(ParameterKey.INTERIOR));

        return carView;
    }
}
