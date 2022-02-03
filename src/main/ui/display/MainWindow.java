package ui.display;

import javafx.scene.layout.GridPane;
import model.SoundTrack;
import ui.SimpleDAW;
import ui.exception.NoSoundTrackSelectedException;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/*
 * Represents the main windows below the meanu bar
 */

public class MainWindow extends JPanel {
    private SimpleDAW sd;
    private Select select;
    private Edit editor;
    private CreateSoundTrack cst;
    private DisplaySoundTrack dst;

    public MainWindow(SimpleDAW sd) {
        this.sd = sd;

        setLayout(new GridLayout(2, 2));

        select = new Select(sd);
        add(select);

        editor = new Edit(sd);
        add(editor);

        dst = new DisplaySoundTrack(sd);
        add(dst);

        cst = new CreateSoundTrack(sd);
        add(cst);

    }

    // MODIFIES: this, select
    // EFFECTS: shows and removes the select panel
    public void select(ArrayList<SoundTrack> soundTracks) {
        select.select(soundTracks);
        select.setVisible(!select.isVisible());
        try {
            sd.getCurrentSoundTrackIndex();
            dst.setVisible(true);
        } catch (NoSoundTrackSelectedException ex) {
            dst.setVisible(false);
        }
        dst.repaint();
    }

    // MODIFIES: this, CreateSoundTrack
    // EFFECTS: shows and removes the create soundtrack panel
    public void create() {
        cst.setVisible(!cst.isVisible());
        dst.repaint();
    }

    // MODIFIES: this, editor
    // EFFECTS: Shows and removes the edit panel
    public void edit() throws NoSoundTrackSelectedException {
        try {
            editor.edit();
            editor.setVisible(!editor.isVisible());
            dst.repaint();
        } catch (NoSoundTrackSelectedException ex) {
            throw ex;
        }
    }
}
