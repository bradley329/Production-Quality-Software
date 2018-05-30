import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.pqs.core.addressbook.Contact;

/*
 * For weakEquals() and setId(), since they are package private, so there is no way to test in this
 * package. However, since they have been called in AddressBook, we could test them in
 * AddressBookTest.
 */
public class ContactTest {
  @Test
  public void test() {
    Contact contact1 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").note("Male").build();

    // test toString()
    String expected = "Name: Lei Guo" + "\n" + "Number: 6462261908" + "\n"
        + "Postal Address: 20 river court" + "\n" + "Note: Male";
    assertEquals(expected, contact1.toString());

    Contact contact2 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261908").note("Male").build();

    // test equals() symmetrically when its true
    assertTrue(contact1.equals(contact2) && contact2.equals(contact1));
    assertTrue(contact1.hashCode() == contact2.hashCode());

    Contact contact3 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("6462261911").note("Male").build();

    // test equals() symmetrically when its false
    assertFalse(contact1.equals(contact3) && contact3.equals(contact1));

    Contact contact4 = new Contact.ContactBuilder().name("Lei Guo").postalAddress("20 river court")
        .number("").note("Male").build();

    // the tests below fails since in "equals()", there is no null check for "number".
    // actually, equals() is very buggy, it does not even determine whether object is an instance of
    // Contact beforehand.
    assertFalse(contact1.equals(contact4));
    assertFalse(contact1.equals(null));
  }
}
