package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.sound.NoteSound;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TestNote {
    Note note1;

    @BeforeEach
    public void makeNoteObject() {
        note1 = new Note("c3", 127);
    }

    @Test
    public void testConstructorBadNoteInput() {
        Note noteMessedUp = new Note("y3", 127);
        assertEquals("y3", noteMessedUp.getPitch());

        NoteSound noteSound = new NoteSound();
        assertFalse(noteSound.play(noteMessedUp));
    }

    @Test
    public void testPlay() {
        NoteSound noteSound = new NoteSound();
        assertTrue(noteSound.play(note1));

        Note noteX = new Note("J", 60);
        assertFalse(noteSound.play(noteX));
    }

    @Test
    public void testGetFilePath() {
        try {
            assertEquals("src/main/model/PianoNotes/c3.wav", note1.getFilePath());

            Note note2 = new Note("a3", 127);
            Note note3 = new Note("a-4", 127);
            Note note4 = new Note("d5", 127);
            assertEquals("src/main/model/PianoNotes/a3.wav", note2.getFilePath());
            assertEquals("src/main/model/PianoNotes/a-4.wav", note3.getFilePath());
            assertEquals("src/main/model/PianoNotes/d5.wav", note4.getFilePath());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException Should not have been called.");
        }

        try {
            Note note5 = new Note("T", 44);
            note5.getFilePath();
            fail();
        } catch (FileNotFoundException e) {
            System.out.println("Passed testGetFilePathTest filedoesnt exist test.");
        }
    }

    @Test
    public void testGetPitch() {

        Note note2 = new Note("a3", 127);
        Note note3 = new Note("a-4", 127);
        Note note4 = new Note("d5", 127);

        assertEquals("c3", note1.getPitch());
        assertEquals("a3", note2.getPitch());
        assertEquals("a-4", note3.getPitch());
        assertEquals("d5", note4.getPitch());
    }

    @Test
    public void testToJson() {
        assertEquals(note1.getPitch(), note1.toJson().getString("notePitch"));
        assertEquals(127, note1.toJson().getInt("noteVelocity"));
    }
}