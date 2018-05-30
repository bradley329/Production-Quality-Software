package edu.nyu.pqs.ps1.acb693;

/**
 * This class constructs an object of ContactPhoneMail to save it in the 
 * Contact object. Primary Number is the only required parameter. Secondary number
 * and the email address are the optional parameters.
 * @author xynazog
 *
 */
class ContactPhoneMail {
	private String primaryNumber;
	private String secondaryNumber;
	private String EmailAddress;
	
	/**
	 * This is the private constructor so that the client can't invoke it
	 * directly to create ContactPhoneMail objects. Using the ContactPhoneMailBuilder
	 * is the only way to create a ContactPhoneMail object.
	 * 
	 * @param builder This is the ContactPhoneMailBuilder object passed to set the parameters
	 * 					and return a ContactPhoneMail object.
	 */
	private ContactPhoneMail(ContactPhoneMailBuilder builder) {
		primaryNumber = builder.nestedPrimaryNumber;
		secondaryNumber = builder.nestedSecondaryNumber;
		EmailAddress = builder.nestedEmailAddress;
	}
	/**
	 * This is the primary number getter function.
	 * @return primary number of the contact as a String.
	 */
	public String getPrimaryNumber() {
		return primaryNumber;
	}
	/**
	 * This is the secondary number getter function.
	 * @return secondary number of the contact as a String.
	 */
	public String getSecondaryNumber() {
		return secondaryNumber;
	}
	/**
	 * This is the email address getter function.
	 * @return email address of the contact as a String.
	 */
	public String getEmailAddress() {
		return EmailAddress;
	}
	
	/**
	 * This is the builder pattern for the ContactPhoneMail class. 
	 *
	 */
	public static class ContactPhoneMailBuilder {
		private String nestedPrimaryNumber;
		private String nestedSecondaryNumber = "";
		private String nestedEmailAddress = "";
		
		/**
		 * Instantiates an object of ContactPhoneMailBuilder using the 
		 * primary number as a required parameter.
		 * Throws an IllegalArgumentException if the phone number is not in a valid form.
		 * 
		 * @param primaryNumber This is the primary number of the contact 
		 * passed as a String which is validated.
		 */
		public ContactPhoneMailBuilder(String primaryNumber) {
			//System.out.println(primaryNumber);
			if (!Validator.checkPhoneNumberValidity(primaryNumber))
	            throw new IllegalArgumentException("Invalid phone number");
	        else{
	        	nestedPrimaryNumber = primaryNumber;
	        }
		}
		/**
		 * This method adds the secondary number to the ContactPhoneMailBuilder object as
		 * it is an optional parameter.
		 * Throws an IllegalArgumentException if the phone number is not in a valid form.
		 * @param secondaryNumber This is the secondary number of the contact 
		 * passed as a String which is validated.
		 * @return Returns a ContactPhoneMailBuilder object with the secondary number field set.
		 */
		public ContactPhoneMailBuilder addSecondaryNumber(String secondaryNumber) {
			if (!Validator.checkPhoneNumberValidity(secondaryNumber) 
					&& !secondaryNumber.equalsIgnoreCase("NA Field"))
	            throw new IllegalArgumentException("Invalid phone number");
	        else{
	        	nestedSecondaryNumber = secondaryNumber;
				return this;
	        }
	        	
		}
		/**
		 * This method adds the email address to the ContactPhoneMailBuilder object as
		 * it is an optional parameter.
		 * Throws an IllegalArgumentException if the email address is not in a valid form.
		 * @param emailAddress This is the email address of the contact
		 * passed as a String which is validated.
		 * @return Returns a ContactPhoneMailBuilder object with the email address field set.
		 */
		public ContactPhoneMailBuilder addEmailAddress(String emailAddress) {
			if (!Validator.checkemailIDValidity(emailAddress) 
					&& !emailAddress.equalsIgnoreCase("NA Field"))
	            throw new IllegalArgumentException("Invalid email address");
	        else{
	        	nestedEmailAddress = emailAddress;
	        	return this;
	        }
		}
		/**
		 * This method builds a ContactPhoneMail object from the ContactPhoneMailBuilder object
		 * with the parameters/fields set if they exist.
		 * @return Returns a ContactPhoneMail object with the existing fields set.
		 */
		public ContactPhoneMail createContactPhoneMail() {
			return new ContactPhoneMail(this);
		}
	}
	/**
	 * This method overrides the original toString() function and 
	 * returns a string with the phone and email details of the contact.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Contact Details:\t");
		sb.append("Primary Number: "+getPrimaryNumber()+"\t");
		sb.append(getSecondaryNumber().equalsIgnoreCase("NA Field")? "":"Secondary number: "+getSecondaryNumber()+"\t");
		sb.append(getEmailAddress().equalsIgnoreCase("NA Field")? "":"Email address: "+getEmailAddress());
		return sb.toString();
	}
	
}