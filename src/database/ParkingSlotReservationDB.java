package database;

import modules.ParkingSlotReservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParkingSlotReservationDB extends DBModel {

    /** Define Singleton */
    private static ParkingSlotReservationDB instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException
     */
    private ParkingSlotReservationDB() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingLotDB
     */
    public static ParkingSlotReservationDB getInstance() {
        if (instance == null) instance = new ParkingSlotReservationDB();
        return instance;
    }

    /**
     * Return all objects of a parking_slot_reservation table
     *
     * @param parking_id id of the parking
     * @exception Exception
     * @return ArrayList<ParkingSlotReservation>
     */
    public ArrayList<ParkingSlotReservation> getAll(int parking_id) {
        setConnection();
        ResultSet rs = get("SELECT parking_slot_reservation.* FROM parking_slot_reservation JOIN parking_slot ON parking_slot_reservation.parking_slot_id = parking_slot.id WHERE parking_slot.parking_id = " + parking_id);

        ArrayList<ParkingSlotReservation> parkingSlotReservationList = new ArrayList<ParkingSlotReservation>();
        try {
            while (rs.next()) {
                ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation(rs.getInt("id"), rs.getInt("vehicle_id"), rs.getInt("parking_slot_id"), rs.getTimestamp("reservation_date"));
                parkingSlotReservationList.add(parkingSlotReservation);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingSlotReservationList;
    }

    /**
     * Return specific object of a parking_slot_reservation table
     *
     * @param parking_slot_id an id of the parking slot
     * @exception Exception
     * @return ParkingSlotReservation
     */
    public ParkingSlotReservation getOne(int parking_slot_id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking_slot_reservation WHERE parking_slot_id = " + parking_slot_id);

        ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation();

        try {
            parkingSlotReservation.setValues(rs.getInt("id"), rs.getInt("vehicle_id"), rs.getInt("parking_slot_id"), rs.getTimestamp("reservation_date"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingSlotReservation;
    }

    /**
     * Insert new record into a parking_slot table
     *
     * @param vehicle_id id of the vehicle
     * @param parking_slot_id id of the parking slot
     * @param reservation_date timestamp of reservation date
     * @exception Exception
     */
    public void insertRecord(int vehicle_id, int parking_slot_id, String reservation_date) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO parking_slot_reservation(vehicle_id, parking_slot_id, reservation_date) VALUES(?, ?, ?)");
            prepareStatement.setInt(1, vehicle_id);
            prepareStatement.setInt(2, parking_slot_id);
            prepareStatement.setString(3, reservation_date);
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
     * @param id id of the parking slot reservation
     * @param vehicle_id id of the vehicle
     * @param parking_slot_id id of the parking slot
     * @param reservation_date timestamp of reservation date
     * @exception Exception
     */
    public void updateRecord(int id, int vehicle_id, int parking_slot_id, String reservation_date) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE parking_slot_reservation SET vehicle_id = ?, parking_slot_id = ?, reservation_date = ? WHERE id = ?");
            prepareStatement.setInt(1, vehicle_id);
            prepareStatement.setInt(2, parking_slot_id);
            prepareStatement.setString(3, reservation_date);
            prepareStatement.setInt(4, id);

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
     * Delete existing record in a parking_slot table
     *
     * @param id id of the parking slot reservation
     * @exception Exception
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM parking_slot_reservation WHERE id = ?");
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

}
