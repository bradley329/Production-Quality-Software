package edu.nyu.pqs.ps1.acb693;

/**
 * This class constructs an object of ContactAddress (address of the contact)
 * to save it into the Contact object. All the parameters are optional.
 *
 * @author xynazog
 *
 */
class ContactAddress {
	private String address;
	private String zip;
	private String apartmentNumber;
	private String city;
	private String state;
	
	/**
	 * This is the private constructor for the ContactAddress class so that a client
	 * can't invoke the constructor directly. Using the ContactAddressBuilder is the only way
	 * to create a ContactAddress object.
	 * @param builder This is the ContactAddressBuilder object.
	 */
	private ContactAddress(ContactAddressBuilder builder) {
		address = builder.nestedAddress;
		zip = builder.nestedZip;
		apartmentNumber = builder.nestedApartmentNumber;
		city = builder.nestedCity;
		state = builder.nestedState;
	}
	
	/**
	 * This is the street address getter function.
	 * @return returns the street address for the contact.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * This is the zip getter function.
	 * @return returns the zip code for the contact.
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * This is the apartment number getter function.
	 * @return returns the apartment number for the contact.
	 */
	public String getApartmentNumber() {
		return apartmentNumber;
	}
	/**
	 * This is the city getter function.
	 * @return returns the city for the contact.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * This is the state getter function.
	 * @return returns the state for the contact.
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * This is the builder pattern for the Contract Address.
	 */
	public static class ContactAddressBuilder {
		private String nestedAddress = "";
		private String nestedZip = "";
		private String nestedCity = "";
		private String nestedApartmentNumber = "";
		private String nestedState = "";
		
		/**
		 * This is a method used to add the street address which is an optional parameter
		 * for the ContactAddress class
		 * @param address This is the address of the contact passed as a String
		 * @return returns an object of ContactAddressBuilder with the street address added.
		 */
		public ContactAddressBuilder addAddress(String address) {
			this.nestedAddress = address;
			return this;
		}
		
		/**
		 * This is a method used to add the zip code which is an optional parameter
		 * for the ContactAddress class
		 * @param zip This is the zip code of the contact passed as a String
		 * @return returns an object of ContactAddressBuilder with the zip code added.
		 */
		public ContactAddressBuilder addZip(String zip) {
			this.nestedZip = zip;
			return this;
		}
		
		/**
		 * This is a method used to add the city which is an optional parameter 
		 * for the ContactAddress class.
		 * @param city This is the city of the contact passed as a String
		 * @return returns an object of ContactAddressBuilder with the city added.
		 */
		public ContactAddressBuilder addCity(String city) {
			this.nestedCity = city;
			return this;
		}
		
		/**
		 * This is a method used to add the apartment number which is an optional parameter 
		 * for the ContactAddress class.
		 * @param apartmentNumber This is the apartment number of the contact passed as a String
		 * @return returns an object of ContactAddressBuilder with the apartment number added.
		 */
		public ContactAddressBuilder addApartmentNumber(String apartmentNumber) {
			this.nestedApartmentNumber = apartmentNumber;
			return this;
		}
		
		/**
		 * This is a method used to add the state which is an optional parameter 
		 * for the ContactAddress class.
		 * @param state This is the state of the contact passed as a String
		 * @return returns an object of ContactAddressBuilder with the state added.
		 */
		public ContactAddressBuilder addState(String state) {
			this.nestedState = state;
			return this;
		}
		
		/**
		 * This method builds a ContactAddress object with the parameters set if they exist.
		 * @return returns a ContactAddress object with the parameters passed.
		 */
		public ContactAddress buildContactAddress() {
			return new ContactAddress(this);
		}
	}
	/**
	 * This method overrides the original toString() function and 
	 * returns a string with the address of the contact.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Contact Address:\t");
		sb.append(getAddress().equalsIgnoreCase("NA Field")? "":getAddress()+", ");
		sb.append(getApartmentNumber().equalsIgnoreCase("NA Field")? "":"Apartment# "+getApartmentNumber()+", ");
		sb.append(getCity().equalsIgnoreCase("NA Field")? "":getCity()+", ");
		sb.append(getState().equalsIgnoreCase("NA Field")? "" : getState()+" ");
		sb.append(getZip().equalsIgnoreCase("NA Field")? "" : " - "+getZip());
		return sb.toString();
	}
}