package modules;

public class ParkingSlot extends Model {
    /* Fields */

    private int parkingId;
    private int slotNumber;

    /* Getters & Setters */

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    /* Constructors */

    public ParkingSlot() {
        parkingId = 0;
        slotNumber = 0;
    }

    public ParkingSlot(int id, int parkingId, int slotNumber) {
        setValues(id, parkingId, slotNumber);
    }

    public ParkingSlot(int id, int parkingId, int slotNumber, String customField) {
        setValues(id, parkingId, slotNumber);
        setCustomField(customField);
    }

    /* Methods */

    public void setValues(int id, int parkingId, int slotNumber) {
        setId(id);
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
    }

    public void updateValues(int parkingId, int slotNumber) {
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
