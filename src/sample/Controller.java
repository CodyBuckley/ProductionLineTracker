package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

  // Fields created for GUI. The only current fields used is the Combo Box
  @FXML private Label lblProductName;
  @FXML private Label lblManufacturer;
  @FXML private Label lblItemType;
  @FXML private Label lblChooseProduct;
  @FXML private Label lblChooseQuality;

  @FXML private TextField txtManufacturer;
  @FXML private TextField txtProductName;

  @FXML private Button btnAddProduct;
  @FXML private Button btnRecordProduction;

  @FXML private ChoiceBox<String> cbItemType;

  @FXML private ComboBox<String> cboChooseQuantity;

  /**
   * Initialize method that implements cboChooseQuantity and populates the values 1 through 10
   * inside the Combo Box.
   */
  @FXML
  private void initialize() {

    // Combo Box cboChooseQuantity is filled with elements,
    // Allow users to add elements, and set to show default elements
    cboChooseQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    cboChooseQuantity.setEditable(true);
    cboChooseQuantity.getSelectionModel().selectFirst();
  }

  /**
   * An action event that handles the button called btnAddProduct. Pressing the button will take in
   * the text fields txtManufacturer and txtProductName and the option selected in the choice box
   * cbItemType. It is currently hardcoded to store an example in SQL.
   *
   * @param event handles when the button is pressed
   */
  public void addProduct(ActionEvent event) {

    // Driver and URL pointing to Database
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = "";

    Connection conn = null;
    Statement stmt = null;

    try {
      // Register Java Database driver
      Class.forName(JDBC_DRIVER);

      // Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // Execute a query
      stmt = conn.createStatement();

      // Example sql to insert fields into Product Table on the Database
      String sql =
          "INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' )";
      stmt.executeUpdate(sql);

      // Clean-up environment
      stmt.close();
      conn.close();

      // Exception to catch an error to find the class path
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

      // Exception to provide information on a database access error
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * An action event that handles the button called btnRecordProduction. Currently, it is set to be
   * a test for the button
   *
   * @param event handles when the button is pressed
   */
  public void recordProduction(ActionEvent event) {
    System.out.println("test");
  }
}
