package modules;

public class Customer extends Model {
    /* Fields */

    private String firstname;
    private String surname;
    private String contactNumber;

    /* Getters & Setters */

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /* Constructors */

    public Customer() {
        firstname = "undefined";
        surname = "undefined";
        contactNumber = "undefined";
    }

    public Customer(int id, String firstname, String surname, String contactNumber) {
        setValues(id, firstname, surname, contactNumber);
    }

    public Customer(int id, String firstname, String surname, String contactNumber, String customField) {
        setValues(id, firstname, surname, contactNumber);
        setCustomField(customField);
    }

    /* Methods */

    public void setValues(int id, String firstname, String surname, String contactNumber) {
        setId(id);
        this.firstname = firstname;
        this.surname = surname;
        this.contactNumber = contactNumber;
    }

    public void updateValues(String firstname, String surname, String contactNumber) {
        this.firstname = firstname;
        this.surname = surname;
        this.contactNumber = contactNumber;
    }

    /* Override Methods */

    @Override
    public String toString() {
        return "Customer ID: " + getId() +
                "\nFirstname: " + firstname +
                "\nSurname: " + surname +
                "\nContact number: " + contactNumber;
    }

    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
