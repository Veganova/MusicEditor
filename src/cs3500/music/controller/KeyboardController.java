package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.PlayRest;

/**
 * Controls the editor with the arrow keys.
 */
public class KeyboardController implements IMusicController {
  protected IMusicModel musicModel;
  protected IMusicView musicViews;
  protected IMusicView normalView;
  protected IMusicView midiView;
  protected IMusicView guiView;
  private KeyHandler keyHandler;
  private PlayRest rest;
  private boolean playing;

  /**
   * Makes a new KeyboardController to receive and deal with arrow key presses.
   *
   * @param musicModel updates the model according to what arrow keys are pressed.
   */
  public KeyboardController(IMusicModel musicModel, Appendable out) {
    this.musicModel = musicModel;
    this.playing = false;
    this.keyHandler = new KeyHandler(musicModel, this);
  }

  @Override
  public void playViews() {
    if (musicViews == null) {
      this.normalView.play();
    } else {
      this.playing = true;
      this.musicViews.play();
      this.rest = new PlayRest(guiView, musicModel);
      rest.start();
    }
  }

  /**
   * Only when playing will the rest be initialized and require stopping.
   */
  @Override
  public void pauseViews() {
    if (this.playing) {
      this.musicViews.pause();
      rest.stop();
    }
  }

  @Override
  public void addView(IMusicView view) {
    this.normalView = view;
  }

  @Override
  public void addViews(MidiViewImpl midiView, GuiViewFrame guiView) {
    //midiView.addPartner(guiView);
    this.guiView = guiView;
    this.musicViews = new CompositeView(midiView, guiView, keyHandler);

  }

  @Override
  public void addNoteToModel(Note.Pitch pitch, int octave, int startTime) {
    musicModel.addNote(pitch, octave, startTime, 1, 0, 64);
    this.musicViews.playCurrent();
  }
}