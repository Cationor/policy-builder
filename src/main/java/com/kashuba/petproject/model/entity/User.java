package com.kashuba.petproject.model.entity;

import java.util.Arrays;

/**
 * The User.
 * <p>
 * Describes the basic characteristics of a user who interacts directly with the application.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class User extends Entity {

    /**
     * The enum Role.
     * Depending on the role assigned to the user, the level of access
     * to service elements is determined.
     */
    public enum Role {
        GUEST,
        CLIENT,
        ADMIN;

        /**
         * Gets userRole by index.
         *
         * @param index the index
         * @return the user role
         */
        public static Role getUserRole(int index) {
            return Arrays.stream(Role.values()).filter(r -> r.ordinal() == index).findFirst().get();
        }
    }

    private long userId;
    private String email;
    private Role role;

    /**
     * Gets userId.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets userId.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return userId == user.userId
                && (email != null && email.equals(user.email))
                && role == user.role;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 37 * result + Long.hashCode(userId);
        result += 37 * result + (email == null ? 0 : email.hashCode());
        result += 37 * result + (role == null ? 0 : role.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return String.format("Id %d: email %s, role %s",
                userId, email, role != null ? role.name() : "null");
    }
}
