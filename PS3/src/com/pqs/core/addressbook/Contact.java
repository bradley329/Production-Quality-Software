package com.pqs.core.addressbook;

import com.pqs.core.addressbook.PhoneNumber;

/* Each contact cosists of a name, postal address, phone number, email address, and a note.
 * Every attribute is optional. 
 * The ContactBuilder and the setter methods method are public 
 * 
 * 
 */
public class Contact {
  private String name=null;
  private String postalAddress=null;
  private String note=null;
  private PhoneNumber number=null;
  private int id;
  private Contact(ContactBuilder c) {
    this.name = c.name;
    this.postalAddress=c.postalAddress;
    this.note=c.note;
    this.number=c.number;
    
  }
  
  @Override
  public String toString() {
    return String.format("Name: "+getName()+"\n"+"Number: "+getNumber() +"\n"+"Postal Address: "+getPostalAddress()
      + "\n"+"Note: "+getNote());
  }
  @Override
  public boolean equals(Object c) {
    if ((((Contact)c).getNumber().equalsIgnoreCase(this.getNumber())) &&
    		(((Contact)c).getName().equalsIgnoreCase(this.getName())) &&
    		(((Contact)c).getNote().equalsIgnoreCase(this.getNote())) && 
    	    (((Contact)c).getPostalAddress().equalsIgnoreCase(this.getPostalAddress()))) {
      return true;
    }
    
    
    return false;
  }
  boolean weakEquals(Contact c)
  {
    if((c.getName().equalsIgnoreCase(this.getName()))||
    		(c.getNumber().equalsIgnoreCase(this.getNumber()))||
    		(c.getPostalAddress().equalsIgnoreCase(this.getPostalAddress()))||
    		(c.getNote().equalsIgnoreCase(this.getNote())))  {
    		  return true;	
    		}
    return false;
  }
  @Override
  public int hashCode() {
    return getId();	  
  }
  

  
  public String getName() {
	return name;
  }

  public String getPostalAddress() {
	return postalAddress;
  }

  public String getNumber() {
	if (number!=null) {
	  return number.getNumber();	
	}
	return null;
  }

  public String getNote() {
	return note;
  }

  void setId(int id) {
    this.id=id;	  
  }
  
  int getId() {
    return id; 	  
  }
  public static class ContactBuilder {
    private String name=null;
    private String postalAddress=null;
    private String note=null;
    private PhoneNumber number=null;	  
    
    public ContactBuilder()
    {
    	  return;
    }
    public ContactBuilder name(String name) {
    	  this.name=name;
    	  return this;
    }
    public ContactBuilder note(String note) {
  	  this.note=note;
  	  return this;
    }
    public ContactBuilder postalAddress(String postalAddress) {
    	  this.postalAddress=postalAddress;
    	  return this;
    }
    public ContactBuilder number(String number) {
    	  PhoneNumber no=PhoneNumber.valueOf(number);
    	  if (no==null) {
    	    return this;	  
    	  }
  	  this.number=no;
  	  return this;
    }
    public Contact build() {
    	  return new Contact(this);
    }
  }
}
