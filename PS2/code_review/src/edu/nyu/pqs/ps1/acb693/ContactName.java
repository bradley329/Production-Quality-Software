package edu.nyu.pqs.ps1.acb693;

/**
 * This class constructs an object of ContactName to save it in a Contact
 * object. First Name is the only required parameter. Middle name, Last name
 * and Suffix are optional parameters.
 * @author xynazog
 *
 */
class ContactName {
	private String firstName;
	private String lastName;
	private String suffix;
	private String middleName;
	
	/**
	 * This is a private constructor so that a client can't invoke it directly
	 * to create objects. Using the ContactNameBuilder is the only way to create a ContactName object.
	 * @param builder This is the ContactNameBuilder object passed to create a ContactName object.
	 */
	private ContactName(ContactNameBuilder builder) {
		firstName = builder.nestedFirstName;
		middleName = builder.nestedMiddleName;
		lastName = builder.nestedLastName;
		suffix = builder.nestedSuffix;
	}
	/**
	 * This is a first name getter function.
	 * @return returns the first name of the contact as a String.
	 * 
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * This is a middle name getter function.
	 * @return returns the middle name of the contact as a String.
	 */
	public String getMiddleName() {
		return middleName;
	}
	
	/**
	 * This is a last name getter function.
	 * @return returns the last name of the contact as a String.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * This is a suffix getter function.
	 * @return returns the suffix of the contact as a String.
	 */
	public String getSuffix() {
		return suffix;
	}
	
	/**
	 * This method overrides the original toString() function and 
	 * returns a string with the name of the contact.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Contact Name:\t");
		sb.append(getFirstName()+" ");
		sb.append(getMiddleName().equalsIgnoreCase("NA Field")? "":getMiddleName()+" ");
		sb.append(getLastName().equalsIgnoreCase("NA Field")? "":getLastName()+" ");
		sb.append(getSuffix().equalsIgnoreCase("NA Field")? "":getSuffix());
		return sb.toString();
	}
	
	/**
	 * This is a builder pattern for the ContactName class.
	 * 
	 */
	public static class ContactNameBuilder {
		private String nestedFirstName; //Required parameter
		
		//Setting the other parameters to an empty string, making it optional
		private String nestedMiddleName = "";
		private String nestedLastName = "";
		private String nestedSuffix = "";
		
		/**
		 * Instantiates an object of ContactNameBuilder with the first name.
		 * @param firstName first name of the contact is passed as a string to the method
		 */
		public ContactNameBuilder(String firstName) {
			this.nestedFirstName = firstName;
		}
		
		/**
		 * This method is used to add the middle name which is an optional parameter to the Contact Name 
		 * @param middleName middle name of the contact is passed as a string to the method
		 * @return returns an object of ContactNameBuilder with middle name set
		 */
		public ContactNameBuilder addMiddleName(String middleName) {
			this.nestedMiddleName = middleName;
			return this;
		}
		
		/**
		 * This method is used to add the last name which is an optional parameter to the Contact Name 
		 * @param lastName last name of the contact is passed as a string to the method
		 * @return returns an object of ContactNameBuilder with last name set
		 */
		public ContactNameBuilder addLastName(String lastName) {
			this.nestedLastName = lastName;
			return this;
		}
		
		/**
		 * This method is used to add the suffix which is an optional parameter to the Contact Name 
		 * @param suffix suffix of the contact is passed as a string to the method
		 * @return returns an object of ContactNameBuilder with suffix set
		 */
		public ContactNameBuilder addSuffix(String suffix) {
			this.nestedSuffix = suffix;
			return this;
		}
		
		/**
		 * This method builds the ContactName object with the parameters set if they exist.
		 * 
		 * @return returns a ContactName object with the parameters passed.
		 */
		public ContactName createContactName() {
			return new ContactName(this);
		}
	}
}