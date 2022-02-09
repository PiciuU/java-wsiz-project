package database.controllers;

import java.sql.*;

import java.util.ArrayList;

/**
 * An abstract class for Singleton Objects that are to be used as database models.
 *
 */
public abstract class Controller {
    /** Absolute path to SQLite database */
    private static final String DB_URL = "jdbc:sqlite:src/database/db.sqlite";

    /** Last known value of inserted record in database */
    private static int lastInsertedId = 0;

    /** Connection with specific database */
    private static Connection connection;

    /** Object for executing a static SQL statement */
    private static Statement db;

    /**
     * Get current database connection
     *
     * @return An object of database connection
     */
    protected static Connection getConnection() {
        return connection;
    }

    /**
     * Get Sqlite statement
     *
     * @return An object of SQL statement
     */
    protected static Statement getStatement() {
        return db;
    }

    /**
     * Get last inserted ID
     *
     * @return An int representing last known ID that was inserted into database
     */
    protected static int getLastInsertedId() {
        return lastInsertedId;
    }

    /**
     * Establish a database connection
     *
     * @exception SQLException if database connection couldn't be established
     */
    public static void setConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            db = connection.createStatement();
        }
        catch (SQLException e) {
            System.err.println("[Controller] Error establishing a database connection: " + e.getMessage());
        }
    }

    /**
     * Close connection to database
     *
     * @exception SQLException if database connection couldn't be closed
     */
    protected static void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.err.println("[Controller] Unable to close a database connection: "+ e.getMessage());
        }
    }

    /**
     * Fetch data from database
     *
     * @param query A string containing SQL SELECT statement
     * @exception SQLException if statement couldn't be resolved
     * @return An ResultSet built from statement result
     */
    protected static ResultSet get(String query) {
        try {
            return db.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println("[Controller::get] SQLException: " + e.getMessage());
            return null;
        }
    }

    /**
     * Execute insert query in database
     *
     * @param stmt A PreparedStatement for insert statement
     * @exception SQLException if statement couldn't be resolved
     */
     protected static void insert(PreparedStatement stmt) {
        try {
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) lastInsertedId = rs.getInt(1);
        }
        catch(SQLException e) {
            System.out.println("[Controller::insert] SQLException: " + e.getMessage());
        }
    }

    /**
     * Execute update query in database
     *
     * @param stmt A PreparedStatement for update statement
     * @exception SQLException if statement couldn't be resolved
     */
    protected static void update(PreparedStatement stmt) {
         try {
             stmt.executeUpdate();
         }
         catch(SQLException e) {
             System.out.println("[Controller::update] SQLException: " + e.getMessage());
         }
    }

    /**
     * Execute delete query in database
     *
     * @param stmt A PreparedStatement for delete statement
     * @exception SQLException if statement couldn't be resolved
     */
    protected static void delete(PreparedStatement stmt) {
        try {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("[Controller::delete] SQLException: " + e.getMessage());
        }
    }

    /** Mandatory methods to be overwritten */

    public ArrayList<?> getAll() {
        throw new RuntimeException("Method not overwritten");
    };
    public Object getOne() { throw new RuntimeException("Method not overwritten"); };
    public void insertRecord() { throw new RuntimeException("Method not overwritten"); };
    public void updateRecord() { throw new RuntimeException("Method not overwritten"); };
    public void deleteRecord() { throw new RuntimeException("Method not overwritten"); };
    public ArrayList<?> getCustom() { throw new RuntimeException("Method not overwritten"); };
    public Object getCustomOne() { throw new RuntimeException("Method not overwritten"); };


}
