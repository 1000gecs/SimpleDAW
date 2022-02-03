package ui;

import ui.display.MainWindow;
import ui.menubar.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
    Represents the GUI of SimpleDAW
 */
public class GUI extends JFrame {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 800;
    public static final int MENUHEIGHT = 20;
    private SimpleDAW simpleDAW;
    MenuBar menuBar;
    MainWindow mainWindow;
    JLabel blackLabel;
    private final Dimension menuSize;
    private final Color menuColor;
    private final Dimension stDisplayAreaDimension;
    private final Color stDisplayAreaColor;


    public GUI() {
        super("Simple DAW");

        menuSize = new Dimension(WIDTH, MENUHEIGHT);
        menuColor = new Color(100, 100, 100);

        stDisplayAreaDimension = new Dimension(WIDTH, HEIGHT - MENUHEIGHT);
        stDisplayAreaColor = new Color(100, 100, 100);

        setUpHomeScreen();

        this.addWindowListener(new WindowAdapter() {
            // this changes the windowadapter so that it prints the event log when it exits
            @Override
            public void windowClosing(WindowEvent windowEventE) {
                super.windowClosing(windowEventE);
                Main.printLog();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets up the home screen for simpleDAW
    public void setUpHomeScreen() {

        setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
        setVisible(true);

        // Starts SimpleDAW
        simpleDAW = new SimpleDAW();

        // Sets up Display Sound Track area
        mainWindow = new MainWindow(simpleDAW);
        mainWindow.setBackground(stDisplayAreaColor);
        mainWindow.setPreferredSize(stDisplayAreaDimension);

        // Adds Display Sound Track to simpleDAW
        simpleDAW.addDisplaySoundTrack(mainWindow);

        // Sets up the menu bar
        menuBar = new MenuBar(simpleDAW, mainWindow, menuSize, menuColor);

        // Adds components to blackLabel
        add(menuBar, BorderLayout.NORTH);
        add(mainWindow);

        // Packs everything together
        pack();

        centreOnScreen();
        setVisible(true);
    }

    // Centres frame on desktop, copied from SpaceInvaders
    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }
}
