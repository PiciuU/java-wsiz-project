package gui.components;

import database.controllers.CustomerController;

import database.controllers.VehicleController;
import gui.GUIManager;
import models.Customer;

import models.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VehicleDetails extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    protected Vehicle vehicle;
    protected Customer customer;

    private GridLayout gridLayout;

    private ArrayList<JTextField> formInputs = new ArrayList<JTextField>();

    private JFrame _frame;
    private JLabel _label;
    private JTextField _textField;
    private JButton _button;
    private JLabel _error;

    public VehicleDetails(EventHandler customEvent, String modalTitle) {
        this.customEvent = customEvent;
        this.vehicle = new Vehicle();
        this.customer = new Customer();
        renderFrame(modalTitle);
        renderCreateContent();
    }

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
        _frame = new JFrame();
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

    public void renderCreateContent() {
        /* Label */
        _label = new JLabel("Właściciel pojazdu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 0, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new JTextField();
        _textField.putClientProperty("customer", "");
        _textField.setPreferredSize(new Dimension(150, 25));
        _textField.setEditable(false);
        gridLayout.setConstraints(1, 0, 2, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("zmień...");
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
        _button = new JButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addVehicle");
        gridLayout.setConstraints(0, 6, 4, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    public void renderEditContent() {
        /* Label */
        _label = new JLabel("Właściciel pojazdu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 0, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new JTextField();
        _textField.putClientProperty("customer", customer);
        _textField.setText(customer.getFirstname() + " " + customer.getSurname());
        _textField.setPreferredSize(new Dimension(150, 25));
        _textField.setEditable(false);
        gridLayout.setConstraints(1, 0, 2, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("zmień...");
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
        _button = new JButton("Zapisz zmiany");
        _button.addActionListener(this);
        _button.setActionCommand("saveVehicle");
        gridLayout.setConstraints(0, 6, 4, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    private void renderInputField(String text, String column_name, String column_value, int gridy) {
        /* Label */
        _label = new JLabel(text + ": ", SwingConstants.LEFT);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, gridy, 2, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new JTextField();
        _textField.putClientProperty(column_name, column_value);
        _textField.setText(_textField.getClientProperty(column_name).toString());
        _textField.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(2, gridy, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        formInputs.add(_textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        
        if (action.equals("chooseCustomer")) {
            new CustomerListModal(new CustomEvent());
        }
        else if (action.equals("saveVehicle")) {
            for (JTextField input: formInputs) {
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
            for (JTextField input: formInputs) {
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

    public String getValueOfProperty(String key) {
        for (JTextField input : formInputs) {
            if (input.getClientProperty(key) != null) return input.getText();
        }
        return null;
    }

    public void throwVisibleError(String text) {
        if (_frame.isAncestorOf(_error)) _frame.remove(_error);
        _error = new JLabel();
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
            customer = (Customer) object;
            for(Component component : _frame.getRootPane().getContentPane().getComponents()) {
                if (component instanceof JTextField && ((JTextField) component).getClientProperty("customer") != null) {
                    ((JTextField) component).setText(customer.getFirstname() + " " + customer.getSurname());
                }
            }
        }
    }
}
