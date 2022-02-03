package model;

import model.event.Event;
import model.exception.NoteEmptyException;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.Main;

import java.util.ArrayList;
import java.util.List;

/* The SoundTrack class represents a single song. A SoundTrack comprises multiple notes.
 * The notes are organized in standard music notation: Measures comprised of x number of beats with each beat n number
 * of seconds long.
 * Measures contain beats which contain sub-beats. The number of beat and sub-beats per measure is decided by the user
 * during the creation of a SoundTrack.
 */
public class SoundTrack {
    private final String name;            // Name of the Sound Track
    private final int beatsPerMeasure;    // The number on the top of the time signature
    private final int noteKind;           // The number on the bottom of the time signature
    private final int bpm;                // The number of beats per minute
    private final int length;             // The length in measures of the SoundTrack
    private final int totalSubBeats;      // Total Amount of subBeats in the track
    private double timeBetweenSubBeats;   // Amount of time in milliseconds between subBeats
    private final List<TimePosition> track = new ArrayList<>();   // The list containing notes

    // MODIFIES: name
    // EFFECTS: Constructs a black soundtrack
    public SoundTrack(String name, int beatsPerMeasure, int noteKind, int bpm, int length) {
        this.name = name;
        this.beatsPerMeasure = beatsPerMeasure;
        this.noteKind = noteKind;
        this.bpm = bpm;
        this.length = length;
        totalSubBeats = beatsPerMeasure * beatsPerMeasure * length;
        timeBetweenSubBeats = ((1 / (double) bpm * 60) / (double) beatsPerMeasure) * 1000;

        // Sets up the track to have space for each note and sub note. One index for each subBeat.
        for (int measure = 1; measure <= length; measure++) {
            for (int beat = 1; beat <= beatsPerMeasure; beat++) {
                for (int subBeat = 1; subBeat <= beatsPerMeasure; subBeat++) {
                    track.add(new TimePosition(measure, beat, subBeat));
                }
            }
        }
    }

    // EFFECTS: Return list of time position for this sound track
    public List<TimePosition> getTimePositions() {
        return track;
    }

    // MODIFIES: track
    // EFFECTS: Removes at note at a certain time position
    public boolean removeNote(TimePosition t) {
        for (TimePosition t2 : track) {
            if ((t2.getMeasure() == t.getMeasure()) && (t2.getBeat() == t.getBeat())
                    && (t2.getSubBeat() == t.getSubBeat())) {
                Main.eventLog.logEvent(new Event("Note: " + " removed from "
                        + name + " at: " + t2.getMeasure() + "." + t2.getBeat() + "." + t2.getSubBeat()));
                t2.removeNote();
                return true;
            }
        }
        return false;
    }

    // MODIFIES: track
    // EFFECTS: Adds a note to a desired point in the track, returns true if successfully, false if not
    public boolean add(TimePosition t) {
        for (TimePosition t2 : track) {
            if ((t2.getMeasure() == t.getMeasure()) && (t2.getBeat() == t.getBeat())
                    && (t2.getSubBeat() == t.getSubBeat())) {
                try {
                    t2.addNote(t.getNote());
                    Main.eventLog.logEvent(new Event("Note: " + t.getNote().getPitch() + " added to " + name
                            + " at: " + t2.getMeasure() + "." + t2.getBeat() + "." + t2.getSubBeat()));
                } catch (NoteEmptyException e) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns length
    public int getLength() {
        return length;
    }

    // EFFECTS: Returns the bpm
    public int getBPM() {
        return bpm;
    }

    // EFFECTS: Returns the name of the soundtrack
    public String getName() {
        return name;
    }

    // EFFECTS: Returns the beatsPerMeasure
    public int getBeatsPerMeasure() {
        return beatsPerMeasure;
    }

    // EFFECTS: Returns true if a note is at timeposition specified, false if not.
    public boolean noteExists(TimePosition t) {
        for (TimePosition t2 : track) {
            if ((t2.getMeasure() == t.getMeasure()) && (t2.getBeat() == t.getBeat())
                    && (t2.getSubBeat() == t.getSubBeat())) {
                return t2.noteExist();
            }
        }
        return false;
    }

    // EFFECTS: Returns true if a timeposition specified exists in , false if not.
    public boolean timePositionExists(TimePosition t) {
        for (TimePosition t2 : track) {
            if ((t2.getMeasure() == t.getMeasure()) && (t2.getBeat() == t.getBeat())
                    && (t2.getSubBeat() == t.getSubBeat())) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: A note must exist a time position t
    // EFFECTS: Returns the note at time position t
    public Note getNote(TimePosition t) {
        Note noteHold = new Note("c3", 127);
        for (TimePosition t2 : track) {
            if ((t2.getMeasure() == t.getMeasure()) && (t2.getBeat() == t.getBeat())
                    && (t2.getSubBeat() == t.getSubBeat())) {
                try {
                    noteHold = t2.getNote();
                } catch (NoteEmptyException e) {
                    return null;
                }
            }
        }
        return noteHold;
    }

    // EFFECTS: returns timebetweensubbeats in milliseconds
    public int getTimeBetweenSubBeats() {
        return (int) timeBetweenSubBeats;
    }

    // EFFECTS: Returns the track array
    public List<TimePosition> getTrack() {
        return track;
    }

    // EFFECTS: Returns a JSON object representing a soundtrack.
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", name);
        jsonObject.put("BPMeasure", beatsPerMeasure);
        jsonObject.put("NK", noteKind);
        jsonObject.put("bpm", bpm);
        jsonObject.put("length", length);
        jsonObject.put("TimePosition", timePositionToJason());

        return jsonObject;
    }

    // EFFECTS: Returns a json array representations of the timepositions in the soundtrack
    public JSONArray timePositionToJason() {
        JSONArray jsonArray = new JSONArray();

        for (TimePosition t : track) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
