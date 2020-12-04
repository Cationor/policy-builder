package com.kashuba.petproject.builder;

import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.model.entity.CarView;

import static com.kashuba.petproject.util.ParameterKey.*;

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

        if (carParameters.containsKey(CAR_ID)) {
            buildingCar.setCarId((long) carParameters.get(CAR_ID));
        }
        buildingCar.setModel((String) carParameters.get(MODEL));
        buildingCar.setType((Car.Type) carParameters.get(CAR_TYPE));
        buildingCar.setNumberSeats((int) carParameters.get(NUMBER_SEATS));
        buildingCar.setRentCost((int) carParameters.get(RENT_COST));
        buildingCar.setFuelType((Car.FuelType) carParameters.get(FUEL_TYPE));
        buildingCar.setFuelConsumption((int) carParameters.get(FUEL_CONSUMPTION));
        buildingCar.setAvailable((boolean) carParameters.get(CAR_AVAILABLE));
        buildingCar.setCarView(buildCarView(carParameters));

        return buildingCar;
    }

    private static CarView buildCarView(Map<String, Object> carParameters) {
        CarView carView = new CarView();
        carView.setExterior((String) carParameters.get(EXTERIOR));
        carView.setExteriorSmall((String) carParameters.get(EXTERIOR_SMALL));
        carView.setInterior((String) carParameters.get(INTERIOR));

        return carView;
    }
}
