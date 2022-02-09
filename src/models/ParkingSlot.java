package models;

public class ParkingSlot extends Model {
    /* Fields */

    private int parkingId;
    private String slotNumber;

    /* Getters & Setters */

    /**
     * Get the parking ID
     *
     * @return An integer representing the parking ID
     */
    public int getParkingId() {
        return parkingId;
    }

    /**
     * Set the parking ID
     *
     * @param parkingId An integer containing the parking ID
     */
    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    /**
     * Get the parking slot number
     *
     * @return A string representing the parking slot number
     */
    public String getSlotNumber() {
        return slotNumber;
    }

    /**
     * Set the parking slot number
     *
     * @param slotNumber A string containing the parking slot number
     */
    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    /* Constructors */

    /**
     * Create a parking slot with empty properties
     *
     */
    public ParkingSlot() {
        parkingId = 0;
        slotNumber = "undefined";
    }

    /**
     * Create a parking slot with specified properties
     *
     * @param id parking slot ID
     * @param parkingId parking ID
     * @param slotNumber parking slot number
     */
    public ParkingSlot(int id, int parkingId, String slotNumber) {
        setValues(id, parkingId, slotNumber);
    }

    /**
     * Create a parking slot with specified properties and custom value
     *
     * @param id parking slot ID
     * @param parkingId parking ID
     * @param slotNumber parking slot number
     * @param customField parking slot custom field
     */
    public ParkingSlot(int id, int parkingId, String slotNumber, String customField) {
        setValues(id, parkingId, slotNumber, customField);
    }

    /* Methods */

    /**
     * Set properties and custom value for specified parking slot
     *
     * @param id parking slot ID
     * @param parkingId parking ID
     * @param slotNumber parking slot number
     * @param customField parking slot custom field
     */
    public void setValues(int id, int parkingId, String slotNumber, String customField) {
        setId(id);
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
        setCustomField(customField);
    }

    /**
     * Set properties for specified parking slot
     *
     * @param id parking slot ID
     * @param parkingId parking ID
     * @param slotNumber parking slot number
     */
    public void setValues(int id, int parkingId, String slotNumber) {
        setId(id);
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
    }

    /**
     * Update properties for parking slot
     *
     * @param parkingId parking ID
     * @param slotNumber parking slot number
     */
    public void updateValues(int parkingId, String slotNumber) {
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
    }

    /* Override Methods */

    /**
     * Get text representation of parking slot
     *
     * @return A String containing information about parking slot
     */
    @Override
    public String toString() {
        return "Parking Slot ID: " + getId() +
                "\nParking Lot ID: " + parkingId +
                "\nSlot number: " + slotNumber;
    }

    /**
     * Check if parking slot object is empty
     *
     * @return A Boolean value that indicates the existence of the parking slot
     */
    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
