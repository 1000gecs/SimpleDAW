package persistence;

import model.SoundTrack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// JsonWriter is a writer that writes JSON representation of soundtracks
public class JsonWriter {
    private static final int TAB = 4;
    private final String filePath;
    private PrintWriter writer;

    // EFFECTS: Constructs a json object that takes in a path for the data to be written to.
    public JsonWriter(String filePath) {
        this.filePath = filePath;
    }

    // MODIFIES: writer
    // EFFECTS: opens the writer; throws FileNotFoundException if file cannot be opened to write
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(filePath));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of soundtrack to file
    public void write(JSONArray ja) {
        JSONObject json = new JSONObject();
        json.put("Soundtracks", ja);
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: writer
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: writer
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
