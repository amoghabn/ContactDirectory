package com.bn.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	//processing Add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact,  Principal principal, HttpSession session){
		String name = principal.getName();
		User user = this.userRepository.getuserByUserName(name);		
		contact.setUser(user);
		user.getContacts().add(contact);
		this.userRepository.save(user);
		session.setAttribute("message", new Message("Contact is added!! Add more", "success"));
		return "normal/add_contact_form";	
	}
	
	//Show contacts handler
	@GetMapping("/show-contacts")
	public String showContact(Model model) {
		model.addAttribute("title", "show Contact");
		return "normal/show_contacts";
	}
	
}
