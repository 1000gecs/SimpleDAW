package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.sound.SoundTrackSound;

import static org.junit.jupiter.api.Assertions.*;

public class TestSoundTrack {
    SoundTrack st1;

    @BeforeEach
    public void makeSoundTrack() {
        st1 = new SoundTrack("Test Track", 3, 4, 120, 20);
    }

    @Test
    public void testPlay() {
        SoundTrackSound soundTrackSound = new SoundTrackSound();

        st1.add(new TimePosition(1, 1, 1, new Note("c3", 127)));
        st1.add(new TimePosition(2, 1, 1, new Note("c3", 127)));
        st1.add(new TimePosition(2, 2, 1, new Note("c3", 127)));
        st1.add(new TimePosition(3, 1, 1, new Note("c3", 127)));
        assertTrue(soundTrackSound.play(st1));
    }

    @Test
    public void testAdd() {
        st1.add(new TimePosition(1, 1, 1, new Note("c3", 127)));
        assertTrue(st1.noteExists(new TimePosition(1, 1, 1)));
        assertFalse(st1.noteExists(new TimePosition(1, 2, 1)));

        st1.add(new TimePosition(1, 2, 1, new Note("c3", 127)));
        assertTrue(st1.noteExists(new TimePosition(1, 2, 1)));
        assertFalse(st1.noteExists(new TimePosition(1, 3, 1)));

        assertFalse(st1.add(new TimePosition(30, 2, 1, new Note("c3", 127))));
        assertFalse(st1.add(new TimePosition(2, 2, 1)));
    }

    @Test
    public void TestExists() {
        assertFalse(st1.noteExists(new TimePosition(1, 1, 1)));
        assertFalse(st1.noteExists(new TimePosition(21, 1, 1)));
        assertFalse(st1.noteExists(new TimePosition(20, 3, 3)));
    }

    @Test
    public void testGetBPM() {
        assertEquals(120, st1.getBPM());
    }

    @Test
    public void testGetBeatsPerMeasure() {
        assertEquals(3, st1.getBeatsPerMeasure());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Track", st1.getName());
    }

    @Test
    public void testGetNote() {
        st1.add(new TimePosition(1, 1, 1, new Note("c3", 127)));
        st1.add(new TimePosition(2, 1, 1));

        assertEquals("c3", st1.getNote(new TimePosition(1, 1, 1)).getPitch());
        assertNull(st1.getNote(new TimePosition(2, 1, 1)));
    }

    @Test
    public void testGetTimeBetweenSubBeats() {
        assertEquals(Math.floor(((1 / (double) st1.getBPM() * 60) / (double) st1.getBeatsPerMeasure()) * 1000), st1.getTimeBetweenSubBeats());
    }

    @Test
    public void testRemoveNote() {
        st1.add(new TimePosition(1, 1, 1, new Note("c3", 127)));
        assertEquals("c3", st1.getNote(new TimePosition(1, 1, 1)).getPitch());
        assertTrue(st1.noteExists(new TimePosition(1, 1, 1)));

        assertTrue(st1.removeNote(new TimePosition(1, 1, 1)));
        assertFalse(st1.noteExists(new TimePosition(1, 1, 1)));

        assertFalse(st1.removeNote(new TimePosition(100, 1, 1)));
        assertFalse(st1.removeNote(new TimePosition(101230, 10123001, 199923)));
        assertFalse(st1.removeNote(new TimePosition(1, 10123001, 1)));
        assertFalse(st1.removeNote(new TimePosition(1, 1, 199923)));
    }

    @Test
    public void testGetTrack() {
        SoundTrack st2 = st1;
        assertEquals(st2.getTrack(), st1.getTrack());
    }

    @Test
    public void testToJason() {
        assertEquals("Test Track", st1.toJson().getString("Name"));
        assertEquals(3, st1.toJson().getInt("BPMeasure"));
        assertEquals(4, st1.toJson().getInt("NK"));
        assertEquals(120, st1.toJson().getInt("bpm"));
        assertEquals(20, st1.toJson().getInt("length"));
    }

    @Test
    public void testGetLength() {
        assertEquals(st1.getLength(), 20);
    }

    @Test
    public void testTimePositionExists() {
        assertTrue(st1.timePositionExists(st1.getTimePositions().get(0)));
        assertFalse(st1.timePositionExists(new TimePosition(1000, 1, 1)));
        assertFalse(st1.timePositionExists(new TimePosition(1, 10000, 1)));
        assertFalse(st1.timePositionExists(new TimePosition(1, 1, 10000)));
    }
}
