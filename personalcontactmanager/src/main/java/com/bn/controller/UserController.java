package com.bn.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bn.dao.ContactRepository;
import com.bn.dao.UserRepository;
import com.bn.helper.Message;
import com.bn.models.Contact;
import com.bn.models.User;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	//method to add common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME "+userName);
		User user = userRepository.getuserByUserName(userName);
		System.out.println("USER "+user);
		model.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		
		return "normal/user_dashboard";
	}
	
	//open add form controller
	@GetMapping("/add-contact")
	public String openAddcontactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
//	//processing Add contact form
//	@PostMapping("/process-contact")
//	public String processContact(@ModelAttribute Contact contact,  Principal principal, HttpSession session){
//		String name = principal.getName();
//		User user = this.userRepository.getuserByUserName(name);		
//		contact.setUser(user);
//		user.getContacts().add(contact);
//		this.userRepository.save(user);
//		session.setAttribute("message", new Message("Contact is added!! Add more", "success"));
//		return "normal/add_contact_form";	
//	}
	
	//processing Add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session){
		try {
		String name = principal.getName();
		User user = this.userRepository.getuserByUserName(name);	
		//Processing and uploading file
		if(file.isEmpty()) {
			System.out.println("empty file");
			contact.setImage("contact.png");
		}
		else {
			String filename = file.getOriginalFilename();
			contact.setImage(filename);
			File saveFile = new ClassPathResource("static/img").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(), path , StandardCopyOption.REPLACE_EXISTING);	 
		}
		
		user.getContacts().add(contact);
		contact.setUser(user);
		this.userRepository.save(user);
		session.setAttribute("message", new Message("Contact is added!! Add more", "success"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "normal/add_contact_form";	
	}
	
	//Show contacts handler
	@GetMapping("/show-contacts/{page}")
	public String showContact(@PathVariable("page") Integer page, Model model, Principal principal) {
		model.addAttribute("title", "View Contacts");
		String userName = principal.getName();
		User user = this.userRepository.getuserByUserName(userName);
		Pageable pageable = PageRequest.of(page, 2);
		Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(), pageable);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		return "normal/show_contacts";
	}
	
	//showing specific contact details
	@GetMapping("/{cId}/contact")
	public String showContactDetails(@PathVariable("cId") Integer cId, Model model){
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		model.addAttribute("contact", contact);
		return "normal/contact_detail";
	}
	
	//delete Contact handler
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cId, Model model, Principal principal) {
		Contact contact = this.contactRepository.findById(cId).get();
		User user = this.userRepository.getuserByUserName(principal.getName());
		user.getContacts().remove(contact);
		this.userRepository.save(user);
		return "redirect:/user/show-contacts/0";
		
	}
	
	//Update Contact Handler
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cId, Model model) {
		
		model.addAttribute("title", "Update Contact");
		Contact contact = this.contactRepository.findById(cId).get(); 
		model.addAttribute("contact", contact);
		return "normal/update_form";
	}
	
	//update contact handler
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Model model, 
			HttpSession session, Principal principal) {
		
		
		try {
			Contact oldcontactdetail = this.contactRepository.findById(contact.getcId()).get();
			if (!file.isEmpty()) {
				//delete old pic
//				File deleteFile = new ClassPathResource("static/img").getFile();
//				File dfile = new File(deleteFile, oldcontactdetail.getImage());
//				dfile.delete(); 	
				//Update new pic
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path , StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
			}
			else {
				contact.setImage(oldcontactdetail.getImage());
			}
			User user = this.userRepository.getuserByUserName(principal.getName());
			contact.setUser(user);
			this.contactRepository.save(contact);
			session.setAttribute("message", new Message("contact details updated",  "success"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		

		return "redirect:/user/"+contact.getcId()+"/contact"; 
	} 
	
	
}
