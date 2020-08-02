package com.ssd.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;

import com.ssd.logs.Logs;

/**
 * Main controller using after login - areas without restrictions.
 * 
 * Annotations: GetMapping - used only to display site. ModelAttribute - added
 * spring object (class Model) to Front-End Controller - class used to request
 * handling by Spring MVC (get and post) RequestMapping - mapping of admin area,
 * all methods have added prefix from this annotation
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020
 *
 */
@Controller
public class MainController {

	/**
	 * Method show home site with default mapping "/". If nobody is login, method
	 * redirect user to login site.
	 * 
	 * @param model - read Model-object
	 * 
	 * @return String name (path of view or redirect:controller_name)
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@GetMapping("/")
	public String showHome(Model model) throws SecurityException, IOException {
		if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			return "redirect:login";
		} else {

			/* create full home site with menu-model only after correct login */
			Menu_OptionsDAO menu = new Menu_OptionsDAOImpl();
			model.addAttribute(menu.addMenuAndLabels(model));

			return "home";
		}
	}

	/**
	 * Method run, when user try to go to area without required permissions. 'try'
	 * it is mean, that in address bar user 'Employeer' manualy type f.e. address of
	 * Administrator's controller
	 * 
	 * @GetMapping - read get-action 'acces-denied'
	 * 
	 * @return String path of view.
	 */
	@GetMapping("/acces-denied")
	public String showAccesDenied(Model model) {

		if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			return "redirect:login";
		} else {
			Menu_OptionsDAO menu = new Menu_OptionsDAOImpl();
			model.addAttribute(menu.addMenuAndLabels(model));
			return "acces-denied";
		}
	}

	/**
	 * Method 'get' for logout: - without this method logout can be only by 'form'
	 * method 'post'. - with this method it is possible to use link with method
	 * 'get'. Method also add to log-file information about logout of user.
	 * 
	 * @RequestMapping - read get-action '/logout' (the same as @GetMapping)
	 * 
	 * @param request  - servlet request method, used in Spring Security
	 *                 SecurityContextLogoutHandler()
	 * @param response - servlet response method, used in Spring Security
	 *                 SecurityContextLogoutHandler()
	 * 
	 * @return string controller name (default Spring Security logout)
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			Logs log = new Logs(
					SecurityContextHolder.getContext().getAuthentication().getName() + " has succesfully logged out.",
					0);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("test wylogowania ");
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
}