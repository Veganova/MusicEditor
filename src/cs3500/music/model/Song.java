package cs3500.music.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a song composed of notes, a particular measure.
 */
public class Song {

  /**
   * A data structure that stores the notes at each beat.
   */
  private HashMap<Integer, List<Note>> notes;

  /**
   * The measure of the song. For most pieces, this value is set to 3 or 4.
   */
  protected int measure;

  /**
   * Keeps record of the latest note added, signifying the length of the piece.
   * This is helpful when displaying the piece.
   * CHANGE - gives access to editorModel.
   */
  protected int songLength;

  /**
   * Object that keeps record of the lowest and highest notes in order.
   * This is helpful when displaying the piece.
   */
  private Range octaveRange;

  /**
   * Constructor for song that takes a measure. Sets other fields to default/empty values.
   */
  Song() {
    this.octaveRange = new Range();
    this.notes = new HashMap<Integer, List<Note>>();
    this.songLength = 0;
  }

  /**
   * Constructor for song that takes a measure. Sets other fields to default/empty values.
   *
   * @param notes A list of notes that will be used to initialize the song with its values.
   */
  Song(List<Note> notes) {
    this();

    // Initialize the this object with the notes at the correct times
    setNotesAtTime(notes);
  }

  /**
   * Add the given note to this song. Also updates the {@link #octaveRange} if the note is at a
   * boundary.
   *
   * @param addition  The note that will be added to this song
   * @throws IllegalArgumentException If the note does not exist in the song
   */
  protected void addNote(Note addition) throws IllegalArgumentException {
    updateNotesAtTimes(addition);

    // Update the range of octaves that occur in this song piece
    this.octaveRange.update(addition);
  }

  /**
   * Remove the given note from this song.
   *
   * @param removal  The note that will be removed from this song
   * @throws IllegalArgumentException If the note does not exist in the song
   */
  protected void removeNote(Note removal) throws IllegalArgumentException {
    removeNoteAtTimes(removal);
  }

  /**
   * Appends the notes at the end of this song. The starting and ending times of the notes are
   * modified to fit directly at the end of the current song.
   *
   * <p>Uses {@link #getLowestStart(List)} to get the earliest starting songLength of the given list
   * of notes shifts all the notes in the list to fit at the latest
   * songLength {@link #songLength}</p>
   *
   * @param notes The collection of notes that will be appended at the end of the song
   */
  protected void appendNotesAtTime(List<Note> notes) {
    int lowestStart = getLowestStart(notes);
    // When the lowest start value is less than songLength, it should be brought up to songLength,
    // other wise brought down to songLength
    int timeDelta = this.songLength - lowestStart;

    for (Note note: notes) {
      note.startTime += timeDelta;
      note.endTime += timeDelta;
      updateNotesAtTimes(note);
    }
  }

  /**
   * Helper method that loops over the list of notes and adds them using {@link #updateNotesAtTimes}
   * to the song.
   *
   * @param notes The collection of notes that will be added to the song.
   */
  protected void setNotesAtTime(List<Note> notes) {
    for (Note note: notes) {
      updateNotesAtTimes(note);
    }
  }

  /**
   * Helper method that gets the notes played at the given songLength.
   * CHANGE - send a copy of the list of notes at the given time.
   *
   * @param time  The songLength at which notes will be retrieved
   * @return  A list of notes played at this songLength
   */
  protected List<Note> getNotesAtTime(int time) {
    List<Note> copy = new ArrayList<Note>();
    if (this.notes.get(time) != null) {
      copy.addAll(this.notes.get(time));
    }
    return copy;
  }


  /**
   * Helper method that retrieves the lowest starting songLength for all the notes in the
   * given collection.
   *
   * @param notes The collection of notes that will be checked as stated above
   * @return  Returns the lowest starting songLength of the notes
   */
  private int getLowestStart(List<Note> notes) {
    int lowest = Integer.MAX_VALUE;
    for (Note note: notes) {
      if (note.startTime < lowest) {
        lowest = note.startTime;
      }
    }
    return lowest;
  }

  /**
   * Helper methods that adds the given note to the song.
   *
   * <p> Updates the {@link #notes} collection which is a list that stores notes by songLength.
   * It does so by iterating over the start and end songLength of the note and fixing the list at
   * each one of these beat values.</p>
   * <p> Overlaps. These occur when a note is already in the place where the note is going to be
   * added. A - note being added. B - Note already present in the conflicting location.
   * <li>If A only covers one of the ends of B, A will be placed on top and B will have its
   * start points xor end points fixed</li>
   * <li>If A is in the middle of B, it will split B into two. The start and end points will be
   * fixed appropriately.</li></p>
   *
   * @param note  The note that is going to be added to the song
   * @throws IllegalArgumentException Throws Exception when there is no such note in this song
   */
  private void updateNotesAtTimes(Note note) throws IllegalArgumentException {
    int t = note.startTime;
    List<Note> notesAtTime;
    for (; t < note.startTime + note.beats; t += 1) {
      // If songLength is being extended, add new terms to the list
      for (; this.songLength <= t; this.songLength += 1) {
        // this loop will usually execute only once
        this.notes.put(this.songLength, new ArrayList<Note>());
      }
      notesAtTime = this.notes.get(t);
      insertInOrderNoDupes(notesAtTime, note);
    }

    // Fix overlap - the notes that come after
    if (t < this.songLength && (this.notes.get(t).contains(note))) {
      notesAtTime = this.notes.get(t);
      Note toFix = getActual(notesAtTime, note);
      int beats = toFix.endTime - t;
      Note replacement = new Note(toFix.pitch, toFix.octave,
              t, beats, toFix.instrument, toFix.volume);
      for (int i = t; i < t + beats; i += 1) {
        notesAtTime = this.notes.get(i);
        Note old = getActual(notesAtTime, replacement);
        old.endTime = note.startTime;
        insertInOrderNoDupes(notesAtTime, replacement);
      }
    }
  }

