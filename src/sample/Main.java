package sample;

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

    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(new Scene(root, 500, 375));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
