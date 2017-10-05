package cs3500.music.util;

import org.junit.Test;

import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;

/**
 * Tests the MusicComposition class.
 */
public class MusicCompositionTest {

  @Test
  public void testEmptyBuild1() {
    CompositionBuilder<IMusicModel> musicBuilder = new MusicComposition(4);
    IMusicModel model = musicBuilder.build();

    assertEquals(model.getMusicState(), new EditorModel(4).getMusicState());
  }

  @Test
  public void testAddNotesAndBuild() {
    CompositionBuilder<IMusicModel> musicBuilder = new MusicComposition(4);
    Note note1 = new Note(Note.Pitch.C, 1, 2, 3, 4, 5);
    Note note2 = new Note(Note.Pitch.D, 2, 2, 3, 4, 5);
    Note note3 = new Note(Note.Pitch.ASHARP, 1, 2, 1, 1, 3);
    musicBuilder.addNote(2, 5, 4, note1.hashCode(), 5);
    musicBuilder.addNote(2, 5, 4, note2.hashCode(), 5);
    musicBuilder.addNote(2, 3, 1, note3.hashCode(), 3);
    IMusicModel model = musicBuilder.build();


    IMusicModel myModel = new EditorModel(4);
    myModel.addNote(note1);
    myModel.addNote(note2);
    myModel.addNote(note3);

    assertEquals(model.getMusicState(), myModel.getMusicState());
  }

  @Test
  public void testAddNotesAndBuild2() {
    CompositionBuilder<IMusicModel> musicBuilder = new MusicComposition(4);
    Note note1 = new Note(Note.Pitch.A, 1, 2, 3, 4, 5);
    Note note2 = new Note(Note.Pitch.A, 2, 2, 3, 4, 5);
    Note note3 = new Note(Note.Pitch.ASHARP, 1, 2, 1, 1, 3);
    musicBuilder.addNote(2, 5, 4, note1.hashCode(), 5);
    musicBuilder.addNote(2, 5, 4, note2.hashCode(), 5);
    musicBuilder.addNote(2, 3, 1, note3.hashCode(), 3);
    IMusicModel model = musicBuilder.build();


    IMusicModel myModel = new EditorModel(4);
    myModel.addNote(note1);
    myModel.addNote(note2);
    myModel.addNote(note3);

    assertEquals(model.getMusicState(), myModel.getMusicState());
  }

  @Test
  public void testAddNotesAndBuild3() {
    CompositionBuilder<IMusicModel> musicBuilder = new MusicComposition(4);
    Note note1 = new Note(Note.Pitch.A, 1, 2, 3, 4, 5);
    Note note2 = new Note(Note.Pitch.A, 2, 2, 3, 4, 5);
    Note note3 = new Note(Note.Pitch.A, 3, 2, 1, 1, 3);
    musicBuilder.addNote(2, 5, 4, note1.hashCode(), 5);
    musicBuilder.addNote(2, 5, 4, note2.hashCode(), 5);
    musicBuilder.addNote(2, 3, 1, note3.hashCode(), 3);
    IMusicModel model = musicBuilder.build();


    IMusicModel myModel = new EditorModel(4);
    myModel.addNote(note1);
    myModel.addNote(note2);
    myModel.addNote(note3);

    assertEquals(model.getMusicState(), myModel.getMusicState());
  }

  @Test
  public void testChangeTempo() {
    CompositionBuilder<IMusicModel> musicBuilder = new MusicComposition(4);

    musicBuilder.setTempo(1000000);
    IMusicModel model = musicBuilder.build();
    IMusicModel myModel = new EditorModel(4, 1000);

    assertEquals(model.getTempo(), myModel.getTempo());
  }
}