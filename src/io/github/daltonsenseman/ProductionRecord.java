package io.github.daltonsenseman;

import java.util.Date;

/**
 * This class holds the information of Products that have been produced by the program, the program
 * uses this class as a format for data from the database so they are correctly placed into the
 * right fields. And displayed correctly when used to populate the record log tab..
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;

  /**
   * Constructor that take in a product ID for new product made in the GUI and establishes them into
   * the record starting at 0 as they are the first product for that type.
   *
   * @param productID int of the ID of the product being made. Unique for each item.
   */
  public ProductionRecord(int productID) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  /**
   * Constructor to populate the fields of the Production Object so they may be used.
   *
   * @param productionNumber int of the number/run the production is currently on.
   * @param productID        int of the unique ID of the type of product.
   * @param serialNumber     String of the unique ID each unique created product has.
   * @param dateProduced     Date the date that the product was made on.
   */
  public ProductionRecord(int productionNumber, int productID, String serialNumber,
      Date dateProduced) {
    this.productID = productID;
    this.productionNumber = productionNumber;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Constructor for the database pull so new products create of a product type continue off the
   * last product made so Serial Nums are sequential.
   *
   * @param producedProduct Product the info of the Product being made.
   * @param amountCreated   Int of the amount Created so far.
   */
  public ProductionRecord(Product producedProduct, int amountCreated, String idNumber) {
    this.productID = Integer.parseInt(idNumber);
    this.dateProduced = new Date();
    this.serialNumber = producedProduct.getManufacturer().substring(0, 3).toUpperCase()
        + producedProduct.getTypeID() + (amountCreated + 1);
  }

  /**
   * This method overrides the toString method from the Object class and allows the user to grab the
   * current values of the fields of Record as a String.
   *
   * @return A 1 line string holding the stub of the record of this product in this format.
   * productNumber, ProductID, SerialNumber, dataProduced.
   */
  @Override
  public String toString() {
    return "Prod. Num: " + this.productionNumber + " Product ID: " + this.productID
        + " Serial Num: " + this.serialNumber + " Date: " + this.dateProduced;
  }

  /**
   * Gets the current value of productionNumber.
   *
   * @return int of the production number the product was made in.
   */
  public int getProductionNum() {
    return this.productionNumber;
  }

  /**
   * sets the productionNumber field.
   *
   * @param productionNumber int the production run this product was made in.
   */
  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * sets the ProductID field.
   *
   * @param productID int of the unique productID .
   */
  public void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * gets the current value of ProductID.
   *
   * @return int the unique ID of the product.
   */
  public int getProductID() {
    return productID;
  }

  /**
   * sets the serialNumber field.
   *
   * @param serialNumber String of the Products SN format(ID(manufacturer first 3 letters)ENUM type,
   *                     00000{incrementing unique number})
   */
  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * gets the current value of serialNum.
   *
   * @return string of the product unique SN. format(ID(manufacturer first 3 letters)ENUM type,
   * 00000{incrementing unique number})
   */
  public String getSerialNum() {
    return serialNumber;
  }

  /**
   * gets the data the product was produced on mm/dd/yyyy.
   *
   * @return Date object in mm/dd/yyyy format.
   */
  public Date getProdDate() {
    return dateProduced;
  }

  /**
   * sets the date the product was created on.
   *
   * @param dateProduced Date object of the current local time fo the computer mm/dd/yyyy
   */
  public void setProdDate(Date dateProduced) {
    this.dateProduced = dateProduced;
  }
}