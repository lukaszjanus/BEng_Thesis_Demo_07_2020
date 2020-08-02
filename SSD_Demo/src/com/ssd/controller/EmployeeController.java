package com.ssd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.views.User_Functions_String_UserNameDAO;
import com.ssd.dao.views.User_Functions_String_UserNameDAOImpl;
import com.ssd.entity.viewsDB.User_Functions_String_UserName;

/**
 * Controller with method accessed only by role 'Employee'.
 * 
 * Annotations: GetMapping - used only to display site. PostMapping - used to
 * Post-method of forms. ModelAttribute - added spring object (class Model) to
 * Front-End Controller - class used to request handling by Spring MVC (get and
 * post) RequestMapping - mapping of area, all methods have added prefix from
 * this annotation
 * 
 * Mapping of "/employee" is area accessed only with role 'Employee'.
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	/**
	 * Flag to inform @GetMapping("/Information") that user has (or has not) available any rools.
	 * Value is added in Model "showCompanies"
	 */
	boolean flagNoFunction;

	/**
	 * Open test View of Employers site
	 * 
	 * @GetMapping - read get-action '/employee/Information'
	 * 
	 * @param model	- object Spring
	 * @param res - Servlet Response method, to add cookie
	 * 
	 * @return String path of view
	 */
	@GetMapping("/Information")
	public String reports(Model model, HttpServletResponse res) {
			
		if(flagNoFunction==true) {
			Cookie cookie = new Cookie("isUserHaveFunctions", "isUserHaveFunctions");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
		}	
		return "employee/information";
	}

	/**
	 * Model for generate available functions list of current user.
	 * If list is empty, method setObjectWithEmptyFunctions() add temporary object
	 * with field informing, that user has not any functions.
	 *
	 * @param model add object-list
	 * 
	 * @return model string-list
	 */
	@ModelAttribute("listFunctions")
	public Model showFunctions(Model model) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		User_Functions_String_UserNameDAO user = new User_Functions_String_UserNameDAOImpl();

		List<User_Functions_String_UserName> listFunctions = new ArrayList<User_Functions_String_UserName>();
		listFunctions = user.getFunctions(name);
		
		flagNoFunction=false;
		/* if user has not this role, add field 'empty' */
		if (listFunctions.size() < 1) {
			flagNoFunction=true;
		}
		model.addAttribute("listFunctions", listFunctions);
		return model;
	}

	/**
	 * Method created in DAO and generated structure of whole Menu: Top-bar,
	 * Sub-folders, Options. Object is added to Front-End.
	 * 
	 * @param model - default spring method, here menu is added to all jsps
	 *              (Front-End)
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
