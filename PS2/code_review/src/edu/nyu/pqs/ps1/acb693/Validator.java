package edu.nyu.pqs.ps1.acb693;

/**
 * This class is a helper class which helps to validate the email address and phone number
 * provided for the contact. It uses the two strings: phoneNumberRegex and emailIDRegex
 * to validate the Strings
 * @author xynazog
 *
 */
class Validator {
	private static String phoneNumberRegex = "[-0-9()+]+";

	private static String emailIDRegex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]+$|^$";

	/**
	 * This method is used to check the validity of a phone number using the phone number regex string.
	 * @param phoneNumber this is the phone number of the contact
	 * @return returns true if the phone number is valid and false otherwise.
	 */
	public static boolean checkPhoneNumberValidity(String phoneNumber) {
		return (phoneNumber.matches(phoneNumberRegex));
	}
	
	/**
	 * This method is used to check the validity of an email address using the email ID regex string.
	 * @param emailID this is the email address of the contact
	 * @return returns true if the email address is valid and false otherwise
	 */
	public static boolean checkemailIDValidity(String emailID) {
		return (emailID.matches(emailIDRegex));
	}
	
}