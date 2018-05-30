package edu.nyu.pqs.ps1.acb693;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * AddressBook is a well defined library for the clients to add a 
 * contact, remove a contact and search a contact. You can provide any string
 * to be searched in the contact. There are 2 more methods for saving the 
 * addressbook to a file and reading the addressbook from a file.
 * 
 * @author xynazog
 *
 */
class AddressBook {
	private List<Contact> addressbook;

	/**
	 * This is a private constructor which wont invoke
     * Constructs a new addressbook object using the AddBookBuilder.
     */
	private AddressBook(AddBookBuilder builder){
		addressbook = builder.nestedAddressbook;
	}
	
	/**
     * Constructs a new addressbook object.
     */
	public static class AddBookBuilder{
		private List<Contact> nestedAddressbook;
		
		/**
		 * Builds an object of AddBookBuilder and initializes the addressbook list.
		 * 
		 * @param ab List of Contact with which addressbook should be initialized
		 */
		public AddBookBuilder(List<Contact> ab) {
			this.nestedAddressbook = ab;
		}
		
		/**
		 * Builds an object AddressBook and returns it.
		 * 
		 * @return a new object of AddressBook 
		 */
		public AddressBook build(){
			return new AddressBook(this);
		}
	}
	
	/**
	 * This functions is an addressbook object getter.
	 * 
	 * @return An addressbook object is returned.
	 */
	public List<Contact> showAddressBook(){
		return addressbook;
	}
	
	/**
	 * Adds a contact to the addressbook. If an entry with same parameters
	 * exists, the addressbook will duplicates of the same contact.
	 * 
	 * @param contact 
	 * 				An object of Contact to be added to the addressbook.
	 * @return return true if the contact is saved successfully 
	 * 			false if contact is null.
	 */
	public boolean addContact(Contact contact) {
		if (contact !=null) {
			addressbook.add(contact);
			return true;
		} 
		return false;
	}
	/**
	 * Removes a contact from the addressbook. 
	 * 
	 * @param contact 
	 * 				An object of Contact to be removed from the addressbook.
	 * @return returns true if the contact is removed successfully or false 
	 * 			if the contact is null.
	 */
	public boolean removeContact(Contact contact) {
		if (contact!=null) {
			addressbook.remove(contact);
			return true;
		}
		return false;
	}
	
	/**
	 * Searches the addressbook for the searchString parameter passed by the user.
	 * 
	 * @param searchString
	 * @return This returns a list of Contacts matching the searchString.
	 */
	public <T> List<Contact> searchContact(T searchString) {
		List<Contact> result = new ArrayList<Contact>();
		for (Contact contact: addressbook) {
			String concatenatedContact = buildStringContact(contact);
			if(concatenatedContact.toLowerCase().contains(((String) searchString).toLowerCase())){
				result.add(contact);
			}
		}
		return result;
	}

	/**
	 * Saves the addressbook to the file named after the username/email address entered.
	 * This stores the list of contacts in the addressbook in the memory to the file line by line.
	 * If the file already exists, the file is first deleted and then updated with the details 
	 * in the memory.
	 * 
	 * @param file File object to which the data has to be saved.
	 * @throws IOException If the file can't be written.
	 */
	public void writeToFile(File file) throws IOException{
		if(file.exists())
			file.delete();
		file.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter os = new OutputStreamWriter(fos);
		
		for (Contact c: addressbook) {
			String tempContact = buildStringContact(c);
			os.write(tempContact);
		}
		os.close();
	}
	/**
	 * This builds a string by concatenating the contact details with a pipe
	 * as a delimiter. 
	 * @param contact Contact object passed 
	 * @return string representation of the Contact object is returned
	 */
	private static String buildStringContact(Contact contact) {
    	String result = contact.getName().getFirstName()+"|"
    					+ contact.getName().getMiddleName()+"|"
    					+ contact.getName().getLastName()+"|"
    					+ contact.getName().getSuffix()+"|"
    					+ contact.getFullAddress().getAddress()+"|"
    					+ contact.getFullAddress().getApartmentNumber()+"|"
    					+ contact.getFullAddress().getCity()+"|"
    					+ contact.getFullAddress().getState()+"|"
    					+ contact.getFullAddress().getZip()+"|"
    					+ contact.getPhoneMail().getPrimaryNumber()+"|"
    					+ contact.getPhoneMail().getSecondaryNumber()+"|"
    					+ contact.getPhoneMail().getEmailAddress()+"|"
    					+ contact.getNotes()+"\n";
    	return result;
    }	
	
