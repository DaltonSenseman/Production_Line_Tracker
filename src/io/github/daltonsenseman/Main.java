package io.github.daltonsenseman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the program calls to the start method to create a stage for the GUI to be created
 * inside of. Extends Application for JavaFX environments.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
public class Main extends Application {

  /**
   * Start method for the JavaFX environment for the GUI to be created inside of, the default
   * resolution is 480p for the stage. The method throws a general exception.
   *
   * @param primaryStage takes a Stage object for the GUI to be created in.
   * @throws Exception general exception for failure to create a new stage.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("mainGUI.fxml"));
    primaryStage.setTitle("Production Line Tracker");
    primaryStage.setScene(new Scene(root, 720, 480));
    primaryStage.show();
  }

  /**
   * Main method of the program.
   *
   * @param args String array and general convention for the main driver.
   */
  public static void main(String[] args) {
    launch(args);
  }

}