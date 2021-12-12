package GUI;

import GUI.layouts.*;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class App extends JFrame {

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

    /**
     * Initialize GUI with default layout and custom properties
     *
     * @param appName A string containing name for application
     * @param appWidth A width for application in pixels
     * @param appHeight A height for application in pixels
     */
    private void init(String appName, int appWidth, int appHeight) {
        defaultLayout = new Default(false);

        setTitle(appName);
        setSize(appWidth, appHeight);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        setContentPane(defaultLayout.getLayout());

    }

    /**
     * Render default layout
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
     * Render authorized layout for specific parking
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

}
