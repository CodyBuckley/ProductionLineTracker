package production.line;

import java.util.Date;

/**
 * Create a ProductionRecord class with int fields for productionNumber (this will be unique for
 * every item produced and get auto incremented by the database), an int field for productID (to
 * correspond to the productID from the Product table / class), String field for serialNumber, and a
 * field for the dateProduced that is type Date (from java.util).
 */
public class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String productName;
  private String serialNumber;
  private Date dateProduced;

  // Get and Set Methods for fields
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductionNumber() {
    return productionNumber;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public int getProductID() {
    return productID;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setDateProduced(Date dateProduced) {
    this.dateProduced = new Date(dateProduced.getTime());
  }

  public Date getDateProduced() {
    return new Date(dateProduced.getTime());
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductName() {
    return productName;
  }

  /**
   * Make one constructor that just has a parameter for the productID. This will be the constructor
   * called when the user records production from the user interface.
   *
   * @param productID the ID generated for each product produced
   */
  public ProductionRecord(int productID) {
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();
  }

  /**
   * Create an overloaded constructor to use when creating ProductionRecord objects from the
   * database. This constructor needs parameters for all fields.
   *
   * @param productionNumber number of product produced
   * @param productID the ID generated for each product produced
   * @param serialNumber generated based on product name, type, and item count
   * @param dateProduced set to be current date for code
   */
  public ProductionRecord(
      int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * New overload ProductionRecord constructor to accept a Product and an int which holds the count
   * of the number of items of its type that have been created.
   *
   * @param product an object to grab the product name, manufacturer, and type found from
   *     Product.java
   * @param itemCount the number of produced used for serial number
   */
  public ProductionRecord(Product product, int itemCount) {
    dateProduced = new Date();
    productName = product.getName();

    serialNumber =
        product.getManufacturer().substring(0, 3)
            + product.getType().getCode()
            + String.format("%05d", itemCount);
  }

  /**
   * toString method used to print the production number, product ID, serial number, and date for
   * the Production Log tab in the User Interface.
   *
   * @return the print statement for the production log tab
   */
  public String toString() {
    return "Prod. Num: "
        + productionNumber
        + " Product Name: "
        + productName
        + " Serial Num: "
        + serialNumber
        + " Date: "
        + dateProduced
        + "\n";
  }
}
