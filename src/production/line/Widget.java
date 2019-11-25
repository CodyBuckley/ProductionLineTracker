package production.line;

/** Test Object to use in order to store into Database using abstract class Product. */
public class Widget extends Product {
  public Widget(String itemName, String manufacturerName, ItemType type) {
    super(itemName, manufacturerName, type);
  }
}
