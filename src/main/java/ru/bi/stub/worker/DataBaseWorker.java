package ru.bi.stub.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bi.stub.config.DbConfigProperties;
import ru.bi.stub.exception.UserNotFoundException;
import ru.bi.stub.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Service
@Slf4j
public class DataBaseWorker {
    private final DbConfigProperties dbConfigProperties;
    private final DateTimeFormatter DATE_TIME_FORMAT;

    @Autowired
    public DataBaseWorker(DbConfigProperties dbConfigProperties) {
        this.dbConfigProperties = dbConfigProperties;
        DATE_TIME_FORMAT = new DateTimeFormatterBuilder().appendPattern(dbConfigProperties.getDateTimePattern()).toFormatter();
    }

    public User selectUser(String login) throws UserNotFoundException {
        String SELECT_USER_EMAILS_BY_LOGIN = "SELECT * FROM user_emails WHERE login='" + login + "'";
        String SELECT_USER_PASSWORDS_BY_LOGIN = "SELECT * FROM user_passwords WHERE login='" + login + "'";
        Connection conn = null;
        Statement stmtEmails = null;
        Statement stmtPasswords = null;
        try {
            conn = DriverManager.getConnection(dbConfigProperties.getUrl(), dbConfigProperties.getUsername(), dbConfigProperties.getPassword());
            stmtEmails = conn.createStatement();
            stmtPasswords = conn.createStatement();
            ResultSet resultUserEmails = stmtEmails.executeQuery(SELECT_USER_EMAILS_BY_LOGIN);
            ResultSet resultUserPasswords = stmtPasswords.executeQuery(SELECT_USER_PASSWORDS_BY_LOGIN);

            if (resultUserEmails.next() && resultUserPasswords.next()) {
                log.info("User with login={} found.", login);
                return User.builder()
                        .login(login)
                        .email(resultUserEmails.getString(2))
                        .password(resultUserPasswords.getString(2))
                        .dateTime(LocalDateTime.parse(resultUserPasswords.getString(3), DATE_TIME_FORMAT))
                        .build();
            } else {
                log.info("User with login={} not found.", login);
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            log.error("ERROR: Failed select user. Message: {}", e.getMessage());
        } finally {
            try {
                if (stmtEmails != null) stmtEmails.close();
                if (stmtPasswords != null) stmtPasswords.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                log.error("ERROR: Failed closed statement or connection. Message: {}", e.getMessage());
            }
        }
        return null;
    }

    public boolean insertUser(User user) {
        String insertUserEmails = "INSERT INTO user_emails (login, email) VALUES (?, ?)";
        String insertUserPasswords = "INSERT INTO user_passwords (login, password, date_time) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbConfigProperties.getUrl(), dbConfigProperties.getUsername(), dbConfigProperties.getPassword());
             PreparedStatement stmt_emails = conn.prepareStatement(insertUserEmails);
             PreparedStatement stmt_passwords = conn.prepareStatement(insertUserPasswords)) {

            conn.setAutoCommit(false);

            stmt_emails.setString(1, user.getLogin());
            stmt_emails.setString(2, user.getEmail());

            stmt_passwords.setString(1, user.getLogin());
            stmt_passwords.setString(2, user.getPassword());
            stmt_passwords.setTimestamp(3, Timestamp.valueOf(user.getDateTime()));
            int countUpdatedRows = stmt_emails.executeUpdate();
            countUpdatedRows += stmt_passwords.executeUpdate();
            if (countUpdatedRows == 2) {
                conn.commit();
                log.info("insertUser(User): Added 1 user.");
            } else {
                conn.rollback();
                throw new SQLException("The user wasn't added.");
            }
            return true;
        } catch (SQLException e) {
            log.error("insertUser(User): ERROR: Failed insert User. Message: {}", e.getMessage());
        }
        return false;
    }
}
