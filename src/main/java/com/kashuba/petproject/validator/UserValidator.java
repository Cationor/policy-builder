package com.kashuba.petproject.validator;

import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.util.ParameterKey;

import java.util.Map;

/**
 * The User validator.
 * <p>
 * Checks the parameters of the User entity for their
 * compliance with certain criteria.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class UserValidator {
    private static final String EMPTY_VALUE = "";
    private static final String EMAIL_PATTERN = "^\\p{Alnum}+[._-]?\\p{Alnum}+@\\p{Alnum}+\\.\\p{Alpha}{2,4}$";
    private static final String PASSWORD_PATTERN = "^((?=.*\\p{Digit})(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*[_-])?.{8,30})$";
    private static final String NAME_PATTERN = "^[а-яА-Яa-zA-Z-]{1,20}$";
    private static final String DRIVER_LICENSE_PATTERN = "^(\\p{Digit}?\\p{Alpha}{2}\\s?\\p{Digit}{6})$";
    private static final String PHONE_NUMBER_PATTERN = "^(\\+)?([-_():\\s]?\\d[-_():\\s]?){12}?$";

    private UserValidator() {
    }

    /**
     * Validate the map of client parameters. Used mainly when adding a new entity to
     * the system. Uses separate methods to test each specific parameter.
     *
     * @param clientParameters the client parameters
     * @return the boolean
     */
    public static boolean validateClientParameters(Map<String, String> clientParameters) {
        boolean isParametersCorrect = true;
        if (!validateEmail(clientParameters.get(ParameterKey.EMAIL))) {
            isParametersCorrect = false;
            clientParameters.put(ParameterKey.EMAIL, EMPTY_VALUE);
        }
        if (!validatePasswords(clientParameters.get(ParameterKey.PASSWORD), clientParameters.get(ParameterKey.CONFIRM_PASSWORD))) {
            isParametersCorrect = false;
            clientParameters.put(ParameterKey.PASSWORD, EMPTY_VALUE);
        }
        if (!validateName(clientParameters.get(ParameterKey.FIRST_NAME))) {
            isParametersCorrect = false;
            clientParameters.put(ParameterKey.FIRST_NAME, EMPTY_VALUE);
        }
        if (!validateName(clientParameters.get(ParameterKey.SECOND_NAME))) {
            isParametersCorrect = false;
            clientParameters.put(ParameterKey.SECOND_NAME, EMPTY_VALUE);
        }
        if (!validateDriverLicense(clientParameters.get(ParameterKey.DRIVER_LICENSE))) {
            isParametersCorrect = false;
            clientParameters.put(ParameterKey.DRIVER_LICENSE, EMPTY_VALUE);
        }
        if (!validatePhoneNumber(clientParameters.get(ParameterKey.PHONE_NUMBER))) {
            isParametersCorrect = false;
            clientParameters.put(ParameterKey.PHONE_NUMBER, EMPTY_VALUE);
        }

        return isParametersCorrect;
    }

    /**
     * Validate email parameter for the existence of the
     * data and if it matches a regular expression.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean validateEmail(String email) {
        boolean isEmailCorrect = false;

        if (email != null && !email.isEmpty()) {
            isEmailCorrect = email.matches(EMAIL_PATTERN);
        }

        return isEmailCorrect;
    }

    /**
     * Validate passwords parameters for the existence of the
     * data, if its matches a regular expression and if it
     * equals each other.
     *
     * @param password        the password
     * @param confirmPassword the confirm password
     * @return the boolean
     */
    public static boolean validatePasswords(String password, String confirmPassword) {
        boolean isPasswordsCorrect = false;

        if (password != null && confirmPassword != null) {
            if (!password.isEmpty() && password.equals(confirmPassword)) {
                isPasswordsCorrect = password.matches(PASSWORD_PATTERN);
            }
        }

        return isPasswordsCorrect;
    }

    /**
     * Validate name parameter for the existence of the
     * data and if it matches a regular expression.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean validateName(String name) {
        boolean isNameCorrect = false;

        if (name != null && !name.isEmpty()) {
            isNameCorrect = name.matches(NAME_PATTERN);
        }

        return isNameCorrect;
    }

    /**
     * Validate driver license parameter for the existence of the
     * data and if it matches a regular expression.
     *
     * @param driverLicense the driver license
     * @return the boolean
     */
    public static boolean validateDriverLicense(String driverLicense) {
        boolean isLicenseCorrect = false;

        if (driverLicense != null && !driverLicense.isEmpty()) {
            isLicenseCorrect = driverLicense.matches(DRIVER_LICENSE_PATTERN);
        }

        return isLicenseCorrect;
    }

    /**
     * Validate phone number parameter if it matches a regular expression.
     * Parameter can be null.
     *
     * @param phoneNumber the phone number
     * @return the boolean
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        boolean isPhoneNumberCorrect = false;

        if (phoneNumber == null) {
            isPhoneNumberCorrect = true;
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            isPhoneNumberCorrect = phoneNumber.matches(PHONE_NUMBER_PATTERN);
        }

        return isPhoneNumberCorrect;
    }

    /**
     * Validate the client status parameter for the existence of the
     * data and the existence of such a type in the
     * corresponding enum.
     *
     * @param statusData the status data
     * @return the boolean
     */
    public static boolean validateClientStatus(String statusData) {
        boolean isStatusCorrect = false;

        if (statusData != null && !statusData.isEmpty()) {
            Client.Status[] statuses = Client.Status.values();
            for (Client.Status status : statuses) {
                if (statusData.toUpperCase().equals(status.name())) {
                    isStatusCorrect = true;
                }
            }
        }

        return isStatusCorrect;
    }
}
