package production.line;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

  @FXML private ChoiceBox<ItemType> cbItemType;

  @FXML private ComboBox<String> cboChooseQuantity;

  @FXML private TextArea taProductionLog;

  /**
   * Initialize method that implements cboChooseQuantity to populate the values 1 through 10 inside
   * the Combo Box and cbItemTypes to populate the values from Enum ItemType.java inside the Choice
   * Box
   */
  @FXML
  private void initialize() {
    testMultimedia();

    // Combo Box cboChooseQuantity is filled with elements,
    // Allow users to add elements, and set to show default elements
    cboChooseQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    cboChooseQuantity.setEditable(true);
    cboChooseQuantity.getSelectionModel().selectFirst();

    // ChoiceBox cbItemType is filled with values from Enum
    // Allow users to select 4 options: Audio, Visual, Audio-Mobile, Visual-Mobile
    cbItemType.getItems().setAll(ItemType.values());

    // Text Area taProductionLog is filled with a test constructor
    // Displays Production Number, Product ID, Serial Number, and the Date logged
    ProductionRecord pr = new ProductionRecord(0, 3, "1", new Date());
    taProductionLog.setText(pr.toString());
  }

  /**
   * An action event that handles the button called btnAddProduct. Pressing the button will take in
   * the text fields txtManufacturer and txtProductName and the option selected in the choice box
   * cbItemType. It is currently hardcoded to store an example in SQL.
   *
   * @param event handles when the button is pressed
   */
  public void addProduct(ActionEvent event) throws SQLException {

    Statement stmt = Main.connectDB();
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
    // System.out.println(sql);
    stmt.executeUpdate(sql);
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

  public void testMultimedia() {
    AudioPlayer newAudioProduct =
        new AudioPlayer(
            "DP-X1A", "Onkyo", "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct =
        new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen, MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }
}
