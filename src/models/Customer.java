package models;

public class Customer extends Model {
    /* Fields */

    private String firstname;
    private String surname;
    private String contactNumber;

    /* Getters & Setters */

    /**
     * Get the customer's first name
     *
     * @return A string representing the customer's first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the customer's first name
     *
     * @param firstname A string containing the customer's first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get the customer's last name
     *
     * @return A string representing the customer's last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set the customer's last name
     *
     * @param surname A string containing the customer's last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Get the customer's contact number
     *
     * @return A string representing the customer's contact number
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Set the customer's contact number
     *
     * @param contactNumber A string containing the customer's contact number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /* Constructors */

    /**
     * Create a customer with empty properties
     *
     */
    public Customer() {
        firstname = "undefined";
        surname = "undefined";
        contactNumber = "undefined";
    }

    /**
     * Create a customer with specified properties
     *
     * @param id customer ID
     * @param firstname customer first name
     * @param surname customer last name
     * @param contactNumber customer contact number
     */
    public Customer(int id, String firstname, String surname, String contactNumber) {
        setValues(id, firstname, surname, contactNumber);
    }

    /**
     * Create a customer with specified properties and custom value
     *
     * @param id customer ID
     * @param firstname customer first name
     * @param surname customer last name
     * @param contactNumber customer contact number
     * @param customField customer custom field
     */
    public Customer(int id, String firstname, String surname, String contactNumber, String customField) {
        setValues(id, firstname, surname, contactNumber, customField);
    }

    /* Methods */

    /**
     * Set properties and custom value for specified customer
     *
     * @param id customer ID
     * @param firstname customer first name
     * @param surname customer last name
     * @param contactNumber customer contact number
     * @param customField customer custom field
     */
    public void setValues(int id, String firstname, String surname, String contactNumber, String customField) {
        setId(id);
        this.firstname = firstname;
        this.surname = surname;
        this.contactNumber = contactNumber;
        setCustomField(customField);
    }

    /**
     * Set properties for specified customer
     *
     * @param id customer ID
     * @param firstname customer first name
     * @param surname customer last name
     * @param contactNumber customer contact number
     */
    public void setValues(int id, String firstname, String surname, String contactNumber) {
        setId(id);
        this.firstname = firstname;
        this.surname = surname;
        this.contactNumber = contactNumber;
    }

    /**
     * Update properties for customer
     *
     * @param firstname customer first name
     * @param surname customer last name
     * @param contactNumber customer contact number
     */
    public void updateValues(String firstname, String surname, String contactNumber) {
        this.firstname = firstname;
        this.surname = surname;
        this.contactNumber = contactNumber;
    }

    /* Override Methods */

    /**
     * Get text representation of customer
     *
     * @return A String containing information about customer
     */
    @Override
    public String toString() {
        return "Customer ID: " + getId() +
                "\nFirstname: " + firstname +
                "\nSurname: " + surname +
                "\nContact number: " + contactNumber;
    }

    /**
     * Check if customer object is empty
     *
     * @return A Boolean value that indicates the existence of the customer
     */
    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
