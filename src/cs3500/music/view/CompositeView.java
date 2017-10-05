package cs3500.music.view;

import cs3500.music.controller.KeyHandler;
import cs3500.music.model.IMusicModel;

/**
 * Class to represent a view composed of a visual and a midi audio view.
 */
public class CompositeView implements IMusicView<IMusicModel> {
  MidiViewImpl midiView;
  GuiViewFrame guiView;

  /**
   * Constructs a new view composed of a MidiViewImpl and a GuiViewFrame.
   *
   * @param midiView the audio portion of the view.
   * @param guiView the visual portion of the view.
   * @param keyHandler the handler for keyEvents for the visual view.
   */
  public CompositeView(MidiViewImpl midiView, GuiViewFrame guiView, KeyHandler keyHandler) {
    this.midiView = midiView;
    this.guiView = guiView;

    this.guiView.addKeyListener(keyHandler);
  }

  @Override
  public void play() {
    midiView.play();
    guiView.play();
  }

  @Override
  public void pause() {
    midiView.pause();
    guiView.pause();
    guiView.playCurrent();
  }

  @Override
  public void playCurrent() {
    midiView.playCurrent();
    guiView.playCurrent();
  }
}