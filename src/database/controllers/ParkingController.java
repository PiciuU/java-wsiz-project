package database.controllers;

import models.Parking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class ParkingController extends Controller {

    /** Define Singleton */
    private static ParkingController instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException if instance already exists
     */
    private ParkingController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingController instance
     */
    public static ParkingController getInstance() {
        if (instance == null) instance = new ParkingController();
        return instance;
    }

    /**
     * Fetch all entries from the parking table
     *
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<Parking>
     */
    public ArrayList<Parking> getAll() {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking");

        ArrayList<Parking> parkingList = new ArrayList<Parking>();
        try {
            while (rs.next()) {
                Parking parking = new Parking(rs.getInt("id"), rs.getString("parking_name"), rs.getString("address"));
                parkingList.add(parking);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingList;
    }

    /**
     * Fetch specific entry from the parking table
     *
     * @param id parking ID
     * @exception Exception if statement couldn't be resolved
     * @return Parking
     */
    public Parking getOne(int id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking WHERE id = " + id);

        Parking parking = new Parking();

        try {
            parking.setValues(rs.getInt("id"), rs.getString("parking_name"), rs.getString("address"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parking;
    }

    /**
     * Insert new record into the parking table
     *
     * @param parkingName parking name
     * @param address parking address
     * @exception SQLException if statement couldn't be resolved
     */
    public void insertRecord(String parkingName, String address) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO parking(parking_name, address) VALUES(?, ?)");
            prepareStatement.setString(1, parkingName);
            prepareStatement.setString(2, address);
            insert(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingController::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in the parking table
     *
     * @param id parking ID
     * @param parkingName parking name
     * @param address parking address
     * @exception SQLException if statement couldn't be resolved
     */
    public void updateRecord(int id, String parkingName, String address) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE parking SET parking_name = ?, address = ? WHERE id = ?");
            prepareStatement.setString(1, parkingName);
            prepareStatement.setString(2, address);
            prepareStatement.setInt(3, id);

            update(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingController::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Delete existing record in the parking table
     *
     * @param id parking ID
     * @exception SQLException if statement couldn't be resolved
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM parking WHERE id = ?");
            prepareStatement.setInt(1, id);

            delete(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingController::delete] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Fetch custom entries from the parking table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<Parking>
     */
    public ArrayList<Parking> getCustom(String query) {
        setConnection();
        ResultSet rs = get(query);

        ArrayList<Parking> parkingList = new ArrayList<Parking>();
        try {
            while (rs.next()) {
                Parking parking = new Parking(rs.getInt("id"), rs.getString("parking_name"), rs.getString("address"));
                parkingList.add(parking);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingList;
    }

    /**
     * Fetch custom entry from the parking table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return Parking
     */
    public Parking getCustomOne(String query) {
        setConnection();
        ResultSet rs = get(query);

        Parking parking = new Parking();

        try {
            parking.setValues(rs.getInt("id"), rs.getString("parking_name"), rs.getString("address"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parking;
    }

}
