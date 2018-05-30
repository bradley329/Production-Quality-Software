package addressbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the book entry which has attributes such as name, phone number, etc.
 * 
 * @author Lei Guo
 */
public class BookEntry {
  private String name;
  private String phoneNumber;
  private String emailAddress;
  private String postalAddress;
  private String note;

  private int id = -1;

  /**
   * The Builder class for BookEntry using the BuilderPattern.
   */
  public static class Builder {

    private final String PHONE_REGEX = "^\\+?[0-9. ()-]{10,25}$";
    private final String EMAIL_REGEX =
        "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$";

    // Required parameters
    private String name;

    // Optional parameters
    private String phoneNumber;
    private String emailAddress;
    private String postalAddress;
    private String note;

    /**
     * Constructor for Builder.
     * 
     * @param name The required parameter for Builder.
     */
    public Builder(String name) {
      if (name == null) {
        throw new NullPointerException("Contact name cannot be null");
      }
      this.name = name;
    }

    /**
     * Set the phone number of the Builder.
     * 
     * @param phoneNumber The phone number to be set.
     * @return The Builder after phone number is set.
     */
    public Builder phoneNumber(String phoneNumber) {
      if (phoneNumber != null) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
          this.phoneNumber = phoneNumber;
        } else {
          throw new IllegalArgumentException("Phone number is not valid");
        }
      }
      return this;
    }

    /**
     * Set the email address of the Builder.
     * 
     * @param emailAddress The email address to be set.
     * @return The Builder after email address is set.
     */
    public Builder emailAddress(String emailAddress) {
      if (emailAddress != null) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(emailAddress);
        if (matcher.matches()) {
          this.emailAddress = emailAddress;
        } else {
          throw new IllegalArgumentException("Email address is not valid");
        }
      }
      return this;
    }

    /**
     * Set the postal address of the Builder.
     * 
     * @param postalAddress The postal address to be set.
     * @return The Builder after postal address is set.
     */
    public Builder postalAddress(String postalAddress) {
      if (postalAddress != null) {
        this.postalAddress = postalAddress;
      }
      return this;
    }

    /**
     * Set the note of the Builder.
     * 
     * @param note The note to be set.
     * @return The Builder after note is set.
     */
    public Builder note(String note) {
      if (note != null) {
        this.note = note;
      }
      return this;
    }

    /**
     * This method create a new BookEntry.
     * 
     * @return The new BookEntry based on the Builder.
     */
    public BookEntry build() {
      return new BookEntry(this);
    }
  }

  private BookEntry(Builder builder) {
    name = builder.name;
    phoneNumber = builder.phoneNumber;
    postalAddress = builder.postalAddress;
    emailAddress = builder.emailAddress;
    note = builder.note;
  }

  /**
   * Setter for id.
   * 
   * @param id The id to be set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter for id.
   * 
   * @return The id of this entry.
   */
  public int getId() {
    return id;
  }

  /**
   * Getter for name.
   * 
   * @return The name of this entry.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter for phone number.
   * 
   * @return The phone number of this entry.
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * Getter for email address.
   * 
   * @return The email address of this entry.
   */
  public String getEmailAddress() {
    return this.emailAddress;
  }

  /**
   * Getter for postal address.
   * 
   * @return The postal address of this entry.
   */
  public String getPostalAddress() {
    return this.postalAddress;
  }

  /**
   * Getter for note.
   * 
   * @return The note of this entry.
   */
  public String getNote() {
    return this.note;
  }

  /**
   * Determine if this entry matches the query.
   * 
   * @param query The query string.
   * @return true if the entry's attributes contains query.
   */
  public boolean matches(String query) {
    if (name.contains(query) || (phoneNumber != null && phoneNumber.contains(query))
        || (postalAddress != null && postalAddress.contains(query))
        || (emailAddress != null && emailAddress.contains(query)
            || (note != null && note.contains(query)))) {
      return true;
    }
    return false;
  }

  /**
   * Override the hashCode function.
   * 
   * @return The hashCode generated.
   */
  @Override
  public int hashCode() {
    int result = 7;
    if (id >= 0) {
      result = 31 * result + id;
    }
    if (name != null) {
      result = 31 * result + name.hashCode();
    } else {
      result = 31 * result;
    }
    if (phoneNumber != null) {
      result = 31 * result + phoneNumber.hashCode();
    } else {
      result = 31 * result;
    }
    if (emailAddress != null) {
      result = 31 * result + emailAddress.hashCode();
    } else {
      result = 31 * result;
    }
    if (postalAddress != null) {
      result = 31 * result + postalAddress.hashCode();
    } else {
      result = 31 * result;
    }
    if (note != null) {
      result = 31 * result + note.hashCode();
    } else {
      result = 31 * result;
    }
    return result;
  }

  /**
   * Override the equals function.
   * 
   * @param o The other object to compared with.
   * @return true if two objects are equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof BookEntry)) {
      return false;
    }
    BookEntry bookEntry = (BookEntry) o;
    return id == bookEntry.getId() && name.equals(bookEntry.getName())
        && phoneNumber.equals(bookEntry.getPhoneNumber())
        && emailAddress.equals(bookEntry.getEmailAddress())
        && postalAddress.equals(bookEntry.getPostalAddress()) && note.equals(bookEntry.getNote());
  }

  /**
   * Override the toString function to generate a good-looking string format.
   * 
   * @return The string generated.
   */
  @Override
  public String toString() {
    return "{id=" + id + ", name=" + name + ", phoneNumber="
        + ((phoneNumber != null) ? phoneNumber : "null") + ", postalAddress="
        + ((postalAddress != null) ? postalAddress : "null") + ", emailAddress="
        + ((emailAddress != null) ? emailAddress : "null") + "}";
  }
}
