package com.ssd.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.UsersDAO;
import com.ssd.dao.UsersDAOImpl;
import com.ssd.entity.Users;

/**
 * Class used to add new user by Administator.
 * Methods only to operate form with add user.
 * 
 * Annotations:
 * GetMapping - used only to display site.
 * PostMapping - used to Post-method of forms.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
@Controller
public class AdminAddUserController {

	/**
	 * Method Load view with 'add user' form.
	 * 
	 * @GetMapping read get-action 'adminTools/AddUser'
	 * 
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * 
	 * @return - localization and name of jsp file (Front-End) as string, without prefix and without suffix
	 */
	@GetMapping("adminTools/AddUser")
	public String showToolAddUser(Model model) {
		return "adminTools/toolAddUser";
	}
	
	/**
	 * Method add new user by administrator to database.
	 * 
	 * Flag:
	 * 0 - add new user correctly, 'return' depends on the option selected.
	 * 1 - duplicate username, new user not added to db
	 * 2 - dupicate e-mail, new user not added to db
	 * 
	 * @PostMapping read post-action 'adminTools/AddUser'
	 *
	 * @param user - new object of user with fields from forms
	 * @param model - read Model-object with user and menu - used in forms by Spring

	 * @param req - read request of servlet, here used to check, with option user has choose: 'Save' or 'Save and Exit'
	 * 
	 * @return - localization and name of jsp file (Front-End) or controller (redirect:/(...)) as string, without prefix and without suffix
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("adminTools/AddUser")
	public String saveNewUser(@ModelAttribute Users user, Model model, HttpServletResponse res, HttpServletRequest req)
			throws SecurityException, IOException {

		if (user.getUserName().contentEquals("")) {
			user.setUserName(user.getFirstName() + "." + user.getLastName());
		}
		user.setEnabled(true);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword("{bcrypt}" + passwordEncoder.encode(user.getPassword()));
		UsersDAO usersDAO = new UsersDAOImpl();
		int flag = usersDAO.saveNewUser(user);

		switch (flag) {
		case 0: {
			String submitType = req.getParameter("submit");
			System.out.println("Case 0============");
			if (submitType.equals("Save")) {

				Cookie cookie = new Cookie("newUser", user.getUserName());
				cookie.setMaxAge(10);
				res.addCookie(cookie);
				return "redirect:/adminTools/ActiveUsers";
			}
			if (submitType.equals("Save and Exit")) {
				return "redirect:/";
			}
			break;
		}
		case 1: {
			Cookie cookie = new Cookie("newUser", "username");
			cookie.setMaxAge(10);
			res.addCookie(cookie);
			return "adminTools/toolAddUser";
		}

		case 2: {
			Cookie cookie = new Cookie("newUser", "email");
			cookie.setMaxAge(10);
			res.addCookie(cookie);
			return "adminTools/toolAddUser";
		}
		}
		return "redirect:/adminTools/AddUser";
	}

	/* * Spring-models * */
	
	/**
	 * Model attribute used in forms
	 *  - create empty object for Frontend forms
	 *  - read data from fields (in post actions) 
	 * 
	 * ModelAttribute - 'newUser'
	 * 
	 * @return empty user-object
	 */
	@ModelAttribute("newUser")
	public Users setUser() {

		return new Users();
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
