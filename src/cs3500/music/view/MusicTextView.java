package cs3500.music.view;

import java.io.IOException;

import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;

/**
 * Presents the music to the user in a text based view.
 */
public class MusicTextView implements IMusicView<EditorModel> {
  private IMusicModel musicModel;
  private Appendable out;

  /**
   * Constructor for the MusicTextView class. Gets the musicModel and plays its state.
   *
   * @param musicModel  The music model that contains the relevant information for this view
   * @param out         Appendable - the output mode that this uses
   */
  public MusicTextView(IMusicModel musicModel, Appendable out) {
    this.musicModel = musicModel;
    this.out = out;
    // Text is static content and does not act as a normal dynamic view.
    this.play();
  }

  @Override
  public void play() {
    try {
      out.append(musicModel.getMusicState());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void pause() {
    // Text is static content and does not act as a normal dynamic view.
  }

  @Override
  public void playCurrent() {
    // Text is static content and does not act as a normal dynamic view.
  }
}
