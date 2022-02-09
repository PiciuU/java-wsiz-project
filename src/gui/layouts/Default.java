package gui.layouts;

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

    private JPanel _panel;
    private JPanel _panelChild;
    private JLabel _label;
    private JComboBox _comboBox;
    private JTextField _textField;
    private JTextField _textFieldSecondary;
    private JButton _button;
    private JLabel _error;

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

        _panel = new JPanel(new BorderLayout());
        _panel.add(_panelChild);
    }

    /**
     * Get default layout
     *
     * @return JPanel
     */
    public JPanel getLayout() {
        return _panel;
    }

    /**
     * Build and mount an inner view containing form that allows to create a new parking lot
     *
     */
    public void buildAddParkingView() {
        gridLayout = new GridLayout();
        _panelChild = new JPanel();
        _panelChild.setLayout(gridLayout);

        /* Label */
        _label = new JLabel("Dodaj nowy parking: ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2, new Insets(10,0,0,0));
        _panelChild.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Nazwa parkingu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1);
        _panelChild.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new JTextField();
        _textField.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(1, 1);
        _panelChild.add(_textField, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Adres parkingu: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 2);
        _panelChild.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textFieldSecondary = new JTextField();
        _textFieldSecondary.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(1, 2);
        _panelChild.add(_textFieldSecondary, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addParking");
        gridLayout.setConstraints(0, 3, 2, new Insets(20,0,0,0));
        _panelChild.add(_button, gridLayout.getConstraints());

        /* Button */
        if (!parkings.isEmpty()) {
            _button = new JButton("Wróć do listy parkingów");
            _button.addActionListener(this);
            _button.setActionCommand("returnToSelectMode");
            gridLayout.setConstraints(0, 3, new Insets(100, 0, 0, 0));
            _panelChild.add(_button, gridLayout.getConstraints());
        }
    }

    /**
     * Build and mount an inner view containing form that allows to select an existing parking lot
     *
     */
    public void buildSelectParkingView() {
        gridLayout = new GUIManager.GridLayout();
        _panelChild = new JPanel();
        _panelChild.setLayout(gridLayout);

        /* Label */
        _label = new JLabel("Wybierz parking, którym chcesz zarządzać: ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2, new Insets(10,0,0,0));
        _panelChild.add(_label, gridLayout.getConstraints());

        /* ComboBox */
        _comboBox = new JComboBox();
        for (Parking parking : parkings) {
            _comboBox.addItem(parking.getParkingName());
        }
        gridLayout.setConstraints(0, 1);
        _panelChild.add(_comboBox, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Zatwierdź");
        _button.addActionListener(this);
        _button.setActionCommand("selectParking");
        gridLayout.setConstraints(0, 2);
        _panelChild.add(_button, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Dodaj nowy parking");
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
        _error = new JLabel();
        _error.setText(Prettier.renderText(text, "text-align:center;"));
        _error.setForeground(Color.red);
        _error.setHorizontalAlignment(SwingConstants.CENTER);
        gridLayout.setConstraints(0, 4, new Insets(5, 0, 0, 0));
        _panelChild.add(_error, gridLayout.getConstraints());
        _panelChild.validate();
    }

}
