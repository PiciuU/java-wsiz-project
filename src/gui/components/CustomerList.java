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

public class CustomerList extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    private ListAction listAction;

    private ArrayList<Customer> customersData;

    private JPanel _panel;
    private JPanel _innerPanel;
    private JScrollPane _scrollPane;
    private JLabel _label;
    private JButton _button;

    private JRadioButton _radioButton;
    private ButtonGroup _buttonGroup;

    private DefaultListModel _listModel;
    private JList _list;

    public JPanel getComponent() {
        return _panel;
    }

    public JScrollPane getComponentScrollable() {
        return _scrollPane;
    }

    public CustomerList(EventHandler customEvent) {
        this.customEvent = customEvent;
        customersData = CustomerController.getInstance().getAll();
        renderPanel();
        renderList();
    }

    /**
     * Render and mount panel
     *
     */
    public void renderPanel() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));
    }

    public void renderList() {
        /* ListModel and List */
        _listModel = new DefaultListModel();
        _list = new JList(_listModel);

        listAction = new ListAction(_list, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JList list = (JList)e.getSource();
                new CustomerDetails(customEvent, "Dane klienta", customersData.get(list.getSelectedIndex()));
            }
        });

        for(Customer customer : customersData) {
            _listModel.addElement(customer.getFirstname() + " " + customer.getSurname());
        }

        /* ScrollPane */
        _scrollPane = new JScrollPane(_list);
        _scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        _panel.add(_scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
    }
}
