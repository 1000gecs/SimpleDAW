package persistence;

import model.SoundTrack;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJsonWriter {
    JsonWriter jw;
    String path;

    @Test
    public void TestOpen() {
        path = "data/persistancedata/test/notrealfile.json";
        jw = new JsonWriter(path);

        try {
            jw.open();
            fail("File Not Found Exception not Called...");
        } catch (FileNotFoundException e) {

        }

        path = "data/persistencedata/test/testopen.json";
        jw = new JsonWriter(path);

        try {
            jw.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("FileNotFoundException called...");
        }

    }

    @Test
    public void TestWrite() {
        path = "data/persistencedata/test/testwrite.json";
        JSONArray ja = new JSONArray();
        JsonReader jr = new JsonReader(path);
        jw = new JsonWriter(path);
        SoundTrack st1 = new SoundTrack("meme 1", 1, 2, 3, 4);
        SoundTrack st2 = new SoundTrack("meme 2", 5, 6, 7, 8);

        ja.put(st1.toJson());
        ja.put(st2.toJson());

        try {
            jw.open();
        } catch (FileNotFoundException e) {
            fail("File Not Found exception shouldn't have been called");
        }
        jw.write(ja);
        jw.close();

        try {
            assertEquals("meme 1", jr.read().get(0).getName());
            assertEquals("meme 2", jr.read().get(1).getName());
        } catch (IOException e) {
            fail("IOException Called");
        }
    }


    @Test
    public void TestClose() {
        path = "data/persistencedata/test/testclose.json";
        jw = new JsonWriter(path);

        try {
            jw.open();
        } catch (FileNotFoundException e) {
            fail("File Not Found Exception Called");
        }
        jw.close();
    }
//
//    @Test
//    public void TestSaveToFile() {
//        path = "data/persistancedata/test/testsavetofile.json";
//        jw = new JsonWriter(path);
//
//    }

}
