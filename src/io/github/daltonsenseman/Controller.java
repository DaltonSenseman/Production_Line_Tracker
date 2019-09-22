package io.github.daltonsenseman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller to dictate the flow of events and actions of the GUI program are to be handled here.
 *
 * @author Dalton Senseman
 * @version a1.0
 * @since 2019-21-09
 */
public class Controller {

  /**
   * allows to populate the boxes with string information for choice dictation ref used:
   * https://www.youtube.com/watch?v=eT3kuQUy8c8
   */
  private ObservableList<String> numOfProducts = FXCollections
      .observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
  private ObservableList<String> itemTypes = FXCollections
      .observableArrayList("AU", "VI", "AM", "VM");

  @FXML
  private ComboBox productQCombo;
  @FXML
  private ChoiceBox itemTypeCBox;
  @FXML
  private TextField productNameTBox;
  @FXML
  private TextField manufacturerTBox;

  /**
   * Adds a product to the H2 database taking the information provided and pushign it via a SQl
   * command.
   *
   * @param event takes in the event action and executes the method
   */
  @FXML
  public void addProduct(ActionEvent event) {
    //prints data to console user has entered until functionality of pushing to database is made.
    System.out.println(productNameTBox.getText());
    System.out.println(manufacturerTBox.getText());
    System.out.println(itemTypeCBox.getSelectionModel().getSelectedItem());
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/data";
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      Class.forName(JDBC_DRIVER);
      System.out.println("Connecting to database....");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      String sql = "INSERT INTO PRODUCT(TYPE, MANUFACTURER, NAME) "
          + "VALUES ('AUDIO', 'Apple', 'iPod')";
      stmt.executeUpdate(sql);

      // closes database.
      stmt.close();
      conn.close();

    } catch (Exception ex) {
      System.out.println("Failed to push to database.");
    }
  }

  /**
   * Will record production, when the ability is added to functionality.
   *
   * @param event on event of type any on the button the following is printed to console.
   */
  @FXML
  public void recordProduction(ActionEvent event) {
    System.out.println("Recorded");
  }

  /**
   * initializes the item lists for the choice boxes.
   */
  @FXML
  public void initialize() {
    productQCombo.setItems(numOfProducts);
    itemTypeCBox.setItems(itemTypes);
  }
}

