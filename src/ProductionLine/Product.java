package ProductionLine;

/**
 * Create an abstract type called Product that will implement the Item interface. Product will
 * implement the basic functionality that all items on a production line should have. Add the
 * following fields to Product: an id, item type, item name, and manufacturer name.
 */
public abstract class Product implements Item {

  // Fields for the abstract class Product
  private int id;
  private String type;
  private String manufacturer;
  private String name;

  // Add a constructor that will take in the name, manufacturer, and type of the product and set
  // them to the field variables.
  public Product(String itemName, String manufacturerName, String type) {
    this.type = type;
    this.name = itemName;
    this.manufacturer = manufacturerName;
  }

  // Set Method that initializes name to be itemName
  public void setName(String itemName) {
    this.name = itemName;
  }

  // Get Method that returns String name
  public String getName() {
    return name;
  }

  // Set Method that initializes manufacturer to be manufacturerName
  public void setManufacturer(String manufacturerName) {
    this.manufacturer = manufacturerName;
  }

  // Get Method that returns String manufacturer
  public String getManufacturer() {
    return manufacturer;
  }

  // Get Method that returns int id
  public int getId() {
    return id;
  }

  // Overridden toString Method that returns the name, manufacturer, and item type
  @Override
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }
}

// To test the Product class, temporarily create a Widget class that extends Product.
class Widget extends Product {
  public Widget(String itemName, String manufacturerName, String type) {
    super(itemName, manufacturerName, type);
  }
}
