package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * An interface to outline the expected general functionality of music editor views.
 */
public interface IMusicView<K extends IMusicModel> {
  /**
   * Presents the Notes of a IMusicModel to the user.
   */
  void play();

  /**
   * Pauses the presentation of the Notes of the IMusicMode.
   */
  void pause();

  /**
   * Presents the current Note of the IMusicModel.
   */
  void playCurrent();
}
