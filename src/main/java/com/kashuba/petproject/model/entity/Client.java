package com.kashuba.petproject.model.entity;

import java.util.Arrays;

/**
 * The type Client.
 * <p>
 * Extends the user class to describe clients using the service. Supplemented
 * with the necessary information for the implementation of actions to order cars,
 * access to various application options.
 *
 * @author Balashevich Gleb
 * @version 1.0
 * @see User
 */
public class Client extends User {

    /**
     * The enum Status.
     * Used to differentiate client access rights.
     */
    public enum Status {
        PENDING,
        ACTIVE,
        BLOCKED;

        /**
         * Gets client status by index.
         *
         * @param index the index
         * @return the client status
         */
        public static Status getClientStatus(int index) {
            return Arrays.stream(Status.values()).filter(s -> s.ordinal() == index).findFirst().get();
        }
    }

    private String firstName;
    private String secondName;
    private String driverLicense;
    private long phoneNumber;
    private Status status;

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets second name.
     *
     * @return the second name
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * Sets second name.
     *
     * @param secondName the second name
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Gets driverLicense.
     *
     * @return the driver license
     */
    public String getDriverLicense() {
        return driverLicense;
    }

    /**
     * Sets driverLicense.
     *
     * @param driverLicense the driver license
     */
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    /**
     * Gets phoneNumber.
     *
     * @return the phone number
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phoneNumber.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        com.kashuba.petproject.model.entity.Client client = (com.kashuba.petproject.model.entity.Client) obj;

        return super.equals(obj)
                && (firstName != null && firstName.equals(client.firstName))
                && (secondName != null && secondName.equals(client.secondName))
                && (driverLicense != null && driverLicense.equals(client.driverLicense))
                && phoneNumber == client.phoneNumber
                && status == client.status;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += super.hashCode();
        result += 37 * result + (firstName == null ? 0 : firstName.hashCode());
        result += 37 * result + (secondName == null ? 0 : secondName.hashCode());
        result += 37 * result + (driverLicense == null ? 0 : driverLicense.hashCode());
        result += 37 * result + phoneNumber;
        result += 37 * result + (status == null ? 0 : status.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return String.format("Client %s, first name %s, second name %s, driver license %s, " +
                        "phone number %d, status %s", super.toString(), firstName, secondName, driverLicense,
                phoneNumber, status != null ? status.name() : "null");
    }
}
