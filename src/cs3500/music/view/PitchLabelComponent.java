package cs3500.music.view;

import java.awt.GridLayout;
import java.awt.Color;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import cs3500.music.model.Note;
import cs3500.music.model.Range;

/**
 * Represents the pith labels on the left side of the noteGrid.
 */
public class PitchLabelComponent extends JComponent {
  private Range range;

  /**
   * Constructor for the this class. Initializes the labels using the given range.
   *
   * @param range The range based on which the labels will be formed
   */
  public PitchLabelComponent(Range range) {
    this.range = range;
    this.initLabels();
  }

  /**
   * Initialize the labels based on the {@link #range} of notes.
   */
  private void initLabels() {
    this.setLayout(new GridLayout(0, 1));

    JLabel emptyLabel = new JLabel("");

    this.add(emptyLabel);

    Stack<JLabel> labelStack = new Stack<JLabel>();

    for (Note note : range) {
      JLabel pitchLabel = new JLabel(note.toString());
      pitchLabel.setBorder(
              BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
      labelStack.push(pitchLabel);
    }

    while (!labelStack.empty()) {
      this.add(labelStack.pop());
    }
  }
}