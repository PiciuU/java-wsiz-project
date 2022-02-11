package gui.components;

import gui.DreamFX.*;
import gui.GUIManager;

import database.controllers.ParkingController;

import models.Parking;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class ParkingDetails extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    protected Parking parking;

    private GridLayout gridLayout;

    private ArrayList<DTextField> formInputs = new ArrayList<DTextField>();

    private DFrame _frame;
    private DLabel _label;
    private DTextField _textField;
    private DButton _button;
    private DLabel _error;

    /**
     * Create component for parking details
     *
     */
    public ParkingDetails(EventHandler customEvent, int id) {
        this.customEvent = customEvent;
        parking = ParkingController.getInstance().getOne(id);
        renderFrame("Edytuj dane parkingu - " + parking.getParkingName());
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
     * Render and mount panel for parking details edit form
     *
     */
    public void renderEditContent() {
        renderInputField("Nazwa parkingu", "name", parking.getParkingName(), 0);
        renderInputField("Adres", "address", parking.getAddress(), 1);

        /* Button */
        _button = new DButton("Zapisz zmiany");
        _button.addActionListener(this);
        _button.setActionCommand("saveParking");
        gridLayout.setConstraints(0, 2, 2, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount input field
     *
     */
    private void renderInputField(String text, String column_name, String column_value, int gridy) {
        /* Label */
        _label = new DLabel(text + ": ", SwingConstants.LEFT);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, gridy, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.putClientProperty(column_name, column_value);
        _textField.setText(_textField.getClientProperty(column_name).toString());
        _textField.setPreferredSize(new Dimension(150, 25));
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

        if (action.equals("saveParking")) {
            for (DTextField input: formInputs) {
                if (input.getText().isEmpty()) {
                    throwVisibleError("Aby zapisać zmiany wszystkie pola muszą być uzupełnione!");
                    return;
                }
            }
            ParkingController.getInstance().updateRecord(parking.getId(), getValueOfProperty("name"), getValueOfProperty("address"));
            customEvent.disposeFrame(_frame);
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
}
