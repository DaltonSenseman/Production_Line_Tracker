package io.github.daltonsenseman;

/**
 * This class implements Item for the contractual agreement that all items have ID, Name, and a
 * manufacturer assigned to them. The class is abstract so more specific items must be made from
 * this class in order to create Objects.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
public abstract class Product implements Item {

  private int id;
  private String type;
  private String manufacturer;
  private String name;

  /**
   * Constructor for general products only setting the values from Item Interface.
   *
   * @param name a String of the name of the product/item.
   * @param manufacturer a String of the manufacturer that creates the item.
   * @param type the type of item for the enum ItemType.
   */
  Product(String name, String manufacturer, String type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * This method overrides the toString method from the Object class and allows the user to grab the
   *     current values of the fields of Product as a String.
   *
   * @return A 3 line String the first showing the Name and the second line Manufacturer and the
   *     last line holding the ENUM type.
   */
  @Override
  public String toString() {
    return "Name: " + this.name + "\n"
        + "Manufacturer: " + this.manufacturer + "\n"
        + "Type: " + this.type;
  }

  /**
   * Get the current value of the ID.
   *
   * @return a int value of the field id.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the Name field of the product to the value supplied in the param.
   *
   * @param name a String holding the name of the item to be set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the current value of the name of the item.
   *
   * @return a String of the field name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the manufacturer field of the product to the value supplied in the param.
   *
   * @param manufacturer a String holding the Manufacturer who makes the item.
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Gets the current value of the manufacturer of the item.
   *
   * @return a Sting of the field manufacturer.
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Sets the ENUM type of the item.
   *
   * @param type the ENUM value that is to be set to the field type of the item.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets the current value of the ENUM type of the item.
   *
   * @return a String of the ENUM type this item is.
   */
  public String getType() {
    return type;
  }
}

/**
 * A temp Widget that extends Product to allow the program to create Objects of product.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
class Widget extends Product {

  /**
   * A constructor that takes in the name, manufacturer and type of the item to be created and calls
   * upon the super constructor of Product to set the fields of the item.
   *
   * @param name a String of the items name.
   * @param manufacturer a String of the manufacturer that makes the item.
   * @param type a String of the ENUM that the item is.
   */
  public Widget(String name, String manufacturer, String type) {
    super(name, manufacturer, type);
  }
}