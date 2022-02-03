package model;

import model.exception.NoteEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTimePosition {
    TimePosition t1;

    @BeforeEach
    public void makeTimePosition() {
        t1 = new TimePosition(1, 2, 3);
    }

    @Test
    public void testGetMeasure() {
        assertEquals(1, t1.getMeasure());
    }

    @Test
    public void testGetBeat() {
        assertEquals(2, t1.getBeat());
    }

    @Test
    public void testGetSubBeat() {
        assertEquals(3, t1.getSubBeat());
    }

    @Test
    public void TestAddNote() {
        t1.addNote(new Note("c3", 127));
        assertTrue(t1.noteExist());

        TimePosition t2 = new TimePosition(5, 1, 2);
        assertFalse(t2.noteExist());
    }

    @Test
    public void TestNoteExist() {
        assertFalse(t1.noteExist());

        TimePosition t2 = new TimePosition(5, 1, 2,
                new Note("e3", 127));
        assertTrue(t2.noteExist());
    }

    @Test
    public void TestRemoveNote() {
        t1.addNote(new Note("c3", 127));
        assertTrue(t1.noteExist());

        t1.removeNote();
        assertFalse(t1.noteExist());

    }

    @Test
    public void TestGetNote() {
        try {
            t1.getNote();
            fail("Exception should have been called.");
        } catch (NoteEmptyException e) {

        }
        t1.addNote(new Note("c3", 127));
        try {
            assertEquals(t1.getNote().getPitch(), "c3");
        } catch (NoteEmptyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToJson() {
        assertEquals(1, t1.toJson().getInt("measure"));
        assertEquals(2, t1.toJson().getInt("beat"));
        assertEquals(3, t1.toJson().getInt("subBeat"));

        t1.addNote(new Note("c3", 127));
        try {
            assertEquals(t1.getNote().getPitch(), t1.toJson().getJSONObject("note").getString("notePitch"));
        } catch (NoteEmptyException e) {
            fail();
        }
    }

}
