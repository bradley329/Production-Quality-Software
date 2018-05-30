import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import com.pqs.core.addressbook.PhoneNumber;

public class PhoneNumberTest {
  @Test
  public void test() {
    PhoneNumber phoneNumber0 = PhoneNumber.valueOf("@@@");
    assertEquals(phoneNumber0, null);
    PhoneNumber phoneNumber1 = PhoneNumber.valueOf("6462261908");
    String expected = "6462261908";
    assertEquals(expected, phoneNumber1.toString());
    assertEquals("6462261908", phoneNumber1.getNumber());
    PhoneNumber phoneNumber2 = PhoneNumber.valueOf("6462261908");
    PhoneNumber phoneNumber3 = PhoneNumber.valueOf("6462261905");
    // test equals() symmetrically when its true
    assertTrue(phoneNumber1.equals(phoneNumber2) && phoneNumber2.equals(phoneNumber1));
    assertFalse(phoneNumber1.equals(phoneNumber3) && phoneNumber3.equals(phoneNumber1));
  }
}
