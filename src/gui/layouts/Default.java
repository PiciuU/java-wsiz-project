package gui.layouts;

import gui.DreamFX.*;
import gui.App;
import gui.GUIManager;

import database.controllers.ParkingController;

import models.Parking;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class Default extends GUIManager implements ActionListener {

    protected ArrayList<Parking> parkings;

    /** GUI */
    private GridLayout gridLayout;

    private DPanel _panel;
    private DPanel _panelChild;
    private DLabel _label;
    private DComboBox _comboBox;
    private DTextField _textField;
    private DTextField _textFieldSecondary;
    private DButton _button;
    private DLabel _error;

    /**
     * Create default layout to application
     *
     * @param isAddModeEnabled A boolean indicating the required inner view
     */
    public Default(Boolean isAddModeEnabled) {
        parkings = ParkingController.getInstance().getAll();
        if (isAddModeEnabled || parkings.isEmpty()) {
            buildAddParkingView();
        }
        else {
            buildSelectParkingView();
        }

        _panel = new DPanel();
        _panel.setLayout(new BorderLayout());
        _panel.add(_panelChild);
    }

    /**
     * Get default layout
     *
     * @return DPanel
     */
    public DPanel getLayout() {
        return _panel;
    }

    /**
     * Build and mount an inner view containing form that allows to create a new parking lot
     *
     */
    public void buildAddParkingView() {
        gridLayout = new GridLayout();
        _panelChild = new DPanel();
        _panelChild.setLayout(gridLayout);

        /* Label */
        _label = new DLabel("Dodaj nowy parking: ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2, new Insets(0,0,20,0));
        _panelChild.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new DLabel("Nazwa parkingu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1, new Insets(0, 0, 0, 10));
        _panelChild.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(1, 1, new Insets(0, 0,0,0));
        _panelChild.add(_textField, gridLayout.getConstraints());

        /* Label */
        _label = new DLabel("Adres parkingu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 2, new Insets(10, 0, 0, 10));
        _panelChild.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textFieldSecondary = new DTextField();
        _textFieldSecondary.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(1, 2, new Insets(10, 0,0,0));
        _panelChild.add(_textFieldSecondary, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addParking");
        gridLayout.setConstraints(0, 3, 2, new Insets(20,0,0,0));
        _panelChild.add(_button, gridLayout.getConstraints());

        /* Button */
        if (!parkings.isEmpty()) {
            _button = new DButton("Wróć do listy parkingów");
            _button.addActionListener(this);
            _button.setActionCommand("returnToSelectMode");
            gridLayout.setConstraints(0, 3, new Insets(110, 0, 0, 0));
            _panelChild.add(_button, gridLayout.getConstraints());
        }
    }

    /**
     * Build and mount an inner view containing form that allows to select an existing parking lot
     *
     */
    public void buildSelectParkingView() {
        gridLayout = new GUIManager.GridLayout();
        _panelChild = new DPanel();
        _panelChild.setLayout(gridLayout);

        /* Label */
        _label = new DLabel("Wybierz parking, którym chcesz zarządzać: ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2, new Insets(10,0,0,0));
        _panelChild.add(_label, gridLayout.getConstraints());

        /* ComboBox */
        _comboBox = new DComboBox();
        for (Parking parking : parkings) {
            _comboBox.addItem(parking.getParkingName());
        }
        gridLayout.setConstraints(0, 1);
        _panelChild.add(_comboBox, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("Zatwierdź");
        _button.addActionListener(this);
        _button.setActionCommand("selectParking");
        gridLayout.setConstraints(0, 2);
        _panelChild.add(_button, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("Dodaj nowy parking");
        _button.addActionListener(this);
        _button.setActionCommand("returnToAddMode");
        gridLayout.setConstraints(0, 3, new Insets(100,0,0,0));
        _panelChild.add(_button, gridLayout.getConstraints());
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("selectParking")) {
            App.getInstance().renderAuthorizedLayout(parkings.get(_comboBox.getSelectedIndex()).getId());
        }
        else if (action.equals("addParking")) {
            if (_textField.getText().isEmpty() || _textFieldSecondary.getText().isEmpty()) {
                throwVisibleError("Aby przejść dalej należy wypełnić wszystkie pola tekstowe");
                return;
            }
            ParkingController.getInstance().insertRecord(_textField.getText(), _textFieldSecondary.getText());
            App.getInstance().renderDefaultLayout(false);
        }
        else if (action.equals("returnToSelectMode")) {
            App.getInstance().renderDefaultLayout(false);
        }
        else if (action.equals("returnToAddMode")) {
            App.getInstance().renderDefaultLayout(true);
        }
    }

    /**
     * Throw visible validation error for GUI
     *
     * @param text text of validation error
     */
    public void throwVisibleError(String text) {
        if (_panelChild.isAncestorOf(_error)) _panelChild.remove(_error);
        _error = new DLabel();
        _error.setText(Prettier.renderText(text, "text-align:center;"));
        _error.setForeground(Color.red);
        _error.setHorizontalAlignment(SwingConstants.CENTER);
        gridLayout.setConstraints(0, 4, new Insets(5, 0, 0, 0));
        _panelChild.add(_error, gridLayout.getConstraints());
        _panelChild.validate();
    }

}
