package com.kashuba.petproject.validator;

import com.kashuba.petproject.model.entity.Order;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Order validator.
 * <p>
 * Checks the parameters of the Order entity for their
 * compliance with certain criteria.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class OrderValidator {
    private static final String DATE_PATTERN = "(\\d{4})-(\\d{2})-(\\d{2})";

    private OrderValidator() {
    }

    /**
     * Validate date value for existence and correctness.
     *
     * @param dateData the date data
     * @return the boolean
     */
    public static boolean validateDate(String dateData) {
        boolean isDateValid = false;

        if (dateData != null && dateData.matches(DATE_PATTERN)) {
            Pattern pattern = Pattern.compile(DATE_PATTERN);
            Matcher matcher = pattern.matcher(dateData);
            if (matcher.find()) {
                int groupIndex = 0;
                int year = Integer.parseInt(matcher.group(++groupIndex));
                int month = Integer.parseInt(matcher.group(++groupIndex));
                int day = Integer.parseInt(matcher.group(++groupIndex));
                if (year > 2000 && year < 2050 && month > 0 && month <= 12) {
                    isDateValid = switch (month) {
                        case 1, 3, 5, 7, 8, 10, 12 -> day > 0 && day <= 31;
                        case 2 -> day > 0 && day <= 29;
                        case 4, 6, 9, 11 -> day > 0 && day <= 30;
                        default -> false;
                    };
                }
            }
        }

        return isDateValid;
    }

    /**
     * Validate the status parameter for the existence of the
     * data and the existence of such a type in the
     * corresponding enum.
     *
     * @param orderStatusData the order status data
     * @return the boolean
     */
    public static boolean validateStatus(String orderStatusData) {
        boolean isOrderStatusValid = false;

        if (orderStatusData != null && !orderStatusData.isEmpty()) {
            Order.Status[] statuses = Order.Status.values();
            for (Order.Status status : statuses) {
                if (orderStatusData.toUpperCase().equals(status.name())) {
                    isOrderStatusValid = true;
                }
            }
        }

        return isOrderStatusValid;
    }
}
