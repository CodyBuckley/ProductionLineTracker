package production.line;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

  // Fields and FX:ids from GUI used for methods inside the controller

  @FXML private TextField txtManufacturer;
  @FXML private TextField txtProductName;
  @FXML private ChoiceBox<ItemType> cbItemType;
  @FXML private ComboBox<String> cboChooseQuantity;
  @FXML private TextArea taProductionLog;
  @FXML private TableView<Product> tableView;
  @FXML private TableColumn<String, Product> productNameCol;
  @FXML private TableColumn<String, Product> manufacturerNameCol;
  @FXML private TableColumn<String, Product> itemTypeCol;
  @FXML private ListView<Product> lvRecordProduction;

  private ObservableList<Product> productLine;
  private ArrayList<Product> productArrayList = new ArrayList<>();

  /**
   * Initialize method that implements cboChooseQuantity to populate the values 1 through 10 inside
   * the Combo Box and cbItemTypes to populate the values from Enum ItemType.java inside the Choice
   * Box
   */
  @FXML
  private void initialize() {
    // testMultimedia();
    connectDB();
    setupProductLineTable();
    // loadProductList();
    // loadProductLog();

    // Combo Box cboChooseQuantity is filled with elements,
    // Allow users to add elements, and set to show default elements
    cboChooseQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    cboChooseQuantity.setEditable(true);
    cboChooseQuantity.getSelectionModel().selectFirst();

    // ChoiceBox cbItemType is filled with values from Enum
    // Allow users to select 4 options: Audio, Visual, Audio-Mobile, Visual-Mobile
    cbItemType.getItems().setAll(ItemType.values());
    cbItemType.getSelectionModel().selectFirst();
  }

  /**
   * A method to run in the iniitialize to start the connection to the Datatbase.
   *
   * @return null because the method is only trying to start the database
   */
  public static Statement connectDB() {

    // Driver and URL pointing to Database
    // Google CheckStyle suggests that JDBC_DRIVER, DB_URL, USER, and PASS
    // Should not have 2 consecutive capital letters
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = "";

    Connection conn;
    Statement stmt = null;

    try {
      // Register Java Database driver
      Class.forName(JDBC_DRIVER);

      // Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // Execute a query
      stmt = conn.createStatement();

      return stmt;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  // A method used to set up the Observable List and Table View for GUI
  private void setupProductLineTable() {

    productLine = FXCollections.observableArrayList(productArrayList);
    productNameCol.setCellValueFactory(new PropertyValueFactory("name"));
    manufacturerNameCol.setCellValueFactory(new PropertyValueFactory("manufacturer"));
    itemTypeCol.setCellValueFactory(new PropertyValueFactory("type"));
    tableView.setItems(productLine);
  }

  //  private void loadProductList()  {
  //    try {
  //      String sql = "SELECT * FROM Product";
  //
  //      Statement stmt = connectDB();
  //      ResultSet rs = stmt.executeQuery(sql);
  //      while (rs.next()) {
  //        Integer id = rs.getInt("ID");
  //        String name = rs.getString("NAME");
  //        String type = rs.getString("TYPE");
  //        String manufacturer = rs.getString("MANUFACTURER");
  //
  //
  //
  //        productArrayList.add(new Widget(name, manufacturer, type));
  //      }
  //    } catch (SQLException e) {
  //      e.printStackTrace();
  //    }
  //  }

  //  private void loadProductLog() {}

  /**
   * An action event that handles the button called btnAddProduct. Pressing the button will take in
   * the text fields txtManufacturer and txtProductName and the option selected in the choice box
   * cbItemType. It also is set to take the two text fields and one choice box and inset it into the
   * ] Table View.
   *
   * @param event handles when the button is pressed
   */
  public void addProduct(ActionEvent event) throws SQLException {

    Statement stmt = Controller.connectDB();
    ItemType code = cbItemType.getValue();

    // Example sql to insert fields into Product Table on the Database
    String sql =
        "INSERT INTO Product(type, manufacturer, name) VALUES ('"
            + code.getCode()
            + "', '"
            + txtManufacturer.getText()
            + "', '"
            + txtProductName.getText()
            + "');";

    assert stmt != null;
    stmt.executeUpdate(sql);

    // Pressing Button inserts the two text fields and choice box into an array list which will be
    // stored into the table view and list view
    productArrayList.add(
        new Widget(txtProductName.getText(), txtManufacturer.getText(), cbItemType.getValue()));

    // Clear the Table view to prevent stacking of old items on subsequent button presses
    tableView.getItems().clear();
    tableView.getItems().addAll(productArrayList);
    lvRecordProduction.getItems().addAll(productArrayList);
  }

  /**
   * An action event that handles the button called btnRecordProduction. It is set up to record the
   * production by selecting a stored product and generating a record for the production.
   *
   * @param event handles when the button is pressed
   */
  public void recordProduction(ActionEvent event) {
    ProductionRecord pr =
        new ProductionRecord(
            lvRecordProduction.getSelectionModel().getSelectedItem(),
            Integer.parseInt(cboChooseQuantity.getValue()));
    taProductionLog.appendText(pr.toString());
  }

  // Testing functionality of media types for the program
  //  public void testMultimedia() {
  //    AudioPlayer newAudioProduct =
  //        new AudioPlayer(
  //            "DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
  //    Screen newScreen = new Screen("720x480", 40, 22);
  //    MoviePlayer newMovieProduct =
  //        new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen, MonitorType.LCD);
  //    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
  //    productList.add(newAudioProduct);
  //    productList.add(newMovieProduct);
  //    for (MultimediaControl p : productList) {
  //      System.out.println(p);
  //      p.play();
  //      p.stop();
  //      p.next();
  //      p.previous();
  //    }
}
