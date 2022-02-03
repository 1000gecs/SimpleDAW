package persistence;

import model.Note;
import model.SoundTrack;
import model.TimePosition;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader {
    private static ArrayList<SoundTrack> stl;

    @BeforeAll
    public static void generateJsonReadFile() {
        String path = "data/persistencedata/test/testread.json";
        JSONArray jsonArray = new JSONArray();
        JsonWriter jw = new JsonWriter(path);
        SoundTrack st1 = new SoundTrack("meme 1", 1, 2, 3, 4);
        SoundTrack st2 = new SoundTrack("meme 2", 5, 6, 7, 8);
        stl = new ArrayList<>();

        st1.add(new TimePosition(1, 1, 1, new Note("c3", 127)));
        stl.add(st1);
        stl.add(st2);

        for (SoundTrack st : stl) {
            jsonArray.put(st.toJson());
        }

        try {
            jw.open();
            jw.write(jsonArray);
            jw.close();

            path = "data/persistencedata/test/testreadnost.json";
            jw = new JsonWriter(path);
            jw.open();
            jw.write(new JSONArray());
            jw.close();
        } catch (IOException e) {
            fail("Before all couldnot start because of called IOEXception");
        }
    }

    @Test
    public void testReadWithTwoSoundTracks() {
        String path = "data/persistencedata/test/testread.json";
        JsonReader jr = new JsonReader(path);
        ArrayList<SoundTrack> stl2 = new ArrayList<>();

        try {
            stl2 = jr.read();
            assertEquals(stl.get(0).getName(), stl2.get(0).getName());
            assertEquals(stl.get(1).getName(), stl2.get(1).getName());
        } catch (IOException e) {
            fail("IOException Shouldn't have been called.");
        }
    }

    @Test
    public void testReadWithNoSoundTracks() {
        String path = "data/persistencedata/test/testreadnost.json";
        JsonReader jr = new JsonReader(path);
        ArrayList<SoundTrack> stl2 = new ArrayList<>();

        try {
            stl2 = jr.read();
            assertTrue(stl2.isEmpty());
        } catch (IOException e) {
            fail("IOException Shouldn't have been called.");
        }
    }


}
