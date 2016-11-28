package com.educluster.ctrl;

import com.educluster.db.MySQLDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    private void saveQuery(String query) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = MySQLDatabase.connection();
            statement = connection.createStatement();
            MySQLDatabase.setData(connection, statement, query);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e1) {
            }
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    public void saveEmail(String email) {
        String query = "INSERT INTO email VALUES('" + email + "')";
        saveQuery(query);
    }

    public void saveEmails(List<String> emails) {
        emails.forEach((email) -> {
            saveEmail(email);
        });
    }

    public List<String> getEmails() {
        Connection connection = null;
        Statement statement = null;
        List<String> emails = new ArrayList<>();
        try {
            connection = MySQLDatabase.connection();
            statement = connection.createStatement();
            ResultSet rs = MySQLDatabase.getData(connection, statement, "SELECT * FROM email");
            while (rs.next()) {
                emails.add(rs.getString(1));
            }
            return emails;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
            }
        }
    }
}
