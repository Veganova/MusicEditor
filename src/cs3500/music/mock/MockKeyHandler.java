package cs3500.music.mock;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IMusicModel;

/**
 * Class representing a mock key handler used for testing purposes.
 */
public class MockKeyHandler implements KeyListener {
  protected Map<Integer, Runnable> keyEventsMap;
  private IMusicModel musicModel;
  public StringBuilder log;

  /**
   * Makes a new MockKeyHandler which will be used for testing.
   */
  public MockKeyHandler(IMusicModel musicModel, StringBuilder log) {
    this.keyEventsMap = new HashMap<Integer, Runnable>();
    this.musicModel = musicModel;
    this.log = log;

    this.configureKeyboardListener();
  }

  /**
   * Initializes a map of keyEvents containing the key code and a runnable which will log the
   * action and the result.
   */
  protected void configureKeyboardListener() {
    keyEventsMap.put(KeyEvent.VK_RIGHT, () -> {
      musicModel.incrementTime(1);
      this.log.append("right arrow makes time: " + musicModel.getCurrentTime() + "\n");
    }
    );
    keyEventsMap.put(KeyEvent.VK_LEFT, () -> {
      musicModel.incrementTime(-1);
      this.log.append("left arrow makes time: " + musicModel.getCurrentTime() + "\n");
    }
    );
    keyEventsMap.put(KeyEvent.VK_SPACE, () -> {
      if (musicModel.isPaused()) {
        musicModel.setPaused(false);
      }
      else if (!musicModel.isPaused()) {
        musicModel.setPaused(true);
      }

      this.log.append("spacebar makes model's paused: " + musicModel.isPaused() + "\n");
    }
    );
    keyEventsMap.put(KeyEvent.VK_HOME, () -> {
      musicModel.setCurrentTime(0);
      this.log.append("home button makes time: " + musicModel.getCurrentTime() + "\n");
    }
    );
    keyEventsMap.put(KeyEvent.VK_END, () -> {
      musicModel.setCurrentTime(musicModel.maxTime());
      this.log.append("end button makes time: " + musicModel.getCurrentTime() + "\n");
    }
    );
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // The only commands we need to take involve keyPress events.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // This method is mocked for testing so there is no need for content.
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // The only commands we need to take involve keyPress events.
  }

  /**
   * Takes a keyCode that would be included in an actual keyEvent if a KeyHandler was
   * called by the KeyListener.
   *
   * @param keyCode the keyEvent keyCode which would be included in an actual keyEvent if a
   *                KeyHandler was called by the KeyListener.
   */
  public void mockKeyPressed(int keyCode) {
    if (keyEventsMap.containsKey(keyCode)) {
      keyEventsMap.get(keyCode).run();
    }
  }
}