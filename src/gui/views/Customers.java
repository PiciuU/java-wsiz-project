package gui.views;

import gui.GUIManager;
import gui.components.CustomerDetails;
import gui.components.VehicleDetails;
import gui.components.CustomerList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Customers extends GUIManager implements ActionListener {

    private CustomerList customerListComponent;

    private JPanel _panel;
    private JToolBar _toolBar;
    private JButton _button;

    public JPanel getView() {
        return _panel;
    }

    public Customers() {
        customerListComponent = new CustomerList(new CustomEvent());
        renderPanel();
    }

    private void renderPanel() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));

        /* Toolbar */
        _toolBar = new JToolBar();
        _toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        _toolBar.setFloatable(false);
        _panel.add(_toolBar);

        /* Button */
        _button = new JButton("Dodaj klienta");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("addVehicle");
        _toolBar.add(_button);

        /* Button */
        _button = new JButton("Wyszukiwanie pojazdów");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("createSlot");
        _toolBar.add(_button);

        /* Mount CustomerList Component */
        _panel.add(customerListComponent.getComponent());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action == "addVehicle") {
            new CustomerDetails(new CustomEvent(), "Dodaj nowego klienta");
        }
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler {
        @Override
        public void disposeFrame(JFrame frame) {
            System.out.println("CustomEvent::disposeFrame");
            frame.dispose();
        }

        @Override
        public void reloadContent() {
            _panel.remove(customerListComponent.getComponent());
            System.out.println("CustomEvent::reloadComponent");
            customerListComponent = new CustomerList(new Customers.CustomEvent());
            _panel.add(customerListComponent.getComponent());
        }
    }
}
