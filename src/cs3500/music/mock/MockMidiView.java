package cs3500.music.mock;


import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicView;

/**
 * Represents a mock MidiView which will log each call to an output for debugging.
 */
public class MockMidiView implements IMusicView<IMusicModel> {
  StringBuilder log;
  private final MockMidiDevice synth;
  private final Receiver receiver;
  private IMusicModel musicModel;
  public static int DEFAULT_INSTRUMENT = 0;
  public static int DEFAULT_VOLUME = 64;

  /**
   * Constructor for the MidiViewImpl. Sets the synthesizer from the midi system. Also sets the
   * receiver from the synthesizes and opens the synthesizer.
   *
   * @param model The IMusicModel which contains the notes that this view will play out
   * @throws MidiUnavailableException When construction of the synthesizer does not work expectedly
   */
  public MockMidiView(IMusicModel model, StringBuilder stringBuilder)
          throws MidiUnavailableException {

    this.log = stringBuilder;
    this.synth = new MockMidiDevice(log);
    this.receiver = synth.getReceiver();
    this.synth.open();
    this.musicModel = model;

    this.playCurrent();
  }

  /**
   * Sends a list of Notes to the receiver with instrument change, start,
   * and stop commands for each.
   *
   * @param notes Each note to be sent to the receiver and played.
   * @throws InvalidMidiDataException If the shortMessage is not valid for the receiver.
   */
  public void playNotes(List<Note> notes) throws InvalidMidiDataException {
    List<MidiMessage> starts = new ArrayList<MidiMessage>();
    List<MidiMessage> stops = new ArrayList<MidiMessage>();
    for (Note note: notes) {
      starts.add(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, note.instrument, 0));
      starts.add(new ShortMessage(ShortMessage.NOTE_ON, 0, note.hashCode(), note.volume));
      stops.add(new ShortMessage(ShortMessage.NOTE_OFF, 0, note.hashCode(), note.volume));
    }
    for (MidiMessage start: starts) {
      this.receiver.send(start, -1);
    }
    for (MidiMessage stop: stops) {
      this.receiver.send(stop, this.synth.getMicrosecondPosition()
              + (musicModel.getTempo() * 1000));
    }
  }

  @Override
  public void play() {
    // The mock midi plays entire songs through play current, play() is not needed.
  }

  @Override
  public void pause() {
    // The mock midi plays entire songs through play current, play() is not needed.
  }

  @Override
  public void playCurrent() {
    List<Note> currentNotes = musicModel.getNotesAtTime(musicModel.getCurrentTime());
    if (musicModel.getCurrentTime() < musicModel.maxTime()) {
      try {
        this.playNotes(currentNotes);
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
    }
    else {
      this.receiver.close();
    }
  }
}
