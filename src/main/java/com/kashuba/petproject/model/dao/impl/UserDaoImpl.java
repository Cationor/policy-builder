package com.kashuba.petproject.model.dao.impl;

import com.kashuba.petproject.builder.UserBuilder;
import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.model.dao.UserDao;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.kashuba.petproject.util.ParameterKey.*;

/**
 * The type User dao.
 * {@code UserDao} interface implementation
 *
 * @author Balashevich Gleb
 * @version 1.0
 * @see UserDao
 */
public class UserDaoImpl implements UserDao {
    private static UserDaoImpl userDao;
    private static final String ADD_CLIENT = "INSERT INTO users(email, password, user_role, first_name, " +
            "second_name, driver_license, phone_number, client_status)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT user_id, email, user_role, first_name, second_name, " +
            "driver_license, phone_number, client_status FROM users";
    private static final String FIND_ALL_CLIENTS = FIND_ALL + " WHERE user_role = 1";
    private static final String FIND_CLIENTS_BY_STATUS = FIND_ALL + " WHERE client_status = ?";
    private static final String FIND_BY_EMAIL = FIND_ALL + " WHERE email = ?";
    private static final String FIND_PASSWORD_BY_EMAIL = "SELECT password FROM users WHERE email = ?";
    private static final String FIND_STATUS_BY_EMAIL = "SELECT client_status FROM users WHERE email = ?";
    private static final String FIND_EMAIL = "SELECT email FROM users WHERE email = ?";
    private static final String CHANGE_STATUS = "UPDATE users SET client_status = (?) where email=(?)";
    private static final String EMPTY_VALUE = "";

    private UserDaoImpl() {
    }

    /**
     * Get instance user dao.
     * Returns a class object {@code UserDaoImpl}
     *
     * @return the user dao
     */
    public static UserDaoImpl getInstance() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }

        return userDao;
    }

    @Override
    public boolean add(Map<String, Object> userParameters) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isClientAdded;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_CLIENT)) {
            statement.setString(1, (String) userParameters.get(EMAIL));
            statement.setString(2, (String) userParameters.get(PASSWORD));
            statement.setInt(3, ((User.Role) userParameters.get(ROLE)).ordinal());
            statement.setString(4, (String) userParameters.get(FIRST_NAME));
            statement.setString(5, (String) userParameters.get(SECOND_NAME));
            statement.setString(6, (String) userParameters.get(DRIVER_LICENSE));
            statement.setLong(7, (long) userParameters.get(PHONE_NUMBER));
            statement.setInt(8, ((Client.Status) userParameters.get(CLIENT_STATUS)).ordinal());
            isClientAdded = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to add a client", e);
        }

        return isClientAdded;
    }

    @Override
    public boolean remove(User user) {
        throw new UnsupportedOperationException("Operation Remove not allowed with user");
    }

    @Override
    public boolean update(User user) {
        throw new UnsupportedOperationException("Operation Update not allowed with user");
    }

    @Override
    public Optional<User> findById(long id) {
        throw new UnsupportedOperationException("Operation FindById not allowed with user");
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Operation FindAll not allowed with user");
    }

    @Override
    public List<Client> findAllClients() throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        List<Client> targetClients = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_CLIENTS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                targetClients.add((Client) createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find all clients", e);
        }

        return targetClients;
    }

    @Override
    public boolean updateClientStatus(String email, Client.Status status) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isParameterChanged;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_STATUS)) {
            statement.setInt(1, status.ordinal());
            statement.setString(2, email);
            isParameterChanged = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to update a client status", e);
        }

        return isParameterChanged;
    }

    @Override
    public Client.Status findStatusByEmail(String targetEmail) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Client.Status targetStatus = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_STATUS_BY_EMAIL)) {
            statement.setString(1, targetEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                targetStatus = Client.Status.getClientStatus(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find client status by client email", e);
        }

        return targetStatus;
    }

    @Override
    public Optional<User> findByEmail(String targetEmail) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Optional<User> targetUser;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL)) {
            statement.setString(1, targetEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                targetUser = Optional.of(createUser(resultSet));
            } else {
                targetUser = Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find a client by email", e);
        }

        return targetUser;
    }

    @Override
    public String findPasswordByEmail(String targetEmail) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        String userPassword = EMPTY_VALUE;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL)) {
            statement.setString(1, targetEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userPassword = resultSet.getString(PASSWORD);
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find client password by a client email", e);
        }

        return userPassword;
    }

    @Override
    public List<Client> findClientsByStatus(Client.Status clientStatus) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        List<Client> targetClients = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_CLIENTS_BY_STATUS)) {
            statement.setInt(1, clientStatus.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                targetClients.add((Client) createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error when executing a query to find clients by status", e);
        }

        return targetClients;
    }

    @Override
    public String existEmail(String targetEmail) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        String email = EMPTY_VALUE;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_EMAIL)) {
            statement.setString(1, targetEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                email = resultSet.getString(EMAIL);
            }
        } catch (SQLException e) {
            throw new DaoProjectException("error when executing a query to check the existence of a user", e);
        }

        return email;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        Map<String, Object> userParameters = new HashMap<>();
        userParameters.put(USER_ID, resultSet.getLong(USER_ID));
        userParameters.put(EMAIL, resultSet.getString(EMAIL));
        userParameters.put(ROLE, User.Role.getUserRole(resultSet.getInt(ROLE)));
        userParameters.put(FIRST_NAME, resultSet.getString(FIRST_NAME));
        userParameters.put(SECOND_NAME, resultSet.getString(SECOND_NAME));
        userParameters.put(DRIVER_LICENSE, resultSet.getString(DRIVER_LICENSE));
        userParameters.put(PHONE_NUMBER, resultSet.getLong(PHONE_NUMBER));
        userParameters.put(CLIENT_STATUS, Client.Status.getClientStatus(resultSet.getInt(CLIENT_STATUS)));

        return UserBuilder.buildUser(userParameters);
    }
}
