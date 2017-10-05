package cs3500.music.util;

/**
 * A factor of Views. Since we do not know in advance what
 * the name of the main type is for a controller, we parameterize this factory interface
 * by an unknown type.
 */
public interface IViewFactory<T> {

  /**
   * Adds an IMusicView to this KeyViewFactory.
   *
   * @param s Different Strings will add different types of views
   * @return a controller which has all the correct views added
   */
  T addView(String s);
}