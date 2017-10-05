package cs3500.music.model;

import java.util.Arrays;
import java.util.List;

/**
 * A model for a music player that can play many songs simultaneously or consecutively.
 */
public class EditorModel implements IMusicModel {

  private Song song;
  private int currentTime;
  private final int measure;
  private final long tempo;
  private boolean isPaused;

  /**
   * EditorModel constructor that takes the measure of the piece.
   *
   * @param measure The measure of the song.
   * @throws IllegalArgumentException If the measure is incorrect
   */
  public EditorModel(int measure) throws IllegalArgumentException {
    if (measure <= 0) {
      throw new IllegalArgumentException("Measure needs to be > 0");
    }
    this.song = new Song();
    this.measure = measure;
    this.currentTime = 0;
    this.tempo = 200000;
    this.isPaused = true;
  }

  /**
   * EditorModel constructor that takes the measure of the piece.
   *
   * @param measure The measure of the song.
   * @param tempo The rate at which music is played in microseconds.
   * @throws IllegalArgumentException If the measure is incorrect
   */
  public EditorModel(int measure, long tempo) throws IllegalArgumentException  {
    if (measure <= 0) {
      throw new IllegalArgumentException("Measure needs to be > 0");
    }
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo needs to be > 0");
    }
    this.song = new Song();
    this.measure = measure;
    this.currentTime = 0;
    this.tempo = tempo;
    this.isPaused = true;
  }

  @Override
  public String getMusicState() {
    return song.toString();
  }

  @Override
  public void addNote(Note.Pitch pitch, int octave, int startTime,
                      int beats, int instrument, int volume) throws IllegalArgumentException {
    validifyInputs(pitch, octave, startTime, beats);
    song.addNote(new Note(pitch, octave, startTime, beats, instrument, volume));
  }

  @Override
  public void addNote(Note note) {
    song.addNote(note);
  }

  @Override
  public void removeNote(Note.Pitch pitch, int octave, int startTime,
                         int instrument, int volume) throws IllegalArgumentException {
    validifyInputs(pitch, octave, startTime, 0);
    song.removeNote(new Note(pitch, octave, startTime, 1, instrument, volume));
  }

  @Override
  public void removeNote(Note note) {
    song.removeNote(note);
  }

  @Override
  public void overlayNotes(IMusicModel musicToOverlay) {
    song.setNotesAtTime(musicToOverlay.getNotes());
  }

  @Override
  public void appendNotes(IMusicModel musicToAppend) {
    song.appendNotesAtTime(musicToAppend.getNotes());
  }

  @Override
  public List<Note> getNotes() {
    return this.song.getNotesList(this);
  }

  // CHANGE
  @Override
  public List<Note> getNotesAtTime(int time) {
    return this.song.getNotesAtTime(time);
  }

  // CHANGE
  @Override
  public int maxTime() {
    return this.song.songLength;
  }

  @Override
  public int getCurrentTime() {
    return this.currentTime;
  }

  @Override
  public void incrementTime(int delta) {
    int newTime = currentTime + delta;
    if (newTime <= this.maxTime() && newTime >= 0) {
      this.currentTime = newTime;
    }
  }

  @Override
  public int getMeasure() {
    return measure;
  }

  @Override
  public Range getRange() {
    return this.song.getRange();
  }

  @Override
  public long getTempo() {
    return this.tempo;
  }

  @Override
  public void setCurrentTime(int time) {
    if (time < 0 || time > this.maxTime()) {
      throw new IllegalArgumentException("Given time out of bounds, time must be between 0 and "
              + "the max length of the song.");
    }
    this.currentTime = time;
  }

  @Override
  public boolean isPaused() {
    boolean toReturn = this.isPaused;
    return toReturn;
  }

  @Override
  public void setPaused(boolean b) {
    this.isPaused = b;
  }

  /**
   * Helper method that checks if the inputs are valid in the context of a music editor.
   * CHANGE - changed from noteType to beats
   *
   * @param pitch     The pitch of the note
   * @param octave    The octave of the note.
   * @param startTime The start songLength of the note that places is correctly with respect
   *                  to other notes the song
   * @param beats     The note length in terms of beats spent
   * @throws IllegalArgumentException If any of the inputs are incorrect. Error will provide further
   *                                  instructions.
   */
  private void validifyInputs(Note.Pitch pitch, int octave,
                              int startTime, int beats) throws IllegalArgumentException {
    if (pitch == null) {
      throw new IllegalArgumentException("Pitch must be non-null. Valid Inputs include:  \n"
              + Arrays.asList(Note.Pitch.values()).toString());
    }
    if (octave < 0) {
      throw new IllegalArgumentException("Octave must be a non-negative value");
    }
    if (startTime < 0) {
      throw new IllegalArgumentException("Start songLength must be a non-negative value");
    }

    if (beats < 0) {
      throw new IllegalArgumentException("Beats must be a non-negative value");
    }
  }
}
