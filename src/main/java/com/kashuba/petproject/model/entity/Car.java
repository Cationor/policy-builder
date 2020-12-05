package com.kashuba.petproject.model.entity;

import java.util.Arrays;

/**
 * The Car.
 * <p>
 * Is the subject area of the service.
 * Describes the cars rented by clients
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class Car extends Entity {
    /**
     * The enum Type.
     * Describes the types of cars.
     */
    public enum Type {
        SUV("Suv"),
        SEDAN("Sedan"),
        MINIVAN("Minivan");

        private String title;

        Type(String title) {
            this.title = title;
        }

        /**
         * Gets car type title.
         *
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Gets type by index.
         *
         * @param index the index
         * @return the type
         */
        public static Type getType(int index) {
            return Arrays.stream(Type.values()).filter(v -> v.ordinal() == index).findFirst().get();
        }
    }

    /**
     * The enum Fuel type.
     * Describes the fuel types of cars.
     */
    public enum FuelType {
        DIESEL("Diesel"),
        PETROL("Petrol");

        private String title;

        FuelType(String title) {
            this.title = title;
        }

        /**
         * Gets fuel type title.
         *
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Gets fuel type by index.
         *
         * @param index the index
         * @return the fuel type
         */
        public static FuelType getFuelType(int index) {
            return Arrays.stream(FuelType.values()).filter(f -> f.ordinal() == index).findFirst().get();
        }
    }

    private long carId;
    private String model;
    private Type type;
    private int numberSeats;
    private int rentCost;
    private FuelType fuelType;
    private int fuelConsumption;
    private CarView carView;
    private boolean available;

    /**
     * Gets carId.
     *
     * @return the car id
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Sets carId.
     *
     * @param carId the car id
     */
    public void setCarId(long carId) {
        this.carId = carId;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets numberSeats.
     *
     * @return the number seats
     */
    public int getNumberSeats() {
        return numberSeats;
    }

    /**
     * Sets numberSeats.
     *
     * @param numberSeats the number seats
     */
    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    /**
     * Gets rentCost.
     *
     * @return the rent cost
     */
    public int getRentCost() {
        return rentCost;
    }

    /**
     * Sets rentCost.
     *
     * @param rentCost the rent cost
     */
    public void setRentCost(int rentCost) {
        this.rentCost = rentCost;
    }

    /**
     * Gets fuelType.
     *
     * @return the fuel type
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Sets fuelType.
     *
     * @param fuelType the fuel type
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Gets fuelConsumption.
     *
     * @return the fuel consumption
     */
    public int getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * Sets fuelConsumption.
     *
     * @param fuelConsumption the fuel consumption
     */
    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    /**
     * Gets carView.
     *
     * @return the car view
     */
    public CarView getCarView() {
        return carView;
    }

    /**
     * Sets carView.
     *
     * @param carView the car view
     */
    public void setCarView(CarView carView) {
        this.carView = carView;
    }

    /**
     * Is available boolean.
     *
     * @return the boolean
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets available.
     *
     * @param available the available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Car car = (Car) obj;

        return carId == car.carId
                && (model != null && model.equals(car.model))
                && type == car.type
                && numberSeats == car.numberSeats
                && rentCost == car.rentCost
                && fuelType == car.fuelType
                && fuelConsumption == car.fuelConsumption
                && (carView != null && carView.equals(car.carView))
                && available == car.available;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 37 * result + Long.hashCode(carId);
        result += 37 * result + (model == null ? 0 : model.hashCode());
        result += 37 * result + (type == null ? 0 : type.hashCode());
        result += 37 * result + numberSeats;
        result += 37 * result + rentCost;
        result += 37 * result + (fuelType == null ? 0 : fuelType.hashCode());
        result += 37 * result + fuelConsumption;
        result += 37 * result + (carView == null ? 0 : carView.hashCode());
        result += 37 * result + (available ? 1 : 0);

        return result;
    }

    @Override
    public String toString() {
        return String.format("Car %d: model %s, type %s, numberSeats %d, rentCost %d, " +
                        "fuelType %s, fuelConsumption %d, carView %s, is available %s",
                carId, model, type != null ? type.name() : "null", numberSeats,
                rentCost, fuelType != null ? fuelType.name() : "null", fuelConsumption, carView, available);
    }
}
