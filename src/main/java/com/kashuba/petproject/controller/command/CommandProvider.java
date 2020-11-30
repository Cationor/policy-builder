package com.kashuba.petproject.controller.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Command provider.
 * <p>
 * The main task of this class is to provide command objects that implement
 * the {@code ActionCommand} interface, expected by the controller to perform
 * operations with the requests of the application clients.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class CommandProvider {
    private static final Logger logger = LogManager.getLogger();

    private CommandProvider() {
    }

    /**
     * Checks the name of the command passed as a parameter for null and for a empty value
     * and tries to return an object that implements the {@code actionCommand} interface
     * from the enum {@code CommandType}. If the command was not found, the method returns
     * an empty command
     *
     * @param commandName the command name
     * @return the action command
     */
    public static ActionCommand defineCommand(String commandName) {
        ActionCommand currentCommand = CommandType.EMPTY.getCommand();

        if (commandName != null && !commandName.isEmpty()) {
            try {
                currentCommand = CommandType.valueOf(commandName.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e) {
                logger.log(Level.ERROR, "The command is not defined " + commandName);
            }
        }

        return currentCommand;
    }
}