  /**
   * Helper method that inserts the note into the given list and if a duplicate exists,
   * it removes the duplicate and replaces it with the given note. The insertion will be the same as
   * seen in insertion sort allowing to maintain the list in sorted order.
   *
   * @param notes The list of notes into which note is going to be added
   * @param note  The note which is going to be inserted into the list of notes.
   */
  private void insertInOrderNoDupes(List<Note> notes, Note note) {
    int i = 0;
    for (; i < notes.size(); i += 1) {
      Note curNote = notes.get(i);

      // Keeps in order (insertion)
      if (curNote.compareTo(note) >= 0) {
        // Remove duplicates
        if (curNote.equals(note)) {
          notes.remove(curNote);
        }
        break;
      }
    }
    notes.add(i, note);
    // Update the octave range of the song
    octaveRange.update(note);
  }

  /**
   * Helper method that gets around the equality definition for Notes and retrieves the exact Object
   * in the list {@link #notes} that is equal to the given note.
   *
   * @param notes The list of notes that contains a note equal to copy
   * @param copy  A note that sounds the same as a note inside the list of notes
   * @return  Returns the note that is equal (in sound) to the copy note in the list of notes given
   */
  private static Note getActual(List<Note> notes, Note copy) {
    return notes.get(notes.indexOf(copy));
  }

  /**
   * Removes the instances of the given note from the song.
   *
   * @param note  Removes all instances of the exact note from the list {@link #notes}
   * @throws IllegalArgumentException If the song does not contain the given note
   */
  private void removeNoteAtTimes(Note note) throws IllegalArgumentException {
    if (!this.notes.get(note.startTime).contains(note)) {
      throw new IllegalArgumentException("No such note exists at songLength " + songLength);
    }
    Note removalNote = getActual(this.notes.get(note.startTime), note);
    for (int time = removalNote.startTime; time < removalNote.endTime; time += 1) {
      List<Note> notesAtTime = this.notes.get(time);
      notesAtTime.remove(removalNote);
    }
  }

  /**
   * Constructors an empty String of given length.
   *
   * @param length  The length of the empty string
   * @return  Returns an empty string of the given length
   */
  private String emptyString(int length) {
    String space = "";
    for (int j = 0; j < length; j += 1) {
      space += " ";
    }
    return space;
  }

  /**
   * Provides a string representation of the song. * X will be for notes that are played for the
   * first songLength and | otherwise. Empty spaces are put in places where there are
   * no notes (rests).
   * CHANGE - using StringBuilder instead for efficiency purposes.
   *
   * @return  A string representation of the song
   */
  @Override
  public String toString() {
    String time = "" + this.songLength;
    String topRightSpace = emptyString( time.length());
    StringBuilder result = new StringBuilder();
    if (this.songLength == 0) {
      return result.toString();
    }
    result.append(topRightSpace).append(octaveRange.toString()).append("\n");
    for (int i = 0; i < this.songLength; i += 1) {
      String num = i + "";
      result.append(num).append(emptyString(topRightSpace.length() - num.length())); //O(1)
      for (Note note: this.octaveRange) {
        List<Note> notes = this.notes.get(i);
        if (notes.contains(note)) {
          Note printNote = getActual(notes, note);
          if (i == printNote.startTime) {
            result.append("  X  ");
          } else {
            result.append("  |  ");
          }
        } else {
          result.append("     ");
        }
      }
      result.append("\n");
    }
    return result.toString();
  }

  /**
   * Provides a List of Note of each Note contained in the given IMusicModel.
   *
   * @param musicModel the model whose HashMap of Notes will be copied into a List of Notes
   */
  protected List<Note> getNotesList(IMusicModel musicModel) {
    List<Note> noteList = new ArrayList<Note>();
    int time = 0;

    for (List<Note> mapEntry : notes.values()) { // for each value of the Notes HashMap
      for (Note note : mapEntry) { // for each Note in each list
        if (note.startTime == time) {
          noteList.add(note);
        }
      }
      time++;
    }
    return noteList;
  }

  /**
   * Returns a duplicate of the range of this song.
   *
   * @return  Returns the duplicate of a range of this song
   */
  public Range getRange() {
    Note low = octaveRange.lowest;
    Note highest = octaveRange.highest;
    return new Range(new Note(low.pitch, low.octave, 1),
            new Note(highest.pitch, highest.octave, 1));
  }
}