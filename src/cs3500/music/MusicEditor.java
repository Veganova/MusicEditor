package cs3500.music;

import cs3500.music.controller.IMusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.KeyViewFactory;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.IViewFactory;
import cs3500.music.util.MusicComposition;
import cs3500.music.util.MusicReader;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Creates a music editor with a certain kind of view, controller,
 * and model that the user can interact with.
 */
public class MusicEditor {

  /**
   * Initializes and runs the program, making a model, view, and controller.
   *
   * @param args Inputs to be interpreted and run.
   * @throws IOException If the input or output streams are invalid.
   * @throws InvalidMidiDataException If the midi data is not accepted by the midi.
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    String textFile = args[0];
    String type = args[1];

    if (type == null) {
      type = "composite";
    }

    CompositionBuilder<IMusicModel> composer = new MusicComposition(4);
    Readable reader = new FileReader(textFile);
    IMusicModel musicModel = MusicReader.parseFile(reader, composer);

    IViewFactory<IMusicController> viewFactory = new KeyViewFactory(musicModel,
            "arrow", System.out);
    IMusicController controller = viewFactory.addView(type);
  }
}
