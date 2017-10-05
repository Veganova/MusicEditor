package cs3500.music.util;

import cs3500.music.controller.KeyboardController;
import cs3500.music.controller.IMusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicTextView;

/**
 * The concrete factory class for IMusicViews.
 */
public class KeyViewFactory implements IViewFactory<IMusicController> {
  private IMusicController musicController;
  private IMusicModel musicModel;
  private Appendable out;

  /**
   * Makes a new KeyViewFactory for making controllers.
   *
   * @param musicModel The model with which to populate the view to be made
   * @param out        The output mode used by the controller and relevant classes
   */
  public KeyViewFactory(IMusicModel musicModel, String controllerType, Appendable out) {
    this.musicModel = musicModel;
    this.out = out;
    this.musicController = this.chooseControllerType(controllerType);
  }

  /**
   * Selects the type of IMusicController that the user wishes to produce using this factory.
   *
   * @param controllerType A String specifier determining which controller to create.
   * @return The IMusicController specified by String controllerType.
   */
  private IMusicController chooseControllerType(String controllerType) {
    if (controllerType.equals("arrow")) {
      return new KeyboardController(musicModel, out);
    }
    else {
      throw new IllegalArgumentException("Not a valid controllerType. Try 'arrow'.");
    }
  }

  @Override
  public IMusicController addView(String s) throws IllegalArgumentException {
    switch (s) {
      case "visual":
        GuiViewFrame view = new GuiViewFrame(musicModel, "Music Editor", musicController);
        this.musicController.addView(view);
        break;
      case "console":
        this.musicController.addView(new MusicTextView(musicModel, out));
        break;
      case "midi":
        this.musicController.addView(new MidiViewImpl(musicModel));
        this.musicController.playViews();
        break;
      case "composite":
        this.musicController.addViews(new MidiViewImpl(musicModel),
                new GuiViewFrame(musicModel, "Music Editor", musicController));
        break;
      default:
        throw new IllegalArgumentException("Unsupported View type - try visual," +
                " console, midi or composite.");
    }
    return this.musicController;
  }
}