package ui.display;

import model.SoundTrack;
import model.TimePosition;
import ui.GUI;
import ui.SimpleDAW;
import ui.exception.NoSoundTrackSelectedException;

import javax.swing.*;
import java.awt.*;

/*
    Represents the display soundtrack panel
 */
public class DisplaySoundTrack extends JPanel {
    private static final int PADDING = 10;
    private static final int NOTE_WIDTH = 5;
    private static final int NOTE_HEIGHT = 10;
    private static final int MEASURE_DIVIDER_WIDTH = 1;
    private static final int MEASURE_DIVIDER_HEIGHT = 20;
    private static final int BEAT_DIVIDER_WIDTH = 1;
    private static final int BEAT_DIVIDER_HEIGHT = 10;
    private static final int HEIGHT_CENTER = ((GUI.HEIGHT - GUI.MENUHEIGHT) / 2 - 1) / 2;
    private static final int PIXELS_BETWEEN_TIMEPOSITION = 5;
    private int counter;
    private SimpleDAW sd;

    public DisplaySoundTrack(SimpleDAW sd) {
        this.sd = sd;

        setVisible(false);
        setPreferredSize(new Dimension(GUI.WIDTH / 2 - 2, (GUI.HEIGHT - GUI.MENUHEIGHT) / 2));
        setBackground(new Color(0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            //setPreferredSize(new Dimension(GUI.WIDTH + pixelsPerMeasure(), (GUI.HEIGHT - GUI.MENUHEIGHT) / 2));
            drawSoundTrack(g, sd.getSoundTracks().get(sd.getCurrentSoundTrackIndex()));
            g.drawRect(0, 0, GUI.WIDTH / 2, (GUI.HEIGHT - GUI.MENUHEIGHT) / 2 - 1);
        } catch (NoSoundTrackSelectedException ex) {
            // Holder
        }
    }

    // EFFECTS: Draws a soundtrack
    private void drawSoundTrack(Graphics g, SoundTrack st) {
        counter = PADDING;

        for (TimePosition t : st.getTimePositions()) {
            if (t.getBeat() == 1 && t.getSubBeat() == 1) {
                drawMeasureDivider(g, counter, HEIGHT_CENTER);
                counter += st.getBeatsPerMeasure() * PIXELS_BETWEEN_TIMEPOSITION;
            }

            if (t.noteExist()) {
                drawNote(g, counter, HEIGHT_CENTER);
            } else {
                if (t.getSubBeat() == 1) {
                    drawBeatDivider(g, counter, HEIGHT_CENTER);
                }
            }
            counter += PIXELS_BETWEEN_TIMEPOSITION;
        }
    }

    // EFFECTS: Draws a note
    private void drawNote(Graphics g, int x, int y) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(x, y + BEAT_DIVIDER_HEIGHT / 2, NOTE_WIDTH, NOTE_HEIGHT);
    }

    // EFFECTS: Draws measure divider
    private void drawMeasureDivider(Graphics g, int x, int y) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(x, y, MEASURE_DIVIDER_WIDTH, MEASURE_DIVIDER_HEIGHT);
    }

    // EFFECTS: Draws measure divider
    private void drawBeatDivider(Graphics g, int x, int y) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(x, y + BEAT_DIVIDER_HEIGHT / 2, BEAT_DIVIDER_WIDTH, BEAT_DIVIDER_HEIGHT);
    }

    // EFFECTS: returns the amount of pixels between measures
    public int pixelsPerMeasure() {
        SoundTrack s;
        try {
            s = sd.getSoundTracks().get(sd.getCurrentSoundTrackIndex());
            ;
            return s.getLength() * s.getBeatsPerMeasure() * s.getBeatsPerMeasure() * PIXELS_BETWEEN_TIMEPOSITION;
        } catch (NoSoundTrackSelectedException ex) {
            return 1;
        }
    }
}
