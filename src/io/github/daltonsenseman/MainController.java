package io.github.daltonsenseman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * This class is the main controller for the GUI program and handles action events from the FXML
 * file, the class uses database connection (database type: H2) in order to preform many of the
 * methods/action events.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("WeakerAccess")
public class MainController {

  //<editor-fold desc="Fields">
  private final ObservableList<String> numOfProducts = FXCollections
      .observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

  final ObservableList<Product> productLine = FXCollections.observableArrayList();

  final ObservableList<ProductionRecord> productRecord = FXCollections.observableArrayList();

  @FXML
  private TextField prodNameTxtField;

  @FXML
  private ListView<Product> prodListView;

  @FXML
  private TableView<Product> existingProdTable;

  @FXML
  private TableColumn<String, Product> existingProdCol1;

  @FXML
  private TableColumn<String, Product> existingProdCol2;

  @FXML
  private TableColumn<String, Product> existingProdCol3;

  @FXML
  private ComboBox<String> chooseQntyCbBox;

  @FXML
  private Button recordProdBtn;

  @FXML
  private Button prodAddBtn;

  @FXML
  private TextArea logTxtArea;

  @FXML
  private TextField manuTxtField;

  @FXML
  private ChoiceBox<ItemType> itypecbbox;
  //</editor-fold>

  @FXML
  void recordProdMouseClick(MouseEvent event) {

    System.out.println("Recorded!");
  }

  /**
   * This method is a button bound method and executes every time a mouse click event happens on the
   * button, it connects to the H2 data base and pushes a new product to the database table named
   * PRODUCT. It throws FileNotFound, ClassNotFound , and SQL Exceptions.
   *
   * @param event the associated mouse event that what done to the bound FXML id.
   */
  @FXML
  void addProdMouseClick(MouseEvent event) {
    Properties prop = new Properties();
    // Uses the data properties file to grab the user and password to the H2 database.
    try (InputStream input = new FileInputStream("./res/data.properties")) {
      prop.load(input);

    } catch (FileNotFoundException e) {
      System.out.println("Properties File not found!");
    } catch (IOException ex) {
      System.out.println("IO exception occurred!");
    }

    final String jdbcDriver = "org.h2.Driver";
    final String dbUrl = "jdbc:h2:./res/data";
    String user = prop.getProperty("db.username");
    String pass = prop.getProperty("db.password");
    Connection conn = null;
    Statement stmt = null;

    // grabs the information stored in the FXML id links to variables for use in the SQL statements.
    String proName = prodNameTxtField.getText();
    String manuName = manuTxtField.getText();
    String itype = itypecbbox.getValue().toString();

    try {
      Class.forName(jdbcDriver);
      System.out.println("Connecting to database....");
      conn = DriverManager.getConnection(dbUrl, user, pass);
      stmt = conn.createStatement();

      // Takes in values supplied by user and injects them to the database
      String sql = "INSERT INTO PRODUCT(TYPE, MANUFACTURER, NAME)"
          + "VALUES('" + itype + "','" + manuName + "','" + proName + "')";
      stmt.executeUpdate(sql);

      System.out.println("New Product Added!");
      // closes database.
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("Failed to push to database. Driver not Found.");
    } catch (SQLException e) {
      System.out.println("Failed to push to database.");
    } finally {
      // closes connections in the event an exception occurred.
      connAndStmtFinallyClose(conn, stmt);
    }

    existingProdCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
    productLine.add(new Widget(proName, itype, manuName));
    existingProdTable.setItems(productLine);
    existingProdCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
    existingProdCol3.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
  }

  /**
   * This method is to make sure the database connections are closed so a SQL injection attack can
   * not occur to the database in the event one is still open after an exception happened when ever
   * a database connection is established.
   *
   * @param conn the connection statement value, if == null then connection is safe.
   * @param stmt the statement value, if == null then connection is safe.
   */
  private void connAndStmtFinallyClose(Connection conn, Statement stmt) {
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (Exception ex) {
      System.out.println("Connection failed to close");
    }
    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (Exception ex) {
      System.out.println("Connection failed to close");
    }
  }

  /**
   * This method is auto ran when the controller is called and initializes the information in the
   * GUI. It sets up the tables, and product tables so they are populated for the user.
   */
  @FXML
  public void initialize() {
    chooseQntyCbBox.setItems(numOfProducts);
    chooseQntyCbBox.setEditable(true);
    chooseQntyCbBox.getSelectionModel().selectFirst();
    itypecbbox.getItems().setAll(ItemType.values());
    itypecbbox.getSelectionModel().selectFirst();
    testMultimedia();
    populateProductionLog(productRecord);
    populateExistingProducts(productLine);
  }


