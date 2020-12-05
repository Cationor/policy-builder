package com.kashuba.petproject.model.service.impl;

import com.kashuba.petproject.exception.DaoProjectException;
import com.kashuba.petproject.exception.ServiceProjectException;
import com.kashuba.petproject.model.dao.impl.UserDaoImpl;
import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.User;
import com.kashuba.petproject.model.service.UserService;
import com.kashuba.petproject.util.PasswordEncryption;
import com.kashuba.petproject.validator.UserValidator;
import com.kashuba.petproject.util.ParameterKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The User service.
 * <p>
 * Implements an interface UserService for processing user-related data
 *
 * @author Kiryl Kashuba
 * @version 1.0
 * @see UserService
 */
public class UserServiceImpl implements UserService {
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final String EMPTY_VALUE = "";
    private static final String PUNCTUATION = "[\\p{Punct}\\p{Space}]";

    @Override
    public boolean add(Map<String, String> clientParameters) throws ServiceProjectException {
        boolean isClientAdded = false;

        if (UserValidator.validateClientParameters(clientParameters)) {
            try {
                Map<String, Object> preparedClientParameters = new HashMap<>();
                String encryptedPassword = PasswordEncryption.encryptPassword(clientParameters.get(ParameterKey.PASSWORD));
                if (!encryptedPassword.isEmpty()) {
                    String normalizedPhone = clientParameters.get(ParameterKey.PHONE_NUMBER).replaceAll(PUNCTUATION, EMPTY_VALUE);
                    long phoneNumber = Long.parseLong(normalizedPhone);
                    preparedClientParameters.put(ParameterKey.EMAIL, clientParameters.get(ParameterKey.EMAIL).toLowerCase());
                    preparedClientParameters.put(ParameterKey.PASSWORD, encryptedPassword);
                    preparedClientParameters.put(ParameterKey.ROLE, User.Role.CLIENT);
                    preparedClientParameters.put(ParameterKey.FIRST_NAME, clientParameters.get(ParameterKey.FIRST_NAME));
                    preparedClientParameters.put(ParameterKey.SECOND_NAME, clientParameters.get(ParameterKey.SECOND_NAME));
                    preparedClientParameters.put(ParameterKey.DRIVER_LICENSE, clientParameters.get(ParameterKey.DRIVER_LICENSE));
                    preparedClientParameters.put(ParameterKey.PHONE_NUMBER, phoneNumber);
                    preparedClientParameters.put(ParameterKey.CLIENT_STATUS, Client.Status.PENDING);
                    isClientAdded = userDao.add(preparedClientParameters);
                }
            } catch (DaoProjectException e) {
                throw new ServiceProjectException(e);
            }
        }

        return isClientAdded;
    }

    @Override
    public boolean activateClient(String email) throws ServiceProjectException {
        boolean isParameterUpdated = false;

        try {
            Client.Status currentStatus = userDao.findStatusByEmail(email);
            if (currentStatus != null) {
                if (currentStatus == Client.Status.PENDING) {
                    isParameterUpdated = userDao.updateClientStatus(email, Client.Status.ACTIVE);
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isParameterUpdated;
    }

    @Override
    public boolean updateClientStatus(Client updatingClient, String statusData) throws ServiceProjectException {
        boolean isStatusUpdated = false;

        try {
            if (UserValidator.validateClientStatus(statusData)) {
                Client.Status status = Client.Status.valueOf(statusData.toUpperCase());
                isStatusUpdated = userDao.updateClientStatus(updatingClient.getEmail(), status);
                if (isStatusUpdated) {
                    updatingClient.setStatus(status);
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isStatusUpdated;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceProjectException {
        Optional<User> targetUser;

        try {
            if (UserValidator.validateEmail(email)) {
                targetUser = userDao.findByEmail(email);
            } else {
                targetUser = Optional.empty();
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetUser;
    }

    @Override
    public List<Client> findClientsByStatus(String clientStatusData) throws ServiceProjectException {
        List<Client> targetClients;

        try {
            if (UserValidator.validateClientStatus(clientStatusData)) {
                targetClients = userDao.findClientsByStatus(Client.Status.valueOf(clientStatusData.toUpperCase()));
            } else {
                targetClients = userDao.findAllClients();
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetClients;
    }

    @Override
    public boolean authorizeUser(String email, String password) throws ServiceProjectException {
        boolean isApproved = false;

        try {
            if (UserValidator.validateEmail(email.toLowerCase())) {
                String userPassword = userDao.findPasswordByEmail(email);
                if (userPassword != null && !userPassword.isEmpty()) {
                    String verifyingPassword = PasswordEncryption.encryptPassword(password);
                    if (!verifyingPassword.isEmpty() && userPassword.equals(verifyingPassword)) {
                        isApproved = true;
                    }
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isApproved;
    }

    @Override
    public boolean existUser(String email) throws ServiceProjectException {
        boolean isExist = false;

        try {
            if (UserValidator.validateEmail(email)) {
                isExist = !userDao.existEmail(email).isEmpty();
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isExist;
    }
}
