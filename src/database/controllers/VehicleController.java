package database.controllers;

import models.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class VehicleController extends Controller {

    /** Define Singleton */
    private static VehicleController instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException if instance already exists
     */
    private VehicleController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return VehicleController instance
     */
    public static VehicleController getInstance() {
        if (instance == null) instance = new VehicleController();
        return instance;
    }

    /**
     * Fetch all entries from the vehicle table
     *
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<Vehicle>
     */
    public ArrayList<Vehicle> getAll() {
        setConnection();
        ResultSet rs = get("SELECT * FROM vehicle");

        ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
        try {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt("id"), rs.getInt("customer_id"), rs.getString("producer"), rs.getString("model"), rs.getInt("horsepower"), rs.getString("production_year"), rs.getString("number_plate"));
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
     * Fetch specific entry from the vehicle table
     *
     * @param id vehicle ID
     * @exception Exception if statement couldn't be resolved
     * @return Vehicle
     */
    public Vehicle getOne(int id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM vehicle WHERE id = " + id);

        Vehicle vehicle = new Vehicle();

        try {
            vehicle.setValues(rs.getInt("id"), rs.getInt("customer_id"), rs.getString("producer"), rs.getString("model"), rs.getInt("horsepower"), rs.getString("production_year"), rs.getString("number_plate"));
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
     * Insert new record into the vehicle table
     *
     * @param customer_id customer ID
     * @param producer vehicle producer
     * @param model vehicle model
     * @param horsepower vehicle horsepower
     * @param production_year vehicle production year
     * @param number_plate vehicle number plates
     * @exception SQLException if statement couldn't be resolved
     */
    public void insertRecord(int customer_id, String producer, String model, int horsepower, String production_year, String number_plate) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO vehicle(customer_id, producer, model, horsepower, production_year, number_plate) VALUES(?, ?, ?, ?, ?, ?)");
            prepareStatement.setInt(1, customer_id);
            prepareStatement.setString(2, producer);
            prepareStatement.setString(3, model);
            prepareStatement.setInt(4, horsepower);
            prepareStatement.setString(5, production_year);
            prepareStatement.setString(6, number_plate);
            insert(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[VehicleController::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in the vehicle table
     *
     * @param id vehicle ID
     * @param customer_id customer ID
     * @param producer vehicle producer
     * @param model vehicle model
     * @param horsepower vehicle horsepower
     * @param production_year vehicle production year
     * @param number_plate vehicle number plates
     * @exception SQLException if statement couldn't be resolved
     */
    public void updateRecord(int id, int customer_id, String producer, String model, int horsepower, String production_year, String number_plate) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE vehicle SET customer_id = ?, producer = ?, model = ?, horsepower = ?, production_year = ?, number_plate = ? WHERE id = ?");
            prepareStatement.setInt(1, customer_id);
            prepareStatement.setString(2, producer);
            prepareStatement.setString(3, model);
            prepareStatement.setInt(4, horsepower);
            prepareStatement.setString(5, production_year);
            prepareStatement.setString(6, number_plate);
            prepareStatement.setInt(7, id);

            update(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[VehicleController::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Delete existing record in the customer table
     *
     * @param id vehicle ID
     * @exception SQLException if statement couldn't be resolved
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM vehicle WHERE id = ?");
            prepareStatement.setInt(1, id);

            delete(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[VehicleController::delete] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Fetch custom entries from the vehicle table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<Vehicle>
     */
    public ArrayList<Vehicle> getCustom(String query) {
        setConnection();
        ResultSet rs = get(query);

        ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
        try {
            while (rs.next()) {
                String custom_field;
                try { custom_field = rs.getString("custom_field"); }
                catch (Exception e) { custom_field = ""; }

                Vehicle vehicle = new Vehicle(rs.getInt("id"), rs.getInt("customer_id"), rs.getString("producer"), rs.getString("model"), rs.getInt("horsepower"), rs.getString("production_year"), rs.getString("number_plate"), custom_field);
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
     * Fetch custom entry from the vehicle table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return Vehicle
     */
    public Vehicle getCustomOne(String query) {
        setConnection();
        ResultSet rs = get(query);

        Vehicle vehicle = new Vehicle();

        try {
            String custom_field;
            try { custom_field = rs.getString("custom_field"); }
            catch (Exception e) { custom_field = ""; }

            vehicle.setValues(rs.getInt("id"), rs.getInt("customer_id"), rs.getString("producer"), rs.getString("model"), rs.getInt("horsepower"), rs.getString("production_year"), rs.getString("number_plate"), custom_field);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return vehicle;
    }

}
