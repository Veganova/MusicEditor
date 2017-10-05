package cs3500.music.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Range;

/**
 * A JPanel used only as a scrollable holder for the Components of the top half of the frame.
 */
public class ConcreteGuiViewPanel extends JPanel {
  private NoteGridComponent noteGrid;

  /**
   * Constructor for ConcreteGuiPanel. Initializes the vertical range to be 120 pitches and adds
   * the child JPanel to this as a scrollable.
   * @param musicModel The IMusicModel of the song that is represented by this classes child.
   */
  public ConcreteGuiViewPanel(IMusicModel musicModel) {
    Range range = new Range(new Note(Note.Pitch.C, 0, 1),
            new Note(Note.Pitch.B, 9, 1));

    this.noteGrid = new NoteGridComponent(musicModel, range);

    // Sets up the noteGrid to scroll
    JScrollPane noteGridScrolls = new JScrollPane(noteGrid);
    this.add(noteGridScrolls);
    setVisible(true);
  }

  /**
   * Plays the Notes at the current time of the musicModel of the JComponents contained within this.
   */
  public void playCurrent() {
    noteGrid.playCurrent();
  }
}
