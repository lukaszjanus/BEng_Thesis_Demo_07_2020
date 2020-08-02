package com.ssd.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.UsersDAO;
import com.ssd.dao.UsersDAOImpl;


/**
 * Controller with options of application for all users.
 * To use this controller (and options in menu), user must have added role 'Settings'.
 * If user has only role 'Settings', it is possible to login and f.e. change password, but no other functionalities are available.
 *
 * Annotations:
 * GetMapping - used only to display site.
 * PostMapping - used to Post-method of forms.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * RequestMapping - mapping of admin area, all methods have added prefix from this annotation
 * 
 * Mapping of "/settings" is area accessed only with role 'Settings'.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

	/**
	 * Change user's own password - open view.
	 * 
	 * @GetMapping - read get-action '/settings/accountEdition'
	 * 
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * 
	 * @return - localization and name of jsp file (Front-End) as string, without prefix and without suffix
	 */
	@GetMapping("/accountEdition")
	public String changePasswordForm(Model model) {
		return "settings/accountEdition";
	}

	/**
	 * Post-Method - user's own password change.
	 * Before password is saved in db, method hashed password by Spring Security built-in library.
	 * User is recognised from current logged user. 
	 *
	 * @param model - read Model-object
	 * @param req - to read button-label
	 * @param password - new password from form 
	 * 
	 * @return localization - name of jsp file (Front-End) as string, without prefix and without suffix
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("/accountEdition")
	public String changePassword(Model model, HttpServletRequest req, @RequestParam("p1") String password) throws SecurityException, IOException {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		UsersDAO userDao = new UsersDAOImpl();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = ("{bcrypt}" + passwordEncoder.encode(password));
		userDao.updatePassword(name, password,0);
		String submitType = req.getParameter("submit");

		if (submitType.equals("Save")) {
			return "settings/accountEdition";
		}
		if (submitType.equals("Save and Exit")) {
			return "home";
		}
		
		return "home";
	}
	
	/**
	 * Method created in DAO and generated structure of whole Menu: Top-bar, Sub-folders, Options.
	 * Object is added to Front-End.
	 * 
	 * @param model - default spring method, here menu is added to all jsps (Front-End) 
	 * 
	 * @return object Menu_OptionsDAO
	 */
	@ModelAttribute
	public Model addMenuAndLabels(Model model) {
		Menu_OptionsDAO menu = new Menu_OptionsDAOImpl();
		model.addAttribute(menu.addMenuAndLabels(model));
		return model;
	}
}
