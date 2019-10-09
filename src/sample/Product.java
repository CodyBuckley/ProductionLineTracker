package sample;

public abstract class Product implements Item {

  // Fields for the abstract class Product
  private int id;
  private String type;
  private String manufacturer;
  private String name;

  public void setName(String itemName) {
    this.name = itemName;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String manufacturerName) {
    this.manufacturer = manufacturerName;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public int getId() {
    return id;
  }

  public Product(String itemName, String manufacturerName, String type) {
    this.type = type;
    this.name = itemName;
    this.manufacturer = manufacturerName;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }
}

class Widget extends Product {
  public Widget(String itemName, String manufacturerName, String type) {
    super(itemName, manufacturerName, type);
  }
}
