package io.github.daltonsenseman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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

  final ArrayList<Integer> ProductCounts = new ArrayList<>();

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
  private TextField passTxtBox;

  @FXML
  private Button addEmplyBtn;

  @FXML
  private ChoiceBox<ItemType> itypecbbox;

  @FXML
  private TextField nameTxtBox;

  //</editor-fold>

  /**
   * Records the selected production options and increments the database and adds the production to
   * the record.
   *
   * @param event on mouse event clicking button
   */
  @FXML
  void recordProdMouseClick(MouseEvent event) {
    Product selectionChoice = prodListView.getSelectionModel().getSelectedItem();
    int numTomMake = Integer.parseInt(chooseQntyCbBox.getValue());
    String idNum = String.valueOf(prodListView.getSelectionModel().getSelectedIndices());
    idNum = idNum.replaceAll("[\\[\\](){}]", "");
    int type = 0;
    getProductCount();
    switch (selectionChoice.getType()) {
      case "AUDIO":
        type = 0;
        break;
      case "VISUAL":
        type = 1;
        break;
      case "AUDIO_MOBILE":
        type = 2;
        break;
      case "VISUAL_MOBILE":
        type = 3;
        break;
      default:
        System.out.println("type not found.");
        break;
    }
    int amountMade = ProductCounts.get(type);
    for (int i = 0; i < numTomMake; i++) {
      ProductionRecord addToRecord = new ProductionRecord(selectionChoice, amountMade, idNum);
      addToProdRecord(addToRecord);
      amountMade++;

    }
    storeProductCount(type, amountMade);
    System.out.println("Recorded!");
    populateProductionLog(productRecord);
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
          + "VALUES(?,?,?)";

      PreparedStatement prep = conn.prepareStatement(sql);
      prep.setString(1, itype);
      prep.setString(2, manuName);
      prep.setString(3, proName);

      prep.executeUpdate();

      System.out.println("New Product Added!");
      // closes database.
      stmt.close();
      conn.close();
      prep.close();

    } catch (ClassNotFoundException e) {
      System.out.println("Failed to push to database. Driver not Found.");
    } catch (SQLException e) {
      System.out.println("Failed to push to database.");
    } finally {
      // closes connections in the event an exception occurred.
      connAndStmtFinallyClose(conn, stmt);
    }

    existingProdCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
    productLine.add(new Widget(proName, manuName, itype));
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
    ProductCounts.add(0);
    ProductCounts.add(0);
    ProductCounts.add(0);
    ProductCounts.add(0);
  }

  /**
   * Adds Employees to the system taking Uses's name and a password and sending them to be
   * automatically formatted and verified as good before setting them to the user.
   *
   * @param event mouse click event onto the button.
   */
  @FXML
  void addEmployeeBtnClick(MouseEvent event) {
    String name = nameTxtBox.getText();
    String passwrd = passTxtBox.getText();
    Employee addNewEmployee = new Employee(name, passwrd);
    System.out.println(addNewEmployee.toString());

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
        productLine.add(new Widget(rs.getString(2), rs.getString(4), rs.getString(3)));

        // sets the Existing products to the product list view.
        prodListView.setItems(productLine);

        existingProdTable.setItems(productLine);
        existingProdCol2.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        existingProdCol3.setCellValueFactory(new PropertyValueFactory<>("type"));

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
   * @param rs   the result statement value, if == null then connection is safe.
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
   *                      products have been made and to what SN they last stopped at.
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
      }
      logTxtArea.clear();
      for (ProductionRecord prod : productRecord) {
        logTxtArea.appendText(prod.toString());
        logTxtArea.appendText("\n");
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

  /**
   * Takes the counts of each type of product from the database and puts them into a Arraylist.
   */
  public void getProductCount() {

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
      String sql = "SELECT * FROM PRODUCTCOUNT";
      rs = stmt.executeQuery(sql);
      int index = 0;
      while (rs.next()) {
        for (int i = 1; i < 5; i++) {
          int number = rs.getInt(i);
          ProductCounts.set(index, number);
          index++;
        }
      }
      System.out.println("Existing Products Counts populated!");
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
   * Takes the new value of the amount of items made and pushes them to the database
   * @param type what type of item was changed
   * @param newAmount how many new items were added in the production run
   */
  public void storeProductCount(int type, int newAmount) {
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

    try {
      Class.forName(jdbcDriver);
      System.out.println("Connecting to database....");
      conn = DriverManager.getConnection(dbUrl, user, pass);
      stmt = conn.createStatement();
      String sql = null;

      if (type == 0) {
        sql = "UPDATE PRODUCTCOUNT SET NUM_OF_AU =" + newAmount + "";
      } else if (type == 1) {
        sql = "UPDATE PRODUCTCOUNT SET NUM_OF_VI =" + newAmount + "";
      } else if (type == 2) {
        sql = "UPDATE PRODUCTCOUNT SET NUM_OF_AM =" + newAmount + "";
      } else if (type == 3) {
        sql = "UPDATE PRODUCTCOUNT SET NUM_OF_VM =" + newAmount + "";
      } else {
        System.out.println("Column not found error");
      }
      stmt.executeUpdate(sql);

      System.out.println("Product counts Added!");
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

  }

  /**
   * Pushes the new product added and sends it into the productionRecord table so it can be
   * displayed in the log or kept on hand.
   * @param newProduct the new product information to add to the record.
   */
  public void addToProdRecord(ProductionRecord newProduct) {
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
    int id = newProduct.getProductID();
    String serNum = newProduct.getSerialNum();
    Date dateProd = newProduct.getProdDate();
    java.sql.Timestamp sqlDate = new java.sql.Timestamp(dateProd.getTime());

    try {
      Class.forName(jdbcDriver);
      System.out.println("Connecting to database....");
      conn = DriverManager.getConnection(dbUrl, user, pass);
      stmt = conn.createStatement();

      // Takes in values supplied by user and injects them to the database
      String sql = "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
          + "VALUES(?,?,?)";

      PreparedStatement prep = conn.prepareStatement(sql);
      prep.setString(1, String.valueOf(id));
      prep.setString(2, serNum);
      prep.setString(3, String.valueOf(sqlDate));

      prep.executeUpdate();

      System.out.println("New Product Added!");
      // closes database.
      stmt.close();
      conn.close();
      prep.close();

    } catch (ClassNotFoundException e) {
      System.out.println("Failed to push to database. Driver not Found.");
    } catch (SQLException e) {
      System.out.println("Failed to push to database.");
    } finally {
      // closes connections in the event an exception occurred.
      connAndStmtFinallyClose(conn, stmt);
    }

  }
}

