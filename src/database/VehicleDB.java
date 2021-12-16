package database;

import modules.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

public class VehicleDB extends DBModel {

    /** Define Singleton */
    private static VehicleDB instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException
     */
    private VehicleDB() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingLotDB
     */
    public static VehicleDB getInstance() {
        if (instance == null) instance = new VehicleDB();
        return instance;
    }

    /**
     * Return all objects of a vehicle table
     *
     * @exception Exception
     * @return ArrayList<Vehicle>
     */
    public ArrayList<Vehicle> getAll() {
        setConnection();
        ResultSet rs = get("SELECT * FROM vehicle");

        ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
        try {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt("id"), rs.getInt("customer_id"), rs.getString("producer"), rs.getString("model"), rs.getInt("horsepower"), rs.getDate("production_year"), rs.getString("number_plate"));
                vehicleList.add(vehicle);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return vehicleList;
    }

    /**
     * Return specific object of a vehicle table
     *
     * @param id an id of the parking slot reservation
     * @exception Exception
     * @return Vehicle
     */
    public Vehicle getOne(int id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM vehicle WHERE id = " + id);

        Vehicle vehicle = new Vehicle();

        try {
            vehicle.setValues(rs.getInt("id"), rs.getInt("customer_id"), rs.getString("producer"), rs.getString("model"), rs.getInt("horsepower"), rs.getDate("production_year"), rs.getString("number_plate"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return vehicle;
    }

    /**
     * Insert new record into a vehicle table
     *
     * @param customer_id id of the customer
     * @param producer name of the producer
     * @param model name of the model
     * @param horsepower car horsepower
     * @param production_year date of production
     * @param number_plate car number plate
     * @exception Exception
     */
    public void insertRecord(int customer_id, String producer, String model, int horsepower, Date production_year, String number_plate) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO vehicle(customer_id, producer, model, horsepower, production_year, number_plate) VALUES(?, ?, ?, ?, ?, ?)");
            prepareStatement.setInt(1, customer_id);
            prepareStatement.setString(2, producer);
            prepareStatement.setString(3, model);
            prepareStatement.setInt(4, horsepower);
            prepareStatement.setDate(5, production_year);
            prepareStatement.setString(6, number_plate);
            insert(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingDB::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in a vehicle table
     *
     * @param id id of the vehicle
     * @param customer_id id of the customer
     * @param producer name of the producer
     * @param model name of the model
     * @param horsepower car horsepower
     * @param production_year date of production
     * @param number_plate car number plate
     * @exception Exception
     */
    public void updateRecord(int id, int customer_id, String producer, String model, int horsepower, Date production_year, String number_plate) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE parking_slot_reservation SET customer_id = ?, producer = ?, model = ?, horsepower = ?, production_year = ?, number_plate = ? WHERE id = ?");
            prepareStatement.setInt(1, customer_id);
            prepareStatement.setString(2, producer);
            prepareStatement.setString(3, model);
            prepareStatement.setInt(4, horsepower);
            prepareStatement.setDate(5, production_year);
            prepareStatement.setString(6, number_plate);
            prepareStatement.setInt(7, id);

            update(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingDB::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

}
