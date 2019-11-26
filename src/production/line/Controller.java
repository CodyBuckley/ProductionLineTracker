package production.line;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
  @FXML private TextField txtEmployeeName;
  @FXML private TextField txtManufacturer;
  @FXML private TextField txtProductName;
  @FXML private ChoiceBox<ItemType> cbItemType;
  @FXML private ComboBox<String> cboChooseQuantity;
  @FXML private TextArea taEmployeeInfo;
  @FXML private TextArea taProductionLog;
  @FXML private TableView<Product> tableView;
  @FXML private TableColumn<String, Product> productNameCol;
  @FXML private TableColumn<String, Product> manufacturerNameCol;
  @FXML private TableColumn<String, Product> itemTypeCol;
  @FXML private ListView<Product> lvRecordProduction;

  private ObservableList<Product> productLine;
  private ArrayList<Product> productArrayList = new ArrayList<>();
  private ArrayList<ProductionRecord> productionRun = new ArrayList<>();
  private ArrayList<ProductionRecord> productionLog = new ArrayList<>();

  private Connection conn = null;
  private int itemCount;

  /**
   * Initialize method that runs at startup. It creates a connection variable to connect to the
   * Database. It runs important methods at startup like setupProductLineTable, loadProductList, and
   * loadProductLog for functionality. It implements cboChooseQuantity to populate the values 1
   * through 10 inside the Combo Box and cbItemTypes to populate the values from Enum ItemType.java
   * inside the Choice Box
   */
  @FXML
  private void initialize() {

    // Create a universal Connection variable to connect to the DB.
    conn = connectDB();

    // Initializes the program by loading the views on the GUI from the Database.
    setupProductLineTable();
    loadProductList();
    loadProductLog();

    // Combo Box cboChooseQuantity is filled with elements,
    // Allow users to add elements, and set to show default elements.
    cboChooseQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    cboChooseQuantity.setEditable(true);
    cboChooseQuantity.getSelectionModel().selectFirst();

    // ChoiceBox cbItemType is filled with values from Enum
    // Allow users to select 4 options: Audio, Visual, Audio-Mobile, Visual-Mobile.
    cbItemType.getItems().setAll(ItemType.values());
    cbItemType.getSelectionModel().selectFirst();
  }

  /**
   * An action event that handles the button called btnAddProduct. Pressing the button will take in
   * the text fields txtManufacturer and txtProductName and the option selected in the choice box
   * cbItemType. It will run the loadProductList method in order to load the stored values into the
   * Table View and List View on the GUI.
   */
  public void addProduct() {

    // Uses ItemType.java enum to get the value from the Choice Box.
    ItemType code = cbItemType.getValue();

    // SQL Statement that utilizes a Prepared Statement to insert values into the PRODUCT table
    // fields type, manufacturer, and product name.
    String sql = "INSERT INTO Product(type, manufacturer, name) VALUES (?,?,?)";

    try {
      // A Prepared Statement that fills in the SQL values for type, manufacturer, and name.
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, code.getCode());
      ps.setString(2, txtManufacturer.getText());
      ps.setString(3, txtProductName.getText());
      ps.executeUpdate();

      // Closing the prepared statement after use.
      ps.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Calls method loadProductList to load the submitted values into the Table View and List View.
    loadProductList();
  }

  /**
   * An action event that handles the button called btnRecordProduction. It is used to loop, adding
   * the ProductionRecord constructor using the List View called lvRecordProduction and itemCount,
   * which is incremented for the number of objects, into the arrayList productionRun. The button
   * will also run the method addToProductionDB using the productionArray as a parameter. It will
   * also call loadProductLig and showProduction.
   */
  public void recordProduction() {

    // For Loop that loops for as long as i is less than the chosen value from the combo box.
    // Continuously add the saved ProductionRecord object and increment the itemCount for the Text
    // Area.
    for (int i = 0; i < Integer.parseInt(cboChooseQuantity.getValue()); i++) {
      productionRun.add(
          new ProductionRecord(
              lvRecordProduction.getSelectionModel().getSelectedItem(), itemCount));

      itemCount++;
    }

    // Calling methods to add the Array List values to the PRODUCTIONRECORD table and load the
    // values for the List View and Text Area on the GUI.
    addToProductionDB(productionRun);
    loadProductLog();
    showProduction();
  }

  /**
   * An action handler that handles the button press of the button btnEmployeeInfo. It takes the
   * full name provided by the textfield txtEmployeeName and creates the object employee and appends
   * the toString text to the Text Area taEmployeeInfo.
   */
  public void addEmployee() {

    // Clear the Text Area to erase any previous employee info inserted.
    taEmployeeInfo.clear();

    // A test password required for the current Employee object. Employee takes the full name
    // provided by the text field and the test password.
    String testPassword = "Password!";
    Employee employee = new Employee(txtEmployeeName.getText(), testPassword);

    // Append the toString from Employee.java to add the employee info.
    taEmployeeInfo.appendText(employee.toString());
  }

  /**
   * A method to run in the initialize to start the connection to the Database.
   *
   * @return null because the method is only trying to start the database
   */
  private static Connection connectDB() {

    // Driver and URL pointing to Database
    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/production";

    //  Database credentials
    final String User = "";
    String pass = "";

    Connection conn;
    Statement stmt;

    try {
      Properties prop = new Properties();
      prop.load(new FileInputStream("res/properties"));
      pass = prop.getProperty("password");
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      // Register Java Database driver
      Class.forName(Jdbc_Driver);

      // Open a connection
      conn = DriverManager.getConnection(Db_Url, User, pass);

      // Execute a query
      stmt = conn.createStatement();

      // Closing the statement after use
      stmt.close();

      return conn;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * A method used to set up the Observable List and Table View for GUI. It sets the Array List
   * productArrayList into the Observable List productLine. The method set ups the three columns for
   * product name, manufacturer, and item type. It also sets up the table view on the first tab on
   * the GUI.
   */
  private void setupProductLineTable() {

    // Insert the Array List into the Observable List for the table view on the GUI.
    productLine = FXCollections.observableArrayList(productArrayList);

    // Initialize the columns on the table view with name, manufacturer, and type.
    productNameCol.setCellValueFactory(new PropertyValueFactory("name"));
    manufacturerNameCol.setCellValueFactory(new PropertyValueFactory("manufacturer"));
    itemTypeCol.setCellValueFactory(new PropertyValueFactory("type"));

    // Set the items stored in the Observable List into table view.
    tableView.setItems(productLine);


  }

  /**
   * A method that pulls the values PRODUCT Table from the Database and inserts those values into
   * the Widget object. It also clears the Array List productArrayList because the method is called
   * when Add Product button is pressed in order to prevent duplications. There is a for loop that
   * will changes the String type stored in the database into an ItemType for the Widget object. The
   * method will add the stored value to the Table View and List View on the first and second tabs
   * of the GUI.
   */
  private void loadProductList() {

    // Clears the Array List to prevent duplicates when you load values from the database.
    productArrayList.clear();

    try {

      // Set up the Prepared Statement to query the PRODUCT table.
      String sql = "SELECT * FROM Product";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      // While loop set to query through the values to insert the values in each column of the table
      // into variables used to store into Widget object.
      while (rs.next()) {
        Integer id = rs.getInt("ID");
        String name = rs.getString("NAME");
        String code = rs.getString("TYPE");
        String manufacturer = rs.getString("MANUFACTURER");

        // Nexted for loop to loop through four item types to find the correct item type. If it is
        // correct, the values from Widget are stored into the Array List.
        for (ItemType type : ItemType.values()) {
          if (type.getCode().equals(code)) {
            productArrayList.add(new Widget(name, manufacturer, type));
          }
        }
      }

      // Closing the Prepared Statement and the Result Set after use.
      ps.close();
      rs.close();

      // Clear the items stored in the table view to prevent duplicates and add the new values from
      // the array list.
      tableView.getItems().clear();
      tableView.getItems().addAll(productArrayList);

      // Clear the items stored in the list view to prevent duplicates and add the new values from
      // the list view.
      lvRecordProduction.getItems().clear();
      lvRecordProduction.getItems().addAll(productArrayList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * A method called loadProductLog that stores values from the PRODUCTIONRECORD Table from the
   * Database into the Array List productionLog through the overloaded constructor of
   * ProductionRecord. It calls the showProduction in order to load the values into the Text Area on
   * the 3rd tab of the GUI.
   */
  private void loadProductLog() {

    try {

      // Set up the Prepared Statement to query the PRODUCTIONRECORD table.
      String sql = "SELECT * FROM PRODUCTIONRECORD";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      // While loop set to query through the values to insert the values in each column of the table
      // into variables used to store into Production Record constructor.
      while (rs.next()) {
        Integer prodNum = rs.getInt("PRODUCTION_NUM");
        Integer prodID = rs.getInt("PRODUCT_ID");
        String serialNum = rs.getString("SERIAL_NUM");
        Timestamp prodDate = rs.getTimestamp("DATE_PRODUCED");

        productionLog.add(new ProductionRecord(prodNum, prodID, serialNum, prodDate));
      }

      // Calling a method to store the values into the Text Area on the GUI.
      showProduction();

      // Closing the Prepared Statement and the Result Set after use.
      ps.close();
      rs.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * A method used to insert into the PRODUCTION RECORD Database table by using a for loop to pull
   * values from the array list and insert into the ProductionRecord object called pr.
   *
   * @param productionRun an array list used to store values and used to input into DB
   */
  private void addToProductionDB(ArrayList<ProductionRecord> productionRun) {

    try {

      // Set up Prepared Statement to update the PRODUCTIONRECORD table with values pulled from the
      // productionRun Array List.
      String sql =
          "INSERT INTO PRODUCTIONRECORD(PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
              + " VALUES (?,?,?,?)";
      PreparedStatement ps = conn.prepareStatement(sql);

      // For loop to loop and set the ID from the PRODUCTIONRECORD table to get the name from the
      // PRODUCT table and set the values from the ProductionRecord object into the PRODUCTIONRECORD
      // table.
      for (ProductionRecord pr : productionRun) {
        pr.setProductID(getProductId(pr.getProductName()));
        ps.setInt(1, pr.getProductionNumber());
        ps.setInt(2, pr.getProductID());
        ps.setString(3, pr.getSerialNumber());
        ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
        ps.executeUpdate();
      }

      // Closing the Prepared Statement after use.
      ps.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * A method called getProductId that uses the One-to-One relationship between the two tables
   * PRODUCT and PRODUCTIONRECORD to match the Name from the PRODUCT table and the ID from the
   * PRODUCTIONRECORD.
   *
   * @param name a String parameter that is used to identify the ID from PRODUCT.
   * @return int id, which contains the PRODUCT_ID from the PRODUCTIONRECORD table.
   */
  private int getProductId(String name) {
    int id = 0;

    try {

      // Set up Prepared Statement to query through PRODUCT table to find the ID of a product name.
      String sql = "SELECT ID FROM PRODUCT WHERE NAME = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, name);
      ResultSet rs = ps.executeQuery();

      // If statement to store the ID from PRODUCT into the int id.
      if (rs.next()) {
        id = rs.getInt("ID");
      }

      // Closing the Prepared Statement and Result Set after use.
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Return ID for the One-to-One relationship between the two tables.
    return id;
  }

  /**
   * The method showProduction uses the getProductID method to get the Name of the product from the
   * PRODUCT Table from the Database to show in the Text Area taProductionLog.
   */
  private void showProduction() {

    // For Loop to loop through all stored values of the productionLog Array List.
    for (ProductionRecord pr : productionLog) {
      try {

        // Set up a Prepared Statement to query through PRODUCT table to find the product name from
        // the ID.
        String sql = "SELECT NAME FROM PRODUCT WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, pr.getProductID());
        ResultSet rs = ps.executeQuery();

        // If statement to set the product name from the query into the product name of the
        // ProductionRecord pr.
        if (rs.next()) {
          pr.setProductName(rs.getString("NAME"));
        }

        // If statement to prevent duplication on the appendText to the Text Area.
        if (!taProductionLog.getText().contains(pr.toString())) {
          taProductionLog.appendText(pr.toString());
        }

        // Closing the Prepared Statement after use.
        ps.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
