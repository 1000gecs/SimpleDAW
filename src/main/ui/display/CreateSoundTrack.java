package ui.display;

import ui.SimpleDAW;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

/*
    Represents the create new soundtrack panel
 */
public class CreateSoundTrack extends JPanel implements PropertyChangeListener {
    private SimpleDAW sd;
    private JLabel nameLabel;
    private JLabel beatsPerMeasureLabel;
    private JLabel beatValueLabel;
    private JLabel beatsPerMinLabel;
    private JLabel lengthLabel;
    private JFormattedTextField nameTextField;
    private JFormattedTextField beatsPerMeasureTextField;
    private JFormattedTextField beatValueTextField;
    private JFormattedTextField beatsPerMinTextField;
    private JFormattedTextField lengthTextField;

    public CreateSoundTrack(SimpleDAW sd) {
        this.sd = sd;
        setLayout(new GridLayout(5, 1));
        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up the AddNote Panel
    private void setup() {
        setVisible(false);

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
        // name Text Field
        nameTextField = new JFormattedTextField();
        nameTextField.addPropertyChangeListener("value", this);
        nameTextField.setColumns(5);

        // beats per measure Text Field
        beatsPerMeasureTextField = new JFormattedTextField(numberFormat);
        beatsPerMeasureTextField.addPropertyChangeListener("value", this);
        beatsPerMeasureTextField.setColumns(5);

        // beat Value Text Field
        beatValueTextField = new JFormattedTextField(numberFormat);
        beatValueTextField.addPropertyChangeListener("value", this);
        beatValueTextField.setColumns(5);

        // beats per min textText Field
        beatsPerMinTextField = new JFormattedTextField(numberFormat);
        beatsPerMinTextField.addPropertyChangeListener("value", this);
        beatsPerMinTextField.setColumns(5);

        // length Text Field
        lengthTextField = new JFormattedTextField(numberFormat);
        lengthTextField.addPropertyChangeListener("value", this);
        lengthTextField.setColumns(5);
    }

    // MODIFIES: this
    // EFFECTS: setups the JLables
    private void setUpLabels() {
        nameLabel = new JLabel("Name:");
        nameLabel.setLabelFor(nameTextField);

        beatsPerMeasureLabel = new JLabel("Beats Per Measure:");
        beatsPerMeasureLabel.setLabelFor(beatsPerMeasureTextField);

        beatValueLabel = new JLabel("Beat Value:");
        beatValueLabel.setLabelFor(beatValueTextField);

        beatsPerMinLabel = new JLabel("Beats Per Min:");
        beatsPerMinLabel.setLabelFor(beatsPerMinTextField);

        lengthLabel = new JLabel("Length:");
        lengthLabel.setLabelFor(lengthTextField);
    }

    // MODIFIES: this
    // EFFECTS: Add the JLabels and TextFields to the EditNote JPanel
    private void addAll() {
        // Add Measure Label
        add(nameLabel);
        add(nameTextField);

        // Add Beat Label
        add(beatsPerMeasureLabel);
        add(beatsPerMeasureTextField);

        // Add SubBeat Label
        add(beatValueLabel);
        add(beatValueTextField);

        // Add Note Pitch Label
        add(beatsPerMinLabel);
        add(beatsPerMinTextField);

        // Add Note Velocity Label
        add(lengthLabel);
        add(lengthTextField);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: reads text box input and edits the current soundtrack
    public void propertyChange(PropertyChangeEvent evt) { // TODO make it so that note values don't need to be entered
        try {
            sd.changeCreateName(nameTextField.getText());
        } catch (Exception e) {
            sd.changeCreateName(null);
        }

        try {
            sd.changeCreateBeatsPerMeasure(((Number) beatsPerMeasureTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeCreateBeatsPerMeasure(-1);
        }

        try {
            sd.changeCreateBeatValue(((Number) beatValueTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeCreateBeatValue(-1);
        }

        dumbRedundantFunction();
    }

    // MODIFIES: this, simpleDAW
    // EFFECTS: adds the createBeatPerMin and CreateLength values to simpleDAW
    private void dumbRedundantFunction() {
        try {
            sd.changeCreateBeatsPerMin(((Number) beatsPerMinTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeCreateBeatsPerMin(-1);
        }

        try {
            sd.changeCreateLength(((Number) lengthTextField.getValue()).intValue());
        } catch (Exception e) {
            sd.changeCreateLength(-1);
        }
    }

}