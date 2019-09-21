package io.github.daltonsenseman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Program is to create and show a production system that records and produces items.
 *
 * @author Dalton Senseman
 * @version a1.0
 * @since 2019-21-09
 */
public class Main extends Application {

  /**
   * GUI start method launched from mai nto create the stage for the .fxml to create the GUI.
   *
   * @param primaryStage sets the Stage for the GUI to be placed inside of
   * @throws Exception generic exception if program fails to launch
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Production Tracker");
    primaryStage.setScene(new Scene(root, 720, 480));
    primaryStage.show();

  }

  /**
   * Main method to drive the program and call for start method to run.
   *
   * @param args main driver naming convention
   */
  public static void main(String[] args) {
    launch(args);
  }
}
