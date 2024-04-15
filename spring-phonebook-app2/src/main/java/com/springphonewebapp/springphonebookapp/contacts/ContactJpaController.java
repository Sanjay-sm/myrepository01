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

import jakarta.validation.Valid;
@Controller
public class ContactJpaController 
{
     private ContactRepository repository;
     private EmailService service;
     
   public ContactJpaController( ContactRepository repository,EmailService service) {
	
	this.repository = repository;
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
	   List<Contacts> contacts = repository.findAll();
	   model.addAttribute("contacts", contacts);
	   return "allContacts"; 
   }

@RequestMapping(value = "contact-info", method = RequestMethod.POST)
public String addNewContact(@RequestParam String name, @RequestParam String email, 
		@RequestParam long phno,  Contacts contact,BindingResult result) 
  {
	  repository.save(contact);
	  service.sendEmail(contact);
	  return "redirect:all-contacts";
   }
@RequestMapping("delete-contact")
public String deleteContact(@RequestParam int id) {
	repository.deleteById(id);
	return "redirect:all-contacts";
	
}

@RequestMapping(value = "update-contact", method = RequestMethod.GET)
public String updateContactPage(@RequestParam int id, ModelMap model) {
	Contacts contact = repository.findById(id).get();
	model.addAttribute("contact",contact);
	return "contactInfo";
}

@RequestMapping(value = "update-contact", method = RequestMethod.POST)
public String updateContact(@RequestParam int id, ModelMap model,
			Contacts contact,BindingResult result) {
	repository.save(contact);
	return "redirect:all-contacts";
}

}
