package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.KeyEvent;

import cs3500.music.mock.MockKeyHandler;
import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;

/**
 * Tests the KeyHandler class.
 */
public class KeyHandlerTest {
  @Test
  public void testConfigure() {
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(new Note(Note.Pitch.C, 1,10));

    StringBuilder log = new StringBuilder();
    MockKeyHandler mkh = new MockKeyHandler(musicModel, log);

    assertEquals("", log.toString());
  }

  @Test
  public void testKeyPressed() {
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(new Note(Note.Pitch.C, 1,10));

    StringBuilder log = new StringBuilder();
    MockKeyHandler mkh = new MockKeyHandler(musicModel, log);

    mkh.mockKeyPressed(KeyEvent.VK_RIGHT);
    assertEquals("right arrow makes time: 1\n", log.toString());

    mkh.mockKeyPressed(KeyEvent.VK_LEFT);
    assertEquals("right arrow makes time: 1\n" +
            "left arrow makes time: 0\n", log.toString());

    mkh.mockKeyPressed(KeyEvent.VK_SPACE);
    assertEquals("right arrow makes time: 1\n" +
            "left arrow makes time: 0\n" +
            "spacebar makes model's paused: false\n", log.toString());

    mkh.mockKeyPressed(KeyEvent.VK_SPACE);
    assertEquals("right arrow makes time: 1\n" +
            "left arrow makes time: 0\n" +
            "spacebar makes model's paused: false\n" +
            "spacebar makes model's paused: true\n", log.toString());
  }

  @Test
  public void testHomeAndEnd() {
    IMusicModel musicModel = new EditorModel(4);
    musicModel.addNote(new Note(Note.Pitch.C, 1,10));

    StringBuilder log = new StringBuilder();
    MockKeyHandler mkh = new MockKeyHandler(musicModel, log);

    mkh.mockKeyPressed(KeyEvent.VK_END);
    assertEquals("end button makes time: 10\n", log.toString());

    mkh.mockKeyPressed(KeyEvent.VK_HOME);
    assertEquals("end button makes time: 10\n" +
            "home button makes time: 0\n", log.toString());

  }
}