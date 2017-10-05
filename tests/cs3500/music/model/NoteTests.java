package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests on the Node class.
 */
public class NoteTests {

  //Test equaility and hashmap
  @Test
  public void testHashCode1() {
    Note n1 = new Note(Note.Pitch.ASHARP, 2, 2);
    Note n2 = new Note(Note.Pitch.ASHARP, 2, 2);
    assertEquals(n1.hashCode(), n2.hashCode());
  }

  @Test
  public void testHashCode2() {
    Note n1 = new Note(Note.Pitch.ASHARP, 2, 2);
    Note n2 = new Note(Note.Pitch.ASHARP, 2, 3);
    assertEquals(n1.hashCode(), n2.hashCode());
  }

  @Test
  public void testHashCode3() {
    Note n1 = new Note(Note.Pitch.ASHARP, 2, 2);
    Note n2 = new Note(Note.Pitch.D, 2, 2);
    assertEquals(n1.hashCode() == n2.hashCode(), false);
    assertEquals(n1.hashCode() - n2.hashCode(), 8);
  }

  @Test
  public void testHashCode4() {
    Note n1 = new Note(Note.Pitch.ASHARP, 3, 2);
    Note n2 = new Note(Note.Pitch.ASHARP, 2, 2);
    assertEquals(n1.hashCode() - n2.hashCode(), 12);
    assertEquals(n1.hashCode() == n2.hashCode(), false);
  }


  @Test
  public void testCompareToNode1() {
    Note n1 = new Note(Note.Pitch.ASHARP, 2, 2);
    Note n2 = new Note(Note.Pitch.D, 2, 2);
    assertEquals(n1.hashCode() == n2.hashCode(), false);
    assertEquals(n1.compareTo(n2), 8);
  }

  @Test
  public void testCompareToNode2() {
    Note n1 = new Note(Note.Pitch.ASHARP, 3, 2);
    Note n2 = new Note(Note.Pitch.ASHARP, 2, 2);
    assertEquals(n1.compareTo(n2), 12);
  }


}
