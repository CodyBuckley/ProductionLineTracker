package production.line;

public class Screen implements ScreenSpec {

  // Fields utilized for the methods implemented from Interface ScreenSpec
  private String resolution;
  private int refreshRate;
  private int responseTime;

  /**
   * Constructor Screen that pulls in the specifications for the screen and set the values into
   * String resolution, int refreshRate, and int responseTime.
   *
   * @param resolution the screen's resolution for visual products
   * @param refreshRate the screen's refresh rate for visual products
   * @param responseTime the screen's response time for visual products
   */
  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  // Method getResolution returns a String called resolution for the screen specifications
  public String getResolution() {
    return resolution;
  }

  // Method getRefreshRate returns an int called refreshRate for the screen specifications
  public int getRefreshRate() {
    return refreshRate;
  }

  // Method getResponseTime returns an int called responseTime for the screen specification
  public int getResponseTime() {
    return responseTime;
  }

  // Overridden Method toString that returns the specification for screens in the same format as the
  // Product class.
  @Override
  public String toString() {
    return "\nResolution: "
        + resolution
        + "\nRefresh Rate: "
        + refreshRate
        + "\nResponse Time: "
        + responseTime;
  }
}
