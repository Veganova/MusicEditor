package cs3500.music.view;

import org.junit.Test;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.mock.MockMidiView;
import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;

/**
 * Tests the mock midi.
 */
public class MockMidiTests {

  @Test
  public void MockMidiViewTest() {
    StringBuilder log = new StringBuilder();
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(Note.Pitch.C, 3, 0, 3, 0, 64);

    try {
      MockMidiView mockMidi = new MockMidiView(musicModel, log);
    } catch (MidiUnavailableException e) {
      System.out.println("mockMidi broken");
    }

    assertEquals("192 0 0 0\n" +
                    "144 0 36 64\n" +
                    "128 0 36 64\n",
            log.toString());

  }

  @Test
  public void MockMidiEmptyTest() {
    StringBuilder log = new StringBuilder();
    IMusicModel musicModel = new EditorModel(4);

    try {
      MockMidiView mockMidi = new MockMidiView(musicModel, log);
    } catch (MidiUnavailableException e) {
      System.out.println("mockMidi broken");
    }

    assertEquals("Receiver closed.\n", log.toString());
  }

  @Test
  public void MockMidiClosedTest() {
    StringBuilder log = new StringBuilder();
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(Note.Pitch.C, 3, 0, 1, 0, 64);

    MockMidiView mockMidi = null;
    try {
      mockMidi = new MockMidiView(musicModel, log);
    } catch (MidiUnavailableException e) {
      System.out.println("mockMidi broken");
    }

    musicModel.incrementTime(1);
    mockMidi.playCurrent();


    assertEquals("192 0 0 0\n" +
                    "144 0 36 64\n" +
                    "128 0 36 64\n" +
                    "Receiver closed.\n",
            log.toString());
  }

  @Test
  public void MockMidiChordTest() {
    StringBuilder log = new StringBuilder();
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(Note.Pitch.C, 3, 0, 3, 0, 64);
    musicModel.addNote(Note.Pitch.E, 4, 0, 2, 0, 64);
    musicModel.addNote(Note.Pitch.FSHARP, 2, 0, 2, 0, 64);

    try {
      MockMidiView mockMidi = new MockMidiView(musicModel, log);
    } catch (MidiUnavailableException e) {
      System.out.println("mockMidi broken");
    }

    assertEquals("192 0 0 0\n" +
                    "144 0 30 64\n" +
                    "192 0 0 0\n" +
                    "144 0 36 64\n" +
                    "192 0 0 0\n" +
                    "144 0 52 64\n" +
                    "128 0 30 64\n" +
                    "128 0 36 64\n" +
                    "128 0 52 64\n",
            log.toString());

  }

  @Test
  public void MockMidiOverlap() {
    StringBuilder log = new StringBuilder();
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(Note.Pitch.C, 3, 0, 3, 0, 64);
    musicModel.addNote(Note.Pitch.C, 3, 1, 2, 0, 64);

    try {
      MockMidiView mockMidi = new MockMidiView(musicModel, log);
      mockMidi.playCurrent();
    } catch (MidiUnavailableException e) {
      System.out.println("mockMidi broken");
    }

    assertEquals("192 0 0 0\n" +
                    "144 0 36 64\n" +
                    "128 0 36 64\n" +
                    "192 0 0 0\n" +
                    "144 0 36 64\n" +
                    "128 0 36 64\n",
            log.toString());
  }

  @Test
  public void MockMidiLong() {
    StringBuilder log = new StringBuilder();
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(Note.Pitch.C, 3, 0, 3, 0, 64);
    musicModel.addNote(Note.Pitch.C, 3, 1000, 2000, 0, 64);

    try {
      MockMidiView mockMidi = new MockMidiView(musicModel, log);
      mockMidi.playCurrent();
    } catch (MidiUnavailableException e) {
      System.out.println("mockMidi broken");
    }

    assertEquals("192 0 0 0\n" +
                    "144 0 36 64\n" +
                    "128 0 36 64\n" +
                    "192 0 0 0\n" +
                    "144 0 36 64\n" +
                    "128 0 36 64\n",
            log.toString());
  }
}
