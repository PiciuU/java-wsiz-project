package gui.components;

import gui.GUIManager;

import database.controllers.CustomerController;

import models.Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class CustomerDetails extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    protected Customer customer;

    private GridLayout gridLayout;

    private ArrayList<JTextField> formInputs = new ArrayList<JTextField>();

    private JFrame _frame;
    private JLabel _label;
    private JTextField _textField;
    private JButton _button;
    private JLabel _error;

    /**
     * Create component for undefined customer
     *
     */
    public CustomerDetails(EventHandler customEvent, String modalTitle) {
        this.customEvent = customEvent;
        this.customer = new Customer();
        renderFrame(modalTitle);
        renderCreateContent();
    }

    /**
     * Create component for defined customer
     *
     */
    public CustomerDetails(EventHandler customEvent, String modalTitle, Customer customer) {
        this.customEvent = customEvent;
        this.customer = customer;
        renderFrame(modalTitle);
        renderEditContent();
    }

    /**
     * Render and mount new frame to application
     *
     * @param title name of frame
     */
    public void renderFrame(String title) {
        gridLayout = new GridLayout();
        _frame = new JFrame();
        _frame.setTitle(title);
        _frame.setSize(450, 300);
        _frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                customEvent.disposeFrame(_frame);
            }
        });
        _frame.setLayout(gridLayout);
        _frame.setResizable(false);
        _frame.setVisible(true);
    }

    /**
     * Render and mount panel for customer create form
     *
     */
    public void renderCreateContent() {
        renderInputField("Imię", "firstname", "", 0);
        renderInputField("Nazwisko", "surname", "", 1);
        renderInputField("Numer telefonu", "contact_number", "", 2);

        /* Button */
        _button = new JButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addCustomer");
        gridLayout.setConstraints(0, 3, 2, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount panel for customer edit form
     *
     */
    public void renderEditContent() {
        renderInputField("Imię", "firstname", customer.getFirstname(), 0);
        renderInputField("Nazwisko", "surname", customer.getSurname(), 1);
        renderInputField("Numer telefonu", "contact_number", customer.getContactNumber(), 2);

        /* Button */
        _button = new JButton("Zapisz zmiany");
        _button.addActionListener(this);
        _button.setActionCommand("saveCustomer");
        gridLayout.setConstraints(0, 3, 2, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount input field
     *
     */
    private void renderInputField(String text, String column_name, String column_value, int gridy) {
        /* Label */
        _label = new JLabel(text + ": ", SwingConstants.LEFT);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, gridy, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new JTextField();
        _textField.putClientProperty(column_name, column_value);
        _textField.setText(_textField.getClientProperty(column_name).toString());
        _textField.setPreferredSize(new Dimension(250, 25));
        gridLayout.setConstraints(1, gridy, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        formInputs.add(_textField);
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("saveCustomer")) {
            for (JTextField input: formInputs) {
                if (input.getText().isEmpty()) {
                    throwVisibleError("Aby zapisać zmiany wszystkie pola muszą być uzupełnione!");
                    return;
                }
            }
            CustomerController.getInstance().updateRecord(customer.getId(), getValueOfProperty("firstname"), getValueOfProperty("surname"), getValueOfProperty("contact_number"));
            customEvent.disposeFrame(_frame);
            customEvent.reloadContent();
        }
        else if (action.equals("addCustomer")) {
            for (JTextField input: formInputs) {
                if (input.getText().isEmpty()) {
                    throwVisibleError("Aby dodać nowego klienta wszystkie pola muszą być uzupełnione!");
                    return;
                }
            }
            CustomerController.getInstance().insertRecord(getValueOfProperty("firstname"), getValueOfProperty("surname"), getValueOfProperty("contact_number"));
            customEvent.disposeFrame(_frame);
            customEvent.reloadContent();
        }
    }

    /**
     * Find if requested property exists in array of inputs and get it
     *
     * @return A string with value of input or null value
     */
    public String getValueOfProperty(String key) {
        for (JTextField input : formInputs) {
            if (input.getClientProperty(key) != null) return input.getText();
        }
        return null;
    }

    /**
     * Throw visible validation error for GUI
     *
     * @param text text of validation error
     */
    public void throwVisibleError(String text) {
        if (_frame.isAncestorOf(_error)) _frame.remove(_error);
        _error = new JLabel();
        _error.setText(Prettier.renderText(text, "text-align:center;"));
        _error.setForeground(Color.red);
        _error.setHorizontalAlignment(SwingConstants.CENTER);
        gridLayout.setConstraints(0, 4, 2, new Insets(5, 0, 0, 0));
        _frame.add(_error, gridLayout.getConstraints());
        _frame.validate();
    }
}
