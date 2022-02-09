package database.controllers;

import models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class CustomerController extends Controller {

    /** Define Singleton */
    private static CustomerController instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException if instance already exists
     */
    private CustomerController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
    }

    /**
     * Get instance of an object
     *
     * @return CustomerController instance
     */
    public static CustomerController getInstance() {
        if (instance == null) instance = new CustomerController();
        return instance;
    }

    /**
     * Fetch all entries from the customer table
     *
     * @exception Exception if statement couldn't be resolved
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
     * Fetch specific entry from the customer table
     *
     * @param id customer ID
     * @exception Exception if statement couldn't be resolved
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
     * Insert new record into the customer table
     *
     * @param firstname customer name
     * @param surname customer surname
     * @param contact_number customer contact number
     * @exception SQLException if statement couldn't be resolved
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
            System.out.println("[CustomerController::insert] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Update existing record in the customer table
     *
     * @param id customer ID
     * @param firstname customer name
     * @param surname customer surname
     * @param contact_number customer contact number
     * @exception SQLException if statement couldn't be resolved
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
            System.out.println("[CustomerController::update] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Delete existing record in the customer table
     *
     * @param id customer ID
     * @exception SQLException if statement couldn't be resolved
     */
    public void deleteRecord(int id) {
        setConnection();

        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement("DELETE FROM customer WHERE id = ?");
            prepareStatement.setInt(1, id);

            delete(prepareStatement);
        }
        catch (SQLException e) {
            System.out.println("[CustomerController::delete] SQLException: " + e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /**
     * Fetch custom entries from the customer table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return ArrayList<Customer>
     */
    public ArrayList<Customer> getCustom(String query) {
        setConnection();
        ResultSet rs = get(query);

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
     * Fetch custom entry from the customer table
     *
     * @param query sqlite statement
     * @exception Exception if statement couldn't be resolved
     * @return Customer
     */
    public Customer getCustomOne(String query) {
        setConnection();
        ResultSet rs = get(query);

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

}
