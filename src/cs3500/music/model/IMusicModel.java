package cs3500.music.model;

import java.util.List;

/**
 *  An interface to outline the expected general functionality of music editor models.
 */
public interface IMusicModel {
  /**
   * Gives the String representation of the contents of the music editor.
   *
   * @return  Return an organized representation of the notes over songLength
   */
  String getMusicState();

  /**
   * Adds a note, as specified, to the music editor at the given songLength. Adding a note on top of
   * another note will give priority to the new note being added.
   * CHANGE - switched to beats instead of noteType (quarter note, whole note, etc.)
   *
   * @param pitch The pitch of the note to be added
   * @param octave  The octave of the note to be added
   * @param startTime The songLength at which the note being added starts
   * @param beats  The note duration in beats
   * @param instrument The instrument with which the Note will be played parsed by MIDI
   * @param volume How loudly the Note will be played parsed by MIDI
   * @throws IllegalArgumentException if the noteType does not yield a whole number given
   *                                  the current measure
   */
  void addNote(Note.Pitch pitch, int octave, int startTime,
               int beats, int instrument, int volume) throws IllegalArgumentException;

  /**
   * Adds a specific Note to the music editor at its time. Adding a note on top of
   * another note will give priority to the new note being added.
   * CHANGE - added for ease of use by the builder and to support different Note types.
   *
   * @param note the specific note to be added
   */
  void addNote(Note note);


  /**
   * Removes a note, as specified, from this music editor at the given songLength.
   * Does not require an ending songLength for the removal note.
   *
   * @param pitch The pitch of the note to be added
   * @param octave  The octave of the note to be added
   * @param startTime The songLength at which the note being added starts
   * @param instrument The instrument with which the Note will be played parsed by MIDI
   * @param volume How loudly the Note will be played parsed by MIDI
   * @throws IllegalArgumentException if the noteType does not yield a whole number given
   *                                  the current measure
   */
  void removeNote(Note.Pitch pitch, int octave,
                  int startTime, int instrument, int volume) throws IllegalArgumentException;

  /**
   * Removes a note, as specified, from this music editor at its songLength.
   * CHANGE - added for ease of use by the builder and to support different Note types.
   *
   * @param note the specific Note to be removed
   */
  void removeNote(Note note);

  /**
   * Adds the given song to the editor, overlaying the music notes of the new on top of the
   * existing ones.
   * CHANGE - overlayNotes now takes an IMusicModel instead of a List of Note so IMusicModel does
   * not rely on outside classes.
   *
   * @param musicToOverlay  An IMusicModel whose Notes will be overlayed onto this model's.
   */
  void overlayNotes(IMusicModel musicToOverlay);

  /**
   * Adds the given song to the editor, appending the music notes of the new at the end of the
   * existing ones.
   * CHANGE - appendNotes now takes an IMusicModel instead of a List of Note so IMusicModel does
   * not rely on outside classes.
   *
   * @param musicToAppend  An IMusicModel whose Notes will be appended onto this model's
   */
  void appendNotes(IMusicModel musicToAppend);

  /**
   * Gets every note of a song as a List of Note.
   *
   * @return return a List of Note of every Note contained within this IMusicModel
   */
  List<Note> getNotes();

  /**
   * Gets the notes played at the given songLength (beat).
   * CHANGE -  added this method for ease of use by the view methods.
   *
   * @param time  The beat at which notes are going to be retrieved
   * @return  A list of notes that were played at the given songLength
   */
  List<Note> getNotesAtTime(int time);

  /**
   * Gets the length of the song in beats.
   * CHANGE - add this method for ease of use by the view methods.
   *
   * @return the length of the song
   */
  int maxTime();

  /**
   * Gets the songLength at which notes are being played.
   * CHANGE - the model should store the state of the music editor, including the current songLength
   * being played.
   *
   * @return the current songLength of the model in beats
   */
  int getCurrentTime();

  /**
   * Increments the current songLength either additively or by subtraction.
   * CHANGE - the model should store the state of the music editor, including the ability to change
   * the current songLength.
   *
   * @param delta how much to increment the current songLength by (can be negative)
   */
  void incrementTime(int delta);

  /**
   * Gets how many beats long a measure is in this piece of music.
   * @return number of beats per measure
   */
  int getMeasure();

  /**
   * Get the range of notes that the song in this editor spans. Range is a copy so that the original
   * cannot be tampered with.
   * CHANGE - added this so that the range for a given model can be displayed by the view (which
   * does not have access too the information otherwise).
   *
   *
   * @return  Return the range of notes that the song in this editor spans
   */
  Range getRange();

  /**
   * Get the tempo at which the music should be played. Tempo is a copy so that the original
   * cannot be tampered with.
   * CHANGE - added this so that the tempo for a given model can be displayed by the view (which
   * does not have access too the information otherwise).
   *
   *
   * @return  Return the range of notes that the song in this editor spans
   */
  long getTempo();

  /**
   * Sets the current time of the model representing there a user is in the song.
   * CHANGE - added this so the controller can set the current time to the beginning and end
   * of the song.
   *
   * @param time The specific beat of the song which the current time will be set to
   */
  void setCurrentTime(int time);

  /**
   * Helper method to return if this model is paused or not in the context of a music editor.
   * CHANGE - added this so the controller can access the paused state of this model.
   */
  boolean isPaused();

  /**
   * Helper method to change if this model is paused or not in the context of a music editor.
   * CHANGE - added this so the controller can access the paused state of this model.
   */
  void setPaused(boolean b);
}
