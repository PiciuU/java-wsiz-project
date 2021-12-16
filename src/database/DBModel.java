package database;

import java.sql.*;
import java.util.ArrayList;

/** An abstract class for Singleton Objects that are to be used as database models.
 * @author Patryk Strzałka
 */
public abstract class DBModel {
    /** Absolute path to SQLite database */
    private static final String DB_URL = "jdbc:sqlite:src/database/db.sqlite";

    /** Last known value of inserted record in database */
    private static int lastInsertedId = 0;

    /** Connection with specific database */
    private static Connection connection;

    /** Object for executing a static SQL statement */
    private static Statement db;

    /**
     * Return object of a database connection
     *
     * @return Connection
     */
    protected static Connection getConnection() {
        return connection;
    }

    /**
     * Return object of a SQL statement
     *
     * @return Statement
     */
    protected static Statement getStatement() {
        return db;
    }

    /**
     * Return last known inserted ID
     *
     * @return int
     */
    protected static int getLastInsertedId() {
        return lastInsertedId;
    }

    /**
     * Establish a database connection
     *
     * @exception SQLException
     */
    public static void setConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            db = connection.createStatement();
        }
        catch (SQLException e) {
            System.err.println("[DBModel] Error establishing a database connection: " + e.getMessage());
        }
    }

    /**
     * Close connection to database
     *
     * @exception SQLException e
     */
    protected static void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.err.println("[DBModel] Unable to close a database connection: "+ e.getMessage());
        }
    }

    /**
     * Return query result from database
     *
     * @param query A string containing SQL SELECT statement
     * @exception SQLException
     * @return ResultSet
     */
    protected static ResultSet get(String query) {
        try {
            return db.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println("[DBModel::get] SQLException: " + e.getMessage());
            return null;
        }
    }

    /**
     * Insert new record in a table
     *
     * @param stmt A PreparedStatement for SQL INSERT INTO
     * @exception SQLException
     */
     protected static void insert(PreparedStatement stmt) {
        try {
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) lastInsertedId = rs.getInt(1);
        }
        catch(SQLException e) {
            System.out.println("[DBModel::insert] SQLException: " + e.getMessage());
        }
    }

    /**
     * Update existing record in a table
     *
     * @param stmt A PreparedStatement for SQL UPDATE
     * @exception SQLException
     */
    protected static void update(PreparedStatement stmt) {
         try {
             stmt.executeUpdate();
         }
         catch(SQLException e) {
             System.out.println("[DBModel::update] SQLException: " + e.getMessage());
         }
    }

    /**
     * Delete existing record in a table
     *
     * @param stmt A PreparedStatement for SQL DELETE
     * @exception SQLException
     */
    protected static void delete(PreparedStatement stmt) {
        try {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("[DBModel::delete] SQLException: " + e.getMessage());
        }
    }

    /** Mandatory methods to be overwritten */

    public ArrayList<?> getAll() {
        throw new RuntimeException("Method not overwritten");
    };
    public Object getOne() { throw new RuntimeException("Method not overwritten"); };
    public void insertRecord() { throw new RuntimeException("Method not overwritten"); };
    public void updateRecord() { throw new RuntimeException("Method not overwritten"); };

}
