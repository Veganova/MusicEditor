package cs3500.music.view;

import java.awt.GridLayout;
import java.awt.Dimension;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;

/**
 * A skeleton Frame (i.e., a window) in Swing.
 */
public class GuiViewFrame extends javax.swing.JFrame
        implements IMusicView<EditorModel> {

  protected final static int FRAME_WIDTH = 1200;
  protected final static int FRAME_HEIGHT = 1000;

  private ConcreteGuiViewPanel noteGridHolder;
  private KeyboardComponent keyboard;


  /**
   * Creates new GuiView.
   * Sets default operation of closing the window as exiting from the application.
   * Creates and adds a music editor panel and a piano into the display panel.
   * Sets this window as the key listener so any inputs will be received through this.
   */
  public GuiViewFrame(IMusicModel model, String title, IMusicController musicController) {
    super(title);

    IMusicModel musicModel = model;
    IMusicController controller = musicController;
    this.keyboard = new KeyboardComponent(musicModel, controller);

    noteGridHolder = new ConcreteGuiViewPanel(musicModel);
    noteGridHolder.setLayout(new GridLayout(1, 1));

    // Delegates the drawing from the main displayPanel to each JComponent contained within it.
    this.setLayout(new GridLayout(2, 1));
    this.add(noteGridHolder);
    this.add(keyboard);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();

    this.setVisible(true);
  }

  @Override
  public boolean isFocusable() {
    return true;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
  }

  @Override
  public void play() {
    this.playCurrent();
  }

  @Override
  public void pause() {
    // For visual views the play method is handled by the controller and playCurrent so the views
    // can run simultaneous to each other, rendering pause unneeded.
  }

  @Override
  public void playCurrent() {
    noteGridHolder.playCurrent();
    keyboard.playCurrent();
  }
}