	/**
	 * This code is to read the file for a specific username/email address
	 * @param file File object which is read using a StringTokenizer
	 * @return This method returns a list of Contact objects.
	 * @throws FileNotFoundException This exception is thrown if the file is not found to read from.
	 */
	public static List<Contact> readFromFile(File file) throws FileNotFoundException{
		List<Contact> readContacts = new ArrayList<Contact>();
		FileInputStream fin = new FileInputStream(file);
		InputStreamReader in = new InputStreamReader(fin);
		
		Scanner sc = new Scanner(in);
		
		while(sc.hasNextLine()){
			StringTokenizer st = new StringTokenizer(sc.nextLine(),"\\|");
			Contact contact = buildContactFromString(st);
			readContacts.add(contact);
		}
		sc.close();
		return readContacts;
	}
	
	
	
	/**
	 * This method is used to build a Contact object from the String read from the file
	 * @param st StringTokenizer object for tokenizing the string according to the delimiter.
	 * @return This method returns a Contact object from the String.
	 */
	private static Contact buildContactFromString(StringTokenizer st){
		String firstName = st.nextToken().trim();
		String middleName = st.nextToken().trim();
		String lastName = st.nextToken().trim();
		String suffix = st.nextToken().trim();
		String add = st.nextToken().trim();
		String apartmentNumber = st.nextToken().trim();
		String city = st.nextToken().trim();
		String state = st.nextToken().trim();
		String zip = st.nextToken().trim();
		String primaryNumber = st.nextToken().trim();
		String secondaryNumber = st.nextToken().trim();
		String emailAddress = st.nextToken().trim();
		String note = st.nextToken().trim();
		
		ContactName name = buildName(firstName, lastName, middleName, suffix);
		ContactAddress address = buildAddress(add,apartmentNumber,city,state,zip);
		ContactPhoneMail phoneMail = buildPhoneMail(primaryNumber,secondaryNumber,emailAddress);
		
		Contact contact = buildContact(name,address,phoneMail,note);
		return contact;
	}
	
    /**
     * This method is used to create a new ContactName object using the parameters passed to the method.
     * 
     * @param firstName This is the first name of the contact. This parameter is required.
     * @param lastName This is the last name of the contact. This parameter is optional.
     * @param middleName This is the middle name of the contact. This parameter is optional.
     * @param suffix This is the suffix of the contact. This parameter is optional.
     * @return
     */
    private static ContactName buildName(String firstName, String lastName,
            String middleName, String suffix) {
        return new ContactName.ContactNameBuilder(firstName).addMiddleName(middleName)
        		.addLastName(lastName).addSuffix(suffix).createContactName();
    }
    
    /**
     * This method is used to create a new ContactAddress object in order to create a new Contact object.
     * @param address This is the street address of the contact. This parameter is optional.
     * @param apartmentNumber This is the apartment number of the contact. This parameter is optional.
     * @param city This is the city of the contact. This parameter is optional.
     * @param state This is the state of the contact. This parameter is optional.
     * @param zip This is the Zip code of the contact. This parameter is optional.
     * @return This method returns a new ContactAddress object with the parameters set.
     */
    private static ContactAddress buildAddress(String address, String apartmentNumber, String city,
    		String state, String zip){
    	return new ContactAddress.ContactAddressBuilder().addAddress(address)
    			.addApartmentNumber(apartmentNumber).addCity(city).addState(state).addZip(zip).buildContactAddress();
    }
    
    /**
     * This method is used to create a new ContactPhoneMail object in order to create a new Contact 
     * object.
     * 
     * @param primaryNumber This is the primary number of the contact. This parameter is optional.
     * @param secondaryNumber This is the secondary number of the contact. This parameter is optional.
     * @param emailAddress This is the email address of the contact. This parameter is optional.
     * @return This returns a ContactPhoneMail object with the parameters set.
     */
    private static ContactPhoneMail buildPhoneMail(String primaryNumber, 
    		String secondaryNumber, String emailAddress){
    	return new ContactPhoneMail.ContactPhoneMailBuilder(primaryNumber)
    			.addSecondaryNumber(secondaryNumber).addEmailAddress(emailAddress).createContactPhoneMail();
    } 
    
    /**
     * This method is used to create a new Contact object for the addressbook.
     * @param name This is the ContactName object which represents the name of the contact.
     * @param address This is the ContactAddress object which represents the address of the contact.
     * @param phoneMail This is the ContactPhoneMail object which represents 
     * the phone and email of the contact.
     * @param note This is a String which represents the notes to be stored for the contact
     * @return returns a Contact object to be stored in the list of Contacts.
     */
    private static Contact buildContact(ContactName name, ContactAddress address, 
    		ContactPhoneMail phoneMail, String note){
    	
    	return new Contact.ContactBuilder(name, phoneMail).addAddress(address).addNote(note).createContact();
    }
}