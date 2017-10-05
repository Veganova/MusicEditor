package cs3500.music.mock;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Represents a mock midi receiver which will record calls in a log for testing purposes.
 */
public class MockMidiReceiver implements Receiver {
  StringBuilder log;

  public MockMidiReceiver(StringBuilder stringBuilder) {
    this.log = stringBuilder;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage msg = ((ShortMessage)message);
    log.append(msg.getCommand() + " " + msg.getChannel() + " "
            + msg.getData1() + " " + msg.getData2()  + "\n");
  }

  @Override
  public void close() {
    log.append("Receiver closed." + "\n");
  }
}
