package com.bn.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bn.dao.UserRepository;
import com.bn.helper.Message;
import com.bn.models.User;

import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;


//import ch.qos.logback.core.model.Model;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home- Personal Contact Manager");
		return "index";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About- Personal Contact Manager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "signup- Personal Contact Manager");
		model.addAttribute("user", new User());	
		return "signup";
	}
	
	//handler for registering user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value="agreement", defaultValue="false") boolean agreement, Model model, HttpSession session) {
		
		if(result.hasErrors()) {
			System.out.println(result);
			 return "signup";
		}
		
		try 
		{
			if(!agreement) 
			{
				System.out.println("You have not agreed to terms and conditions");
				throw new Exception("You have not agreed to terms and conditions");
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("user", user);
			
			session.setAttribute("message", new Message("Something went wrong "+e.getMessage(), "alert-danger"));
					
		}
		
		return "signup";
		
	}
	
//handler for login 
	@GetMapping("/signin")
	public String customLogin(Model model, HttpSession session) {
		model.addAttribute("title", "login Page- Personal Contact Manager");
		session.setAttribute("message", new Message(" ", "alert"));
		return "login";
	}

}
