package persistence;

import model.Note;
import model.SoundTrack;
import model.TimePosition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads list of soundtracks from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<SoundTrack> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSoundTrackList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses soundtrack from JSONObject and returns it
    private ArrayList<SoundTrack> parseSoundTrackList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Soundtracks");
        ArrayList<SoundTrack> ls = new ArrayList<SoundTrack>();
        SoundTrack st;
        String name;
        int bpmeasure;
        int noteKind;
        int bpm;
        int length;

        for (Object json : jsonArray) {
            JSONObject soundTrack = (JSONObject) json;
            name = soundTrack.getString("Name");
            bpmeasure = soundTrack.getInt("BPMeasure");
            noteKind = soundTrack.getInt("NK");
            bpm = soundTrack.getInt("bpm");
            length = soundTrack.getInt("length");

            st = new SoundTrack(name, bpmeasure, noteKind, bpm, length);
            addTimePositions(st, soundTrack);
            ls.add(st);
        }
        return ls;
    }

    // MODIFIES: st
    // EFFECTS: parses the timePositions in soundtrack from JSON Object and adds them to the soundtrack
    private void addTimePositions(SoundTrack st, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("TimePosition");
        int measure;
        int beat;
        int subBeat;
        Note note;

        for (Object json : jsonArray) {
            JSONObject nextTimePosition = (JSONObject) json;
            measure = nextTimePosition.getInt("measure");
            beat = nextTimePosition.getInt("beat");
            subBeat = nextTimePosition.getInt("subBeat");
            try {
                note = addNote(st, (JSONObject) nextTimePosition.get("note"));
                st.add(new TimePosition(measure, beat, subBeat, note));
            } catch (JSONException e) {
                st.add(new TimePosition(measure, beat, subBeat));
            }
        }
    }

    // MODIFIES: st
    // EFFECTS: parses the note in a time position from JSON Object and adds them to the soundtrack
    private Note addNote(SoundTrack st, JSONObject jsonObject) {
        String pitch = jsonObject.getString("notePitch");
        int velocity = jsonObject.getInt("noteVelocity");

        return new Note(pitch, velocity);
    }
}
