package io.github.daltonsenseman;

/**
 * This class holds screen information such as resolution and refresh rate and implements the
 * interface ScreenSpec to contractually bind the the class ti implement those methods.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
public class Screen implements ScreenSpec {

  private final String resolution;
  private final int refreshrate;
  private final int responsetime;

  /**
   * Constructor to populate the fields of the class with the screen information.
   *
   * @param resolution   String of the resolution (720p, 1080p.... etc...)
   * @param refreshrate  Int of the refreshrate how fast the screen refreshes pixels in Hertz.
   * @param responsetime Int of the response time how fast the screen takes in new data in milisec.
   */
  public Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  /**
   * gets the current value of the Resolution of hte screen.
   *
   * @return String of the field resolution (480p, 720p etc...)
   */
  public String getResolution() {
    return this.resolution;
  }

  /**
   * gets the current value of the RefreshRate of the Screen.
   *
   * @return Int of how fast the screen refreshes its pixels.
   */
  public int getRefreshRate() {
    return this.refreshrate;
  }

  /**
   * gets the current Response time of the screen.
   *
   * @return INt of how fast new data is handled by the screen.
   */
  public int getResponseTime() {
    return this.responsetime;
  }

  /**
   * This method overrides the toString method from the Object class and allows the user to grab the
   * current values of the fields of Screen as a String.
   *
   * @return A 3 line String the first showing resolution the second line refreshrate, and the last
   * one holding responsetime..
   */
  public String toString() {
    return "Resolution: " + this.resolution + "\nRefresh rate: " + this.refreshrate
        + "\nResponse time: " + this.responsetime;
  }
}
