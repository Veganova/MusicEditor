package cs3500.music.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * The concrete builder class for IMusicModels.
 */
public class MusicComposition implements CompositionBuilder<IMusicModel> {
  private int measure;
  private List<Note> noteList;
  private long tempo;
  private HashMap<Note, Integer> instruments;
  private HashMap<Note, Integer> velocities;

  /**
   * Makes a new MusicComposition with default values.
   */
  public MusicComposition() {
    // Default values
    noteList = new ArrayList<Note>();
    tempo = 200000;
    this.measure = 4;
  }

  /**
   * Makes a new MusicComposition with a given measure.
   *
   * @param measure how many beats long a measure for this piece of music is
   */
  public MusicComposition(int measure) {
    this();
    this.measure = measure;
  }

  @Override
  public IMusicModel build() {
    IMusicModel musicModel = new EditorModel(this.measure, this.tempo);
    for (Note note : noteList) {
      musicModel.addNote(note);
    }
    return musicModel;
  }

  @Override
  public CompositionBuilder<IMusicModel> setTempo(int tempo) {
    this.tempo = tempo / 1000;
    return this;
  }

  @Override
  public CompositionBuilder<IMusicModel> addNote(int start, int end,
                                                 int instrument, int pitch, int volume) {
    Note.Pitch realPitch = Note.Pitch.values()[pitch %  Note.Pitch.values().length];
    int octave = pitch /  Note.Pitch.values().length;
    Note note = new Note(realPitch, octave, start, end - start, instrument, volume);
    this.noteList.add(note);
    return this;
  }
}
