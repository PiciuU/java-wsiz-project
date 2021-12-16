package GUI;

import GUI.layouts.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {

    /** Define Singleton */
    private static App instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException
     */
    private App(String appName, int appWidth, int appHeight) {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, use getInstance method instead.");
        }
        init(appName, appWidth, appHeight);
    }

    /**
     * Create instance of an object
     *
     */
    public static void setInstance(String name, int appWidth, int appHeight) {
        instance = new App(name, appWidth, appHeight);
    }

    /**
     * Get instance of an object
     *
     * @return App
     */
    public static App getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cannot get non existing instance, use createInstance method first.");
        }
        return instance;
    }

    /** Layout for choosing or creating new parking */
    protected Default defaultLayout;

    /** Layout for specific parking */
    protected Authorized authorizedLayout;

    /** Menu Bar */
    protected JMenuBar menuBar;
    protected JMenu fileMenu;
    protected JMenuItem changeParkingItem;
    protected JMenuItem exitItem;

    /**
     * Initialize GUI with default layout and custom properties
     *
     * @param appName A string containing name for application
     * @param appWidth A width for application in pixels
     * @param appHeight A height for application in pixels
     */
    private void init(String appName, int appWidth, int appHeight) {
        setTitle(appName);
        setSize(appWidth, appHeight);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        renderMenuBar();
        renderAuthorizedLayout(1);

        setVisible(true);
    }

    /**
     * Render and mount menu bar to application
     *
     */
    public void renderMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("Plik");

        changeParkingItem = new JMenuItem("Zmień parking");
        exitItem = new JMenuItem("Wyjdź");

        changeParkingItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(changeParkingItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Render and mount default layout to application
     *
     * @param enableAddMode Define mode for layout
     */
    public void renderDefaultLayout(Boolean enableAddMode) {
        defaultLayout = new Default(enableAddMode);

        getContentPane().removeAll();
        setContentPane(defaultLayout.getLayout());
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Render and mount layout for specific parking to application
     *
     * @param parkingId An id of parking lot
     */
    public void renderAuthorizedLayout(int parkingId) {
        authorizedLayout = new Authorized(parkingId);

        getContentPane().removeAll();
        setContentPane(authorizedLayout.getLayout());
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeParkingItem) renderDefaultLayout(false);
        else System.exit(0);
    }
}
