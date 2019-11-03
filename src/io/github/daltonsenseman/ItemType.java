package io.github.daltonsenseman;

/**
 * This is the ItemType Enum and holds the accepted values of the item types to use for the program
 *
 * <p>Audio - the device only utilizes audio functionality.
 * Visual - the device has a visual element such as a T.V, monitor, etc... Audio Mobile - the device
 * only utilizes audio functionality and is portable. Audio Mobile - the device has a visual element
 * such as a T.V, monitor, etc... and is portable.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  private final String code;

  /**
   * The constructor sets the code that the item to the corresponding Enum.
   *
   * @param code takes in a String that holds the code AU,VI,AM, or VM.
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * This method returns the value of the code of the Enum that is used.
   *
   * @return a string corresponding to the enum it is referencing.
   */
  public String code() {
    return code;
  }
}