package com.springphonewebapp.springphonebookapp.contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
@Service
public class ContactService 
{
  private static List<Contacts> contacts = new ArrayList<Contacts>();
  private static int sr_count = 0;
  
  static {
	  contacts.add(new Contacts(++sr_count,"Sanjay","sanju@gmail.com",986476477l));
	  contacts.add(new Contacts(++sr_count,"Sachin","sachin@gmail.com",912546477l));
	  contacts.add(new Contacts(++sr_count,"Sunay","Sunay@gmail.com",912365835l));
  }
  
  public List<Contacts> retrieveAllContacts(){
	  return contacts;
  }
  
  public void addContact(String name, String email, long phno) {
	 contacts.add(new Contacts(++sr_count,name,email,phno));
  }

public void deleteById(int id) {
	Predicate<Contacts> predicate = contact -> contact.getId()==id ;
	contacts.removeIf(predicate);
	
}

public Contacts findById(int id) {
	Predicate<Contacts> predicate = contact -> contact.getId()==id ;
	return contacts.stream().filter(predicate).findFirst().get();
}

public void updateContact(Contacts contact) {
	deleteById(contact.getId());
	contacts.add(contact);
	
}
}
