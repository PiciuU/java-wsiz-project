package database;

import modules.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CustomerDB extends DBModel {

    /** Define Singleton */
    private static CustomerDB instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException
     */
    private CustomerDB() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return ParkingLotDB
     */
    public static CustomerDB getInstance() {
        if (instance == null) instance = new CustomerDB();
        return instance;
    }

    /**
     * Return all objects of a customer table
     *
     * @exception Exception
     * @return ArrayList<Customer>
     */
    public ArrayList<Customer> getAll() {
        setConnection();
        ResultSet rs = get("SELECT * FROM customer");

        ArrayList<Customer> customerList = new ArrayList<Customer>();
        try {
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("id"), rs.getString("firstname"), rs.getString("surname"), rs.getString("contact_number"));
                customerList.add(customer);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return customerList;
    }

    /**
     * Return specific object of a customer table
     *
     * @param id an id of the parking slot reservation
     * @exception Exception
     * @return Customer
     */
    public Customer getOne(int id) {
        setConnection();
        ResultSet rs = get("SELECT * FROM customer WHERE id = " + id);

        Customer customer = new Customer();

        try {
            customer.setValues(rs.getInt("id"), rs.getString("firstname"), rs.getString("surname"), rs.getString("contact_number"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            closeConnection();
        }

        return customer;
    }

    /**
     * Insert new record into a customer table
     *
     * @param firstname firstname of the customer
     * @param surname surname of the customer
     * @param contact_number contact number of the customer
     * @exception Exception
     */
    public void insertRecord(String firstname, String surname, String contact_number) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO customer(firstname, surname, contact_number) VALUES(?, ?, ?)");
            prepareStatement.setString(1, firstname);
            prepareStatement.setString(2, surname);
            prepareStatement.setString(3, contact_number);
            insert(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingDB::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in a customer table
     *
     * @param id id of the parking slot reservation
     * @param firstname firstname of the customer
     * @param surname surname of the customer
     * @param contact_number contact number of the customer
     * @exception Exception
     */
    public void updateRecord(int id, String firstname, String surname, String contact_number) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE customer SET firstname = ?, surname = ?, contact_number = ? WHERE id = ?");
            prepareStatement.setString(1, firstname);
            prepareStatement.setString(2, surname);
            prepareStatement.setString(3, contact_number);
            prepareStatement.setInt(4, id);

            update(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[ParkingDB::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

}
