package gui;

import gui.layouts.*;

import gui.components.ParkingDetails;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class App extends GUIManager implements ActionListener {

    /** Define Singleton */
    private static App instance;

    /**
     * Constructor to prevent creating new instance
     *
     * @exception IllegalStateException  if instance already exists
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
     * @return App instance
     */
    public static App getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cannot get non existing instance, use createInstance method first.");
        }
        return instance;
    }

    /** Application frame */
    protected JFrame _frame;

    /** Layout for choosing or creating new parking */
    protected Default defaultLayout;

    /** Layout for specific parking */
    protected Authorized authorizedLayout;

    /** Menu Bar */
    protected JMenuBar _menuBar;
    protected JMenu _fileMenu;
    protected JMenuItem _editParkingItem;
    protected JMenuItem _parkingItem;
    protected JMenuItem _exitItem;

    /**
     * Initialize GUI with default layout and custom properties
     *
     * @param appName A string containing name for application
     * @param appWidth A width for application in pixels
     * @param appHeight A height for application in pixels
     */
    private void init(String appName, int appWidth, int appHeight) {
        renderFrame(appName, appWidth, appHeight);
        renderMenuBar();
        renderDefaultLayout(false);
    }

    /**
     * Render and mount global frame to application
     *
     */
    public void renderFrame(String appName, int appWidth, int appHeight) {
        _frame = new JFrame();
        _frame.setTitle(appName);
        _frame.setSize(appWidth, appHeight);
        _frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _frame.setVisible(true);
    }

    /**
     * Render and mount menu bar to application
     *
     */
    public void renderMenuBar() {
        _menuBar = new JMenuBar();

        _fileMenu = new JMenu("Plik");

        _editParkingItem = new JMenuItem("Edytuj dane parkingu");
        _parkingItem = new JMenuItem("Zmień parking");
        _exitItem = new JMenuItem("Wyjdź");

        _editParkingItem.addActionListener(this);
        _parkingItem.addActionListener(this);
        _exitItem.addActionListener(this);

        renderMenuItems();

        _menuBar.add(_fileMenu);

        _frame.setJMenuBar(_menuBar);
    }

    /**
     * Render and mount default layout to application
     *
     * @param enableAddMode Define mode for layout
     */
    public void renderDefaultLayout(Boolean enableAddMode) {
        defaultLayout = new Default(enableAddMode);
        renderMenuItems();

        _frame.getContentPane().removeAll();
        _frame.setContentPane(defaultLayout.getLayout());
        _frame.getContentPane().revalidate();
        _frame.getContentPane().repaint();
    }

    /**
     * Render and mount layout for specific parking to application
     *
     * @param parkingId An id of parking lot
     */
    public void renderAuthorizedLayout(int parkingId) {
        authorizedLayout = new Authorized(parkingId);
        renderMenuItems();

        _frame.getContentPane().removeAll();
        _frame.setContentPane(authorizedLayout.getLayout());
        _frame.getContentPane().revalidate();
        _frame.getContentPane().repaint();
    }

    /**
     * Render and mount items to menu bar
     *
     */
    public void renderMenuItems() {
        _fileMenu.removeAll();

        if (getEnv().getParkingId() != 0) {
            _fileMenu.add(_editParkingItem);
            _fileMenu.add(_parkingItem);
        }

        _fileMenu.add(_exitItem);
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _editParkingItem) {
            if (getEnv().getParkingId() == 0) return;
            new ParkingDetails(new CustomEvent(), getEnv().getParkingId());
        }
        else if (e.getSource() == _parkingItem) {
            getEnv().setParkingId(0);
            renderDefaultLayout(false);
        }
        else System.exit(0);
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler {
        @Override
        public void disposeFrame(JFrame frame) {
            if (getEnv().getEnvType() == "local") System.out.println("[App]CustomEvent::disposeFrame");
            frame.dispose();
        }
    }
}
