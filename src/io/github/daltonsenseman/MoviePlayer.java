package io.github.daltonsenseman;

/**
 * This class is to create Movie players objects that hold the value of created Movie Players. The
 * class extends Product for the general product information that all products have, and implements
 * the MultimediaControl interface for the action methods it holds. (Play, Stop, etc...)
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
public class MoviePlayer extends Product implements MultimediaControl {

  private final Screen screen;
  private final MonitorType monitorType;

  /**
   * Constructor that builds a MoviePlayer Object it calls to the Super of Product to pass up the
   * name, manufacturer, and set the Type to VISUAL. Setting the rest of the fields according to the
   * information passed in as Params.
   *
   * @param name         the name of the product itself.
   * @param manufacturer the manufacturer or the product.
   * @param screen       Screen Object to hold the refresh rate and resolution.
   * @param monitorType  MonitorType Object to pass in the Screen Enums.
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, "VISUAL");
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * This method prints to the console that the device is currently playing a video.
   */
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * This method prints to the console that the device is stopping the video playing.
   */
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * This method prints to the console that the device is moving to the precious video.
   */
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * This method prints to the console that the device is moving to the next video.
   */
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * This method overrides the toString method from the Object class and allows the user to grab the
   * current values of the fields of MoviePlayer as a String.
   *
   * @return A 2 line String the first showing Screen info and the second line MonitorType Enum.
   */
  public String toString() {
    return super.toString() + "Screen: " + this.screen + "\nMonitor Type: "
        + this.monitorType;
  }

}
