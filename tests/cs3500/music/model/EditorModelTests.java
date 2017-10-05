package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests on the EditorModel.
 */
public class EditorModelTests {

  @Test
  public void testEditor() {
    IMusicModel editor = new EditorModel(4);
    IMusicModel toOverlay = new EditorModel(4);

    toOverlay.addNote(Note.Pitch.ASHARP, 3, 1, 2, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 2, 0, 2, 0, 64);
    editor.overlayNotes(toOverlay);

    editor.addNote(Note.Pitch.G, 3, 4, 2, 0, 64);
    editor.addNote(Note.Pitch.B, 4, 2, 12, 0, 64);

    String result = "   A#2   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4" +
            "  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4 \n" +
            "0   X                                                                               " +
            "                                                \n" +
            "1   |                                                           X                   " +
            "                                                \n" +
            "2                                                               |                   " +
            "                                             X  \n" +
            "3                                                                                   " +
            "                                             |  \n" +
            "4                                                X                                  " +
            "                                             |  \n" +
            "5                                                |                                  " +
            "                                             |  \n" +
            "6                                                                                   " +
            "                                             |  \n" +
            "7                                                                                   " +
            "                                             |  \n" +
            "8                                                                                   " +
            "                                             |  \n" +
            "9                                                                                   " +
            "                                             |  \n" +
            "10                                                                                  " +
            "                                             |  \n" +
            "11                                                                                  " +
            "                                             |  \n" +
            "12                                                                                  " +
            "                                             |  \n" +
            "13                                                                                  " +
            "                                             |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorAddNote() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.ASHARP, 3, 1, 2, 0, 64);

    String result = "  A#3 \n" +
            "0     \n" +
            "1  X  \n" +
            "2  |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorAddNoteDifferentMeasure() {
    IMusicModel editor = new EditorModel(3);
    editor.addNote(Note.Pitch.ASHARP, 3, 4, 3, 0, 64);
    String result = "  A#3 \n" +
            "0     \n" +
            "1     \n" +
            "2     \n" +
            "3     \n" +
            "4  X  \n" +
            "5  |  \n" +
            "6  |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorAddNoteDifferentMeasure2() {
    IMusicModel editor = new EditorModel(3);
    editor.addNote(Note.Pitch.ASHARP, 3, 4, 1, 0, 64);

    String result = "  A#3 \n" +
            "0     \n" +
            "1     \n" +
            "2     \n" +
            "3     \n" +
            "4  X  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorAddNotes() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.ASHARP, 3, 4, 1, 0, 64);
    editor.addNote(Note.Pitch.G, 3, 0, 2, 0, 64);
    editor.addNote(Note.Pitch.DSHARP, 2, 2, 4, 0, 64);
    String result = "  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3  D#3   E3   F3 " +
            " F#3   G3  G#3   A3  A#3 \n" +
            "0                                                                                  X" +
            "                 \n" +
            "1                                                                                  |" +
            "                 \n" +
            "2  X                                                                                " +
            "                 \n" +
            "3  |                                                                                " +
            "                 \n" +
            "4  |                                                                                " +
            "              X  \n" +
            "5  |                                                                                " +
            "                 \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEditorAddNotesIllegalNoteType() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.DSHARP, 2, 2, -1, 0, 64);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEditorAddNotesIllegalNoteType2() {
    IMusicModel editor = new EditorModel(3);
    editor.addNote(Note.Pitch.DSHARP, 2, 2, -20, 0, 64);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEditorNullPitch() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(null, 2, 2, 2, 0, 64);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEditorInvalidOctave() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.A, -1, 2, 2, 0, 64);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEditorInvalidStartTime() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.A, 1, -1, 2, 0, 64);
  }


  @Test
  public void testEditorEdgeValues() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.DSHARP, 0, 0, 0, 0, 64);

    String result = "";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorOverlayNotesWithoutConflict() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.E, 3, 0, 2, 0, 64);
    editor.addNote(Note.Pitch.F, 3, 0, 4, 0, 64);

    IMusicModel toOverlay = new EditorModel(4);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 0, 6, 0, 64);
    toOverlay.addNote(Note.Pitch.D, 3, 2, 2, 0, 64);
    toOverlay.addNote(Note.Pitch.B, 2, 0,2, 0, 64);
    editor.overlayNotes(toOverlay);

    String result = "   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3 \n" +
            "0  X                        X    X                        X  \n" +
            "1  |                        |    |                        |  \n" +
            "2                 X              |                        |  \n" +
            "3                 |              |                        |  \n" +
            "4                                                         |  \n" +
            "5                                                         |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorOverlayNotes() {
    IMusicModel toOverlay = new EditorModel(4);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 0, 6, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 2, 2, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 2, 0, 2, 0, 64);
    IMusicModel editor = new EditorModel(4);
    editor.overlayNotes(toOverlay);

    String result = "  A#2   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3 \n" +
            "0  X                                                           X  \n" +
            "1  |                                                           |  \n" +
            "2                                                              X  \n" +
            "3                                                              |  \n" +
            "4                                                              X  \n" +
            "5                                                              |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorOverlayNotes2() {
    IMusicModel toOverlay = new EditorModel(4);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 0, 2, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 2, 4, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 2, 0, 2, 0, 64);
    IMusicModel editor = new EditorModel(4);
    editor.overlayNotes(toOverlay);
    String result = "  A#2   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3 \n" +
            "0  X                                                           X  \n" +
            "1  |                                                           |  \n" +
            "2                                                              X  \n" +
            "3                                                              |  \n" +
            "4                                                              |  \n" +
            "5                                                              |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorAppendNotes() {
    IMusicModel toOverlay = new EditorModel(4);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 1, 8, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 2, 0, 8, 0, 64);
    IMusicModel editor = new EditorModel(4);
    editor.overlayNotes(toOverlay);

    IMusicModel toAppend = new EditorModel(4);
    toAppend.addNote(Note.Pitch.D, 3, 0, 24, 0, 64);
    toAppend.addNote(Note.Pitch.E, 3, 4, 8, 0, 64);
    editor.appendNotes(toAppend);
    String result = "   A#2   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3 \n" +
            "0   X                                                              \n" +
            "1   |                                                           X  \n" +
            "2   |                                                           |  \n" +
            "3   |                                                           |  \n" +
            "4   |                                                           |  \n" +
            "5   |                                                           |  \n" +
            "6   |                                                           |  \n" +
            "7   |                                                           |  \n" +
            "8                                                               |  \n" +
            "9                       X                                          \n" +
            "10                      |                                          \n" +
            "11                      |                                          \n" +
            "12                      |                                          \n" +
            "13                      |         X                                \n" +
            "14                      |         |                                \n" +
            "15                      |         |                                \n" +
            "16                      |         |                                \n" +
            "17                      |         |                                \n" +
            "18                      |         |                                \n" +
            "19                      |         |                                \n" +
            "20                      |         |                                \n" +
            "21                      |                                          \n" +
            "22                      |                                          \n" +
            "23                      |                                          \n" +
            "24                      |                                          \n" +
            "25                      |                                          \n" +
            "26                      |                                          \n" +
            "27                      |                                          \n" +
            "28                      |                                          \n" +
            "29                      |                                          \n" +
            "30                      |                                          \n" +
            "31                      |                                          \n" +
            "32                      |                                          \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorAppendNotesOnTop() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.ASHARP, 3, 1, 2, 0, 64);
    IMusicModel toAppend = new EditorModel(4);
    toAppend.addNote(Note.Pitch.ASHARP, 3, 0, 6, 0, 64);
    editor.appendNotes(toAppend);
    String result = "  A#3 \n" +
            "0     \n" +
            "1  X  \n" +
            "2  |  \n" +
            "3  X  \n" +
            "4  |  \n" +
            "5  |  \n" +
            "6  |  \n" +
            "7  |  \n" +
            "8  |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorRemoveNoteRegular1() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.ASHARP, 3, 1, 8, 0, 64);
    editor.removeNote(Note.Pitch.ASHARP, 3, 4, 0, 64);

    String result = "  A#3 \n" +
            "0     \n" +
            "1     \n" +
            "2     \n" +
            "3     \n" +
            "4     \n" +
            "5     \n" +
            "6     \n" +
            "7     \n" +
            "8     \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorRemoveNoteRegular2() {
    IMusicModel editor = new EditorModel(4);
    IMusicModel toOverlay = new EditorModel(4);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 1, 8, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 2, 0, 8, 0, 64);

    editor.overlayNotes(toOverlay);
    editor.removeNote(Note.Pitch.ASHARP, 3, 1, 0, 64);

    String result = "  A#2   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3 \n" +
            "0  X                                                              \n" +
            "1  |                                                              \n" +
            "2  |                                                              \n" +
            "3  |                                                              \n" +
            "4  |                                                              \n" +
            "5  |                                                              \n" +
            "6  |                                                              \n" +
            "7  |                                                              \n" +
            "8                                                                 \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testEditorRemoveOverlapping() {
    IMusicModel editor = new EditorModel(1);
    IMusicModel toOverlay = new EditorModel(4);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 0, 6, 0, 64);
    toOverlay.addNote(Note.Pitch.ASHARP, 3, 2, 2, 0, 64);

    editor.overlayNotes(toOverlay);
    editor.removeNote(Note.Pitch.ASHARP, 3, 0, 0, 64);

    String result = "  A#3 \n" +
            "0     \n" +
            "1     \n" +
            "2  X  \n" +
            "3  |  \n" +
            "4  X  \n" +
            "5  |  \n";
    assertEquals(editor.getMusicState(), result);
  }


  @Test
  public void testEditorIterateOverRange() {
    Note start = new Note(Note.Pitch.A, 2, 3);
    Note end = new Note(Note.Pitch.D, 4, 3);
    Range r = new Range(start, end);
    String result = "";
    for (Note note : r) {
      result += note.toString();
    }
    assertEquals(result, "A2A#2B2C3C#3D3D#3E3F3F#3G3G#3A3A#3B3C4C#4D4");
  }

  @Test
  public void testEditorEmptyGetStatus() {
    IMusicModel editor = new EditorModel(4);
    String result = "";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testRangeHighTimePadding() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.A, 2, 0, 104, 0, 64);

    String result = "     A2 \n" +
            "0    X  \n" +
            "1    |  \n" +
            "2    |  \n" +
            "3    |  \n" +
            "4    |  \n" +
            "5    |  \n" +
            "6    |  \n" +
            "7    |  \n" +
            "8    |  \n" +
            "9    |  \n" +
            "10   |  \n" +
            "11   |  \n" +
            "12   |  \n" +
            "13   |  \n" +
            "14   |  \n" +
            "15   |  \n" +
            "16   |  \n" +
            "17   |  \n" +
            "18   |  \n" +
            "19   |  \n" +
            "20   |  \n" +
            "21   |  \n" +
            "22   |  \n" +
            "23   |  \n" +
            "24   |  \n" +
            "25   |  \n" +
            "26   |  \n" +
            "27   |  \n" +
            "28   |  \n" +
            "29   |  \n" +
            "30   |  \n" +
            "31   |  \n" +
            "32   |  \n" +
            "33   |  \n" +
            "34   |  \n" +
            "35   |  \n" +
            "36   |  \n" +
            "37   |  \n" +
            "38   |  \n" +
            "39   |  \n" +
            "40   |  \n" +
            "41   |  \n" +
            "42   |  \n" +
            "43   |  \n" +
            "44   |  \n" +
            "45   |  \n" +
            "46   |  \n" +
            "47   |  \n" +
            "48   |  \n" +
            "49   |  \n" +
            "50   |  \n" +
            "51   |  \n" +
            "52   |  \n" +
            "53   |  \n" +
            "54   |  \n" +
            "55   |  \n" +
            "56   |  \n" +
            "57   |  \n" +
            "58   |  \n" +
            "59   |  \n" +
            "60   |  \n" +
            "61   |  \n" +
            "62   |  \n" +
            "63   |  \n" +
            "64   |  \n" +
            "65   |  \n" +
            "66   |  \n" +
            "67   |  \n" +
            "68   |  \n" +
            "69   |  \n" +
            "70   |  \n" +
            "71   |  \n" +
            "72   |  \n" +
            "73   |  \n" +
            "74   |  \n" +
            "75   |  \n" +
            "76   |  \n" +
            "77   |  \n" +
            "78   |  \n" +
            "79   |  \n" +
            "80   |  \n" +
            "81   |  \n" +
            "82   |  \n" +
            "83   |  \n" +
            "84   |  \n" +
            "85   |  \n" +
            "86   |  \n" +
            "87   |  \n" +
            "88   |  \n" +
            "89   |  \n" +
            "90   |  \n" +
            "91   |  \n" +
            "92   |  \n" +
            "93   |  \n" +
            "94   |  \n" +
            "95   |  \n" +
            "96   |  \n" +
            "97   |  \n" +
            "98   |  \n" +
            "99   |  \n" +
            "100  |  \n" +
            "101  |  \n" +
            "102  |  \n" +
            "103  |  \n";
    assertEquals(editor.getMusicState(), result);
  }


  @Test
  public void testTenOctaveRangeTest() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.A, 2, 0, 2, 0, 64);
    editor.addNote(Note.Pitch.B, 10, 3, 2, 0, 64);
    String result = "   A2  A#2   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3 " +
            "  C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5" +
            "   F5  F#5   G5  G#5   A5  A#5   B5   C6  C#6   D6  D#6   E6   F6  F#6   G6  G#6   " +
            "A6  A#6   B6   C7  C#7   D7  D#7   E7   F7  F#7   G7  G#7   A7  A#7   B7   C8  C#8  " +
            " D8  D#8   E8   F8  F#8   G8  G#8   A8  A#8   B8   C9  C#9   D9  D#9   E9   F9  F#9 " +
            "  G9  G#9   A9  A#9   B9  C10  C#10 D10  D#10 E10  F10  F#10 G10  G#10 A10  A#10 B10" +
            " \n" +
            "0  X                                                                                " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                            \n" +
            "1  |                                                                                " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                            \n" +
            "2                                                                                   " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                            \n" +
            "3                                                                                   " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                         X  \n" +
            "4                                                                                   " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                                    " +
            "                                                                         |  \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testHigherOctaveRangeTest() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.A, 12, 0, 2, 0, 64);
    editor.addNote(Note.Pitch.ASHARP, 9, 2, 4, 0, 64);
    editor.addNote(Note.Pitch.B, 10, 3, 2, 0, 64);
    String result = "  A#9   B9  C10  C#10 D10  D#10 E10  F10  F#10 G10  G#10 A10  A#10 B10  C11 " +
            " C#11 D11  D#11 E11  F11  F#11 G11  G#11 A11  A#11 B11  C12  C#12 D12  D#12 E12  F12" +
            "  F#12 G12  G#12 A12 \n" +
            "0                                                                                   " +
            "                                                                                    " +
            "          X  \n" +
            "1                                                                                   " +
            "                                                                                    " +
            "          |  \n" +
            "2  X                                                                                " +
            "                                                                                    " +
            "             \n" +
            "3  |                                                                X               " +
            "                                                                                    " +
            "             \n" +
            "4  |                                                                |               " +
            "                                                                                    " +
            "             \n" +
            "5  |                                                                                " +
            "                                                                                    " +
            "             \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testHigherOctaveRangeTestWithHighTime() {
    IMusicModel editor = new EditorModel(4);
    editor.addNote(Note.Pitch.A, 12, 0, 2, 0, 64);
    editor.addNote(Note.Pitch.ASHARP, 9, 2, 16, 0, 64);
    editor.addNote(Note.Pitch.B, 10, 3, 32, 0, 64);
    String result = "   A#9   B9  C10  C#10 D10  D#10 E10  F10  F#10 G10  G#10 A10  A#10 B10  C11" +
            "  C#11 D11  D#11 E11  F11  F#11 G11  G#11 A11  A#11 B11  C12  C#12 D12  D#12 E12  " +
            "F12  F#12 G12  G#12 A12 \n" +
            "0                                                                                   " +
            "                                                                                    " +
            "           X  \n" +
            "1                                                                                   " +
            "                                                                                    " +
            "           |  \n" +
            "2   X                                                                               " +
            "                                                                                    " +
            "              \n" +
            "3   |                                                                X              " +
            "                                                                                    " +
            "              \n" +
            "4   |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "5   |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "6   |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "7   |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "8   |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "9   |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "10  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "11  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "12  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "13  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "14  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "15  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "16  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "17  |                                                                |              " +
            "                                                                                    " +
            "              \n" +
            "18                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "19                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "20                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "21                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "22                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "23                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "24                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "25                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "26                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "27                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "28                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "29                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "30                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "31                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "32                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "33                                                                   |              " +
            "                                                                                    " +
            "              \n" +
            "34                                                                   |              " +
            "                                                                                    " +
            "              \n";
    assertEquals(editor.getMusicState(), result);
  }

  @Test
  public void testGetNotes() {
    IMusicModel editor = new EditorModel(4);
    Note note1 = new Note(Note.Pitch.A, 12, 0, 2, 0, 64);
    Note note2 = new Note(Note.Pitch.ASHARP, 9, 0, 16, 0, 64);
    editor.addNote(note1);
    editor.addNote(note2);
    List<Note> notesSorted = new ArrayList<>(Arrays.asList(note2, note1));
    List<Note> notesNotSorted = new ArrayList<>(Arrays.asList(note1, note2));
    assertEquals(notesSorted, editor.getNotes());
    assertNotEquals(notesNotSorted, editor.getNotes());
  }

  @Test
  public void testGetNotesAtTime() {
    IMusicModel editor = new EditorModel(4);
    Note note1 = new Note(Note.Pitch.A, 12, 0, 2, 0, 64);
    Note note2 = new Note(Note.Pitch.ASHARP, 9, 0, 16, 0, 64);
    editor.addNote(note1);
    editor.addNote(note2);
    List<Note> notesFew = new ArrayList<>(Arrays.asList(note2));
    List<Note> notesSorted = new ArrayList<>(Arrays.asList(note2, note1));
    List<Note> notesNotSorted = new ArrayList<>(Arrays.asList(note1, note2));
    assertEquals(notesSorted, editor.getNotesAtTime(0));
    assertEquals(notesSorted, editor.getNotesAtTime(1));
    assertEquals(notesFew, editor.getNotesAtTime(2));
    assertNotEquals(notesNotSorted, editor.getNotesAtTime(0));
  }

  @Test
  public void testMaxTime() {
    IMusicModel editor = new EditorModel(4);
    assertEquals(0, editor.maxTime());

    Note note1 = new Note(Note.Pitch.A, 12, 0, 2, 0, 64);
    editor.addNote(note1);
    assertEquals(2, editor.maxTime());

    Note note2 = new Note(Note.Pitch.ASHARP, 9, 0, 16, 0, 64);
    editor.addNote(note2);
    assertEquals(16, editor.maxTime());
  }

  @Test
  public void testCurrentTime() {
    IMusicModel editor = new EditorModel(4);
    assertEquals(0, editor.getCurrentTime());

    editor.incrementTime(0);
    assertEquals(0, editor.getCurrentTime());

    editor.incrementTime(1);
    assertEquals(0, editor.getCurrentTime());

    editor.addNote(Note.Pitch.C, 2, 0, 10, 0,64);
    editor.incrementTime(10);
    assertEquals(10, editor.getCurrentTime());

    editor.incrementTime(-1);
    assertEquals(9, editor.getCurrentTime());

    editor.incrementTime(-10);
    assertEquals(9, editor.getCurrentTime());

    editor.incrementTime(-9);
    assertEquals(0, editor.getCurrentTime());
  }

  @Test
  public void testGetMeasure() {
    IMusicModel editor = new EditorModel(4);
    assertEquals(4, editor.getMeasure());
    editor = new EditorModel(1);
    assertEquals(1, editor.getMeasure());
    editor = new EditorModel(10);
    assertEquals(10, editor.getMeasure());
  }

  @Test (expected =  IllegalArgumentException.class)
  public void testGetMeasureInvalidInput1() {
    IMusicModel editor = new EditorModel(0);
    assertEquals(4, editor.getMeasure());
  }

  @Test (expected =  IllegalArgumentException.class)
  public void testGetMeasureInvalidInput2() {
    IMusicModel editor = new EditorModel(-1);
    assertEquals(4, editor.getMeasure());
  }

  @Test
  public void testGetRange() {
    IMusicModel editor = new EditorModel(4);
    Note note = new Note(Note.Pitch.C, 1, 2, 3, 4, 5);
    editor.addNote(note);
    assertEquals(editor.getRange().toString(), new Range(note, note).toString());
  }

  @Test
  public void testGetNote1() {
    IMusicModel editor = new EditorModel(4);
    assertEquals(editor.getTempo(), 200000);
  }

  @Test
  public void testGetNote2() {
    IMusicModel editor = new EditorModel(4, 10);
    assertEquals(editor.getTempo(), 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNoteInvalid() {
    IMusicModel editor = new EditorModel(4, -10);
    assertEquals(editor.getTempo(), -10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNoteInvalid2() {
    IMusicModel editor = new EditorModel(4, 0);
    assertEquals(editor.getTempo(), 0);
  }

  @Test
  public void testSetCurrentTime() {
    IMusicModel editor = new EditorModel(4);
    Note note = new Note(Note.Pitch.C, 1, 2, 20, 4, 5);
    editor.addNote(note);
    assertEquals(editor.getCurrentTime(), 0);

    editor.setCurrentTime(20);
    assertEquals(editor.getCurrentTime(), 20);

    editor.setCurrentTime(0);
    assertEquals(editor.getCurrentTime(), 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetCurrentTimeInvalid() {
    IMusicModel editor = new EditorModel(4);
    Note note = new Note(Note.Pitch.C, 1, 2, 20, 4, 5);
    editor.addNote(note);

    editor.setCurrentTime(-1);
    assertEquals(editor.getCurrentTime(), 20);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetCurrentTimeInvalid2() {
    IMusicModel editor = new EditorModel(4);
    Note note = new Note(Note.Pitch.C, 1, 2, 20, 4, 5);
    editor.addNote(note);

    editor.setCurrentTime(30);
    assertEquals(editor.getCurrentTime(), 20);
  }

  @Test
  public void testIsPaused() {
    IMusicModel editor = new EditorModel(4);
    Note note = new Note(Note.Pitch.C, 1, 2, 20, 4, 5);
    editor.addNote(note);

    assertEquals(true, editor.isPaused());
  }

  @Test
  public void testSetPausedAndIsPaused() {
    IMusicModel editor = new EditorModel(4);
    Note note = new Note(Note.Pitch.C, 1, 2, 20, 4, 5);
    editor.addNote(note);

    editor.setPaused(true);
    assertEquals(true, editor.isPaused());

    editor.setPaused(false);
    assertEquals(false, editor.isPaused());
  }
}