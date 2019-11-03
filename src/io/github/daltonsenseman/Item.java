package io.github.daltonsenseman;

/**
 * This interface sets the contract for implementing item requiring classes to contain the fields
 * and furnish the methods outlined below.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
interface Item {

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();
}
