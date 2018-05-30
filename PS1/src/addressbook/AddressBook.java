package addressbook;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This class represents an address book which stores contact entries and be able to add, remove,
 * search, save, load contact entries.
 * 
 * @author Lei Guo
 */
public class AddressBook {
  private List<BookEntry> entryList;
  private int nextId;

  /**
   * Constructor for AddressBook
   */
  public AddressBook() {
    this.entryList = new ArrayList<>();
    this.nextId = 0;
  }

  /**
   * Add a new BookEntry to the AddressBook.
   * 
   * @param bookEntry The book entry to be added.
   * @return true if the book entry could be add to the address book successfully.
   */
  public boolean addEntry(BookEntry bookEntry) {
    if (bookEntry == null) {
      throw new NullPointerException("Contact cannot be null");
    } else if (entryList.contains(bookEntry)) {
      return false;
    } else {
      bookEntry.setId(nextId);
      nextId++;
      entryList.add(bookEntry);
      return true;
    }
  }

  /**
   * Remove an BookEntry (Object) from the AddressBook.
   * 
   * @param bookEntry The book entry to be removed.
   * @return true if the book entry could be removed successfully.
   */
  public boolean removeEntry(BookEntry bookEntry) {
    if (bookEntry == null) {
      throw new NullPointerException("Contact cannot be null");
    }
    return entryList.remove(bookEntry);
  }

  /**
   * Remove an BookEntry by its id from the AddressBook.
   * 
   * @param id The id of the book entry to be removed.
   * @return The book entry removed.
   */
  public BookEntry removeEntryById(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("Contact id cannot be less than 0");
    }

    for (int i = 0; i < entryList.size(); i++) {
      BookEntry bookEntry = entryList.get(i);
      if (bookEntry.getId() == id) {
        return entryList.remove(id);
      }
    }
    throw new NoSuchElementException("The contact want to be removed does not exist");
  }

  /**
   * Clear the AddressBook
   */
  public void clear() {
    entryList.clear();
    nextId = 0;
  }

  /**
   * Search for book entries which match the query string.
   * 
   * @param query string to be queried.
   * @return List of book entries which matches the query.
   */
  public List<BookEntry> searchEntry(String query) {
    List<BookEntry> results = new ArrayList<>();

    if (query == null) {
      throw new NullPointerException("Query string cannot be null");
    }

    for (BookEntry bookEntry : entryList) {
      if (bookEntry.matches(query)) {
        results.add(bookEntry);
      }
    }
    return results;
  }

  /**
   * Save the AddressBook to a JSON file.
   * 
   * @param path the output path.
   * @throws FileNotFoundException if file cannot be found.
   */
  public void saveToFile(String path) throws FileNotFoundException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    try (Writer writer = new FileWriter(path)) {
      gson.toJson(entryList, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Read the AddressBook from a JSON file.
   * 
   * @param path the input path.
   * @throws FileNotFoundException if file cannot be found.
   */
  public void readFromFile(String path) throws FileNotFoundException {
    Gson gson = new GsonBuilder().create();
    try (Reader reader = new FileReader(path)) {
      Type targetType = new TypeToken<List<BookEntry>>() {}.getType();
      List<BookEntry> entryListRead = gson.fromJson(reader, targetType);

      for (BookEntry bookEntryRead : entryListRead) {
        // create a clone of the bookEntryRead but do not preserve id
        BookEntry bookEntry = new BookEntry.Builder(bookEntryRead.getName())
            .phoneNumber(bookEntryRead.getPhoneNumber())
            .emailAddress(bookEntryRead.getEmailAddress())
            .postalAddress(bookEntryRead.getPostalAddress()).note(bookEntryRead.getNote()).build();
        addEntry(bookEntry);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Override toString() for the AddressBook.
   * 
   * @return the good-looking String format of the AddressBook.
   */
  @Override
  public String toString() {
    String result = "";
    for (BookEntry bookEntry : entryList) {
      result += bookEntry.toString() + "\n";
    }
    return result;
  }
}
