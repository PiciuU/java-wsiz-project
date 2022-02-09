package gui.components;

import gui.GUIManager;

import database.controllers.VehicleController;

import models.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class VehicleListModal extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    private ArrayList<Vehicle> vehiclesData;

    private GridLayout gridLayout;

    private JFrame _frame;
    private JScrollPane _scrollPane;

    private DefaultListModel _listModel;
    private JList _list;
    private JButton _button;
    private JLabel _error;

    /**
     * Create component for vehicle list modal
     *
     */
    public VehicleListModal(EventHandler customEvent, Boolean onlyAvailableVehicles) {
        this.customEvent = customEvent;
        vehiclesData = onlyAvailableVehicles ? VehicleController.getInstance().getCustom("SELECT vehicle.* FROM vehicle LEFT JOIN parking_slot_reservation ON vehicle.id = parking_slot_reservation.vehicle_id WHERE parking_slot_reservation.id IS NULL") : VehicleController.getInstance().getAll();
        renderFrame();
        renderContent();
    }

    /**
     * Render and mount frame for available vehicles
     *
     */
    public void renderFrame() {
        gridLayout = new GridLayout();
        _frame = new JFrame();
        _frame.setLayout(gridLayout);
        _frame.setTitle("Lista pojazdów");
        _frame.setSize(400, 400);
        _frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _frame.setResizable(false);
        _frame.setVisible(true);
    }

    /**
     * Render and mount content to _frame
     *
     */
    public void renderContent() {
        /* ListModel and List */
        _listModel = new DefaultListModel();
        _list = new JList(_listModel);

        for(Vehicle vehicle : vehiclesData) {
            _listModel.addElement(vehicle.getProducer() + " " + vehicle.getModel() + " - " + vehicle.getNumberPlate());
        }

        /* ScrollPane */
        _scrollPane = new JScrollPane(_list);
        _scrollPane.setPreferredSize(new Dimension(250, 200));
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 1);
        _frame.add(_scrollPane, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Wybierz");
        _button.addActionListener(this);
        _button.setActionCommand("chooseVehicle");
        _button.setPreferredSize(new Dimension(250, 50));
        gridLayout.setConstraints(0, 1, new Insets(20, 0, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("chooseVehicle")) {
            if (_list.getSelectedIndex() == -1) {
                throwVisibleError("Aby przejść dalej pojazd musi być wybrany!");
                return;
            }
            customEvent.handleSelect(vehiclesData.get(_list.getSelectedIndex()));
            _frame.dispose();
        }
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
        gridLayout.setConstraints(0, 2, new Insets(5, 0, 0, 0));
        _frame.add(_error, gridLayout.getConstraints());
        _frame.validate();
    }
}
