package edu.nyu.pqs.ps1.acb693;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class is the driver class. This drives the code and has the main code for the client.
 * @author xynazog
 *
 */
public class Driver{
	/**
	 * This is the main function
	 * @param args
	 * @throws IOException This exceptions is thrown if the file cannot be written.
	 */
	public static void main(String args[]) throws IOException{
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your username/emailaddress\n>");
		String username = sc.nextLine();
		File file = new File(username);
		AddressBook ab;
		if (file.exists()){
			List<Contact> tempList = readFromFile(file);
			ab = new AddressBook.AddBookBuilder(tempList).build();
		}
		else{
			List<Contact> tempList = new ArrayList<Contact>();
			ab = new AddressBook.AddBookBuilder(tempList).build();
		}
		String option;
		while(true){
			System.out.print("What would you like to do? \n1. Add a new contact \n2. Remove a contact \n3. Search for a contact \n4. View all your contacts\n5. Exit \n>");
			option = sc.nextLine();
			
			if(option.equalsIgnoreCase("exit") || option.equalsIgnoreCase("5") )	break;
			switch(option){
			case "1": 
				System.out.println("Please enter the following information:\n");
				String[] questions = {"Enter the first name","Enter the middle name","Enter the last name",
						"Enter the suffix","Enter the primary number","Enter the secondary number","Enter the email address",
						"Enter the Street address","Enter the Apartment number","Enter the name of the city","Enter the name of the state",
						"Enter the zip code","Enter some notes"};
				String[] answers = new String[13];
				String tempAnswer;
				for(int i=0;i<questions.length;i++){
					System.out.print(questions[i]+":\n>");
					tempAnswer = sc.nextLine();
					if(tempAnswer.isEmpty()){
						answers[i] = "NA Field";
					}
					else	answers[i] = tempAnswer;
				}
				ContactName name = buildName(answers[0], answers[2], answers[1], answers[3]);
				ContactAddress address = buildAddress(answers[7], answers[8], answers[9], answers[10], answers[11]);
				ContactPhoneMail phoneMail = buildPhoneMail(answers[4], answers[5], answers[6]);
				String note = answers[12];
				Contact contact = new Contact.ContactBuilder(name, phoneMail).addAddress(address).addNote(note).createContact();
				ab.addContact(contact);
				ab.writeToFile(file);
				break;
			case "2":	
				System.out.println("Please enter the name/primary number of the person:\n>");
				String phoneNumber = sc.nextLine();
				List<Contact> contacts = ab.searchContact(phoneNumber);
				if(contacts.size()>1){
					System.out.println("Please select one of the following contacts:\n");
				}
				else if(contacts.size()==1){
					ab.removeContact(contacts.get(0));
				}
				else{
					System.out.println("Contact not found!");
				}
				ab.writeToFile(file);
				break;
			case "3":
				System.out.println("Search your contacts:\n>");
				String tempString = sc.nextLine();
				List<Contact> cs = ab.searchContact(tempString);
				for(Contact c: cs){
					System.out.println(c.toString());
					}
				break;
			case "4":
				for(Contact c: ab.showAddressBook()){
					System.out.println(c.toString());
				}
				break;
			}
		}
		
		sc.close();
		System.out.println("Thank you for using the addressbook");
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