  /**
   * This is a temporary method to test the functionality of the Products creating a stub to show
   * that they are working as intended.
   */
  public static void testMultimedia() {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<>();

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

  /**
   * This method creates a database connection (H2) and gathers the data from the PRODUCTS table and
   * populates the Existing products table, and Production list view for item selection when
   * creating more products.
   *
   * @param productLine a Product array list to populate and append new products into when called.
   */
  public void populateExistingProducts(ObservableList<Product> productLine) {
    Properties prop = new Properties();
    // looks into the properties file for user and password to database.
    try (InputStream input = new FileInputStream("./res/data.properties")) {
      prop.load(input);

    } catch (FileNotFoundException e) {
      System.out.println("Properties File not found!");
    } catch (IOException ex) {
      System.out.println("IO exception occurred!");
    }

    final String jdbcDriver = "org.h2.Driver";
    final String dbUrl = "jdbc:h2:./res/data";
    String user = prop.getProperty("db.username");
    String pass = prop.getProperty("db.password");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      Class.forName(jdbcDriver);
      System.out.println("Connecting to database....");
      conn = DriverManager.getConnection(dbUrl, user, pass);
      stmt = conn.createStatement();

      // Takes in values supplied by user and injects them to the database
      String sql = "SELECT * FROM PRODUCT";

      rs = stmt.executeQuery(sql);
      while (rs.next()) {

        existingProdCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        productLine.add(new Widget(rs.getString(2), rs.getString(3), rs.getString(4)));

        // sets the Existing products to the product list view.
        prodListView.setItems(productLine);

        existingProdTable.setItems(productLine);
        existingProdCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
        existingProdCol3.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

      }
      System.out.println("Existing Products populated!");
      // closes database.
      rs.close();
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("Failed to push to database. Driver not Found.");

    } catch (SQLException e) {
      System.out.println("Failed to pull from database.");
    } finally {
      //closes database connection in the event of an exception.
      connectionFinalClose(conn, stmt, rs);
    }

  }

  /**
   * This method is to make sure the database connections are closed so a SQL injection attack can
   * not occur to the database in the event one is still open after an exception happened when ever
   * a database connection is established.
   *
   * @param conn the connection statement value, if == null then connection is safe.
   * @param stmt the statement value, if == null then connection is safe.
   * @param rs the result statement value, if == null then connection is safe.
   */
  private void connectionFinalClose(Connection conn, Statement stmt, ResultSet rs) {
    connAndStmtFinallyClose(conn, stmt);
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (Exception ex) {
      System.out.println("Connection failed to close");
    }
  }

  /**
   * This method creates a database connection (H2) and gathers data from the PRODCUTIONRECORD table
   * and populates the record log and sets up what products we have made and their SN so new
   * products continue off of where the last item stopped at.
   *
   * @param productRecord a ProductionRecord array list to populate the log and gather data on what
   *     products have been made and to what SN they last stopped at.
   */
  public void populateProductionLog(ObservableList<ProductionRecord> productRecord) {
    Properties prop = new Properties();
    // gathers the properties file info for the username and password.
    try (InputStream input = new FileInputStream("./res/data.properties")) {
      prop.load(input);

    } catch (FileNotFoundException e) {
      System.out.println("Properties File not found!");
    } catch (IOException ex) {
      System.out.println("IO exception occurred!");
    }

    final String jdbcDriver = "org.h2.Driver";
    final String dbUrl = "jdbc:h2:./res/data";
    String user = prop.getProperty("db.username");
    String pass = prop.getProperty("db.password");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      Class.forName(jdbcDriver);
      System.out.println("Connecting to database....");
      conn = DriverManager.getConnection(dbUrl, user, pass);
      stmt = conn.createStatement();

      // Takes in values supplied by user and injects them to the database
      String sql = "SELECT * FROM PRODUCTIONRECORD";
      rs = stmt.executeQuery(sql);
      logTxtArea.setPrefColumnCount(4);
      logTxtArea.setEditable(false);

      while (rs.next()) {
        productRecord.add(new ProductionRecord(rs.getInt(1), rs.getInt(2),
            rs.getString(3), rs.getDate(4)));
        for (ProductionRecord prod : productRecord) {
          logTxtArea.setText(prod.toString());
        }

      }
      System.out.println("Database Records Pulled!");
      // closes database.
      rs.close();
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      System.out.println("Failed to push to database. Driver not Found.");
    } catch (SQLException e) {
      System.out.println("Failed to push to database.");
    } finally {
      // makes sure the connection to the database is closed in the event of an exception.
      connectionFinalClose(conn, stmt, rs);
    }
  }
}
