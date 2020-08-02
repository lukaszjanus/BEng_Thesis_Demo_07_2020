package com.ssd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;

/**
 * General Controller Class for role Manager.
 *  
 * Annotations:
 * GetMapping - used only to display site.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * RequestMapping - mapping of path, all methods have added prefix from this annotation
 * 
 * Mapping of "/manage" is area accessed only with role 'Manager'.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 */
@Controller
@RequestMapping("/manage")
public class ManagerController {

	/**
	 * Display test Site - only for Managers.
	 *
	 * @param model - read Model-object
	 * @return localization of view
	 */
	@GetMapping("/managers")
	public String showLeaders(Model model) {
		return "manage/information_site";
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