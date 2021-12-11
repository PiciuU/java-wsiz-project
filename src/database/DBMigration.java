package database;

import java.sql.SQLException;

/** An abstract class for database migration.
 *  This class should only be used in case of database corruption!
 *
 * @author Patryk Strzałka
 */
public abstract class DBMigration extends DBModel {
    /**
     * Init migration or rollback process of the database
     *
     * @param operation A string containing requested operation
     */
    public static void init(String operation) {
        setConnection();
        if (operation == "migrate") {
            createParkingLotTable();
            createParkingSlotTable();
            createParkingSlotReservationTable();
            createVehicleTable();
            createCustomerTable();
        }
        else if (operation == "rollback") {
            dropParkingLotTable();
            dropParkingSlotTable();
            dropParkingSlotReservationTable();
            dropVehicleTable();
            dropCustomerTable();
        }
        else {
            throw new IllegalStateException("The requested operation is not available.");
        }
        closeConnection();
    }

    /**
     * Migrate parking lot table
     *
     * @exception SQLException
     */
    private static void createParkingLotTable() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS parking_lot (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	parking_name varchar(100) NOT NULL,\n"
                    + "	address varchar(100) NOT NULL\n"
                    + ") ");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::createParkingLotTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Migrate parking slot table
     *
     * @exception SQLException
     */
    private static void createParkingSlotTable() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS parking_slot (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	parking_id integer NOT NULL,\n"
                    + "	slot_number integer NOT NULL\n"
                    + ") ");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::createParkingSlotTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Migrate parking slot reservation table
     *
     * @exception SQLException
     */
    private static void createParkingSlotReservationTable() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS parking_slot_reservation (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	vehicle_id integer NOT NULL,\n"
                    + "	parking_slot_id integer NOT NULL,\n"
                    + " reservation_date timestamp DEFAULT(CURRENT_TIMESTAMP)"
                    + ") ");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::createParkingSlotReservationTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Migrate vehicle table
     *
     * @exception SQLException
     */
    private static void createVehicleTable() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS vehicle (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	customer_id integer NOT NULL,\n"
                    + "	producer varchar(100) NOT NULL,\n"
                    + "	model varchar(100) NOT NULL,\n"
                    + " horsepower integer NOT NULL,\n"
                    + "	production_year date NOT NULL,\n"
                    + "	number_plate varchar(10) NOT NULL"
                    + ") ");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::createVehicleTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Migrate customer table
     *
     * @exception SQLException
     */
    private static void createCustomerTable() {
        try {
            getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS customer (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	firstname varchar(100) NOT NULL,\n"
                    + "	surname varchar(100) NOT NULL,\n"
                    + "	contact_number varchar(9) DEFAULT(NULL)\n"
                    + ") ");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::createCustomerTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Rollback parking lot table
     *
     * @exception SQLException
     */
    private static void dropParkingLotTable() {
        try {
            getStatement().executeUpdate("DROP TABLE IF EXISTS parking_lot");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::dropParkingLotTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Rollback parking slot table
     *
     * @exception SQLException
     */
    private static void dropParkingSlotTable() {
        try {
            getStatement().executeUpdate("DROP TABLE IF EXISTS parking_slot");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::dropParkingSlotTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Rollback parking slot reservation table
     *
     * @exception SQLException
     */
    private static void dropParkingSlotReservationTable() {
        try {
            getStatement().executeUpdate("DROP TABLE IF EXISTS parking_slot_reservation");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::dropParkingSlotReservationTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Rollback vehicle table
     *
     * @exception SQLException
     */
    private static void dropVehicleTable() {
        try {
            getStatement().executeUpdate("DROP TABLE IF EXISTS vehicle");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::dropVehicleTable] SQLException: " + e.getMessage());
        }
    }

    /**
     * Rollback customer table
     *
     * @exception SQLException
     */
    private static void dropCustomerTable() {
        try {
            getStatement().executeUpdate("DROP TABLE IF EXISTS customer");
        }
        catch (SQLException e) {
            System.err.println("[DBMigration::dropCustomerTable] SQLException: " + e.getMessage());
        }
    }
}
