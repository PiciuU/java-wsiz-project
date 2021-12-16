package database;

import modules.ParkingSlot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParkingSlotDB extends DBModel {

    /** Define Singleton */
    private static ParkingSlotDB instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException
     */
    private ParkingSlotDB() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingLotDB
     */
    public static ParkingSlotDB getInstance() {
        if (instance == null) instance = new ParkingSlotDB();
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
                ParkingSlot parkingSlot = new ParkingSlot(rs.getInt("id"), rs.getInt("parking_id"), rs.getInt("slot_number"));
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
            parkingSlot.setValues(rs.getInt("id"), rs.getInt("parking_id"), rs.getInt("slot_number"));
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
     * @param parking_id id of the parking lot
     * @param slot_number number of the parking slot
     * @exception Exception
     */
    public void insertRecord(int parking_id, int slot_number) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO parking_slot(parking_id, slot_number) VALUES(?, ?)");
            prepareStatement.setInt(1, parking_id);
            prepareStatement.setInt(2, slot_number);
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
     * @param parking_id id of the parking
     * @param slot_number number of the parking slot
     * @exception Exception
     */
    public void updateRecord(int id, int parking_id, int slot_number) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE parking_slot SET parking_id = ?, slot_number = ? WHERE id = ?");
            prepareStatement.setInt(1, parking_id);
            prepareStatement.setInt(2, slot_number);
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


    public ArrayList<ParkingSlot> getCustom(String query) {
        setConnection();
        ResultSet rs = get(query);

        ArrayList<ParkingSlot> parkingSlotList = new ArrayList<ParkingSlot>();
        try {
            while (rs.next()) {
                ParkingSlot parkingSlot = new ParkingSlot(rs.getInt("id"), rs.getInt("parking_id"), rs.getInt("slot_number"), rs.getString("custom_field"));
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

}
