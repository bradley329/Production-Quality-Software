package com.pqs.core.addressbook;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pqs.core.addressbook.Contact;

 class GeneralUtils {

  static int exportAddressBook(Collection<Contact> contactList, String filename) {
	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder;
	  try {
    	    docBuilder = docFactory.newDocumentBuilder();
	    Document doc = docBuilder.newDocument();
  	    Element rootElement = doc.createElement("addressbook");
  	    doc.appendChild(rootElement); 
  	    String temp;
  	    for (Contact c: contactList) {
		  Element contact = doc.createElement("contact");
		  rootElement.appendChild(contact);
		    
	      Attr attr = doc.createAttribute("id");
  	      attr.setValue(Integer.toString(c.getId()));
		  contact.setAttributeNode(attr);
		    
	      Element name = doc.createElement("name");
	      temp=c.getName();
	      if (temp==null) {
	    	    temp="null";
	      }
  	      name.appendChild(doc.createTextNode(temp));
		  contact.appendChild(name);
		
		  Element postalAddress = doc.createElement("address");
		  temp=c.getPostalAddress();
	      if (temp==null) {
	    	    temp="null";
	      }
	      postalAddress.appendChild(doc.createTextNode(temp));
	      contact.appendChild(postalAddress);
				
		  Element number = doc.createElement("number");
		  temp=c.getNumber();
	      if (temp==null) {
	    	    temp="null";
	      }
	  	  number.appendChild(doc.createTextNode(temp));
		  contact.appendChild(number);
				
		  Element note = doc.createElement("note");
		  temp=c.getNote();
	      if (temp==null) {
	    	    temp="null";
	      }
	      note.appendChild(doc.createTextNode(temp));
		  contact.appendChild(note);
        }
  	    // write the content into xml file
  	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filename));

  		// Output to console for testing
  		// StreamResult result = new StreamResult(System.out);

  		transformer.transform(source, result);
      } catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
	    e.printStackTrace();
	  }

		
	return -1;
  }
  static HashMap<Integer, Contact> importAddressBook (String filename) {
	HashMap<Integer, Contact> addressList = new HashMap<Integer,Contact>();  
    try {
    	  File fXmlFile = new File(filename);
    	  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	  Document doc = dBuilder.parse(fXmlFile);
	  doc.getDocumentElement().normalize();
	  NodeList nList = doc.getElementsByTagName("contact");
	  for (int temp = 0; temp < nList.getLength(); temp++) {
	    Node nNode = nList.item(temp);
	    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	      Element eElement = (Element) nNode;
	      int id =Integer.parseInt(eElement.getAttribute("id"));
	      String name=eElement.getElementsByTagName("name").item(0).getTextContent();
	      if (name.equals("null")) {
	    	    name=null;
	      }
	      String postalAddress=eElement.getElementsByTagName("address").item(0).getTextContent();
	      if (postalAddress.equals("null")) {
	    	    postalAddress=null;
	      }
	      String number=eElement.getElementsByTagName("number").item(0).getTextContent();
	      if (number.equals("null")) {
	    	    number=null;
	      }
	      String note=eElement.getElementsByTagName("note").item(0).getTextContent();
	      if (note.equals("null")) {
	    	    note=null;
	      }
	      Contact c= new Contact.ContactBuilder()
	    		  .name(name)
	    		  .postalAddress(postalAddress)
	    		  .number(number)
	    		  .note(note)
	    		  .build();
	      addressList.put(id, c);
	    }
	  }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return addressList;
  }
}
