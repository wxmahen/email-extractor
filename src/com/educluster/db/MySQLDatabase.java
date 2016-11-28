package com.educluster.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLDatabase {

    private static final String driver = "com.mysql.jdbc.Driver"; // MySQL Driver
    private static String database = "emails"; // MySQL Database name
    private static String url; // MySQL Database connection URL
    private static String user = "root"; // MySQL server username
    private static String password = "1234"; // MySQL server password of the specific user
    private static String hostname = "localhost"; // Host of the MySQL server
    private static String port = "3307"; // Port of the MySQL service
    private static MySQLDatabase mysql; // Singleton Object of this class
    private static String BIN_PATH; // MySQL bin path
    private static String BACKUP_PATH; // MySQL backup path
    private static final String encoding = "?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8"; // Custom encodings for connection URL

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String aDatabase) {
        database = aDatabase;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String aUser) {
        user = aUser;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    public static String getHostname() {
        return hostname;
    }

    public static void setHostname(String aHostname) {
        hostname = aHostname;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String aPort) {
        port = aPort;
    }

    public static String getBIN_PATH() {
        return BIN_PATH;
    }

    public static void setBIN_PATH(String aBIN_PATH) {
        BIN_PATH = aBIN_PATH;
    }

    public static String getBACKUP_PATH() {
        return BACKUP_PATH;
    }

    public static void setBACKUP_PATH(String aBACKUP_PATH) {
        BACKUP_PATH = aBACKUP_PATH;
    }

    private MySQLDatabase(String hostname, String port, String user, String password, String database) {
        this.hostname = hostname;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    // get the current MySQL database
    public static synchronized MySQLDatabase getDatabase(String hostname, String port, String user, String password, String database) throws Exception {
        if (mysql == null) {
            mysql = new MySQLDatabase(hostname, port, user, password, database);
        }
        return mysql;
    }

    // get the current MySQL database
    public static synchronized MySQLDatabase getDb() throws Exception {
        if (mysql == null) {
            mysql = new MySQLDatabase(hostname, port, user, password, database);
        }
        return mysql;
    }

    // get the current MySQL database connection
    public static java.sql.Connection connection() throws Exception {
        if (url == null) {
            url = "jdbc:mysql://" + getHostname() + ":" + getPort() + "/" + getDatabase() + encoding;
        }
        Class.forName(driver);
        Connection connection = (java.sql.Connection) DriverManager.getConnection(url, getUser(), getPassword());
        connection.setAutoCommit(false);
        return connection;
    }

    // getting data from the database without changing them
    public static ResultSet getData(java.sql.Connection connection, Statement statement, String sqlQuery) throws Exception {
        connection.setReadOnly(true);
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        return resultSet;
    }

    // changing the data inside the database
    public static int setData(java.sql.Connection connection, Statement statement, String sqlQuery) throws Exception {
        connection.setReadOnly(false);
        return statement.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS);
    }
}
