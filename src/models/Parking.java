package models;

public class Parking extends Model {
    /* Fields */

    private String parkingName;
    private String address;

    /* Getters & Setters */

    /**
     * Get the parking name
     *
     * @return A string representing the parking name
     */
    public String getParkingName() {
        return parkingName;
    }

    /**
     * Set the parking name
     *
     * @param parkingName A string containing the parking name
     */
    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    /**
     * Get the parking address
     *
     * @return A string representing the parking address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the parking address
     *
     * @param address A string containing the parking address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /* Constructors */

    /**
     * Create a parking with empty properties
     *
     */
    public Parking() {
        parkingName = "undefined";
        address = "undefined";
    }

    /**
     * Create a parking with specified properties
     *
     * @param id parking ID
     * @param parkingName parking name
     * @param address parking address
     */
    public Parking(int id, String parkingName, String address) {
        setValues(id, parkingName, address);
    }

    /**
     * Create a parking with specified properties and custom value
     *
     * @param id parking ID
     * @param parkingName parking name
     * @param address parking address
     * @param customField parking custom field
     */
    public Parking(int id, String parkingName, String address, String customField) {
        setValues(id, parkingName, address, customField);
    }

    /* Methods */

    /**
     * Set properties and custom value for specified parking
     *
     * @param id parking ID
     * @param parkingName parking name
     * @param address parking address
     * @param customField parking custom field
     */
    public void setValues(int id, String parkingName, String address, String customField) {
        setId(id);
        this.parkingName = parkingName;
        this.address = address;
        setCustomField(customField);
    }

    /**
     * Set properties for specified parking
     *
     * @param id parking ID
     * @param parkingName parking name
     * @param address parking address
     */
    public void setValues(int id, String parkingName, String address) {
        setId(id);
        this.parkingName = parkingName;
        this.address = address;
    }

    /**
     * Update properties for parking
     *
     * @param parkingName parking name
     * @param address parking address
     */
    public void updateValues(String parkingName, String address) {
        this.parkingName = parkingName;
        this.address = address;
    }

    /* Override Methods */

    /**
     * Get text representation of parking
     *
     * @return A String containing information about parking
     */
    @Override
    public String toString() {
        return "Parking Lot ID: " + getId() +
                "\nParking name: " + parkingName +
                "\nAddress: " + address;
    }

    /**
     * Check if parking object is empty
     *
     * @return A Boolean value that indicates the existence of the parking
     */
    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
