package gui.components;

import gui.GUIManager;

import database.controllers.VehicleController;

import models.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class VehicleList extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    private ListAction listAction;

    private ArrayList<Vehicle> vehiclesData;

    private String currentFilterValue;

    private JPanel _panel;
    private JPanel _innerPanel;
    private JScrollPane _scrollPane;
    private JLabel _label;
    private JButton _button;

    private JRadioButton _radioButton;
    private ButtonGroup _buttonGroup;

    private DefaultListModel _listModel;
    private JList _list;

    /**
     * Create component for vehicle list
     *
     */
    public VehicleList(EventHandler customEvent) {
        this.customEvent = customEvent;
        refreshList("all_vehicles", false);
        renderPanel();
        renderFilterButtons();
        renderList();
    }

    /**
     * Create component for vehicle list with defined filter
     *
     */
    public VehicleList(EventHandler customEvent, String filterValue) {
        this.customEvent = customEvent;
        refreshList(filterValue, false);
        renderPanel();
        renderFilterButtons();
        renderList();
    }

    /**
     * Get current value of filter
     *
     * @return A string containing current filter value
     */
    public String getCurrentFilterValue() { return currentFilterValue; }

    /**
     * Get component of vehicle list
     *
     * @return JPanel
     */
    public JPanel getComponent() {
        return _panel;
    }

    /**
     * Get scrollable component of vehicle list
     *
     * @return JScrollPane
     */
    public JScrollPane getComponentScrollable() {
        return _scrollPane;
    }


    /**
     * Render and mount panel
     *
     */
    public void renderPanel() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));
    }

    /**
     * Render and mount filter buttons
     *
     */
    public void renderFilterButtons() {
        _innerPanel = new JPanel();
        _innerPanel.setLayout(new BoxLayout(_innerPanel, BoxLayout.LINE_AXIS));
        _innerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        _innerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        /* Label */
        _label = new JLabel("Filtry wyszukiwania: ", SwingConstants.LEFT);
        _innerPanel.add(_label);

        _buttonGroup = new ButtonGroup();

        _radioButton = new JRadioButton("Wszystkie pojazdy", currentFilterValue == "all_vehicles");
        _radioButton.setActionCommand("all_vehicles");
        _radioButton.addActionListener(this);
        _buttonGroup.add(_radioButton);
        _innerPanel.add(_radioButton);

        _radioButton = new JRadioButton("Zaparkowane pojazdy", currentFilterValue == "parked_vehicles");
        _radioButton.setActionCommand("parked_vehicles");
        _radioButton.addActionListener(this);
        _buttonGroup.add(_radioButton);
        _innerPanel.add(_radioButton);

        _radioButton = new JRadioButton("Wolne pojazdy", currentFilterValue == "free_vehicles");
        _radioButton.setActionCommand("free_vehicles");
        _radioButton.addActionListener(this);
        _buttonGroup.add(_radioButton);
        _innerPanel.add(_radioButton);

        _panel.add(_innerPanel);
    }

    /**
     * Render and mount filtered list
     *
     */
    public void renderList() {
        /* ListModel and List */
        _listModel = new DefaultListModel();
        _list = new JList(_listModel);

        listAction = new ListAction(_list, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JList list = (JList)e.getSource();
                new VehicleDetails(customEvent, "Szczegóły pojazdu", vehiclesData.get(list.getSelectedIndex()));
            }
        });

        for(Vehicle vehicle : vehiclesData) {
            _listModel.addElement(vehicle.getProducer() + " " + vehicle.getModel() + " - " + vehicle.getNumberPlate());
        }

        /* ScrollPane */
        _scrollPane = new JScrollPane(_list);
        _scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        _panel.add(_scrollPane);
    }

    /**
     * Refresh list basing on current filter
     *
     * @param filter value of filter
     * @param reloadGUI if GUI should be reloaded
     */
    public void refreshList(String filter, Boolean reloadGUI) {
        currentFilterValue = filter;
        switch(filter) {
            case "parked_vehicles":
                vehiclesData = VehicleController.getInstance().getCustom("SELECT vehicle.* FROM vehicle LEFT JOIN parking_slot_reservation ON vehicle.id = parking_slot_reservation.vehicle_id WHERE parking_slot_reservation.id IS NOT NULL");
                break;
            case "free_vehicles":
                vehiclesData = VehicleController.getInstance().getCustom("SELECT vehicle.* FROM vehicle LEFT JOIN parking_slot_reservation ON vehicle.id = parking_slot_reservation.vehicle_id WHERE parking_slot_reservation.id IS NULL");
                break;
            default:
                vehiclesData = VehicleController.getInstance().getAll();
                break;
        }
        if (reloadGUI) {
            _panel.remove(_scrollPane);
            renderList();
            _panel.updateUI();
        }
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        refreshList(action, true);
    }
}
