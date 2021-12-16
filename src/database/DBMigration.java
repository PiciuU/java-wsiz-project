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
                    + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	parking_name TEXT NOT NULL,\n"
                    + "	address TEXT NOT NULL,\n"
                    + " UNIQUE(parking_name, address)\n"
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
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "parking_id INTEGER NOT NULL,\n"
                    + "slot_number INTEGER NOT NULL,\n"
                    + "UNIQUE(parking_id, slot_number)\n"
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
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "vehicle_id INTEGER NOT NULL UNIQUE,\n"
                    + "parking_slot_id INTEGER NOT NULL UNIQUE,\n"
                    + "reservation_date TEXT DEFAULT(datetime('now', 'localtime'))\n"
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
                    + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	customer_id INTEGER NOT NULL,\n"
                    + "	producer TEXT NOT NULL,\n"
                    + "	model TEXT NOT NULL,\n"
                    + " horsepower INTEGER NOT NULL,\n"
                    + "	production_year TEXT NOT NULL,\n"
                    + "	number_plate TEXT NOT NULL"
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
                    + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	firstname TEXT NOT NULL,\n"
                    + "	surname TEXT NOT NULL,\n"
                    + "	contact_number TEXT DEFAULT(NULL)\n"
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
