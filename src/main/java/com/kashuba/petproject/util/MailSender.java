package com.kashuba.petproject.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Mail sender.
 * <p>
 * Sends letters from the e-mail of the booking service to the e-mail of users.
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONFIG_FILEPATH = "config/mail.properties";
    private static final String USER_NAME = "mail.user.name";
    private static final String USER_PASSWORD = "mail.user.password";

    private MailSender() {
    }

    /**
     * Prepares a connection to an email client, generates a
     * message and sends it to the user to the specified email address.
     *
     * @param recipientAddress the recipient address
     * @param subject          the subject
     * @param text             the text
     */
    public static void sendMail(String recipientAddress, String subject, String text) {
        try {
            Properties properties = getProperties();
            Session session = getSession(properties);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get(USER_NAME)));
            message.setSubject(subject);
            message.setText(text);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Error occurred when email sending to " + recipientAddress, e);
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(CONFIG_FILEPATH);

        try {
            properties.load(stream);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error occurred when email sending, problem with mail api properties data", e);
        }

        return properties;
    }

    private static Session getSession(Properties properties) {
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String passwordGlobal = (String) properties.get(USER_PASSWORD);
                String passwordValue = System.getenv(passwordGlobal);
                return new PasswordAuthentication((String) properties.get(USER_NAME),
                        passwordValue);
            }
        });

        return session;
    }
}
