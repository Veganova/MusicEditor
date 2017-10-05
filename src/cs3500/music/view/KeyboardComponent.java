package cs3500.music.view;

import java.awt.Color;
import java.util.List;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * Component which displays the keyboard visual in the lower panel of the frame.
 */
public class KeyboardComponent extends JComponent implements IMusicView<EditorModel> {
  private IMusicModel musicModel;
  private IMusicController controller;
  private HashMap<Note, PianoKeyComponent> piano;
  private static int BEGIN_X = GuiViewFrame.FRAME_WIDTH / 20;
  private JLayeredPane layeredPane;

  /**
   * Makes a new piano visualization of 120 tones and 10 octaves.
   * Sets up the piano HashMap by normal and sharp notes.
   *
   * @param musicModel The current model which contains the Notes needed to press keys when played.
   */
  KeyboardComponent(IMusicModel musicModel, IMusicController controller) {
    this.musicModel = musicModel;
    this.controller = controller;
    this.piano = new HashMap<Note, PianoKeyComponent>();
    int curX = BEGIN_X;

    layeredPane = new JLayeredPane();

    layeredPane.setSize(GuiViewFrame.FRAME_WIDTH, GuiViewFrame.FRAME_HEIGHT / 2);
    layeredPane.setOpaque(true);
    layeredPane.setBackground(Color.GRAY);
    this.add(layeredPane);

    // Initializes the piano hashMap
    int noteWidth = GuiViewFrame.FRAME_WIDTH / 80;
    int sharpWidth = GuiViewFrame.FRAME_WIDTH / 150;
    for (int octave = 1; octave <= 10; octave += 1) {
      for (Note.Pitch p : Note.Pitch.values()) {
        Note note = new Note(p, octave, 1);

        // if the note is non-sharp
        if (p.toString().length() == 1) {
          PianoKeyComponent key = new PianoKeyComponent(false, false,
                  this, curX);
          curX += noteWidth;
          piano.put(note, key);
        }

        // if the note is sharp
        else {
          PianoKeyComponent key = new PianoKeyComponent(false, true,
                  this, curX - (sharpWidth / 2));
          piano.put(note, key);
        }
      }
    }
    piano = getNewPiano();

    this.drawPiano();
    this.playCurrent();
  }

  /**
   * A blank piano containing all keys correctly initialized and in the unpressed state.
   *
   * @return A copy of this blank piano.
   */
  private HashMap<Note, PianoKeyComponent> getNewPiano() {
    for (Note note : piano.keySet()) {
      piano.get(note).isPressed = false;
    }
    return piano;
  }

  /**
   * Draws the normal keys of the piano, the pressed normal keys of the piano, the sharp keys
   * of the piano, and the pressed sharp keys of the piano in that order so that the sharps and
   * pressed sharps display over both kinds of normal keys.
   */
  private void drawPiano() {
    int noteWidth = GuiViewFrame.FRAME_WIDTH / 80;
    int noteHeight = GuiViewFrame.FRAME_HEIGHT / 5 * 2;
    int sharpWidth = GuiViewFrame.FRAME_WIDTH / 150;
    int sharpHeight = GuiViewFrame.FRAME_HEIGHT / 6;

    for (Note note : piano.keySet()) {
      PianoKeyComponent key = piano.get(note);
      // if the note is non-sharp
      if (!key.isSharp) {
        key.setSize(noteWidth, noteHeight);
        key.setLocation(key.x, 0);
        layeredPane.add(key, 0);
      }
    }

    for (Note note : piano.keySet()) {
      PianoKeyComponent key = piano.get(note);
      // if the note is sharp
      if (key.isSharp) {
        key.setSize(sharpWidth, sharpHeight);
        key.setLocation(key.x, 0);
        layeredPane.add(key, 1);
      }
    }
  }

  /**
   * Resets the current piano to having no keys pressed, then presses the appropriate keys that
   * correspond to the Notes present at the current time.
   */
  private void pressPiano() {
    this.piano = getNewPiano();
    List<Note> currentNotes = musicModel.getNotesAtTime(musicModel.getCurrentTime());
    if (musicModel.getCurrentTime() < musicModel.maxTime()) {
      for (Note note : currentNotes) {
        PianoKeyComponent key = piano.get(note);
        key.isPressed = true;
        piano.put(note, key);
      }
    }
  }

  /**
   * Adds a specific PianoKeyComponent as its associated Note to the musicModel.
   *
   * @param key The piano key that plays the Note to be added
   */
  protected void addNote(PianoKeyComponent key) {
    for (Note note : piano.keySet()) {
      if (piano.get(note).equals(key)) {
        controller.addNoteToModel(note.pitch, note.octave, musicModel.getCurrentTime());
      }
    }
  }

  @Override
  public void play() {
    // For visual views the play method is handled by the controller and playCurrent so the views
    // can run simultaneous to each other.
  }

  @Override
  public void pause() {
    // For visual views the play method is handled by the controller and playCurrent so the views
    // can run simultaneous to each other, rendering pause unneeded.
  }

  @Override
  public void playCurrent() {
    // retrieve notes and play them
    pressPiano();
    super.repaint();
  }
}