package models;

public class ParkingSlotReservation extends Model {
    /* Fields */

    private int vehicleId;
    private int parkingSlotId;
    private String reservationDate;

    /* Getters & Setters */

    /**
     * Get the vehicle ID
     *
     * @return An integer representing the vehicle ID
     */
    public int getVehicleId() {
        return vehicleId;
    }

    /**
     * Set the vehicle ID
     *
     * @param vehicleId An integer containing the vehicle ID
     */
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Get the parking slot ID
     *
     * @return An integer representing the parking slot ID
     */
    public int getParkingSlotId() {
        return parkingSlotId;
    }

    /**
     * Set the parking slot ID
     *
     * @param parkingSlotId An integer containing the parking slot ID
     */
    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    /**
     * Get the parking slot reservation date
     *
     * @return A string representing the parking slot reservation date
     */
    public String getReservationDate() {
        return reservationDate;
    }

    /**
     * Set the parking slot reservation date
     *
     * @param reservationDate A string containing the parking slot reservation date
     */
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    /* Constructors */

    /**
     * Create a parking slot reservation with empty properties
     *
     */
    public ParkingSlotReservation() {
        vehicleId = 0;
        parkingSlotId = 0;
        reservationDate = null;
    }

    /**
     * Create a parking slot reservation with specified properties
     *
     * @param id parking slot reservation ID
     * @param vehicleId vehicle ID
     * @param parkingSlotId parking slot ID
     * @param reservationDate parking slot reservation date
     */
    public ParkingSlotReservation(int id, int vehicleId, int parkingSlotId, String reservationDate) {
        setValues(id, vehicleId, parkingSlotId, reservationDate);
    }

    /**
     * Create a parking slot reservation with specified properties and custom value
     *
     * @param id parking slot reservation ID
     * @param vehicleId vehicle ID
     * @param parkingSlotId parking slot ID
     * @param reservationDate parking slot reservation date
     * @param customField parking slot reservation custom field
     */
    public ParkingSlotReservation(int id, int vehicleId, int parkingSlotId, String reservationDate, String customField) {
        setValues(id, vehicleId, parkingSlotId, reservationDate, customField);
    }

    /* Methods */

    /**
     * Set properties and custom value for specified parking slot reservation
     *
     * @param id parking slot reservation ID
     * @param vehicleId vehicle ID
     * @param parkingSlotId parking slot ID
     * @param reservationDate parking slot reservation date
     * @param customField parking slot reservation custom field
     */
    public void setValues(int id, int vehicleId, int parkingSlotId, String reservationDate, String customField) {
        setId(id);
        this.vehicleId = vehicleId;
        this.parkingSlotId = parkingSlotId;
        this.reservationDate = reservationDate;
        setCustomField(customField);
    }

    /**
     * Set properties for specified parking slot reservation
     *
     * @param id parking slot reservation ID
     * @param vehicleId vehicle ID
     * @param parkingSlotId parking slot ID
     * @param reservationDate parking slot reservation date
     */
    public void setValues(int id, int vehicleId, int parkingSlotId, String reservationDate) {
        setId(id);
        this.vehicleId = vehicleId;
        this.parkingSlotId = parkingSlotId;
        this.reservationDate = reservationDate;
    }

    /**
     * Update properties for parking slot reservation
     *
     * @param vehicleId vehicle ID
     * @param parkingSlotId parking slot ID
     * @param reservationDate parking slot reservation date
     */
    public void updateValues(int vehicleId, int parkingSlotId, String reservationDate) {
        this.vehicleId = vehicleId;
        this.parkingSlotId = parkingSlotId;
        this.reservationDate = reservationDate;
    }

    /* Override Methods */

    /**
     * Get text representation of parking slot reservation
     *
     * @return A String containing information about parking slot reservation
     */
    @Override
    public String toString() {
        return "Parking Slot Reservation ID: " + getId() +
                "\nVehicle ID: " + vehicleId +
                "\nParking Slot ID: " + parkingSlotId +
                "\nReservation date: " + reservationDate;
    }

    /**
     * Check if parking slot reservation object is empty
     *
     * @return A Boolean value that indicates the existence of the parking slot reservation
     */
    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
