package ui;

import model.Note;
import model.SoundTrack;
import model.TimePosition;
import ui.exception.NoneExistentNoteException;
import org.json.JSONArray;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.display.MainWindow;
import ui.exception.NoSoundTrackSelectedException;
import ui.exception.NoSoundTracksCreatedException;
import ui.sound.NoteSound;
import ui.sound.SoundTrackSound;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Represents the SimpleDAW program.
 */
public class SimpleDAW {
    private MainWindow mainWindow;
    private String currentSoundTrackIndex;
    private static ArrayList<SoundTrack> soundTracks = new ArrayList<>();  // Holds Soundtracks that user creates
    private static final String JSON_STORE = "data/soundtracks.json";
    private int editMeasure;
    private int editBeat;
    private int editSubBeat;
    private String editNotePitch;
    private int editNoteVelocity;
    private String createName;
    private int createBeatsPerMeasure;
    private int createBeatValue;
    private int createBeatsPerMin;
    private int createLength;

    // MODIFIES: this
    // EFFECTS: changes the current SoundTrackIndex to one selected by user
    public void select() throws NoSoundTracksCreatedException {
        if (soundTracks.isEmpty()) {
            throw new NoSoundTracksCreatedException();
        } else {
            mainWindow.select(soundTracks);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the currentSoundTrackIndex to index provided
    public void changeCurrentSoundTrackIndex(int index) {
        currentSoundTrackIndex = String.valueOf(index);
        mainWindow.repaint();
    }

    // EFFECTS: reads the saved soundtrack data from JSON_STORE
    public void read() {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        try {
            soundTracks = jsonReader.read();
            System.out.println("READ WORKED GET RIDE OF THIS MESSAGE LATER");
        } catch (IOException e) {
            System.out.println("Unable to read file: " + JSON_STORE);
        }
    }

    // EFFECTS: Save the workroom to file
    public void save() {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(parseSoundTracks(soundTracks));
            jsonWriter.close();
            System.out.println("Saved SoundTracks to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: Parse the list of soundtracks to json array
    public JSONArray parseSoundTracks(ArrayList<SoundTrack> stl) {
        JSONArray jsonArray = new JSONArray();

        for (SoundTrack st : stl) {
            jsonArray.put(st.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: soundTracks
    // EFFECTS: Adds a soundtrack to the running list of soundtracks
    public void addNewSoundTrack() {
        soundTracks.add(new
                SoundTrack(createName, createBeatsPerMeasure, createBeatValue, createBeatsPerMin, createLength));
    }

    // REQUIRES: the index given to be of a soundtrack that exists
    // EFFECTS: Plays note in soundtrack given
    public void playNote(int index) {
        Scanner input = new Scanner(System.in);
        NoteSound noteSound;
        int measure;
        int beat;
        int subBeat;

        System.out.println("Enter Measure:");
        measure = input.nextInt();
        System.out.println("Enter Beat:");
        beat = input.nextInt();
        System.out.println("Enter Sub-Beat:");
        subBeat = input.nextInt();

        noteSound = new NoteSound();
        noteSound.play(soundTracks.get(--index).getNote(new TimePosition(measure, beat, subBeat)));
    }

    // EFFECTS: Plays the SoundTrack
    public void play() throws NoSoundTrackSelectedException, NoSoundTracksCreatedException {
        if (soundTracks.size() > 0) {
            if (currentSoundTrackIndex == null) {
                throw new NoSoundTrackSelectedException();
            } else {
                SoundTrackSound soundTrackSound = new SoundTrackSound();
                soundTrackSound.play(soundTracks.get(Integer.parseInt(currentSoundTrackIndex)));
            }
        } else {
            throw new NoSoundTracksCreatedException();
        }
    }

    // REQUIRES: A soundtrack must exist at index given by user.
    // MODIFIES: soundTracks
    // EFFECTS: Edits a soundtrack that is located in soundTrack.
    public void edit() throws NoSoundTracksCreatedException, NoSoundTrackSelectedException {
        if (soundTracks.isEmpty()) {
            throw new NoSoundTracksCreatedException();
        } else {
            if (currentSoundTrackIndex == null) {
                throw new NoSoundTrackSelectedException();
            } else {
                mainWindow.edit();
            }
        }
    }

    // REQUIRES: A soundtrack must exist at index given by user.
    // MODIFIES: soundTracks
    // EFFECTS: Add Note to a specific point in soundTrack
    public void addNote() throws NoneExistentNoteException {
        if (soundTracks.get(Integer.parseInt(currentSoundTrackIndex)).timePositionExists(new
                TimePosition(editMeasure, editBeat, editSubBeat))) {
            soundTracks.get(Integer.parseInt(currentSoundTrackIndex)).add(
                    new TimePosition(editMeasure, editBeat, editSubBeat, new Note(editNotePitch, editNoteVelocity)));
        } else {
            throw new NoneExistentNoteException();
        }
    }

    // REQUIRES: A soundtrack must exist at index given by user.
    // MODIFIES: soundTracks
    // EFFECTS: removes a note from a specific point in a soundTrack
    public void removeNote() throws NoneExistentNoteException {
        if (soundTracks.get(Integer.parseInt(currentSoundTrackIndex)).noteExists(new
                TimePosition(editMeasure, editBeat, editSubBeat))) {
            soundTracks.get(Integer.parseInt(currentSoundTrackIndex)).removeNote(new
                    TimePosition(editMeasure, editBeat, editSubBeat));
        } else {
            throw new NoneExistentNoteException();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds DisplaySoundTrack object to SimpleDAW
    public void addDisplaySoundTrack(MainWindow dst) {
        this.mainWindow = dst;
    }

    // EFFECTS: returns the currentSoundTrackIndex
    public int getCurrentSoundTrackIndex() throws NoSoundTrackSelectedException {
        if (currentSoundTrackIndex == null) {
            throw new NoSoundTrackSelectedException();
        } else {
            return Integer.parseInt(currentSoundTrackIndex);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the create soundtrack characteristics
    public void changeCreateName(String createName) {
        this.createName = createName;
    }

    // MODIFIES: this
    // EFFECTS: changes the create soundtrack characteristics
    public void changeCreateBeatsPerMeasure(int createBeatsPerMeasure) {
        this.createBeatsPerMeasure = createBeatsPerMeasure;
    }

    // MODIFIES: this
    // EFFECTS: changes the create soundtrack characteristics
    public void changeCreateBeatValue(int createBeatValue) {
        this.createBeatValue = createBeatValue;
    }

    // MODIFIES: this
    // EFFECTS: changes the create soundtrack characteristics
    public void changeCreateBeatsPerMin(int createBeatsPerMin) {
        this.createBeatsPerMin = createBeatsPerMin;
    }

    // MODIFIES: this
    // EFFECTS: changes the create soundtrack characteristics
    public void changeCreateLength(int createLength) {
        this.createLength = createLength;
    }

    // MODIFIES: this
    // EFFECTS: changes the edit note characteristics
    public void changeEditMeasureValue(int editMeasure) {
        this.editMeasure = editMeasure;
    }

    // MODIFIES: this
    // EFFECTS: changes the edit note characteristics
    public void changeEditBeatValue(int editBeat) {
        this.editBeat = editBeat;
    }

    // MODIFIES: this
    // EFFECTS: changes the edit note characteristics
    public void changeEditSubBeatValue(int editSubBeat) {
        this.editSubBeat = editSubBeat;
    }

    // MODIFIES: this
    // EFFECTS: changes the edit note characteristics
    public void changeEditNotePitchValue(String editNotePitch) {
        this.editNotePitch = editNotePitch;
    }

    // MODIFIES: this
    // EFFECTS: changes the edit note characteristics
    public void changeEditNoteVelocityValue(int editNoteVelocity) {
        this.editNoteVelocity = editNoteVelocity;
    }

    // MODIFIES: this
    // EFFECTS: Displays create soundtrack panel for user to enter new info
    public void displayCreateSoundTrack() {
        mainWindow.create();
    }

    // EFFECTS: Returns the list of soundtracks
    public ArrayList<SoundTrack> getSoundTracks() {
        return soundTracks;
    }
}
