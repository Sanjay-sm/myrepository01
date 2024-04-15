package com.springphonewebapp.springphonebookapp.contacts;


import java.util.List;

import org.springframework.aot.hint.BindingReflectionHintsRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
//@Controller
public class ContactController 
{
//   private ContactRepository repository;
   private ContactService service;



   public ContactController( ContactService service) {
	super();
	this.service = service;
}
   
    @RequestMapping(value = "contact-info" ,method = RequestMethod.GET)
	public String contactInfo(ModelMap model) {
    	Contacts contact = new Contacts(0,"","",0);
    	model.put("contact", contact);
		return "contactInfo";
	}
	
@RequestMapping("all-contacts")
public String allContacts(ModelMap model) {
	   List<Contacts> contacts = service.retrieveAllContacts();
	   model.addAttribute("contacts", contacts);
	   return "allContacts"; 
   }

@RequestMapping(value = "contact-info", method = RequestMethod.POST)
public String addNewContact(@RequestParam String name, @RequestParam String email, 
		@RequestParam int phno,Contacts contact,BindingResult result) 
  {
	  service.addContact(name,email,phno);
	  return "redirect:all-contacts";
   }
@RequestMapping("delete-contact")
public String deleteContact(@RequestParam int id) {
	service.deleteById(id);
	return "redirect:all-contacts";
	
}

@RequestMapping(value = "update-contact", method = RequestMethod.GET)
public String updateContactPage(@RequestParam int id, ModelMap model) {
	Contacts contact = service.findById(id);
	model.addAttribute("contact",contact);
	return "contactInfo";
}

@RequestMapping(value = "update-contact", method = RequestMethod.POST)
public String updateContact(@RequestParam int id, ModelMap model,
		Contacts contact,BindingResult result) {
	service.updateContact(contact);
	return "redirect:all-contacts";
}

}
