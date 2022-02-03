package model;


import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.lang.String;

import java.io.File;

/*
 * The note class represents a single musical note. A note has a specific pitch that is expressed in standard musical
 * notation e.g. c3, a4, g3 ect. A note also has a velocity which represents the volume that the note is played at.
 */
public class Note {
    private final String notePitch;             // notePitch to be one of {"A", "B", "C", "D", "E", "F", "G"}
    private final int noteVelocity;             // The volume of the note, int only from 0-127
    private final String noteCollectionPath = "data/PianoNotes"; // The relative path to the notes folder
    private String filePath;

    // REQUIRES: noteVelocity to ONLY exist in the range 0-127
    //           notePitch to be one of {"A", "B", "C", "D", "E", "F", "G"}
    // MODIFIES: notePitch and note Velocity
    // EFFECTS: Creates a note object with a given pitch and velocity.
    public Note(String notePitch, int noteVelocity) {
        this.notePitch = notePitch;
        this.noteVelocity = noteVelocity;
        try {
            this.filePath = this.getFilePath();
        } catch (FileNotFoundException e) {
            this.filePath = "src/main/model/PianoNotes/c3.wav"; //defaults to c3
            e.printStackTrace();
        }
    }

    // EFFECTS: Returns the pitch of this note
    public String getPitch() {
        return notePitch;
    }

    // REQUIRES: notePitch to be one of {"A", "B", "C", "D", "E", "F", "G"}
    // EFFECTS: Gets the filepath for the specific note, returns "NOTFOUND" is not found
    public String getFilePath() throws FileNotFoundException {
        File notesCollection = new File(noteCollectionPath);
        File[] notes = notesCollection.listFiles();
        int indexOfW;

        for (File n : notes) {
            indexOfW = n.getName().indexOf("."); // Returns the index of w, the index chop off the .wav
            if (n.getName().substring(0, indexOfW).equals(notePitch)) {
                return n.toPath().toString();        // Returns the relative path to the note
            }
        }
        throw new FileNotFoundException();
    }

    // EFFECTS: returns note as a JSON Object
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("notePitch", notePitch);
        jsonObject.put("noteVelocity", noteVelocity);

        return jsonObject;

    }
}
