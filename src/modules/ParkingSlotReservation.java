package modules;

import java.sql.Timestamp;

public class ParkingSlotReservation extends Model {
    /* Fields */

    private int vehicleId;
    private int parkingSlotId;
    private Timestamp reservationDate;

    /* Getters & Setters */

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    /* Constructors */

    public ParkingSlotReservation() {
        vehicleId = 0;
        parkingSlotId = 0;
        reservationDate = null;
    }

    public ParkingSlotReservation(int id, int vehicleId, int parkingSlotId, Timestamp reservationDate) {
        setValues(id, vehicleId, parkingSlotId, reservationDate);
    }

    public ParkingSlotReservation(int id, int vehicleId, int parkingSlotId, Timestamp reservationDate, String customField) {
        setValues(id, vehicleId, parkingSlotId, reservationDate);
        setCustomField(customField);
    }

    /* Methods */

    public void setValues(int id, int vehicleId, int parkingSlotId, Timestamp reservationDate) {
        setId(id);
        this.vehicleId = vehicleId;
        this.parkingSlotId = parkingSlotId;
        this.reservationDate = reservationDate;
    }

    public void updateValues(int vehicleId, int parkingSlotId, Timestamp reservationDate) {
        this.vehicleId = vehicleId;
        this.parkingSlotId = parkingSlotId;
        this.reservationDate = reservationDate;
    }

    /* Override Methods */

    @Override
    public String toString() {
        return "Parking Slot Reservation ID: " + getId() +
                "\nVehicle ID: " + vehicleId +
                "\nParking Slot ID: " + parkingSlotId +
                "\nReservation date: " + reservationDate;
    }

    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
