package com.ssd.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.entity.Menu_Options;
import com.ssd.statics.Statics;

/**
 * Class with method to add/remove/update menu of application. 
 * 
 * Annotations:
 * GetMapping - used only to display site.
 * PostMapping - used to Post-method of forms.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * RequestMapping - mapping of admin area, all methods have added prefix from this annotation  
 * 
 * Mapping of "/adminTools" is area accesed only with role 'Administrator'.
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
@Controller
@RequestMapping("/adminTools")
public class AdminMenuController {


	/**
	 * Display site with forms to managing menu by Administrator 
	 * 
	 * @GetMapping - read get-action '/adminTools/extendMenu' and '/adminTools/deleteOption'
	 * 
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * 
	 * @return - localization and name of jsp file (Front-End) as string, without prefix and without suffix
	 */
	@GetMapping(value = { "/extendMenu", "/deleteOption" })
	public String AddMenuOption(Model model) {
		return "adminTools/extendMenu";
	}

	/**
	 * Method Delete option or sub-folder.
	 * Flag:
	 * If flag is true, sub-folder/option can be deleted.
	 * Only for sub-folder - if flag is false, sub-folder cannot be delete, because is not empty - still has assign option (one or more).   
	 * 
	 * @PostMapping - read post-action 'adminTools/deleteOption'
	 * 
	 * @param id - read option-id from form
	 * @param res - servlet response, here adding cookie  
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * 
	 * @return redirect to 'get' controller and display  
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping("/deleteOption")
	public String deleteOption(@RequestParam("id") Integer id, HttpServletResponse res, Model model)
			throws SecurityException, IOException {

		if (id == null) {
			return "adminTools/extendMenu";
		}
		Menu_OptionsDAO deleteItem = new Menu_OptionsDAOImpl();
		boolean flag = deleteItem.delete(id);

		if (flag == true) {
			// cookie for information
			Cookie cookie = new Cookie("delOptionCookie", "removed");
			cookie.setMaxAge(10);
			res.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("delOptionCookie", "check");
			cookie.setMaxAge(10);
			res.addCookie(cookie);
		}
		return "redirect:/adminTools/extendMenu";
	}

	/**
	 * Method adding new option/sub-folder or  to menu after choose Role and sub-folder.
	 * 
	 * @PostMapping read post-action 'adminTools/extendMenu'
	 * 
	 * @param menuOpt - read data of new option/sub-folder 
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * @param req - read request of servlet, here used to check, with option user has choose: 'Save' or 'Save and Exit'
	 * @param res - read response of servlet, here used to add cookie
	 * 
	 * @return - localization and name of jsp file (Front-End) or controller (redirect:/(...)) as string, without prefix and without suffix
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("/extendMenu")
	public String SaveOption(@ModelAttribute Menu_Options menuOpt, Model model, HttpServletRequest req,
			HttpServletResponse res) throws SecurityException, IOException {

		Menu_OptionsDAO newOption = new Menu_OptionsDAOImpl();
		menuOpt.setType(Statics.getMenuOption());

		System.out.println(" testowanie " + menuOpt.toString());
		Boolean flag = newOption.saveNewOption(menuOpt);

		if (flag == true) {

			String submitType = req.getParameter("submit");

			if (submitType.equals("Save")) {
				Cookie cookie = new Cookie("newOptionCookie", "newOptionAdded");
				cookie.setMaxAge(5);
				res.addCookie(cookie);

				return "redirect:/adminTools/extendMenu";
			}
			if (submitType.equals("Save and Exit")) {
				return "redirect:/";
			}

		} else {
			Cookie cookie = new Cookie("newOptionCookie", menuOpt.getMenuOptionName());
			Cookie cookie_sub = new Cookie("newOptionCookieSub", menuOpt.getSubMenu());
			cookie.setMaxAge(10);
			cookie_sub.setMaxAge(10);
			res.addCookie(cookie);
			res.addCookie(cookie_sub);
		}
		/********************************************************/
		return "adminTools/extendMenu";
	}

	/**
	 * Model - return map to shows all sub-folders by dedicated role
	 * 
	 * ModelAttribute - 'type'
	 * 
	 * @return map with type (sub-folder or option) and full name ('Option', 'Sub-folder').
	 */
	@ModelAttribute("type")
	public Map<String, String> getTypes() {
		Map<String, String> list = new HashMap<String, String>();
		list.put(Statics.getMenuOption(), "Option");
		list.put(Statics.getSubMenu(), "Sub-folder");
		return list;
	}

	/**
	 * Method return all role-names from 'menu' (topBar).
	 * 
	 * ModelAttribute - 'roleName'
	 * 
	 * @return List<String> with roles
	 */
	@ModelAttribute("roleName")
	public List<String> getRoles() {

		// get list of all options
		Menu_OptionsDAO menuManageTop = new Menu_OptionsDAOImpl();
		List<Menu_Options> temp = menuManageTop.showMenu();

		// temporary array
		List<String> rolesList = new ArrayList<String>();

		// menu - labels with type 'TOP' add to list
		for (int j = 0; j < temp.size(); j++) {
			if (temp.get(j).getType().equals(Statics.getMenuTop())) {
				Menu_Options list = temp.get(j);
				rolesList.add(list.getMenuOptionName());
			}
		}
		
		//System.out.println(rolesList);
		
		return rolesList;
	}

	/**
	 * Method return Options items - objects.
	 * 
	 * ModelAttribute - 'getAllMenuItems'
	 * 
	 * @return List<Menu_Options> with options
	 */
	@ModelAttribute("getAllMenuItems")
	public List<Menu_Options> getAllOptions() {

		Menu_OptionsDAO itemsMenu = new Menu_OptionsDAOImpl();
		List<Menu_Options> temp = itemsMenu.showMenu();
		List<Menu_Options> optionList = new ArrayList<Menu_Options>();

		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).getType().equals(Statics.getMenuOption())) {
				optionList.add(temp.get(i));
			}
		}
		return optionList;
	}

	/**
	 * Method return sub-folders - objects.
	 * 
	 * ModelAttribute - 'submenuName'
	 * 
	 * @return List<Menu_Options> with full objects - sub-folders
	 */
	@ModelAttribute("submenuName")
	public List<Menu_Options> getSubmenus() {

		Menu_OptionsDAO subMenus = new Menu_OptionsDAOImpl();
		List<Menu_Options> temp = subMenus.showMenu();
		List<Menu_Options> tempList = new ArrayList<Menu_Options>();

		for (int j = 0; j < temp.size(); j++) {
			Menu_Options tempOpt = temp.get(j);

			if (tempOpt.getType().equals(Statics.getSubMenu())) {
				tempList.add(tempOpt);
			}
		}
		/* Delete submenu 'TOP' - it used only for creating whole Menu. */
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).equals(Statics.getMenuTop())) {
				tempList.remove(i);
			}
		}
		return tempList;
	}

	/**
	 * Method return List only with names of sub-folders - string.
	 * 
	 * ModelAttribute - 'subMenu'
	 * 
	 * @return List<String> with name of sub-folders
	 */
	@ModelAttribute("subMenu")
	public List<String> getSubmenusString() {

		Menu_OptionsDAO subMenus = new Menu_OptionsDAOImpl();
		List<Menu_Options> temp = subMenus.showMenu();
		Set<String> tempList = new HashSet<>();
		List<String> submenusList = new ArrayList<String>();

		for (int j = 0; j < temp.size(); j++) {
			Menu_Options tempOpt = temp.get(j);
			if (tempOpt.getType().equals(Statics.getSubMenu()) || tempOpt.getType().equals(Statics.getMenuTop())) {
				tempList.add(tempOpt.getMenuOptionName());
			}
		}

		submenusList.addAll(tempList);

		for (int i = 0; i < submenusList.size(); i++) {
			if (submenusList.get(i).equals(Statics.getMenuTop())) {
				submenusList.remove(i);
			}
		}

		Collections.sort(submenusList);
		return submenusList;
	}

	/**
	 * Mehtod return empty-object of Menu_Options, used in form to add new option.
	 * 
	 * ModelAttribute - 'newOption'
	 * 
	 * @return empty object Menu_Options
	 */
	@ModelAttribute("newOption")
	public Menu_Options addOption() {
		return new Menu_Options();
	}

	/**
	 * Method display site with form to change choosen menu option-name.
	 * 
	 * @GetMapping read get-action 'adminTools/modifyMenuOption'
	 *
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * @return
	 */
	@GetMapping("/modifyMenuOption")
	public String modifyMenuOption(Model model) {
		return "/adminTools/modifyOption";
	}

	/**
	 * 
	 * Method save changed option name.
	 *  
	 * @PostMapping -  read post-action 'adminTools/modifyMenuOption'
	 *  
	 * @param menuOpt - original object menu-option 
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * @param req - read request of servlet, here used new name by attribute and button-label
	 * @param res - read response of servlet, here used to add cookie
	 * 
	 * 
	 * @return - localization of controller (redirect:/(...)) as string, without prefix and without suffix
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("/modifyMenuOption")
	public String saveModifyMenuOption(@ModelAttribute Menu_Options menuOpt, Model model, HttpServletRequest req,
			HttpServletResponse res) throws SecurityException, IOException {

		Menu_OptionsDAO newOption = new Menu_OptionsDAOImpl();
		String newName = req.getParameter("newName");
		menuOpt.setType(Statics.getMenuOption());
			
		Boolean flag = newOption.updateOption(menuOpt, newName);
				
		if (flag == true) {
			String submitType = req.getParameter("submit");
			if (submitType.equals("Save")) {

				Cookie cookie = new Cookie("updatedOptionCookie", "updated");
				cookie.setMaxAge(5);
				res.addCookie(cookie);
				return "redirect:/adminTools/modifyMenuOption";
			}
			if (submitType.equals("Save and Exit")) {
				return "redirect:/";
			}

		} else {
			Cookie cookie = new Cookie("updatedOptionCookie", "duplicated");
			cookie.setMaxAge(10);
			res.addCookie(cookie);
		}
		return "redirect:/adminTools/modifyMenuOption";
	}

	/**
	 * Add new subfolder to menu. View: adminTools/addMenuOption.jsp Cookie is
	 * created to load bookmark 'Add subfolder' after 'save' button, and return to
	 * the same bookmark. On the other case (use button 'Save and exit'), program
	 * redirect localization to home.jsp.
	 * 
	 * @PostMapping -  read post-action 'adminTools/extendSubfolder'
	 * 
	 * @param menuSubfolder - read original menu object from form 
	 * @param model - read Model-object, used in forms by Spring for display and use action in Front-end
	 * @param req - for get button-label
	 * @param res - for cookies
	 * 
	 * @return - redirect localization, depends of chosen submit
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping("/extendSubfolder")
	public String SaveAddedSubfolder(@ModelAttribute Menu_Options menuSubfolder, Model model, HttpServletRequest req,
			HttpServletResponse res) throws SecurityException, IOException {

		Menu_OptionsDAO newSubfolder = new Menu_OptionsDAOImpl();
		menuSubfolder.setType(Statics.getSubMenu());

		Boolean flag = newSubfolder.saveNewSubfolder(menuSubfolder);

		if (flag == true) {

			String submitType = req.getParameter("submit");
			if (submitType.equals("Save")) {

				Cookie cookie = new Cookie("subFolderBookMark", "submenuAdded");
				cookie.setMaxAge(10);
				res.addCookie(cookie);
				return "redirect:/adminTools/extendMenu";
			}
			if (submitType.equals("Save and Exit")) {
				return "redirect:/";
			}

		} else {
			Cookie cookie = new Cookie("subFolderBookMark", menuSubfolder.getMenuOptionName());
			cookie.setMaxAge(10);
			res.addCookie(cookie);
		}

		return "adminTools/extendMenu";
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