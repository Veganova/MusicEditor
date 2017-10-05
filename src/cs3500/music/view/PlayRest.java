package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * Plays the other given view alongside the others by putting it in its own thread.
 */
public final class PlayRest extends Thread {

  private IMusicView otherView;
  private IMusicModel musicModel;

  /**
   * Default constructor for PlayRest. Takes references to the guiView and model to keep in sync.
   *
   * @param otherView The other view to be played
   * @param model     The model to be used as a reference for inputs and ata
   */
  public PlayRest(IMusicView otherView, IMusicModel model) {
    this.otherView = otherView;
    this.musicModel = model;
  }

  @Override
  public void run() {
    int delta = Math.max(0, this.musicModel.maxTime() - this.musicModel.getCurrentTime());
    for (int i = 0; i < delta; i += 1) {
      otherView.playCurrent();
      musicModel.incrementTime(1);
      try {
        Thread.sleep(musicModel.getTempo());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
