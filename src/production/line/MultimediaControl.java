package production.line;

/**
 * All of the items on this production line will have basic media controls. Create an interface
 * called MultimediaControl that will define the following methods which don't need to return
 * anything.
 */
public interface MultimediaControl {

  // The basic media controls are defined in the interface. They are void because they do not return
  // anything.
  void play();

  void stop();

  void previous();

  void next();
}
