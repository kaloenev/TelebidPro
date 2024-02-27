package config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer implements ServletContextListener {
    // JDBC URL, username, and password of the MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // SQL commands to create database with the name mydatabase and users table
    private static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS mydatabase";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, " +
            "first_name VARCHAR(255), last_name VARCHAR(255), username VARCHAR(255), password VARCHAR(255), " +
            "email VARCHAR(255), isEnabled BOOLEAN, verification_link VARCHAR(255))";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try
            // Establish connection to the MySQL server
            (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()){

            // Create database

            statement.executeUpdate(CREATE_DATABASE);
            System.out.println("Database created successfully");

            // Select the created database
            statement.execute("USE mydatabase");

            // Create the table
            statement.executeUpdate(CREATE_TABLE);
            System.out.println("Table created successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
