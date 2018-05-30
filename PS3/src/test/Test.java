package test;

import java.util.Scanner;

import com.pqs.core.addressbook.AddressBook;
import com.pqs.core.addressbook.Contact;

public class Test {
  public static void main(String[] args) {
    AddressBook book = new AddressBook();
    System.out.println("We have created a new address book"+book);
    System.out.println("The entries in it are: ");
    for (Contact c: book.getAddressList()) {
    	  System.out.println(c);
    }
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter a contact");
    System.out.println("Enter contact name ");
    String name = sc.nextLine();
    System.out.println("Enter contact number ");
    String number = sc.nextLine();
    System.out.println("Enter contact address ");
    String postalAddress = sc.nextLine();
    System.out.println("Enter note ");
    String note = sc.nextLine();
    Contact c= new Contact.ContactBuilder()
  		  .name(name)
  		  .postalAddress(postalAddress)
  		  .number(number)
  		  .note(note)
  		  .build();
    book.addEntrytoAddressBook(c);
    System.out.println("The entries in the book are: ");
    for (Contact x: book.getAddressList()) {
    	  System.out.println(x);
    }
    System.out.println("Now enter a contact with phonenumber null");
    System.out.println("Enter contact name ");
    name = sc.nextLine();
    //System.out.println("Enter contact number ");
    //number = sc.nextLine();
    System.out.println("Enter contact address ");
    postalAddress = sc.nextLine();
    System.out.println("Enter note ");
    note = sc.nextLine();
    c= new Contact.ContactBuilder()
        .name(name)
        .postalAddress(postalAddress)
        .number(number)
        .note(note)
        .build();
    book.addEntrytoAddressBook(c);
    System.out.println("The entries in the book are: ");
    for (Contact x: book.getAddressList()) {
    	  System.out.println(x);
    }
    System.out.println("Enter name to search with: ");
    name=sc.nextLine();
    c= new Contact.ContactBuilder().name(name).build();
    System.out.println("Found contact "+book.searchEntry(c));
    System.out.println("Now removing this entry");
    book.removeEntry(book.searchEntry(c));
    System.out.println("The entries in the book are: ");
    for (Contact x: book.getAddressList()) {
    	  System.out.println(x);
    }
    System.out.println("Exporting to ab.xml");
    book.exportAddressBook("ab.xml");
    System.out.println("Importing to a new book");
    AddressBook book2 = new AddressBook();
    book2.importAddressBook("ab.xml");
    System.out.println("The entries in the new book are: ");
    for (Contact x: book2.getAddressList()) {
    	  System.out.println(x);
    }
    sc.close();
  }
}
