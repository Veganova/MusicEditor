package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Track;

import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback. Implements the music view, making it responsible for playback of
 * the song.
 */
public class MidiViewImpl implements IMusicView<EditorModel> {
  public final static int DEFAULT_INSTRUMENT = 0;
  public final static int DEFAULT_VOLUME = 64;

  private IMusicModel musicModel;
  // Midi specific objects:
  private Sequencer sequencer;
  private Sequence sequence;
  private Track track;
  private boolean running = false;

  /**
   * Constructor for the MidiViewImpl. Sets the sequencer from the midi system. Also sets the
   * sequence from the sequencer and opens its.
   *
   * @param model The IMusicModel which contains the notes that this view will play out
   * @throws MidiUnavailableException When construction of the Midi objects does not work as
   *                                  expected
   */
  public MidiViewImpl(IMusicModel model) {
    this.musicModel = model;

    try {
      this.sequencer = MidiSystem.getSequencer();
      this.sequence = new Sequence(Sequence.PPQ, 1);
      this.track = this.sequence.createTrack();
      initSequence();
    } catch (MidiUnavailableException | InvalidMidiDataException e) {
      e.printStackTrace();
    }
    sequencer.addMetaEventListener(m -> {
      // A message of this type is automatically sent
      // when we reach the end of the track
      if (m.getType( ) == 47) {
        running = false;
      }
    });
  }

  /**
   * Initializes the sequencer with the correct tempo.
   *
   * @throws InvalidMidiDataException If problems with creation of sequencer
   */
  private void initSequence() throws InvalidMidiDataException {
    try {
      this.sequencer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.sequencer.setSequence(this.sequence);
    this.sequencer.setTempoInMPQ(musicModel.getTempo() * 1000);
  }

  /**
   * Load the note(s) at the given time by the model - currentTime().
   *
   * @throws InvalidMidiDataException if the midi data is not valid upon creation.
   */
  private void loadNote() throws InvalidMidiDataException {
    this.sequence.deleteTrack(this.track);
    this.track = this.sequence.createTrack();
    this.sequencer.setSequence(this.sequence);
    this.sequencer.setTempoInMPQ(musicModel.getTempo() * 1000);
    List<Note> notes = startNotes(musicModel.getNotesAtTime(musicModel.getCurrentTime()),
            musicModel.getCurrentTime());

    for (Note note : notes) {
      storeNote(note);
    }
  }

  /**
   * Load the entire song into the sequencer.
   *
   * @throws InvalidMidiDataException if the midi data is not valid upon creation.
   */
  private void loadSong() throws InvalidMidiDataException {
    this.sequence.deleteTrack(this.track);
    this.track = this.sequence.createTrack();

    for (int i = musicModel.getCurrentTime(); i < musicModel.maxTime(); i += 1) {
      List<Note> notes = startNotes(musicModel.getNotesAtTime(i), i);
      for (Note note : notes) {
        storeNoteTime(note);
      }
    }
  }

  @Override
  public void play() {
    this.running = true;
    try {
      this.loadSong();
      this.sequencer.setSequence(this.sequence);
      this.sequencer.setTickPosition(this.musicModel.getCurrentTime());
      this.sequencer.start();
      sequencer.setTempoInMPQ(this.musicModel.getTempo() * 1000);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void playCurrent() {
    try {
      this.loadNote();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    sequencer.setTickPosition(0);
    sequencer.start();

    this.sequencer.setTempoInMPQ(musicModel.getTempo() * 1000);
  }

  @Override
  public void pause() {
    if (running) {
      running = false;
      this.sequencer.stop();
      // Syncing purposes
      this.musicModel.setCurrentTime((int)this.sequencer.getTickPosition());
    }
  }

  /**
   * Filter out the notes that start at the current time state of the music musicModel field.
   *
   * @param notes The notes that which will be looked at for containing a starting note
   * @param time  The time at which the gives notes will be played
   * @return The notes that start at the current time
   */
  private List<Note> startNotes(List<Note> notes, int time) {
    List<Note> startNotes = new ArrayList<Note>();

    for (Note note : notes) {
      if (note.isStart(time)) {
        startNotes.add(note);
      }
    }
    return startNotes;
  }

  /**
   * Plays the note provided. Compiles 3 MidiMessages instrument, start and stop. Sends
   * the start(to play out the sound) to the receiver. Ends the sound by
   * sending a stop message to the receiver.
   * Uses the note fields and properties to fill the midi ShortMessage for start and stops.
   * CHANGE - changes to incorporate a sequencer
   *
   * @param note The notes to be played out
   * @throws InvalidMidiDataException Throws when the midi messages are made incorrectly
   */
  private void storeNote(Note note) throws InvalidMidiDataException {
    ShortMessage sm = new ShortMessage();
    sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, note.instrument + 1, 0);
    track.add(new MidiEvent(sm, 0));

    MidiEvent on = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON,
            0, note.hashCode(), note.volume), 0);
    MidiEvent off = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF,
            0, note.hashCode(), note.volume), note.beats  );
    track.add(on);
    track.add(off);
  }

  /**
   * Plays the note provided. Compiles 3 MidiMessages instrument, start and stop. Sends
   * the start(to play out the sound) to the receiver. Ends the sound by
   * sending a stop message to the receiver.
   * Uses the note fields and properties to fill the midi ShortMessage for start and stops.
   * CHANGE - changes to incorporate a sequencer
   *
   * @param note The notes to be played out
   * @throws InvalidMidiDataException Throws when the midi messages are made incorrectly
   */
  private void storeNoteTime(Note note) throws InvalidMidiDataException {
    ShortMessage sm = new ShortMessage();
    sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, note.instrument + 1, 0);
    track.add(new MidiEvent(sm, 0));

    MidiEvent on = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON,
            0, note.hashCode(), note.volume), note.startTime);
    MidiEvent off = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF,
            0, note.hashCode(), note.volume), note.endTime);
    track.add(on);
    track.add(off);
  }
}