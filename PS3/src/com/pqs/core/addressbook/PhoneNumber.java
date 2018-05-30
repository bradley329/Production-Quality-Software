package com.pqs.core.addressbook;

import java.util.regex.Pattern;

/*
 * This Class Represents a Phone Number. Checks for a valid Phone Number
 * 
 */
public class PhoneNumber {
  private String number;
  private PhoneNumber(String number) {
    this.setNumber(number);
  }
  /*@param A string containing the phone number
   * @return An instance of a PhoneNumber object if valid, else null is returned
   */
  public static PhoneNumber valueOf(String number) {
    Pattern pattern = Pattern.compile("(?:\\(\\d{3}\\)|\\d{3}[-]*)\\d{3}[-]*\\d{4}");
    if (pattern.matcher(number).matches()) {
    	  return new PhoneNumber(number.replaceAll("-()", ""));
    	}
    else {
    	  return null;
    	}
  }
  @Override
  public String toString() {
    return number;
  }
  @Override
  public boolean equals(Object p) {
    if (((PhoneNumber)p).number.equals(this.number) ) {
      return true;
    }
    return false;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String number) {
    this.number = number;
  }
	   
}