package models;

public class ParkingSlot extends Model {
    /* Fields */

    private int parkingId;
    private String slotNumber;

    /* Getters & Setters */

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    /* Constructors */

    public ParkingSlot() {
        parkingId = 0;
        slotNumber = "undefined";
    }

    public ParkingSlot(int id, int parkingId, String slotNumber) {
        setValues(id, parkingId, slotNumber);
    }

    public ParkingSlot(int id, int parkingId, String slotNumber, String customField) {
        setValues(id, parkingId, slotNumber, customField);
    }

    /* Methods */

    public void setValues(int id, int parkingId, String slotNumber, String customField) {
        setId(id);
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
        setCustomField(customField);
    }

    public void setValues(int id, int parkingId, String slotNumber) {
        setId(id);
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
    }

    public void updateValues(int parkingId, String slotNumber) {
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
    }

    /* Override Methods */

    @Override
    public String toString() {
        return "Parking Slot ID: " + getId() +
                "\nParking Lot ID: " + parkingId +
                "\nSlot number: " + slotNumber;
    }

    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
