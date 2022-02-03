package ui.display;

import ui.SimpleDAW;
import ui.exception.NoSoundTrackSelectedException;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

/*
    Represents the edit note input panel
 */
public class EditNote extends JPanel implements PropertyChangeListener {
    private SimpleDAW sd;
    private JLabel measureLabel;
    private JLabel beatLabel;
    private JLabel subBeatLabel;
    private JLabel notePitchLabel;
    private JLabel noteVelocityLabel;
    private JFormattedTextField measureTextField;
    private JFormattedTextField beatTextField;
    private JFormattedTextField subBeatTextField;
    private JFormattedTextField notePitchTextField;
    private JFormattedTextField noteVelocityTextField;

    public EditNote(SimpleDAW sd) {
        this.sd = sd;
        setLayout(new GridLayout(5, 1));
        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up the AddNote Panel
    private void setup() {
        setVisible(true);

        // Set up the JLabs and JTextFields
        setUpTextFields();
        setUpLabels();

        // Adds all the JLabels and JTextFields above to EditNote JPanel
        addAll();
    }

    // MODIFIES: this
    // EFFECTS: sets up the JLabels and JText Fields
    private void setUpTextFields() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        // Measure Text Field
        measureTextField = new JFormattedTextField(numberFormat);
        measureTextField.addPropertyChangeListener("value", this);
        measureTextField.setColumns(5);

        // Beat Text Field
        beatTextField = new JFormattedTextField(numberFormat);
        beatTextField.addPropertyChangeListener("value", this);
        beatTextField.setColumns(5);

        // Sub Beat Text Field
        subBeatTextField = new JFormattedTextField(numberFormat);
        subBeatTextField.addPropertyChangeListener("value", this);
        subBeatTextField.setColumns(5);

        // Note Pitch Text Field
        notePitchTextField = new JFormattedTextField();
        notePitchTextField.addPropertyChangeListener("value", this);
        notePitchTextField.setColumns(5);

        // Note Velocity Text Field
        noteVelocityTextField = new JFormattedTextField(numberFormat);
        noteVelocityTextField.addPropertyChangeListener("value", this);
        noteVelocityTextField.setColumns(5);
    }

    // MODIFIES: this
    // EFFECTS: setups the JLables
    private void setUpLabels() {
        measureLabel = new JLabel("Measure:");
        measureLabel.setLabelFor(measureTextField);

        beatLabel = new JLabel("Beat:");
        beatLabel.setLabelFor(beatTextField);

        subBeatLabel = new JLabel("Sub Beat:");
        subBeatLabel.setLabelFor(subBeatTextField);

        notePitchLabel = new JLabel("Note Pitch:");
        notePitchLabel.setLabelFor(notePitchTextField);

        noteVelocityLabel = new JLabel("Note Velocity:");
        noteVelocityLabel.setLabelFor(noteVelocityTextField);
    }

    // MODIFIES: this
    // EFFECTS: Add the JLabels and TextFields to the EditNote JPanel
    private void addAll() {
        // Add Measure Label
        add(measureLabel);
        add(measureTextField);

        // Add Beat Label
        add(beatLabel);
        add(beatTextField);

        // Add SubBeat Label
        add(subBeatLabel);
        add(subBeatTextField);

        // Add Note Pitch Label
        add(notePitchLabel);
        add(notePitchTextField);

        // Add Note Velocity Label
        add(noteVelocityLabel);
        add(noteVelocityTextField);
    }

    // MODIFIES: this
    // EFFECTS: Looks up current soundtrack and displays the add note panel
    public void edit() throws NoSoundTrackSelectedException {
        try {
            int currentSoundTrackIndex = sd.getCurrentSoundTrackIndex();
        } catch (NoSoundTrackSelectedException ex) {
            throw ex;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: reads text box input and edits the current soundtrack
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            sd.changeEditMeasureValue(((Number) measureTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeEditMeasureValue(-1);
        }

        try {
            sd.changeEditBeatValue(((Number) beatTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeEditBeatValue(-1);
        }

        try {
            sd.changeEditSubBeatValue(((Number) subBeatTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeEditSubBeatValue(-1);
        }

        dumbRedundantFunction();
    }

    // MODIFIES: this, editnote
    // EFFECTS: give simpleDAW the notePitch and NoteVelocity fields
    public void dumbRedundantFunction() {

        try {
            sd.changeEditNotePitchValue(notePitchTextField.getText());
        } catch (Exception e) {
            sd.changeEditNotePitchValue(null);
        }

        try {
            sd.changeEditNoteVelocityValue(((Number) noteVelocityTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeEditNoteVelocityValue(-1);
        }
    }
}
