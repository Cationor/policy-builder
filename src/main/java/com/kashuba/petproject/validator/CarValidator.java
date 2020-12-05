package com.kashuba.petproject.validator;

import com.kashuba.petproject.model.entity.Car;
import com.kashuba.petproject.util.ParameterKey;

import java.util.Map;

/**
 * The Car validator.
 * <p>
 * Checks the parameters of the Car entity for their
 * compliance with certain criteria.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class CarValidator {
    private static final String PRICE_PATTERN = "\\d{2,3};\\d{2,3}";
    private static final String MODEL_PATTERN = "^[a-zA-Z0-9\\s-]{2,100}$";
    private static final String RENT_COST_PATTERN = "^\\d{2,3}$";
    private static final int RENT_COST_MIN = 10;
    private static final int RENT_COST_MAX = 200;
    private static final String NUMBER_SEATS_PATTERN = "^\\d{1,2}$";
    private static final int NUMBER_SEATS_MIN = 1;
    private static final int NUMBER_SEATS_MAX = 50;
    private static final String FUEL_CONSUMPTION_PATTERN = "^\\d{1,2}$";
    private static final int FUEL_CONSUMPTION_MIN = 1;
    private static final int FUEL_CONSUMPTION_MAX = 50;
    private static final String PRICE_DELIMITER = ";";
    private static final String EMPTY_VALUE = "";
    private static final String BOOLEAN_TRUE = "true";
    private static final String BOOLEAN_FALSE = "false";

    private CarValidator() {
    }

    /**
     * Validate the map of car parameters. Used mainly when adding a new entity to
     * the system. Uses separate methods to test each specific parameter.
     *
     * @param carParameters the car parameters
     * @return the boolean
     */
    public static boolean validateCarParameters(Map<String, String> carParameters) {
        boolean isParametersCorrect = true;

        if (!validateModel(carParameters.get(ParameterKey.MODEL))) {
            isParametersCorrect = false;
            carParameters.put(ParameterKey.MODEL, EMPTY_VALUE);
        }
        if (!validateType(carParameters.get(ParameterKey.CAR_TYPE))) {
            isParametersCorrect = false;
            carParameters.put(ParameterKey.CAR_TYPE, EMPTY_VALUE);
        }
        if (!validateNumberSeats(carParameters.get(ParameterKey.NUMBER_SEATS))) {
            isParametersCorrect = false;
            carParameters.put(ParameterKey.NUMBER_SEATS, EMPTY_VALUE);
        }
        if (!validateRentCost(carParameters.get(ParameterKey.RENT_COST))) {
            isParametersCorrect = false;
            carParameters.put(ParameterKey.RENT_COST, EMPTY_VALUE);
        }
        if (!validateFuelType(carParameters.get(ParameterKey.FUEL_TYPE))) {
            isParametersCorrect = false;
            carParameters.put(ParameterKey.FUEL_TYPE, EMPTY_VALUE);
        }
        if (!validateFuelConsumption(carParameters.get(ParameterKey.FUEL_CONSUMPTION))) {
            isParametersCorrect = false;
            carParameters.put(ParameterKey.FUEL_CONSUMPTION, EMPTY_VALUE);
        }
        if (!validateAvailable(carParameters.get(ParameterKey.CAR_AVAILABLE))) {
            isParametersCorrect = false;
            carParameters.put(ParameterKey.CAR_AVAILABLE, EMPTY_VALUE);
        }

        return isParametersCorrect;
    }

    /**
     * Validate the model parameter for the existence of the
     * data and if it matches a regular expression.
     *
     * @param model the model
     * @return the boolean
     */
    public static boolean validateModel(String model) {
        boolean isModelValid = false;

        if (model != null && !model.isEmpty()) {
            isModelValid = model.matches(MODEL_PATTERN);
        }

        return isModelValid;
    }

    /**
     * Validate the type parameter for the existence of the
     * data and the existence of such a type in the
     * corresponding enum.
     *
     * @param carTypeData the car type data
     * @return the boolean
     */
    public static boolean validateType(String carTypeData) {
        boolean isCarTypeValid = false;

        if (carTypeData != null && !carTypeData.isEmpty()) {
            Car.Type[] types = Car.Type.values();
            for (Car.Type type : types) {
                if (carTypeData.toUpperCase().equals(type.name())) {
                    isCarTypeValid = true;
                }
            }
        }

        return isCarTypeValid;
    }

    /**
     * Validate the number seats parameter for the existence of the
     * data, if it matches a regular expression and whether
     * the value is in a specific range.
     *
     * @param numberSeatsData the number seats data
     * @return the boolean
     */
    public static boolean validateNumberSeats(String numberSeatsData) {
        boolean isNumberSeatsValid = false;

        if (numberSeatsData != null && !numberSeatsData.isEmpty()) {
            if (numberSeatsData.matches(NUMBER_SEATS_PATTERN)) {
                int numberSeats = Integer.parseInt(numberSeatsData);
                isNumberSeatsValid = numberSeats >= NUMBER_SEATS_MIN && numberSeats <= NUMBER_SEATS_MAX;
            }
        }

        return isNumberSeatsValid;
    }

    /**
     * Validate the rent cost parameter for the existence of the
     * data, if it matches a regular expression and whether
     * the value is in a specific range.
     *
     * @param carRentCostData the car rent cost data
     * @return the boolean
     */
    public static boolean validateRentCost(String carRentCostData) {
        boolean isRentCostValid = false;

        if (carRentCostData != null && !carRentCostData.isEmpty()) {
            if (carRentCostData.matches(RENT_COST_PATTERN)) {
                int rentCost = Integer.parseInt(carRentCostData);
                isRentCostValid = rentCost >= RENT_COST_MIN && rentCost <= RENT_COST_MAX;
            }
        }

        return isRentCostValid;
    }

    /**
     * Validate the fuel type parameter for the existence of the
     * data and the existence of such a type in the
     * corresponding enum.
     *
     * @param fuelTypeData the fuel type data
     * @return the boolean
     */
    public static boolean validateFuelType(String fuelTypeData) {
        boolean isFuelTypeValid = false;

        if (fuelTypeData != null && !fuelTypeData.isEmpty()) {
            Car.FuelType[] fuelTypes = Car.FuelType.values();
            for (Car.FuelType fuelType : fuelTypes) {
                if (fuelTypeData.toUpperCase().equals(fuelType.name())) {
                    isFuelTypeValid = true;
                }
            }
        }

        return isFuelTypeValid;
    }

    /**
     * Validate the fuel consumption parameter for the existence of the
     * data, if it matches a regular expression and whether
     * the value is in a specific range.
     *
     * @param fuelConsumptionData the fuel consumption data
     * @return the boolean
     */
    public static boolean validateFuelConsumption(String fuelConsumptionData) {
        boolean isFuelConsumptionValid = false;

        if (fuelConsumptionData != null && !fuelConsumptionData.isEmpty()) {
            if (fuelConsumptionData.matches(FUEL_CONSUMPTION_PATTERN)) {
                int fuelConsumption = Integer.parseInt(fuelConsumptionData);
                isFuelConsumptionValid = fuelConsumption >= FUEL_CONSUMPTION_MIN
                        && fuelConsumption <= FUEL_CONSUMPTION_MAX;
            }
        }

        return isFuelConsumptionValid;
    }

    /**
     * Validate the price range parameter for the existence of the
     * data, if it matches a regular expression and whether
     * the value is in a specific range.
     *
     * @param priceRangeData the price range data
     * @return the boolean
     */
    public static boolean validatePriceRangeData(String priceRangeData) {
        boolean isPriceRangeValid = false;

        if (priceRangeData != null && priceRangeData.matches(PRICE_PATTERN)) {
            String[] prices = priceRangeData.split(PRICE_DELIMITER);
            int priceFrom = Integer.parseInt(prices[0]);
            int priceTo = Integer.parseInt(prices[1]);
            if (priceFrom >= RENT_COST_MIN && priceTo <= RENT_COST_MAX) {
                if (priceFrom <= priceTo) {
                    isPriceRangeValid = true;
                }
            }
        }

        return isPriceRangeValid;
    }

    /**
     * Validate the available parameter for the existence of the
     * data and if it boolean value.
     *
     * @param carAvailableData the car available data
     * @return the boolean
     */
    public static boolean validateAvailable(String carAvailableData) {
        boolean isCarAvailableValid = false;

        if (carAvailableData != null && !carAvailableData.isEmpty()) {
            if (carAvailableData.equals(BOOLEAN_TRUE) || carAvailableData.equals(BOOLEAN_FALSE)) {
                isCarAvailableValid = true;
            }
        }

        return isCarAvailableValid;
    }
}
