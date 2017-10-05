package cs3500.music.model;

import java.util.Iterator;

/**
 * Iterator that loops over all the intermediate (inclusive of ends) of the given notes.
 */
class IterateOverRange implements Iterator<Note> {

  // The current node of the iteration
  Note current;
  Note end;

  // Signals when one the last one allowing to be inclusive of the ending node
  boolean doneLast;

  /**
   * Constructor for IterateOverRange constructor.
   *
   * @param start The starting node at which the iteration will start
   * @param end   The ending node at which the iteration will end
   */
  IterateOverRange(Note start, Note end) {
    this.current = start;
    this.end = end;
    this.doneLast = false;
  }

  @Override
  public boolean hasNext() {
    return !this.doneLast;
  }

  @Override
  public Note next() {
    if (this.hasNext()) {
      Note result = this.current;
      Note.Pitch[] values = Note.Pitch.values();
      int octave = this.current.octave;
      int ordinal = this.current.pitch.ordinal();
      ordinal += 1;
      if (ordinal >= values.length) {
        octave += 1;
        ordinal = ordinal % values.length;
      }
      Note.Pitch newPitch = values[ordinal];
      this.current = new Note(newPitch, octave, 1);
      if (result.equals(this.end)) {
        this.doneLast = true;
      }
      return result;
    }
    throw new RuntimeException("No more notes left to iterate over.");
  }
}
