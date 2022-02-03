/**
 * The class you store in the database must fit 2 simple constraints:
 * <p>
 * The class must have a default constructor that takes no arguments The class must define public
 * getters for the properties to be assigned.
 * <p>
 * Properties without a public getter will be set to their default value when an instance is
 * deserialized.
 * <p>
 * You don't have to store custom classes, you can also store things like Strings.
 */
public class Post {

  private final String author;
  private final String message;

  public Post() {
    author = "";
    message = "";
  }

  public Post(String author, String message) {
    this.author = author;
    this.message = message;
  }

  public String getAuthor() {
    return author;
  }

  public String getMessage() {
    return message;
  }

  public String toString() {
    return "NAME: " + author + ", MESSAGE: " + message;
  }

}