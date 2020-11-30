package com.kashuba.petproject.validator;

import java.time.LocalDate;
import java.util.Map;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The Payment validator.
 * <p>
 * Verifies the payment data entered by the client when paying for the order.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class PaymentValidator {
    private static final String CARD_HOLDER_PATTERN = "^\\p{Alpha}{1,20} \\p{Alpha}{1,20}$";
    private static final String CARD_NUMBER_PATTERN = "^(\\p{Digit}{4}\\s?){4}$";
    private static final String CARD_CVV_PATTERN = "^\\p{Digit}{3}$";
    private static final String DATE_PART_PATTERN = "^\\p{Digit}{1,2}$";
    private static final String SPACE_PATTERN = " ";
    private static final String EMPTY_PATTERN = "";

    private PaymentValidator() {
    }

    /**
     * Validate all payment parameters. Divided into by element check.
     *
     * @param paymentParameters the payment parameters
     * @return the boolean
     */
    public static boolean validatePaymentParameters(Map<String, String> paymentParameters) {
        boolean isPaymentCorrect = true;
        if (!validateCardHolder(paymentParameters.get(CARD_HOLDER))) {
            isPaymentCorrect = false;
        }
        if (!validateCardNumber(paymentParameters.get(CARD_NUMBER))) {
            isPaymentCorrect = false;
        }
        if (!validateExpirationDate(paymentParameters.get(CARD_EXPIRATION_MONTH),
                paymentParameters.get(CARD_EXPIRATION_YEAR))) {
            isPaymentCorrect = false;
        }
        if (!validateCvvCode(paymentParameters.get(CARD_CVV_CODE))) {
            isPaymentCorrect = false;
        }

        return isPaymentCorrect;
    }

    /**
     * Validate card holder parameter for the existence of the
     * data and if it matches a regular expression.
     *
     * @param cardHolderData the card holder data
     * @return the boolean
     */
    public static boolean validateCardHolder(String cardHolderData) {
        boolean isCardHolderValid = false;

        if (cardHolderData != null && !cardHolderData.isEmpty()) {
            isCardHolderValid = cardHolderData.matches(CARD_HOLDER_PATTERN);
        }

        return isCardHolderValid;
    }

    /**
     * Validate card number parameter for the existence of the
     * data, if it matches a regular expression and if value
     * more than zero.
     *
     * @param cardNumberData the card number data
     * @return the boolean
     */
    public static boolean validateCardNumber(String cardNumberData) {
        boolean isCardNumberValid = false;

        if (cardNumberData != null && !cardNumberData.isEmpty()) {
            if (cardNumberData.matches(CARD_NUMBER_PATTERN)) {
                isCardNumberValid = Long.parseLong(cardNumberData.replaceAll(SPACE_PATTERN, EMPTY_PATTERN)) > 0;
            }
        }

        return isCardNumberValid;
    }

    /**
     * Validate card expiration date parameter for the existence of the
     * data, and if the data matches the month value and the year number.
     * That's values must be more than current date.
     *
     * @param monthData the month data
     * @param yearData  the year data
     * @return the boolean
     */
    public static boolean validateExpirationDate(String monthData, String yearData) {
        boolean isExpirationDateValid = false;

        if (monthData != null && !monthData.isEmpty() && yearData != null && !yearData.isEmpty()) {
            if (monthData.matches(DATE_PART_PATTERN) && yearData.matches(DATE_PART_PATTERN)) {
                LocalDate currentDate = LocalDate.now();
                int currentMonth = currentDate.getMonth().getValue();
                int currentYear = currentDate.getYear() % 2000;
                int month = Integer.parseInt(monthData);
                int year = Integer.parseInt(yearData);
                if (year > currentYear) {
                    isExpirationDateValid = (month >= 1 && month <= 12);
                }
                if (year == currentYear) {
                    isExpirationDateValid = (month >= currentMonth && month <= 12);
                }
            }
        }

        return isExpirationDateValid;
    }

    /**
     * Validate cvv code  parameter for the existence of the
     * data, and if it matches a regular expression.
     *
     * @param cvvCodeData the cvv code data
     * @return the boolean
     */
    public static boolean validateCvvCode(String cvvCodeData) {
        boolean isCvvCodeValid = false;

        if (cvvCodeData != null && !cvvCodeData.isEmpty()) {
            if (cvvCodeData.matches(CARD_CVV_PATTERN)) {
                isCvvCodeValid = Integer.parseInt(cvvCodeData) > 0;
            }
        }

        return isCvvCodeValid;
    }
}
