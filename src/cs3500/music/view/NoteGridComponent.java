package cs3500.music.view;

import java.awt.GridLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import cs3500.music.model.EditorModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Range;

/**
 * Component which displays the grid of notes in the upper panel of the frame.
 */
public class NoteGridComponent extends JComponent implements IMusicView<EditorModel> {
  private IMusicModel musicModel;
  private int previousTime;
  private Range range;
  private int startTime;
  private static final int timeLength = 32;
  private List<GridColumnComponent> cols;
  private PitchLabelComponent leftBar;

  /**
   * Constructor for the NoteGridComponent class. It sets all non-static fields to default values.
   * Sets the layout to be one column so which correctly represents a NoteGridColumn.
   *
   * @param musicModel The music model based on which this column will be filled
   */
  NoteGridComponent(IMusicModel musicModel, Range range) {
    this.range = range;
    this.musicModel = musicModel;
    this.previousTime = 0;
    this.startTime = 0;

    GridLayout layout = new GridLayout(1, 1);
    this.setLayout(layout);
    this.leftBar = new PitchLabelComponent(range);

    this.cols = new LinkedList<GridColumnComponent>();


    // Sets up the inner GridColumnComponents
    this.cols = setColsAround(this.musicModel.getCurrentTime());
  }

  @Override
  public void playCurrent() {
    // case where there is a jump of some sort
    if (Math.abs(this.musicModel.getCurrentTime() - this.previousTime) > 1) {
      this.cols = this.setColsAround(musicModel.getCurrentTime());
      this.previousTime = musicModel.getCurrentTime();
    }
    // Prevents current from exceeding range
    else if (musicModel.getCurrentTime() <= musicModel.maxTime()) {
      if (musicModel.getCurrentTime() > this.startTime + timeLength) {
        this.remove(1);
        this.startTime += 1;

        GridColumnComponent column = new GridColumnComponent(musicModel, range,
                musicModel.getCurrentTime());
        this.add(column);
        this.cols.add(column);
        this.cols.remove(0);
      }
      else if (musicModel.getCurrentTime() < this.startTime) {
        this.cols = this.setColsAround(musicModel.getCurrentTime());
        this.revalidate();
        this.previousTime = musicModel.getCurrentTime();
        return;
      }
    }
    this.cols.get(previousTime - this.startTime).reform();
    this.cols.get(musicModel.getCurrentTime() - this.startTime).reform();
    this.previousTime = musicModel.getCurrentTime();
  }

  /**
   * Allows the view to skip to a new time. Redraws each visible GridColumnComponent to the
   * appropriate column of the view.
   *
   * @param newTime The time in the song to set the current time to and set the columns around.
   * @return A List of the new visible GridColumnComponents.
   */
  private List<GridColumnComponent> setColsAround(int newTime) {
    this.removeAll();
    this.add(leftBar);
    List<GridColumnComponent> grids = new ArrayList<GridColumnComponent>();
    int idealOffset = timeLength / 2;
    // if enough space on the left
    boolean left = (newTime - idealOffset >= 0);
    int max = Math.max(timeLength, musicModel.maxTime());
    boolean right = (newTime + idealOffset <= max);
    int i;
    if (left && right) {
      i = newTime - idealOffset;
    } else if (right) {
      i = 0;
    } else {
      i = max - timeLength;
    }
    for (int l = i;l <= i + timeLength; l += 1) {
      GridColumnComponent column = new GridColumnComponent(musicModel, range, l);
      grids.add(column);
      this.add(column);
    }
    this.startTime = i;
    return grids;
  }


  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
  }

  @Override
  public void play() {
    // The play() method is linked to other classes which call playCurrent as the time is updated.
  }

  @Override
  public void pause() {
    // For visual views the play method is handled by the controller and playCurrent so the views
    // can run simultaneous to each other, rendering pause unneeded.
  }
}