package cs3500.music.model;

import cs3500.music.view.MidiViewImpl;

/**
 * Represents a music note with an octave and pitch.
 * CHANGE - Note is now public so the view can access it.
 */
public class Note implements Comparable<Note> {


  /**
   * A public enumeration of the possible values from regular music notation.
   */
  public enum Pitch {
    C("C"), CSHARP("C#"), D("D"), DSHARP("D#"), E("E"), F("F"),
    FSHARP("F#"), G("G"), GSHARP("G#"), A("A"), ASHARP("A#"), B("B");

    private final String value;

    /**
     * Constructor for pitches that allows for them to store a string value that can be used
     * to visually represent them.
     *
     * @param value The value that the pitch will take on
     */
    Pitch(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  /**
   * The fields that represent a note and how it sounds as well as the songLength and length
   * of its sound.
   */
  public Pitch pitch;
  public int octave;
  public int beats;
  public int startTime;
  public int endTime;
  public int instrument;
  public int volume;
  // CHANGE - added the 2 above to accommodate for the MIDI requirements.

  /**
   * Constructor for a regular musical note.
   *
   * @param pitch The pitch of the music note that this object represents
   * @param octave  The octave of the music note that this object represents
   * @param beats  The beats of the music note that this object represents, also known as the length
   */
  public Note(Pitch pitch, int octave, int beats) {
    this.pitch = pitch;
    this.octave = octave;
    this.beats = beats;
    this.startTime = 0;
    this.endTime = beats;
    this.instrument = MidiViewImpl.DEFAULT_INSTRUMENT;
    this.volume = MidiViewImpl.DEFAULT_VOLUME;
  }

  /**
   * Constructor for a regular musical note.
   * CHANGE - made public for ease of access.
   *
   * @param pitch The pitch of the music note that this object represents
   * @param octave  The octave of the music note that this object represents
   * @param startTime Gives the songLength at which this note is played
   * @param beats  The beats of the music note that this object represents, also known as the length
   * @param instrument The instrument with which the Note will be played
   * @param volume How loudly the Note should be played
   */
  public Note(Pitch pitch, int octave, int startTime, int beats, int instrument, int volume) {
    this(pitch, octave, beats);
    this.startTime = startTime;
    this.endTime = startTime + beats;
    this.instrument = instrument;
    this.volume = volume;
  }

  @Override
  public String toString() {
    return this.pitch.toString() + this.octave;
  }

  @Override
  public int hashCode() {
    return this.pitch.ordinal() + this.octave * Pitch.values().length;
  }

  /**
   * Checks if two notes sound the same. This measn that the pitch and the octave are the same.
   *
   * @param other The other note against which equality with this note is going to be checked
   * @return True if the two notes are equal
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Note) {
      return (((Note) other).octave == this.octave
              && ((Note) other).pitch == this.pitch);
    }
    return false;
  }

  /**
   * Checks if two notes are exactly the same - sound and beat length and songLength.
   *
   * @param other The other object which will be checked to be equal to this note
   * @return  True if the other object is equal to this
   */
  public boolean exactEquals(Object other) {
    return (this.equals(other) && ((Note) other).startTime == this.startTime
              && ((Note) other).endTime == this.endTime);
  }

  /**
   * Checks if this Note starts at the given songLength.
   * CHANGE - added because view does not hvae access to the fields.
   *
   * @param time the songLength to be checked against
   * @return true if this Note does start at the given songLength
   */
  public boolean isStart(int time) {
    return time == this.startTime;
  }

  @Override
  public int compareTo(Note o) {
    return this.hashCode() - o.hashCode();
  }
}
