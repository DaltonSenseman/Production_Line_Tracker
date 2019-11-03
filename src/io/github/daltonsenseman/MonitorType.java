package io.github.daltonsenseman;

/**
 * This enum class is to specify what Monitor Types the program may use. (LCD or LED)
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
enum MonitorType {
  LCD("LCD"),
  LED("LED");

  private final String type;

  /**
   * sets the type of the enum to the field when the Enum is used.
   */
  MonitorType(String type) {
    this.type = type;
  }

  /**
   * Method gets the current value of the field type.
   *
   * @return a String that holds the selected enums value.
   */
  public String type() {
    return type;
  }
}
