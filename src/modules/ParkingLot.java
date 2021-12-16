package modules;

public class ParkingLot extends Model {

    /* Fields */

    private String parkingName;
    private String address;

    /* Getters & Setters */

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /* Constructors */

    public ParkingLot() {
        parkingName = "undefined";
        address = "undefined";
    }

    public ParkingLot(int id, String parkingName, String address) {
        setValues(id, parkingName, address);
    }

    public ParkingLot(int id, String parkingName, String address, String customField) {
        setValues(id, parkingName, address);
        setCustomField(customField);
    }

    /* Methods */

    public void setValues(int id, String parkingName, String address) {
        setId(id);
        this.parkingName = parkingName;
        this.address = address;
    }

    public void updateValues(String parkingName, String address) {
        this.parkingName = parkingName;
        this.address = address;
    }

    /* Override Methods */

    @Override
    public String toString() {
        return "Parking Lot ID: " + getId() +
                "\nParking name: " + parkingName +
                "\nAddress: " + address;
    }

    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
