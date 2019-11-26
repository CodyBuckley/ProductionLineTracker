package production.line;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Production Line Tracker with a GUI and database. Software for a media player production facility
 * that will keep track of what products are produced.
 *
 * @author Cody Buckley
 */
public class Main extends Application {

  /**
   * The starting point of a JavaFX program.
   *
   * @param primaryStage the first thing the user sees
   * @throws Exception general exception handler
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("ProductionTabs.fxml"));
    Scene scene = new Scene(root, 500, 375);
    primaryStage.setTitle("ObjectOriented-ProductionLine");
    primaryStage.setScene(scene);
    scene.getStylesheets().add("ProductionLineTracker.css");

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * To ensure that sensitive information is not leaked it is important that the information saved
   * to file is encoded. To meet these regulations you need to add a method that will reverse the
   * order of the text stored for the database password. This should be done recursively using a
   * method named reverseString().
   *
   * @param pw the password stored for the Database log in.
   * @return if the password is empty, it will return password. if not, it will reverse the string
   *     by the character.
   */
  public String reverseString(String pw) {
    if (pw.isEmpty()) {
      return pw;
    }

    // Calling Function Recursively
    return reverseString(pw.substring(1)) + pw.charAt(0);
  }
}
