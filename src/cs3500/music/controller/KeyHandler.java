package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.KeyboardController;
import cs3500.music.model.IMusicModel;

/**
 * A class to handle key events to be passed as a KeyListener to other classes.
 */
public class KeyHandler implements KeyListener {
  protected Map<Integer, Runnable> keyEventsMap;
  private IMusicModel musicModel;
  private KeyboardController controller;

  /**
   * Makes a new KeyHandler which will be passes as a KeyListener.
   */
  public KeyHandler(IMusicModel musicModel, KeyboardController controller) {
    this.keyEventsMap = new HashMap<Integer, Runnable>();
    this.musicModel = musicModel;
    this.controller = controller;

    this.configureKeyboardListener();
  }

  /**
   * Initializes a map of keyEvents containing the key code and a runnable to be run if the
   * associated key is pressed.
   */
  protected void configureKeyboardListener() {
    keyEventsMap.put(KeyEvent.VK_RIGHT, () -> {
      musicModel.incrementTime(1);
      this.controller.musicViews.playCurrent();
    }
    );
    keyEventsMap.put(KeyEvent.VK_LEFT, () -> {
      musicModel.incrementTime(-1);
      this.controller.musicViews.playCurrent();
    }
    );
    keyEventsMap.put(KeyEvent.VK_SPACE, () -> {
      if (musicModel.isPaused()) {
        musicModel.setPaused(false);
        this.controller.playViews();
      }
      else if (!musicModel.isPaused()) {
        musicModel.setPaused(true);
        this.controller.pauseViews();
      }
    }
    );
    keyEventsMap.put(KeyEvent.VK_HOME, () -> {
      musicModel.setCurrentTime(0);
      this.controller.musicViews.playCurrent();
    }
    );
    keyEventsMap.put(KeyEvent.VK_END, () -> {
      musicModel.setCurrentTime(musicModel.maxTime());
      this.controller.musicViews.playCurrent();
    }
    );
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // The only commands we need to take involve keyPress events.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyEventsMap.containsKey(e.getKeyCode())) {
      keyEventsMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // The only commands we need to take involve keyPress events.
  }
}
