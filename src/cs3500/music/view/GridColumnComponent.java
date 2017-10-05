package cs3500.music.view;

import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Range;

/**
 * Represents a column in the collection of columns of the noteGrid.
 */
public class GridColumnComponent extends JComponent {
  private int time;
  private IMusicModel musicModel;
  private Range range;

  private static Border topBottom = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK);
  private static Border redLine = BorderFactory.createMatteBorder(0, 4, 0, 0, Color.RED);
  private static Border measureBorder = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK);

  /**
   * Constructor for GridColumnComponent. Sets the values to those given and initializes this
   * column.
   *
   * @param musicModel  The music musicModel that contains the important information for creation
   * @param range       The range (height) of this column
   * @param time        The time at which this column is situated
   */
  protected GridColumnComponent(IMusicModel musicModel, Range range, int time) {
    this.musicModel = musicModel;
    this.time = time;
    this.range = range;

    initColumn();
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
  }

  /**
   * Initializes this column objects using the musicModel and its current time.
   */
  private void initColumn() {
    JLabel label = new JLabel(time + "");
    JLabel emptyLabel = new JLabel("");

    List<Note> currentNotes = musicModel.getNotesAtTime(time);
    this.setLayout(new GridLayout(0, 1));
    List<JButton> toAdd = new ArrayList<JButton>();

    Border cellBorder;

    if (musicModel.getCurrentTime() == this.time) {
      cellBorder = new CompoundBorder(redLine, topBottom);
    }
    else if (this.time % musicModel.getMeasure() == 0) {
      cellBorder = new CompoundBorder(measureBorder, topBottom);
    }
    else {
      cellBorder = topBottom;
    }

    if (this.time % musicModel.getMeasure() == 0) {
      this.add(label);
    }
    else {
      this.add(emptyLabel);
    }


    for (Note note : range) {
      JButton noteButton = new JButton();
      noteButton.setBorder(cellBorder);

      if (currentNotes != null && currentNotes.contains(note)) {
        Note actual = currentNotes.get(currentNotes.indexOf(note));
        if (actual.isStart(time)) {
          noteButton.setBackground(Color.BLACK);
        }
        else {
          noteButton.setBackground(Color.GREEN);
        }
      }
      else {
        noteButton.setBackground(Color.WHITE);
      }

      toAdd.add(0, noteButton);
    }
    // Adds the buttons in the correct order
    for (JButton button : toAdd) {
      this.add(button);
    }
  }

  /**
   * Recreates this column based on the musicModel and its current time.
   */
  protected void reform() {
    this.removeAll();
    initColumn();
    revalidate();
  }
}
