package addressbook;

import java.io.File;
import java.io.IOException;

public class Test {
  public static void main(String[] args) throws IOException {
    AddressBook addressBook = new AddressBook();
    addressBook.addEntry(new BookEntry.Builder("lei").phoneNumber("6462261908")
        .postalAddress("07310").emailAddress("lg2681@nyu.edu").note("me").build());
    addressBook.addEntry(new BookEntry.Builder("man").phoneNumber("6462261000")
        .postalAddress("06310").emailAddress("xxxwan@nyu.edu").note("he").build());
    addressBook.addEntry(new BookEntry.Builder("woman").phoneNumber("6462261001")
        .postalAddress("05310").emailAddress("wanxxx@nyu.edu").note("she").build());

    // Print all the data we have so far
    System.out.println("All data: ");
    System.out.println(addressBook);
    String path = "output\\test.json";

    // Check if path exists
    File outFile = new File(path);
    if (!outFile.exists()) {
      outFile.getParentFile().mkdirs();
      outFile.createNewFile();
    }

    // Save data to json file
    addressBook.saveToFile(path);

    // Clear all data in addressBook
    addressBook.clear();
    System.out.println("Data after cleared: ");
    System.out.println(addressBook);
    System.out.println();

    // Read data from json file
    addressBook.readFromFile(path);
    System.out.println("Data read: ");
    System.out.println(addressBook);

    // Search all entries which contains "xxx"
    String query = "xxx";
    System.out.println("Query: 'xxx'");
    for (BookEntry bookEntry : addressBook.searchEntry(query)) {
      System.out.println(bookEntry);
    }
    System.out.println();
    // Remove the last entry
    addressBook.removeEntryById(2);
    System.out.println("After removing the last entry");
    System.out.println(addressBook);
  }
}
