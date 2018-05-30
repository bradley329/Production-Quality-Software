package edu.nyu.pqs.ps1.acb693;

/**
 * This class constructs an object of Contact to save in the address book. The
 * Contact object has 2 required parameters: name and phoneMail and 2
 * optional parameters: address and notes.
 * 
 * An object of Contact is created using the ContactBuilder() method of Builder pattern.
 * pattern.
 * 
 * @author xynazog
 *
 */
class Contact {
	private ContactName name;
	private ContactAddress address;
	private ContactPhoneMail phoneMail;
	private String notes;
	
	/**
	 * This is a private constructor. The builder is the only way to create an object.
	 * This constructor can't be invoked to create objects.
	 * @param builder A ContactBuilder object is passed which helps to create the Contact object.
	 */
	private Contact(ContactBuilder builder) {
		name = builder.nestedName;
		address = builder.nestedAddress;
		phoneMail = builder.nestedPhoneMail;
		notes = builder.nestedNotes;
	}
	
	/**
	 * Returns a ContactName object stored in the Contact object.
	 * @return This returns the name of the Contact.
	 */
	public ContactName getName() {
		return name;
	}
	
	/**
	 * Returns a ContactAddress object stored in the Contact object.
	 * @return This returns the address of the Contact.
	 */
	public ContactAddress getFullAddress() {
		return address;
	}
	
	/**
	 * Returns a ContactPhoneMail object stored in the Contact object.
	 * @return this returns the phone and email ID of the contact.
	 */
	public ContactPhoneMail getPhoneMail() {
		return phoneMail;
	}
	
	/**
	 * Returns a String which are the notes in the Contact object.
	 * @return this returns the notes of the contact.
	 */
	public String getNotes(){
		return notes;
	}
	
	public static class ContactBuilder {
		private ContactName nestedName;
		private ContactPhoneMail nestedPhoneMail;
		
		//Optional Parameters
		private ContactAddress nestedAddress = new ContactAddress.
											ContactAddressBuilder().buildContactAddress();
		private String nestedNotes = "";
		
		/**
		 * Instantiates an object of ContactBuilder with required fields: name and phoneMail
		 * @param name 
		 * 			An object of ContactName is passed which has fields like FirstName, LastName...
		 * @param phoneMail 
		 * 			An object of ContactPhoneMail is passed which has fields like PhoneNumber, 
		 * 			Email address...
		 */
		public ContactBuilder (ContactName name, ContactPhoneMail phoneMail){
			this.nestedName = name;
			this.nestedPhoneMail = phoneMail;
		}
		
		/**
		 * Method to add the address fields to the object
		 * @param ad
		 * 			An object of ContactAddress is passed which has fields like Street address,
		 * 			City, Zip...
		 * @return	An object of Builder() is returned.
		 */
		public ContactBuilder addAddress(ContactAddress ad){
			this.nestedAddress = new ContactAddress.ContactAddressBuilder().addAddress(ad.getAddress()).
					addApartmentNumber(ad.getApartmentNumber()).addCity(ad.getCity()).addState(ad.getState()).addZip(ad.getZip()).buildContactAddress();
			return this;
		}
		
		/**
		 * Method to add notes to the object.
		 * @param notes A string called notes is passed to the contact object
		 * @return	An object of Builder() is returned.
		 */
		public ContactBuilder addNote(String notes){
			this.nestedNotes = notes;
			return this;
		}
		
		/**
		 * This creates a new object of the Contact class with the parameters set.
		 * @return An object of Contact is returned.
		 */
		public Contact createContact(){
			return new Contact(this);
		}
	}
	
	/**
	 * This method overrides the original toString() function and 
	 * returns a string with the details of the contact.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.name.toString()+"\n");
		sb.append(this.phoneMail.toString()+"\n");
		sb.append(this.address.toString()+"\n");
		sb.append(notes.equalsIgnoreCase("NA Field")? "":"Note:"+this.notes+"\n");
		return sb.toString();
	}
	
}