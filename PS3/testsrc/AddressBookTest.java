import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.junit.Test;
import com.pqs.core.addressbook.AddressBook;
import com.pqs.core.addressbook.Contact;

public class AddressBookTest {

  @Test
  public void testCreateNewAddressBook() {
    AddressBook addressBook = new AddressBook();
    assertEquals(0, addressBook.getAddressList().size());
    assertEquals("Address Book: 0 entries", addressBook.toString());
  }

  @Test
  public void testAddNewContact() {
    AddressBook addressBook = new AddressBook();
    Contact contact1 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").note("Male").build();
    addressBook.addEntrytoAddressBook(contact1);
    assertEquals(1, addressBook.getAddressList().size());
    assertTrue(addressBook.getAddressList().contains(contact1));
    Contact contact2 = new Contact.ContactBuilder().name("Rose").postalAddress("8 river court")
        .number("6455622312").note("Female").build();
    addressBook.addEntrytoAddressBook(contact2);
    // still, cannot test if id is correct
    assertEquals(2, addressBook.getAddressList().size());
    assertEquals("Address Book: 2 entries", addressBook.toString());
  }

  @Test
  public void testRemoveContact() {
    AddressBook addressBook = new AddressBook();
    Contact contact1 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").note("Male").build();
    addressBook.addEntrytoAddressBook(contact1);
    assertEquals(1, addressBook.getAddressList().size());
    Contact contact2 = new Contact.ContactBuilder().name("Rose").postalAddress("8 river court")
        .number("6455622312").note("Female").build();
    assertFalse(addressBook.removeEntry(contact2));
    assertTrue(addressBook.removeEntry(contact1));
    assertEquals(0, addressBook.getAddressList().size());
  }

  @Test
  public void testDataSecurity() {
    AddressBook addressBook = new AddressBook();
    Contact contact1 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").note("Male").build();
    addressBook.addEntrytoAddressBook(contact1);
    Collection<Contact> list = addressBook.getAddressList();
    list.clear();

    // make sure the user cannot corrupt the data
    // it fails since getAddressList() did not return a copy.
    assertEquals(1, addressBook.getAddressList().size());
  }


  @Test
  public void testSearchContact() {
    AddressBook addressBook = new AddressBook();
    Contact contact1 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").note("Male").build();
    addressBook.addEntrytoAddressBook(contact1);
    Contact contact2 = new Contact.ContactBuilder().name("Rose").postalAddress("8 river court")
        .number("6455622312").note("Female").build();
    addressBook.addEntrytoAddressBook(contact2);

    // since the code is buggy without check for nulls
    // we just give some other number and note.
    Contact contact3 = new Contact.ContactBuilder().name("Rose").postalAddress("8 river court")
        .number("1211548789").note("scas").build();

    // since search uses weakEquals, so it should return an instance which equals contact2.
    assertEquals(contact2, addressBook.searchEntry(contact3));
    Contact contact4 = new Contact.ContactBuilder().name("Mike").postalAddress("can't tell you")
        .number("5555555555").note("vb").build();
    assertEquals(null, addressBook.searchEntry(contact4));
  }

  /*
   * GeneralUtils is not public so we have to use AddressBook to indirectly access GeneralUtils.
   * Also, since the author did not throw the exception, so its very hard to test the exception.
   */

  @Test
  public void testImportExport() {
    AddressBook addressBook = new AddressBook();
    addressBook.exportAddressBook("mybook");
    AddressBook addressBook2 = new AddressBook();
    addressBook2.importAddressBook("mybook");

    Contact contact1 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").note("Male").build();
    addressBook.addEntrytoAddressBook(contact1);
    assertEquals(-1, addressBook.exportAddressBook("mybook"));

    // test when name is null
    Contact contact2 = new Contact.ContactBuilder().postalAddress("20 river court")
        .number("6462261908").note("Male").build();
    addressBook.addEntrytoAddressBook(contact2);
    assertEquals(-1, addressBook.exportAddressBook("mybook"));

    // test when postal address is null
    Contact contact3 =
        new Contact.ContactBuilder().name("Lei Guo").number("6462261908").note("Male").build();
    addressBook.addEntrytoAddressBook(contact3);
    assertEquals(-1, addressBook.exportAddressBook("mybook"));

    // test when note is null
    Contact contact4 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").build();
    addressBook.addEntrytoAddressBook(contact4);
    assertEquals(-1, addressBook.exportAddressBook("mybook"));

    // test when number is null
    Contact contact5 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .note("Male").build();
    addressBook.addEntrytoAddressBook(contact5);
    assertEquals(-1, addressBook.exportAddressBook("mybook"));

    // test import
    addressBook2.importAddressBook("mybook");

    // code below would throw NullPointerException since the lack of null checks
    assertEquals(5, addressBook2.getAddressList().size());
  }
}
