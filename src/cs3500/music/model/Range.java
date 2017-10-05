package cs3500.music.model;

import java.util.Iterator;

/**
 * Represents the octaves scale over which a song may range.
 */
public class Range implements Iterable<Note> {

  // CHANGE - removed lowest, highest Note and put
  public Note lowest;
  public Note highest;

  /**
   * Default constructor for Range.
   */
  public Range() {
    this.lowest = null;
    this.highest = null;
  }

  /**
   * Constructor for Range that takes preset values for the range of the octave scale.
   *
   * @param lowest  The lowest octave
   * @param highest The highest octave
   */
  public Range(Note lowest, Note highest) {
    this.lowest = lowest;
    this.highest = highest;
  }

  /**
   * Updates the lowest and highest values if the given value exceeds either threshhold.
   *
   * @param value The value which will be checked against this.lowest, this.highest
   */
  public void update(Note value) {
    if (this.lowest == null || value.compareTo(this.lowest) < 0) {
      this.lowest = value;
    }
    if (this.highest == null || value.compareTo(this.highest) > 0) {
      this.highest = value;
    }
  }

  @Override
  public String toString() {
    String result = "";
    if (this.highest != null && this.lowest != null) {
      for (Note note: this) {
        if (note.toString().length() == 4) {
          result += " " + note.toString();
        }
        else if (note.toString().length() == 3) { // A sharp note
          result += " " + note.toString() + " ";
        } else {
          result += "  " + note.toString() + " ";
        }
      }
    }
    return result;
  }

  @Override
  public Iterator<Note> iterator() {
    return new IterateOverRange(this.lowest, this.highest);
  }
}