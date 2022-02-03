package ui.sound;

import model.Note;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/*
    * Plays a note
 */
public class NoteSound {

    // EFFECTS: Plays the note through audio. Returns true if successful
    public boolean play(Note note) {
        Clip soundClip;
        AudioInputStream audioIS;

        try {
            audioIS = AudioSystem.getAudioInputStream(new File(note.getFilePath()));
            soundClip = AudioSystem.getClip();
            soundClip.open(audioIS);
            soundClip.loop(0);
            soundClip.start();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
