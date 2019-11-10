package production.line;

/**
 * Create an interface called ScreenSpec. This will define 3 methods for the resolution, refresh
 * rate, and response time.
 */
public interface ScreenSpec {
  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
