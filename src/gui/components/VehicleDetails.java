package gui.components;

import gui.DreamFX.*;
import gui.GUIManager;

import database.controllers.CustomerController;
import database.controllers.VehicleController;

import models.Customer;
import models.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class VehicleDetails extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    protected Vehicle vehicle;
    protected Customer customer;

    private GridLayout gridLayout;

    private ArrayList<DTextField> formInputs = new ArrayList<DTextField>();

    private DFrame _frame;
    private DLabel _label;
    private DTextField _textField;
    private DButton _button;
    private DLabel _error;

    /**
     * Create component for undefined vehicle details
     *
     */
    public VehicleDetails(EventHandler customEvent, String modalTitle) {
        this.customEvent = customEvent;
        this.vehicle = new Vehicle();
        this.customer = new Customer();
        renderFrame(modalTitle);
        renderCreateContent();
    }

    /**
     * Create component for defined vehicle details
     *
     */
    public VehicleDetails(EventHandler customEvent, String modalTitle, Vehicle vehicle) {
        this.customEvent = customEvent;
        this.vehicle = vehicle;
        this.customer = CustomerController.getInstance().getOne(vehicle.getCustomerId());
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
        _frame = new DFrame();
        _frame.setTitle(title);
        _frame.setSize(450, 450);
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
     * Render and mount panel for vehicle details create form
     *
     */
    public void renderCreateContent() {
        /* Label */
        _label = new DLabel("Właściciel pojazdu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 0, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.putClientProperty("customer", "");
        _textField.setPreferredSize(new Dimension(150, 25));
        _textField.setEditable(false);
        gridLayout.setConstraints(1, 0, 2, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("secondary","zmień...");
        _button.addActionListener(this);
        _button.setActionCommand("chooseCustomer");
        gridLayout.setConstraints(3, 0, 1, new Insets(20, 10, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());

        renderInputField("Producent pojazdu", "producer", "", 1);
        renderInputField("Model pojazdu", "model", "", 2);
        renderInputField("Moc silnika", "horsepower", "", 3);
        renderInputField("Rok produkcji", "production_year", "", 4);
        renderInputField("Tablica rejestracyjna", "number_plate", "", 5);

        /* Button */
        _button = new DButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addVehicle");
        gridLayout.setConstraints(0, 6, 4, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount panel for vehicle details edit form
     *
     */
    public void renderEditContent() {
        /* Label */
        _label = new DLabel("Właściciel pojazdu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 0, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.putClientProperty("customer", customer);
        _textField.setText(customer.getFirstname() + " " + customer.getSurname());
        _textField.setPreferredSize(new Dimension(150, 25));
        _textField.setEditable(false);
        gridLayout.setConstraints(1, 0, 2, new Insets(20, 17, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("secondary", "zmień...");
        _button.addActionListener(this);
        _button.setActionCommand("chooseCustomer");
        gridLayout.setConstraints(3, 0, 1, new Insets(20, 10, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());

        renderInputField("Producent pojazdu", "producer", vehicle.getProducer(), 1);
        renderInputField("Model pojazdu", "model", vehicle.getModel(), 2);
        renderInputField("Moc silnika", "horsepower", String.valueOf(vehicle.getHorsepower()), 3);
        renderInputField("Rok produkcji", "production_year", vehicle.getProductionYear(), 4);
        renderInputField("Tablica rejestracyjna", "number_plate", vehicle.getNumberPlate(), 5);

        /* Button */
        _button = new DButton("Zapisz zmiany");
        _button.addActionListener(this);
        _button.setActionCommand("saveVehicle");
        gridLayout.setConstraints(0, 6, 4, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount input field
     *
     */
    public void renderInputField(String text, String column_name, String column_value, int gridy) {
        /* Label */
        _label = new DLabel(text + ": ", SwingConstants.LEFT);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, gridy, 2, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.putClientProperty(column_name, column_value);
        _textField.setText(_textField.getClientProperty(column_name).toString());
        _textField.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(2, gridy, new Insets(20, 10, 0, 0));
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

        if (action.equals("chooseCustomer")) {
            new CustomerListModal(new CustomEvent());
        }
        else if (action.equals("saveVehicle")) {
            for (DTextField input: formInputs) {
                if (input.getText().isEmpty()) {
                    throwVisibleError("Aby zapisać zmiany wszystkie pola muszą być uzupełnione!");
                    return;
                }
            }
            VehicleController.getInstance().updateRecord(vehicle.getId(), customer.getId(), getValueOfProperty("producer"), getValueOfProperty("model"), Integer.parseInt(getValueOfProperty("horsepower")), getValueOfProperty("production_year"), getValueOfProperty("number_plate"));
            customEvent.disposeFrame(_frame);
            customEvent.reloadContent();
        }
        else if (action.equals("addVehicle")) {
            if (customer.isEmpty()) {
                throwVisibleError("Aby dodać nowy pojazd musi zostać wybrany właściciel pojazdu!");
                return;
            }
            for (DTextField input: formInputs) {
                if (input.getText().isEmpty()) {
                    throwVisibleError("Aby dodać nowy pojazd wszystkie pola muszą być uzupełnione!");
                    return;
                }
            }
            VehicleController.getInstance().insertRecord(customer.getId(), getValueOfProperty("producer"), getValueOfProperty("model"), Integer.parseInt(getValueOfProperty("horsepower")), getValueOfProperty("production_year"), getValueOfProperty("number_plate"));
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
        for (DTextField input : formInputs) {
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
        _error = new DLabel();
        _error.setText(Prettier.renderText(text, "text-align:center;"));
        _error.setForeground(Color.red);
        _error.setHorizontalAlignment(SwingConstants.CENTER);
        gridLayout.setConstraints(0, 7, 4, new Insets(5, 0, 0, 0));
        _frame.add(_error, gridLayout.getConstraints());
        _frame.validate();
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler
    {
        @Override
        public void handleSelect(Object object) {
            if (getEnv().getEnvType() == "local") System.out.println("[VehicleDetails]CustomEvent::handleSelect");
            customer = (Customer) object;
            for(Component component : _frame.getRootPane().getContentPane().getComponents()) {
                if (component instanceof DTextField && ((DTextField) component).getClientProperty("customer") != null) {
                    ((DTextField) component).setText(customer.getFirstname() + " " + customer.getSurname());
                }
            }
        }
    }
}
