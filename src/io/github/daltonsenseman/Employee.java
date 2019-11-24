package io.github.daltonsenseman;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is to make and populate a Employee roster for use to keep track on who is on the
 * system
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("WeakerAccess")
public class Employee {

  private final StringBuilder name;
  private String username;
  private final String password;
  private String email;

  /**
   * Constructor to pass in information to be validated and the set
   *
   * @param name     full name of the Employee
   * @param password password supplied to be checked
   */
  public Employee(String name, String password) {
    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    } else {
      System.out.println("default username and password.");
      this.username = "default";
      this.email = "user@oracleacademy.Test";
    }
    this.name = new StringBuilder(name);
    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * Sets the username checking for a " " and spiting it so it can be formatted first initial
   * lastname.
   *
   * @param name name to be parsed and verified.
   */
  private void setUsername(String name) {
    String[] splitName = name.split("\\s+");
    this.username = splitName[0].substring(0, 1).toLowerCase() + splitName[1].toLowerCase();
  }

  /**
   * Validates the name to have a single " " separating the first and last name.
   *
   * @param name name to be verified.
   * @return the matching bool is the Name is acceptable to be used.
   */
  private boolean checkName(String name) {
    String regex = "[a-zA-z]+[\\s{1}][a-zA-Z]+";
    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(name);

    return matcher.matches();
  }

  /**
   * takes in the name and spilts it setting the email for the account.
   * firstnamelastname@@oracleacademy.Test
   *
   * @param name name to be turned into a email.
   */
  private void setEmail(String name) {
    String[] splitName = name.split("\\s+");
    this.email = splitName[0].toLowerCase() + splitName[1].toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * Checks to see if the password is acceptable and follows convention. 1 uppercase, 1 lowercase, 1
   * special character, 1 digit, and 6-12 long.
   *
   * @param password password to be checked.
   * @return bool is the password is acceptable.
   */
  private boolean isValidPassword(String password) {

    // Wanted to make sure it worked in all cases so looked into making a password Regex.
    // Source = https://ramj2ee.blogspot.com/2017/12/how-to-write-regex-to-validate-password.html
    String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,12})";
    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(password);

    return matcher.matches();
  }

  /**
   * ToString to print data to console for verification for the user that is was correctly imputed.
   *
   * @return String of the fields of this class.
   */
  @Override
  public String toString() {
    return "Employee Details\n" +
        "Name : " + this.name + "\n" +
        "Username : " + this.username + "\n" +
        "Email : " + this.email + "\n" +
        "Initial Password : " + password + "!\n";
  }
}
