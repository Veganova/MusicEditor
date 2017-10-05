package cs3500.music.mock;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 * Represents a mock midi device emulating a synthesizer which will record
 * calls in a log for testing purposes.
 */
public class MockMidiDevice implements MidiDevice {
  Receiver midiReceiver;
  StringBuilder log;

  public MockMidiDevice(StringBuilder stringBuilder) {
    this.log = stringBuilder;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    // The mock midi synthesizer never needs to open while testing.
  }

  @Override
  public void close() {
    // The mock midi synthesizer never needs to close while testing.
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockMidiReceiver(log);
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }
}
