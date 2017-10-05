package cs3500.music.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.BorderFactory;

/**
 * A class to represent a key on the piano represented by KeyboardComponent.
 */
public class PianoKeyComponent extends JComponent implements MouseListener {
  protected boolean isPressed;
  protected boolean isSharp;
  protected final int x;
  private boolean isUnderCursor;
  private KeyboardComponent parent;

  /**
   * Constructs a new PianoKeyComponent representing a single key on the piano's keyboard.
   *
   * @param isPressed If this piano key starts off being pressed or not.
   * @param isSharp If this key is one of the black "sharp" keys on the piano keyboard.
   */
  public PianoKeyComponent(boolean isPressed, boolean isSharp, KeyboardComponent parent, int x) {
    this.isPressed = isPressed;
    this.isSharp = isSharp;
    this.x = x;
    this.isUnderCursor = false;
    this.parent = parent;

    addMouseListener(this);

    this.setOpaque(true);
    this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    this.setColor(g);
    g.fillRect(0, 0, GuiViewFrame.FRAME_WIDTH / 80, GuiViewFrame.FRAME_HEIGHT / 5 * 2);
  }

  /**
   * Sets the background color of this JComponent to its correct color.
   *
   * @param g The Graphics object of this JComponent we use to set the color.
   */
  private void setColor(Graphics g) {
    if (this.isPressed) {
      g.setColor(Color.ORANGE);
    }
    else if (this.isSharp) {
      g.setColor(Color.BLACK);
    }
    else {
      g.setColor(Color.WHITE);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (this.isUnderCursor) {
      parent.addNote(this);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // The only commands we need to take involve mousePressed events.
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // The only commands we need to take involve mousePressed events.
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.isUnderCursor = true;
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.isUnderCursor = false;
  }
}