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
     * @exception IllegalStateException
     */
    private ParkingSlotController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingController
     */
    public static ParkingSlotController getInstance() {
        if (instance == null) instance = new ParkingSlotController();
        return instance;
    }

    /**
     * Return all objects of a parking_slot table
     *
     * @param parking_id id of the parking
     * @exception Exception
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
     * Return specific object of a parking_slot table
     *
     * @param id an id of the parking slot
     * @exception Exception
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
     * Insert new record into a parking_slot table
     *
     * @param parkingId id of the parking lot
     * @param slotNumber number of the parking slot
     * @exception Exception
     */
    public void insertRecord(int parkingId, String slotNumber) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO parking_slot(parking_id, slot_number) VALUES(?, ?)");
            prepareStatement.setInt(1, parkingId);
            prepareStatement.setString(2, slotNumber);
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
     * Update existing record in a parking_slot table
     *
     * @param id id of the parking slot
     * @param parkingId id of the parking
     * @param slotNumber number of the parking slot
     * @exception Exception
     */
    public void updateRecord(int id, int parkingId, String slotNumber) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE parking_slot SET parking_id = ?, slot_number = ? WHERE id = ?");
            prepareStatement.setInt(1, parkingId);
            prepareStatement.setString(2, slotNumber);
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
     * Delete existing record in a parking slot table
     *
     * @param id id of the parking slot
     * @exception Exception
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM parking_slot WHERE id = ?");
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
