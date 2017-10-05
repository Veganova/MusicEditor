package cs3500.music.controller;

import cs3500.music.model.Note;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicView;
import cs3500.music.view.MidiViewImpl;

/**
 * An interface to outline the expected funtionality of a music editor controller.
 * CHANGE - IMusicController now extends KeyListener instead of using our own homebrewed
 * keyEventHandler. This is to separate out view and controller as previously the view
 * implemented KeyListener.
 */
public interface IMusicController {

  /**
   * Adds a view to this controller. The view takes in inputs from the user and provides it to this
   * for further operation onto the model. Allows multiple views to be added.
   *
   * @param view The view which is going bound to this controller.
   */
  void addView(IMusicView view);

  /**
   * Adds a CompositeView to this controller.
   *
   * @param midiView the midi/audio view of this composite.
   * @param guiView  the gui/visual view of this composite.
   */
  void addViews(MidiViewImpl midiView, GuiViewFrame guiView);

  /**
   * Using inputs from events triggered by actions in the view, add a Note to the IMusicModel.
   *
   * @param pitch     The pitch of the note to be added
   * @param octave    The octave of the note to be added
   * @param startTime The startTime of the note to be added
   */
  void addNoteToModel(Note.Pitch pitch, int octave, int startTime);


  /**
   * Correctly plays each view of the controller in sync.
   */
  void playViews();

  /**
   * Correctly pauses each view of the controller in sync.
   */
  void pauseViews();

}
