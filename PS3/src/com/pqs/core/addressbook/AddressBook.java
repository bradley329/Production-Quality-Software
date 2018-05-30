/* Author: Nitisha Pandharpurkar 
 * PQS Assignment 1
 */
package com.pqs.core.addressbook;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.pqs.core.addressbook.Contact;
import com.pqs.core.addressbook.GeneralUtils;

/*This is the core implementation of an address book library. It's functionality includes 
* Create an empty address book.
* Add an entry.  An entry consists of a Contact object from the Contact class
* Remove an entry.
* Search for an entry by any of the contact properties.
* Save the address book to a file.
* Read the address book from a file
*/
public class AddressBook
{
  HashMap<Integer,Contact> addressList;
  /* @return the entire AddressBook as a Collection of Contact objects */
  public  Collection<Contact> getAddressList() {
	return addressList.values();
  }
  /* Creates an empty Address Book */
  public AddressBook() {
    addressList = new HashMap<Integer,Contact>();  
  }
  /*@param The XML file name to export to
   * @return Status, 0 meaning successful, -1 unsuccessful
   */
  public int exportAddressBook(String filename) {
    return GeneralUtils.exportAddressBook(getAddressList(), filename);	  
  }
  /*
   * @param The XML filename to export from
   * Imports to an Initialized Empty AddressBook
   */
  public void importAddressBook(String filename) {
    this.addressList=GeneralUtils.importAddressBook( filename);
  }
  /* @param The name, postal address, phone number, note. If an entity is not to be added, we set it to null
   * @return The added contact object
   */
  public Contact addEntrytoAddressBook(Contact c) {
    int key;
	if (addressList.size()==0 ){
	  Random rand = new Random();
	  int max=100;
	  int min=10;
	  //Setting the first key from 10 to 100
      key = rand.nextInt((max - min) + 1) + min;

	}
	else {
	  key =Collections.max(addressList.keySet())+1;
	}
		//Keys are then added in increasing order
		
    c.setId(key);
	addressList.put(key,c);
    return c;
  }
  /* A short description of the addressbook */
  @Override
  public String toString() {
    return String.format("Address Book: "+addressList.size()+" entries");
  }
 
  /* @param The Contact object. If an entity is not known, we set it to null
   * @return The found contact object. If not found, null is returned
   */
  public Contact searchEntry(Contact x) {
    for (Contact c: addressList.values()) {
      if (c.weakEquals(x)) {
   	    return c;
    	 }
    }
    return null;
  }
  
  
  /* @param The Contact Object. If an entity is not known, we set it to null
   * @return Whether a contact was deleted or not
   */

  public boolean removeEntry(Contact c) {
    if (addressList.containsValue(c)) {
      addressList.remove(c.getId(),c);
	  return true;
	}
    return false;
  }
}