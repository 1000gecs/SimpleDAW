package ui.menubar;

import ui.display.MainWindow;
import ui.exception.NoneExistentNoteException;
import ui.SimpleDAW;
import ui.exception.NoSoundTrackSelectedException;
import ui.exception.NoSoundTracksCreatedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Represents a menubar in the GUI
 */
public class MenuBar extends JMenuBar implements ActionListener {
    private MainWindow mainWindow;
    private SimpleDAW simpleDAW;
    private JToggleButton newSoundTrack;
    private JButton load;
    private JToggleButton open;
    private JButton save;
    private JButton play;
    private JToggleButton edit;
    private JButton addNote;
    private JButton deleteNote;
    private JButton create;
    private boolean editSubButtonsVisible;
    private final Dimension buttonSize;

    public MenuBar(SimpleDAW simpleDAW, MainWindow mainWindow, Dimension d, Color c) {
        // Sets up MenuBar
        super();
        this.mainWindow = mainWindow;
        setLayout(new GridLayout(1, 9));
        setOpaque(true);
        setBackground(c);
        setPreferredSize(d);
        buttonSize = new Dimension(40, 20);

        // Saves simpleDAW program object
        this.simpleDAW = simpleDAW;

        // Sets edit menu button visible to false
        editSubButtonsVisible = false;

        // adds all the buttons to the menu
        addSaveButton();
        addNewSoundTrackButton();
        addLoadButton();
        addOpenButton();
        addPlayButton();
        addEditButton();
        addAddNoteButton();
        addDeleteNoteButton();
        addCreateButton();
    }

    // MODIFIES: this
    // EFFECTS: Adds load button to the menubar
    private void addLoadButton() {
        load = new JButton("Load");
        load.setSize(buttonSize);
        load.setActionCommand("Load");
        load.addActionListener(this);

        add(load);
    }

    // MODIFIES: this
    // EFFECTS: Adds open button to the menubar
    private void addOpenButton() {
        open = new JToggleButton("Select");
        open.setSize(buttonSize);
        open.setActionCommand("Open");
        open.addActionListener(this);
        add(open);
    }

    // MODIFIES: this
    // EFFECTS: Adds save button to the menubar
    private void addSaveButton() {
        save = new JButton("Save");
        save.setSize(buttonSize);
        save.setActionCommand("Save");
        save.addActionListener(this);
        add(save);
    }

    // MODIFIES: this
    // EFFECTS: Adds the Play button to menu
    private void addPlayButton() {
        play = new JButton("Play");
        play.setSize(buttonSize);
        play.setActionCommand("Play");
        play.addActionListener(this);
        add(play);
    }

    // MODIFIES: this
    // EFFECTS: Adds the edit button to menu
    private void addEditButton() {
        edit = new JToggleButton("Edit");
        edit.setSize(buttonSize);
        edit.setActionCommand("Edit");
        edit.addActionListener(this);
        add(edit);
    }

    // MODIFIES: this
    // EFFECTS: Adds the add note button to menu, default visibility is false
    private void addAddNoteButton() {
        addNote = new JButton("AddNote");
        addNote.setSize(buttonSize);
        addNote.setActionCommand("AddNote");
        addNote.addActionListener(this);
        addNote.setVisible(editSubButtonsVisible);
        add(addNote);
    }

    // MODIFIES: this
    // EFFECTS: Adds the deletenote button to menu, default visibility is false
    private void addDeleteNoteButton() {
        deleteNote = new JButton("DeleteNote");
        deleteNote.setSize(buttonSize);
        deleteNote.setActionCommand("DeleteNote");
        deleteNote.addActionListener(this);
        deleteNote.setVisible(editSubButtonsVisible);
        add(deleteNote);
    }

    // MODIFIES: this
    // EFFECTS: Adds the create sound track button to menu, default visibility is false
    private void addNewSoundTrackButton() {
        newSoundTrack = new JToggleButton("New");
        newSoundTrack.setSize(buttonSize);
        newSoundTrack.setActionCommand("New");
        newSoundTrack.addActionListener(this);
        add(newSoundTrack);
    }

    // MODIFIES: this
    // EFFECTS: Adds the create sound track button to menu, default visibility is false
    private void addCreateButton() {
        create = new JButton("Create");
        create.setSize(buttonSize);
        create.setActionCommand("Create");
        create.addActionListener(this);
        create.setVisible(false);
        add(create);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    // MODIFIES: SimpleDAW
    // EFFECTS: preforms actions for button click in MenuBar
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load":
                simpleDAW.read();
                mainWindow.repaint();
                break;
            case "Open":
                try {
                    simpleDAW.select();
                } catch (NoSoundTracksCreatedException ex) {
                    open.setSelected(false);
                    System.out.println(ex.toString() + "ADD THIS TO SCREEN BUT NO SOUNDTRACK SELECTED");
                    //todo make this error display GUI
                }
                mainWindow.repaint();
                break;
            case "Save":
                simpleDAW.save();
                mainWindow.repaint();
                break;
            case "Play":
                try {
                    simpleDAW.play();
                } catch (NoSoundTrackSelectedException ex) {
                    play.setSelected(false);
                    System.out.println(ex.toString() + "ADD THIS TO SCREEN BUT NO SOUNDTRACK ADDED");
                    //todo make this error display GUI
                } catch (NoSoundTracksCreatedException ex) {
                    play.setSelected(false);
                    System.out.println(ex.toString() + "ADD THIS TO SCREEN BUT NO SOUNDTRACK CREATED");
                    //todo make this error display GUI
                }
                mainWindow.repaint();
                break;
            case "Edit":
                try {
                    simpleDAW.edit();
                    editSubButtonsVisible = !editSubButtonsVisible;
                    addNote.setVisible(editSubButtonsVisible);
                    deleteNote.setVisible(editSubButtonsVisible);
                } catch (NoSoundTracksCreatedException ex) {
                    edit.setSelected(false);
                    System.out.println(ex.toString() + "ADD THIS TO SCREEN BUT NO SOUNDTRACK CREATED");
                    //todo make this error display GUI
                } catch (NoSoundTrackSelectedException ex) {
                    edit.setSelected(false);
                    System.out.println(ex.toString() + "ADD THIS TO SCREEN BUT NO SOUNDTRACK SELECTED");
                    //todo make this error display GUI
                }
                mainWindow.repaint();
                break;
            case "AddNote":
                try {
                    simpleDAW.addNote();
                } catch (NoneExistentNoteException ex) {
                    System.out.println(ex.toString() + "ADD THIS TO SCREEN BUT NONE EXISTANT NOTE AT THAT POIN");
                }
                mainWindow.repaint();
                break;
            case "DeleteNote":
                try {
                    simpleDAW.removeNote();
                } catch (NoneExistentNoteException ex) {
                    System.out.println(ex.toString() + "ADD THIS TO SCREEN BUT NONE EXISTANT NOTE AT THAT POIN");
                }
                mainWindow.repaint();
                break;
            case "New":
                simpleDAW.displayCreateSoundTrack();
                create.setVisible(!create.isVisible());
                mainWindow.repaint();
                break;
            case "Create":
                simpleDAW.addNewSoundTrack();
                mainWindow.repaint();
                break;
        }
    }
}
