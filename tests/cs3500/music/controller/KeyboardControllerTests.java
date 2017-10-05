package cs3500.music.controller;

import org.junit.Test;

import java.util.ArrayList;

import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.util.IViewFactory;
import cs3500.music.util.KeyViewFactory;

import static org.junit.Assert.assertEquals;

/**
 * Tests methods in the KeyboardController class.
 */
public class KeyboardControllerTests {

  @Test
  public void testAddView() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.E, 3, 0, 2, 0, 64);
    editor.addNote(Note.Pitch.F, 3, 0, 4, 0, 64);

    IMusicModel toOverlay = new EditorModel(4);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 0, 6, 0, 64);
    toOverlay.addNote(Note.Pitch.D, 3, 2, 2, 0, 64);
    toOverlay.addNote(Note.Pitch.B, 2, 0,2, 0, 64);
    editor.overlayNotes(toOverlay);

    StringBuilder out = new StringBuilder();
    IViewFactory<IMusicController> viewFactory = new KeyViewFactory(editor,
            "arrow", out);

    String console = "console";
    IMusicController controller = viewFactory.addView(console);
    String result = "   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3 \n" +
            "0  X                        X    X                        X  \n" +
            "1  |                        |    |                        |  \n" +
            "2                 X              |                        |  \n" +
            "3                 |              |                        |  \n" +
            "4                                                         |  \n" +
            "5                                                         |  \n";

    assertEquals(result, out.toString());
  }

  @Test
  public void testAddViewsAndAddNote() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.E, 3, 0, 2, 0, 64);
    editor.addNote(Note.Pitch.F, 3, 0, 4, 0, 64);

    StringBuilder out = new StringBuilder();
    IViewFactory<IMusicController> viewFactory = new KeyViewFactory(editor,
            "arrow", out);

    String type = "composite";
    IMusicController controller = viewFactory.addView(type);

    controller.addNoteToModel(Note.Pitch.C, 1, 6);
    ArrayList<Note> notes = new ArrayList<Note>();
    notes.add(new Note(Note.Pitch.C, 1, 6, 1, 0, 64));

    assertEquals(notes, editor.getNotesAtTime(6));
  }
}