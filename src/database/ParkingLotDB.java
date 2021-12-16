package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import modules.ParkingLot;

public class ParkingLotDB extends DBModel {

    /** Define Singleton */
    private static ParkingLotDB instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException
     */
    private ParkingLotDB() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingLotDB
     */
    public static ParkingLotDB getInstance() {
        if (instance == null) instance = new ParkingLotDB();
        return instance;
    }

    /**
     * Return all objects of a parking_lot table
     *
     * @exception Exception
     * @return ArrayList<ParkingLot>
     */
    public ArrayList<ParkingLot> getAll() {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking_lot");

        ArrayList<ParkingLot> parkingLotList = new ArrayList<ParkingLot>();
        try {
            while (rs.next()) {
                ParkingLot parkingLot = new ParkingLot(rs.getInt("id"), rs.getString("parking_name"), rs.getString("address"));
                parkingLotList.add(parkingLot);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingLotList;
    }

    /**
     * Return specific object of a parking_lot table
     *
     * @param id an id of the parking lot
     * @exception Exception
     * @return ParkingLot
     */
    public ParkingLot getOne(int id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking_lot WHERE id = " + id);

        ParkingLot parkingLot = new ParkingLot();

        try {
            parkingLot.setValues(rs.getInt("id"), rs.getString("parking_name"), rs.getString("address"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingLot;
    }

    /**
     * Insert new record into a parking_lot table
     *
     * @param parkingName name of the parking
     * @param address address of the parking
     * @exception Exception
     */
    public void insertRecord(String parkingName, String address) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO parking_lot(parking_name, address) VALUES(?, ?)");
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
     * Update existing record in a parking_lot table
     *
     * @param id id of the parking
     * @param parkingName name of the parking
     * @param address address of the parking
     * @exception Exception
     */
    public void updateRecord(int id, String parkingName, String address) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE parking_lot SET parking_name = ?, address = ? WHERE id = ?");
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

}
