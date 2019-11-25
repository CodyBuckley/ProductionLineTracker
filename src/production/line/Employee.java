package production.line;

// imports required to use Registered Expression
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The program is required to create an audit trail of the production line so that it records which
 * employee recorded production. To accomplish this you will need to create a class and tab named
 * Employee that will allow the user to input their full name and then create a user id of their
 * first name, a period, and then their surname, an email address of their first initial and last
 * name.
 */
public class Employee {

  private StringBuilder name = new StringBuilder();
  private String username;
  private String password;
  private String email;

  /**
   * The method setUsername will set the username field to the first initial of the first name and
   * then the last name, all lowercase.
   *
   * @param name a StringBuilder that will be broken up at the space to create the first and last
   *     name to use for username.
   */
  private void setUsername(StringBuilder name) {

    // Create variables to break up the name into first and last name.
    int space = name.toString().indexOf(' ');
    String firstName = name.substring(0, space);
    String lastName = name.substring(space + 1);

    // Create username based on the first letter of first name and full last name.
    username = firstName.toLowerCase().charAt(0) + lastName.toLowerCase();
  }

  /**
   * The method checkName will check if the name contains a space. If it does, it will call
   * setUsername and setEmail, passing the name in to both. If it doesn't contain a space, set the
   * username to "default" and the email to "user@oracleacademy.Test".
   *
   * @param name a StringBuilder that will be examined to see if there a space between first and
   *     last name.
   * @return true regardless of the name, but will set username and email to default Strings if the
   *     name did not contain a space.
   */
  private boolean checkName(StringBuilder name) {

    // If Statement to examine if StringBuilder name has a space for methods setUsername and
    // setEmail.
    if (name.toString().contains(" ")) {
      return true;
    }

    // Default Strings if name does not contain a space.
    username = "default";
    email = "user@oracleacademy.Test";
    return true;
  }

  /**
   * The method setEmail will set the email field to the first name, then a period, then the last
   * name (all lowercase) followed by @oracleacademy.Test.
   *
   * @param name a StringBuilder that will broken up at the space to create the first and last name
   *     to use for an email.
   */
  private void setEmail(StringBuilder name) {

    int space = name.toString().indexOf(' ');
    String firstName = name.substring(0, space);
    String lastName = name.substring(space + 1);

    email =
        firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * The method isValidPassword will determine if the input password is valid. If the password is
   * valid (containing a lowercase letter, uppercase letter, and a special character) the password
   * field gets set to the supplied password. If the password is invalid, the password field gets
   * set to "pw".
   *
   * @param password supplied password by the user
   * @return either true or false: if the password contains a lowercase, uppercase, and a special
   *     character, it is true. if the password fails the regular expression matcher, it is false
   *     and the password is replaced to "pw".
   */
  private boolean isValidPassword(String password) {

    // Regular Expression to examine if the supplied password contains a lowercase ([a-z]),
    // uppercase ([A-Z]), and a special character ([!@#$%^&*()]).
    final String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).*$";

    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    final Matcher matcher = pattern.matcher(password);

    // If Statement to check and see if the supplied password matches the requirements.
    if (matcher.find()) {
      return true;
    }

    return false;
  }

  /**
   * The constructor Employee will accept a String for fullName (first name and surname) and String
   * for password. It will append the fullName into a StringBuilder object. Using name, it will
   * check the supplied name to set the username and email variable either through setUsername and
   * setEmail or through default Strings in the checkName method. It will examine if the supplied
   * password is valid, adhering to the password requirements of a lowercase, uppercase, and special
   * character.
   *
   * @param fullName a String for the user's first and last name.
   * @param password a String for the user's provided password.
   */
  public Employee(String fullName, String password) {
    name.append(fullName);
    this.password = password;

    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    }

    if (!isValidPassword(password)) {
      this.password = "pw";
    }
  }

  /**
   * The method toString is set to return the Employee Details of name, username, email, and
   * password.
   *
   * @return a String containing the name, username, email, and password.
   */
  public String toString() {
    return "Employee Details\nName : "
        + name
        + "\nUsername : "
        + username
        + "\nEmail : "
        + email
        + "\nInitial Password : "
        + password;
  }
}
