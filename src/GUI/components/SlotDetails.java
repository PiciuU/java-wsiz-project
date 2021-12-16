package GUI.components;

import GUI.GUIManager;
import GUI.layouts.Authorized;

import database.ParkingSlotReservationDB;
import database.ParkingSlotDB;
import database.VehicleDB;

import modules.ParkingSlotReservation;
import modules.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class SlotDetails extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    protected ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation();

    protected int slotId;
    protected Boolean isOccupied;
    protected Vehicle chosenVehicle;

    private GridLayout gridLayout;

    private JFrame _frame;
    private JLabel _label;
    private JTextField _input;
    private JButton _button;
    private JLabel _error;

    public SlotDetails(EventHandler customEvent, String modalTitle) {
        this.customEvent = customEvent;
        renderFrame(modalTitle);
        renderCreateModal();
    }

    public SlotDetails(EventHandler customEvent, String modalTitle, int slotId, int isOccupied) {
        this.customEvent = customEvent;
        this.slotId = slotId;
        this.isOccupied = (isOccupied != 0);
        this.chosenVehicle = new Vehicle();
        renderFrame(modalTitle);

        if(this.isOccupied) renderDeleteModal();
        else renderEditModal();
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
        _frame.setSize(450, 250);
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
     * Render and mount to frame content for creating new parking slot
     *
     */
    public void renderCreateModal() {
        /* Label */
        _label = new JLabel("Utwórz miejsce parkingowe: ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2);
        _frame.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Numer miejsca parkingowego: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* Input */
        _input = new JTextField();
        _input.setPreferredSize(new Dimension(50, 25));
        gridLayout.setConstraints(1, 1, new Insets(20, 10, 0, 0));
        _frame.add(_input, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addSlot");
        gridLayout.setConstraints(0, 2, 2, new Insets(20,0,0,0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount to frame content for creating new parking slot reservation
     *
     */
    public void renderEditModal() {
        /* Label */
        _label = new JLabel("Miejsce parkingowe nr " + slotId + ": ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 3);
        _frame.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Pojazd do zaparkowania: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* Input */
        _input = new JTextField();
        _input.setPreferredSize(new Dimension(150, 25));
        _input.setEditable(false);
        gridLayout.setConstraints(1, 1, new Insets(20, 10, 0, 0));
        _frame.add(_input, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("wybierz...");
        _button.addActionListener(this);
        _button.setActionCommand("chooseVehicle");
        gridLayout.setConstraints(2, 1, new Insets(20, 10, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addSlotReservation");
        gridLayout.setConstraints(0, 2, 3, new Insets(20,0,0,0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount to frame content for deleting an existing parking slot reservation
     *
     */
    public void renderDeleteModal() {
        parkingSlotReservation = ParkingSlotReservationDB.getInstance().getOne(slotId);
        Vehicle vehicle = VehicleDB.getInstance().getOne(parkingSlotReservation.getVehicleId());

        /* Label */
        _label = new JLabel("Miejsce parkingowe nr " + slotId + ": ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 3);
        _frame.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Zaparkowany pojazd: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* Input */
        _input = new JTextField();
        _input.setPreferredSize(new Dimension(150, 25));
        _input.setEditable(false);
        _input.setText(vehicle.getProducer() + " " + vehicle.getModel() + " - " + vehicle.getNumberPlate());
        gridLayout.setConstraints(1, 1, new Insets(20, 10, 0, 0));
        _frame.add(_input, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Wyparkuj");
        _button.addActionListener(this);
        _button.setActionCommand("deleteSlotReservation");
        gridLayout.setConstraints(0, 2, 3, new Insets(20,0,0,0));
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

        if (action.equals("addSlot")) {
            if (_input.getText().isEmpty()) {
                throwVisibleError("Numer miejsca parkingowego nie może być pusty!");
                return;
            }
            ParkingSlotDB.getInstance().insertRecord(Authorized.parkingId, Integer.parseInt(_input.getText()));
        }
        else if (action.equals("chooseVehicle")) {
            new VehicleList(new CustomEvent());
            return;
        }
        else if (action.equals("addSlotReservation")) {
            if (chosenVehicle.isEmpty() || _input.getText().isEmpty() ) {
                throwVisibleError("Aby przejść dalej należy wybrać pojazd!");
                return;
            }
            ParkingSlotReservationDB.getInstance().insertRecord(chosenVehicle.getId(), slotId, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC).format(Instant.now()));
        }
        else if (action.equals("deleteSlotReservation")) {
            if(parkingSlotReservation.isEmpty()) {
                throwVisibleError("Podane miejsce parkingowe nie jest zajęte!");
                return;
            }
            ParkingSlotReservationDB.getInstance().deleteRecord(parkingSlotReservation.getId());
        }

        customEvent.disposeFrame(_frame);
        customEvent.reloadContent();
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
        gridLayout.setConstraints(0, 3, new Insets(5, 0, 0, 0));
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
            chosenVehicle = (Vehicle) object;
            _input.setText(chosenVehicle.getProducer() + " " + chosenVehicle.getModel() + " - " + chosenVehicle.getNumberPlate());
        }
    }
}
