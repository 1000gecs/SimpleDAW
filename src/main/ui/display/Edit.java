package ui.display;

import model.SoundTrack;

import ui.SimpleDAW;
import ui.exception.NoSoundTrackSelectedException;

import javax.swing.*;
import java.awt.*;

/*
    Represents the main edit panel
 */
public class Edit extends JPanel {
    SimpleDAW sd;
    EditNote editNote;
    SoundTrack st;

    public Edit(SimpleDAW sd) {
        this.sd = sd;
        setup();
    }

    // MODIFIES: this
    // EFFECTS: set up the editor panel
    public void setup() {
        setVisible(false);
        setLayout(new GridLayout(2, 1));

        editNote = new EditNote(sd);

        JLabel editNoteLabel = new JLabel("Edit Note");
        editNoteLabel.setLabelFor(editNote);

        add(editNoteLabel);
        add(editNote);
    }

    // MODIFIES: this
    // EFFECTS: shows add note and delete not panels
    public void edit() throws NoSoundTrackSelectedException {
        try {
            editNote.edit();
        } catch (NoSoundTrackSelectedException ex) {
            throw ex;
        }
    }
}
