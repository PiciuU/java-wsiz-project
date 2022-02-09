package database.controllers;

import models.ParkingSlotReservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class ParkingSlotReservationController extends Controller {

    /** Define Singleton */
    private static ParkingSlotReservationController instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException if instance already exists
     */
    private ParkingSlotReservationController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingSlotReservationController instance
     */
    public static ParkingSlotReservationController getInstance() {
        if (instance == null) instance = new ParkingSlotReservationController();
        return instance;
    }

    /**
     * Fetch all entries from the parking_slot_reservation table
     *
     * @param parking_id parking ID
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<ParkingSlotReservation>
     */
    public ArrayList<ParkingSlotReservation> getAll(int parking_id) {
        setConnection();
        ResultSet rs = get("SELECT parking_slot_reservation.* FROM parking_slot_reservation JOIN parking_slot ON parking_slot_reservation.parking_slot_id = parking_slot.id WHERE parking_slot.parking_id = " + parking_id);

        ArrayList<ParkingSlotReservation> parkingSlotReservationList = new ArrayList<ParkingSlotReservation>();
        try {
            while (rs.next()) {
                ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation(rs.getInt("id"), rs.getInt("vehicle_id"), rs.getInt("parking_slot_id"), rs.getString("reservation_date"));
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
     * Fetch specific entry from the parking_slot_reservation table
     *
     * @param parking_slot_id parking slot ID
     * @exception Exception if statement couldn't be resolved
     * @return ParkingSlotReservation
     */
    public ParkingSlotReservation getOne(int parking_slot_id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM parking_slot_reservation WHERE parking_slot_id = " + parking_slot_id);

        ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation();

        try {
            parkingSlotReservation.setValues(rs.getInt("id"), rs.getInt("vehicle_id"), rs.getInt("parking_slot_id"), rs.getString("reservation_date"));
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
     * Insert new record into the parking_slot_reservation table
     *
     * @param vehicle_id vehicle ID
     * @param parking_slot_id parking slot ID
     * @param reservation_date parking slot reservation date
     * @exception SQLException if statement couldn't be resolved
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
            System.out.println("[ParkingSlotReservation::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in the parking_slot_reservation table
     *
     * @param id parking slot reservation ID
     * @param vehicle_id vehicle ID
     * @param parking_slot_id parking slot ID
     * @param reservation_date parking slot reservation date
     * @exception SQLException if statement couldn't be resolved
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
            System.out.println("[ParkingSlotReservation::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Delete existing record in the parking_slot_reservation table
     *
     * @param id parking slot reservation ID
     * @exception SQLException if statement couldn't be resolved
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM parking_slot_reservation WHERE id = ?");
            prepareStatement.setInt(1, id);

            delete(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingSlotReservation::delete] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Fetch custom entries from the parking_slot_reservation table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<ParkingSlotReservation>
     */
    public ArrayList<ParkingSlotReservation> getCustom(String query) {
        setConnection();
        ResultSet rs = get(query);

        ArrayList<ParkingSlotReservation> parkingSlotReservationList = new ArrayList<ParkingSlotReservation>();
        try {
            while (rs.next()) {
                ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation(rs.getInt("id"), rs.getInt("vehicle_id"), rs.getInt("parking_slot_id"), rs.getString("reservation_date"));
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
     * Fetch custom entry from the customer table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return ParkingSlotReservation
     */
    public ParkingSlotReservation getCustomOne(String query) {
        setConnection();
        ResultSet rs = get(query);

        ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation();

        try {
            parkingSlotReservation.setValues(rs.getInt("id"), rs.getInt("vehicle_id"), rs.getInt("parking_slot_id"), rs.getString("reservation_date"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return parkingSlotReservation;
    }

}
