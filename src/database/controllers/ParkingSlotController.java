package database.controllers;

import models.ParkingSlot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class ParkingSlotController extends Controller {

    /** Define Singleton */
    private static ParkingSlotController instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException if instance already exists
     */
    private ParkingSlotController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingController instance
     */
    public static ParkingSlotController getInstance() {
        if (instance == null) instance = new ParkingSlotController();
        return instance;
    }

    /**
     * Fetch all entries from the parking_slot table
     *
     * @param parking_id parking ID
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<ParkingSlot>
     */
    public ArrayList<ParkingSlot> getAll(int parking_id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking_slot WHERE parking_id = " + parking_id);

        ArrayList<ParkingSlot> parkingSlotList = new ArrayList<ParkingSlot>();
        try {
            while (rs.next()) {
                ParkingSlot parkingSlot = new ParkingSlot(rs.getInt("id"), rs.getInt("parking_id"), rs.getString("slot_number"));
                parkingSlotList.add(parkingSlot);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingSlotList;
    }

    /**
     * Fetch specific entry from the parking_slot table
     *
     * @param id parking slot ID
     * @exception Exception if statement couldn't be resolved
     * @return ParkingSlot
     */
    public ParkingSlot getOne(int id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking_slot WHERE id = " + id);

        ParkingSlot parkingSlot = new ParkingSlot();

        try {
            parkingSlot.setValues(rs.getInt("id"), rs.getInt("parking_id"), rs.getString("slot_number"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingSlot;
    }

    /**
     * Insert new record into the parking_slot table
     *
     * @param parking_id parking ID
     * @param slot_number parking slot number
     * @exception SQLException if statement couldn't be resolved
     */
    public void insertRecord(int parking_id, String slot_number) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO parking_slot(parking_id, slot_number) VALUES(?, ?)");
            prepareStatement.setInt(1, parking_id);
            prepareStatement.setString(2, slot_number);
            insert(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingSlotController::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in the parking_slot table
     *
     * @param id parking slot ID
     * @param parking_id parking ID
     * @param slot_number number of the parking slot
     * @exception SQLException if statement couldn't be resolved
     */
    public void updateRecord(int id, int parking_id, String slot_number) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE parking_slot SET parking_id = ?, slot_number = ? WHERE id = ?");
            prepareStatement.setInt(1, parking_id);
            prepareStatement.setString(2, slot_number);
            prepareStatement.setInt(3, id);

            update(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingSlotController::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Delete existing record in the parking_slot table
     *
     * @param id parking slot ID
     * @exception SQLException if statement couldn't be resolved
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM parking_slot WHERE id = ?");
            prepareStatement.setInt(1, id);

            delete(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingSlotController::delete] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Fetch custom entries from the parking_slot table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<ParkingSlot>
     */
    public ArrayList<ParkingSlot> getCustom(String query) {
        setConnection();
        ResultSet rs = get(query);

        ArrayList<ParkingSlot> parkingSlotList = new ArrayList<ParkingSlot>();
        try {
            while (rs.next()) {
                ParkingSlot parkingSlot = new ParkingSlot(rs.getInt("id"), rs.getInt("parking_id"), rs.getString("slot_number"), rs.getString("custom_field"));
                parkingSlotList.add(parkingSlot);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingSlotList;
    }

    /**
     * Fetch custom entry from the parking_slot table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return ParkingSlot
     */
    public ParkingSlot getCustomOne(String query) {
        setConnection();
        ResultSet rs = get(query);

        ParkingSlot parkingSlot = new ParkingSlot();

        try {
            parkingSlot.setValues(rs.getInt("id"), rs.getInt("parking_id"), rs.getString("slot_number"), rs.getString("custom_field"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingSlot;
    }

}
