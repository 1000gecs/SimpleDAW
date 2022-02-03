package ui.display;

import model.SoundTrack;
import ui.GUI;
import ui.SimpleDAW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
    Represents a dropdown screen to select current soundtrack
 */
public class Select extends JPanel implements ActionListener {
    private SimpleDAW sd;
    private JPanel stSelect;
    private ButtonGroup bg;

    public Select(SimpleDAW sd) {
        this.sd = sd;
        setupSelect();
    }


    // MODIFIES: this
    // EFFECTS: setup the select button
    public void setupSelect() {
        stSelect = new JPanel(new GridLayout(0, 1));
        bg = new ButtonGroup();
        stSelect.setPreferredSize(new Dimension(GUI.WIDTH / 4, (GUI.HEIGHT - GUI.MENUHEIGHT) / 2));
        setVisible(false);
        stSelect.setVisible(false);
        add(stSelect, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: changes select to the opposite value of selectVisible, to turn on and off select screen.
    public void select(ArrayList<SoundTrack> soundTracks) {
        updateSelect(soundTracks);
        stSelect.setVisible(!stSelect.isVisible());
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: Updates the select radio buttons
    public void updateSelect(ArrayList<SoundTrack> soundTracks) {
        int counter = 0;
        stSelect.removeAll();
        bg.clearSelection();
        JRadioButton temp;

        for (SoundTrack st : soundTracks) {
            temp = new JRadioButton(st.getName());
            stSelect.add(temp);
            bg.add(temp);
            temp.addActionListener(this);
            temp.setActionCommand(String.valueOf(counter));
            counter++;
        }
    }

    @Override
    // MODIFIES: sd
    // EFFECTS: Gets the action command and passes to changeCurrentSoundTrackIndex top change the index in SimpleDAW
    public void actionPerformed(ActionEvent e) {
        sd.changeCurrentSoundTrackIndex(Integer.parseInt(e.getActionCommand()));
    }
}
