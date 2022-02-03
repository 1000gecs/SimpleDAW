package model;

import model.event.Event;
import model.exception.NoteEmptyException;
import org.json.JSONObject;
import ui.Main;

/*
 * The TimePosition class represents a specific position on a musical chart. Starting a 1.1.1, the first number
 * represents the measure number, the second represents the beat inside that measure, and the third number represents
 * the sub-beat in between the beats.
 */
public class TimePosition {
    private final int measure;      // The measure
    private final int beat;         // The beat in the measure (beats in measure depend on bpm of soundtrack)
    private final int subBeat;      // The position in the beat (same number of subBets per beat as beats per measure)
    private Note note;              // The note that is contained on this TimePosition. (may or may not exist)
    private boolean noteStatus;

    // MODIFIES: measure, beat, subBeat, beatsPerBar, noteStatus
    // EFFECTS: Creates a new TimePosition which represents a specific position in a SoundTrack
    public TimePosition(int measure, int beat, int subBeat) {
        this.measure = measure;
        this.beat = beat;
        this.subBeat = subBeat;
        noteStatus = false;
    }

    // MODIFIES: measure, beat, subBeat, beatsPerBar, note, noteStatus
    // EFFECTS: Creates a new TimePosition which represents a specific position in a SoundTrack and adds a note to T.P.
    public TimePosition(int measure, int beat, int subBeat, Note note) {
        this.measure = measure;
        this.beat = beat;
        this.subBeat = subBeat;
        //this.beatsPerMeasure = beatsPerMeasure;
        this.note = note;
        noteStatus = true;
    }

    // MODIFIES: note
    // EFFECTS: Assigns this TimePosition a note (can overwrite a note)
    public void addNote(Note n) {
        note = n;
        noteStatus = true;
    }

    // MODIFIES: note
    // EFFECTS: Deletes the note by assigning noteStatus to false
    public void removeNote() {
        noteStatus = false;
    }

    // EFFECTS Returns if a note exists at this time position
    public boolean noteExist() {
        return noteStatus;
    }

    // EFFECTS: Returns the measure value
    public int getMeasure() {
        return measure;
    }

    // EFFECTS: Returns the beat value
    public int getBeat() {
        return beat;
    }

    // EFFECTS: Returns the subBeat value
    public int getSubBeat() {
        return subBeat;
    }

    // EFFECTS: Returns the note
    public Note getNote() throws NoteEmptyException {
        if (noteExist()) {
            return note;
        } else {
            throw new NoteEmptyException();
        }
    }

    // EFFECTS: Returns a json representation of a time position
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("measure", measure);
        json.put("beat", beat);
        json.put("subBeat", subBeat);
        if (noteExist()) {
            json.put("note", note.toJson());
        }
        return json;
    }
}
