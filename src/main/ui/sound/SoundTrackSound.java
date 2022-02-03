package ui.sound;

import model.SoundTrack;
import model.TimePosition;
import model.exception.NoteEmptyException;
import ui.sound.NoteSound;

import java.util.concurrent.TimeUnit;

/*
 * Plays a soundtrack
 */
public class SoundTrackSound {

    // EFFECTS: Plays the soundtrack at the bpm, returns true if successful, false if not
    public boolean play(SoundTrack soundTrack) {
        TimeUnit time = TimeUnit.MILLISECONDS;
        NoteSound noteSound;

        for (TimePosition t : soundTrack.getTrack()) {
            if (t.noteExist()) {
                noteSound = new NoteSound();
                try {
                    noteSound.play(t.getNote());
                } catch (NoteEmptyException e) {
                    soundTrack.getBPM(); //TODO get rid of this place holder
                }
            }

            try {
                time.sleep(soundTrack.getTimeBetweenSubBeats());
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
