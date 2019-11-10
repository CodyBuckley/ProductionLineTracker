package production.line;

public class MoviePlayer extends Product implements MultimediaControl {

  // Add 2 fields to this class called screen and monitorType and assign appropriate types to them.
  private Screen screen;
  private MonitorType monitorType;

  /**
   * Create a constructor that accepts the name, manufacturer, a screen, and a monitor type. The
   * constructor can set the item type to VISUAL. The constructor pulls name and manufacturer from
   * Product and defaults the type to VISUAL
   *
   * @param itemName the name of the product
   * @param manufacturerName the name of the manufacturer
   * @param screen the type of screen for visual products
   * @param monitorType the type of monitor for visual products
   */
  public MoviePlayer(
      String itemName, String manufacturerName, Screen screen, MonitorType monitorType) {
    super(itemName, manufacturerName, ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  // Basic visual media control "Play" is defaulted to print "Playing movie"
  public void play() {
    System.out.println("Playing movie");
  }

  // Basic visual media control "Stop" is defaulted to print "Stopping movie"
  public void stop() {
    System.out.println("Stopping movie");
  }

  // Basic visual media control "Next" is defaulted to print "Next movie"
  public void next() {
    System.out.println("Next movie");
  }

  // Basic visual media control "Previous" is defaulted to print "Previous movie"
  public void previous() {
    System.out.println("Previous movie");
  }

  // Overridden String Method toString that pulls the parent toString from Product class and the
  // toString from Screen.java and the monitorType from enum MonitorType.java
  @Override
  public String toString() {
    return super.toString() + screen.toString() + "\nScreen: " + "\nMonitor Type: " + monitorType;
  }
}
