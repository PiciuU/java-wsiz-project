package gui.components;

import gui.GUIManager;

import database.controllers.CustomerController;

import models.Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import java.util.ArrayList;

public class CustomerList extends GUIManager {

    private EventHandler customEvent;

    private ListAction listAction;

    private ArrayList<Customer> customersData;

    private JPanel _panel;
    private JScrollPane _scrollPane;

    private DefaultListModel _listModel;
    private JList _list;

    /**
     * Create component for customers list
     *
     */
    public CustomerList(EventHandler customEvent) {
        this.customEvent = customEvent;
        customersData = CustomerController.getInstance().getAll();
        renderPanel();
        renderList();
    }

    /**
     * Get component of customers list
     *
     * @return JPanel
     */
    public JPanel getComponent() {
        return _panel;
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
     * Render and mount list of customers
     *
     */
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
}
