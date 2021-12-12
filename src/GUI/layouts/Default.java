package GUI.layouts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.ParkingLotDB;
import modules.ParkingLot;

import GUI.App;
import GUI.LayoutManager;

public class Default extends LayoutManager implements ActionListener {

    /** ArrayList containing existing parking lots */
    protected ArrayList<ParkingLot> parkingLots;

    /** GUI */

    private JPanel layout;
    private JPanel currentView;

    private JLabel parkingLabel;

    private JComboBox parkingBox;

    private JLabel parkingNameLabel;
    private JTextField parkingNameInput;

    private JLabel parkingAddressLabel;
    private JTextField parkingAddressInput;

    private JButton parkingButton;
    private JButton actionButton;

    /**
     * Render default layout components
     *
     * @param isAddModeEnabled A boolean indicating the required view
     */
    public Default(Boolean isAddModeEnabled) {
        if (isAddModeEnabled) {
            buildAddParkingView();
        }
        else {
            parkingLots = ParkingLotDB.getInstance().getAll();
            if (parkingLots.isEmpty()) buildAddParkingView();
            else buildSelectParkingView();
        }

        layout = new JPanel(new BorderLayout());
        layout.add(currentView);
    }

    /**
     * Get current layout
     *
     * @return JPanel
     */
    public JPanel getLayout() {
        return layout;
    }

    /**
     * Build a view for adding a new parking lot
     *
     */
    public void buildAddParkingView() {
        currentView = new JPanel();
        currentView.setLayout(new GridBagLayout());

        parkingLabel = new JLabel("Dodaj nowy parking: ", SwingConstants.CENTER);
        setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2, new Insets(10,0,0,0));
        currentView.add(parkingLabel, getConstraints());

        parkingNameLabel = new JLabel("Nazwa parkingu: ", SwingConstants.LEFT);
        setConstraints(0, 1, 1);
        currentView.add(parkingNameLabel, getConstraints());

        parkingNameInput = new JTextField();
        parkingNameInput.setPreferredSize(new Dimension(150, 25));
        setConstraints(1, 1);
        currentView.add(parkingNameInput, getConstraints());

        parkingAddressLabel = new JLabel("Adres parkingu: ", SwingConstants.LEFT);
        setConstraints(0, 2);
        currentView.add(parkingAddressLabel, getConstraints());

        parkingAddressInput = new JTextField();
        parkingAddressInput.setPreferredSize(new Dimension(150, 25));
        setConstraints(1, 2);
        currentView.add(parkingAddressInput, getConstraints());

        parkingButton = new JButton("Dodaj");
        parkingButton.putClientProperty("operation", "ADD");
        parkingButton.addActionListener(this);
        setConstraints(0, 3, 2, new Insets(20,0,0,0));
        currentView.add(parkingButton, getConstraints());

        if (!parkingLots.isEmpty()) {
            actionButton = new JButton("Wróć do listy parkingów");
            actionButton.putClientProperty("operation", "SELECT_MODE");
            actionButton.addActionListener(this);
            setConstraints(0, 3, new Insets(100, 0, 0, 0));
            currentView.add(actionButton, getConstraints());
        }
    }

    /**
     * Build a view for selecting an existing parking lot
     *
     */
    public void buildSelectParkingView() {
        currentView = new JPanel();
        currentView.setLayout(new GridBagLayout());

        parkingLabel = new JLabel("Wybierz parking, którym chcesz zarządzać: ", SwingConstants.CENTER);
        setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2, new Insets(10,0,0,0));
        currentView.add(parkingLabel, getConstraints());

        parkingBox = new JComboBox();
        for (ParkingLot parking : parkingLots) {
            parkingBox.addItem(parking.getParkingName());
        }

        setConstraints(0, 1);
        currentView.add(parkingBox, getConstraints());

        parkingButton = new JButton("Zatwierdź");
        parkingButton.putClientProperty("operation", "SELECT");
        parkingButton.addActionListener(this);
        setConstraints(0, 2);
        currentView.add(parkingButton, getConstraints());

        actionButton = new JButton("Dodaj nowy parking");
        actionButton.putClientProperty("operation", "ADD_MODE");
        actionButton.addActionListener(this);
        setConstraints(0, 3, new Insets(100,0,0,0));
        currentView.add(actionButton, getConstraints());
    }
    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == parkingButton) {
            switch (parkingButton.getClientProperty("operation").toString()) {
                case "SELECT":
                    App.getInstance().renderAuthorizedLayout(parkingLots.get(parkingBox.getSelectedIndex()).getId());
                    break;
                case "ADD":
                    if (parkingNameInput.getText().isEmpty() || parkingAddressInput.getText().isEmpty()) {
                        System.out.println("Input field must not be empty!");
                        return;
                    }
                    ParkingLotDB.getInstance().insertRecord(parkingNameInput.getText(), parkingAddressInput.getText());
                    App.getInstance().renderDefaultLayout(false);
                    break;
            }
        }
        else {
            switch (actionButton.getClientProperty("operation").toString()) {
                case "SELECT_MODE":
                    App.getInstance().renderDefaultLayout(false);
                    break;
                case "ADD_MODE":
                    App.getInstance().renderDefaultLayout(true);
                    break;
            }
        }
    }

}
