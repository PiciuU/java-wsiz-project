package database.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import models.Parking;
import models.ParkingSlot;

public class ParkingController extends Controller {

    /** Define Singleton */
    private static ParkingController instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException
     */
    private ParkingController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingController
     */
    public static ParkingController getInstance() {
        if (instance == null) instance = new ParkingController();
        return instance;
    }

    /**
     * Return all objects of a parking table
     *
     * @exception Exception
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
     * Return specific object of a parking table
     *
     * @param id an id of the parking lot
     * @exception Exception
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
     * Insert new record into a parking table
     *
     * @param parkingName name of the parking
     * @param address address of the parking
     * @exception Exception
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
            System.out.println("[ParkingDB::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in a parking table
     *
     * @param id id of the parking
     * @param parkingName name of the parking
     * @param address address of the parking
     * @exception Exception
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
            System.out.println("[ParkingDB::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Delete existing record in a parking table
     *
     * @param id id of the parking
     * @exception Exception
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM parking WHERE id = ?");
            prepareStatement.setInt(1, id);

            delete(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingDB::delete] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

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
