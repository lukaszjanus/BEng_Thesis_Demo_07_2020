package com.ssd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ssd.statics.Statics;

/**
 * Annotations:
 * GetMapping - used only to display site.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
@Controller
public class LoginController {

	/**
	 * Controller open login site.
	 * 
	 * @GetMapping - read get-action '/login'
	 * 
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * 
	 * @return path to login-site
	 */
	@GetMapping("/login")
	public String showMyLoginPage(Model model) {
		model.addAttribute("nameApp", Statics.getnameApp());
		return "login";
	}
}
