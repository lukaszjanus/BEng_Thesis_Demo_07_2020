package com.ssd.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ssd.dao.CompanyDAO;
import com.ssd.dao.CompanyDAOImpl;
import com.ssd.dao.FunctionsDAO;
import com.ssd.dao.FunctionsDAOImpl;
import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.User_FunctionsDAO;
import com.ssd.dao.User_FunctionsDAOImpl;
import com.ssd.dao.UsersDAO;
import com.ssd.dao.UsersDAOImpl;
import com.ssd.entity.Company;
import com.ssd.entity.Functions;
import com.ssd.entity.Users;
import com.ssd.entity.containers.User_Function_String_Contener;

/**
 * Class with controllers and models for management functions dedicated to role 'Manager'.
 * 
 * Annotations:
 * GetMapping - used only to display site.
 * PostMapping - used to Post-method of forms.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * RequestMapping - mapping of Administrator area, all methods have added prefix from this annotation  
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
@Controller
public class ManagerFunctionsController {

	/**
	 * Method show View with form 'Add Company'.
	 * 
	 * @GetMapping - read get-action 'manage/FunctionList'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return String localization of View with forms add functions 
	 */
	@GetMapping("manage/FunctionList")
	public String showAddFunctionA(Model model, HttpServletRequest res) {
			
		model.addAttribute("operationResult", null);
		Cookie[] cookies = res.getCookies();
		for (Cookie cookie : cookies) {
			if ("added".equals(cookie.getName())) {
			
				model.addAttribute("operationResult", "New function has been added succesfully.");
			}
			if ("duplicated".equals(cookie.getName())) {

				model.addAttribute("operationResult", "Function already exists!\nYou must choose different name.");
			}
			
			if ("descriptionUpdate".equals(cookie.getName())) {

				model.addAttribute("operationResult", "Description has been updated succesfully.");
			}

			if ("disabledFunction".equals(cookie.getName())) {

				model.addAttribute("operationResult", "Function has been disabled.");
			}
			
			if ("enabledFunction".equals(cookie.getName())) {

				model.addAttribute("operationResult", "Function has been enabled.");
			}
			
			cookie.setValue(null);
			cookie.setMaxAge(0);
		}
			
		return "/functions/addFunctionM";
	}
	
	/**
	 * Method save new Function from form.
	 * 
	 * Flags:
	 * - information - type of update object Function used in DAO-Method, here mean 'save new'
	 * - flag - responce from FunctionsDAO generated cookie: 0 - name duplicated, 1 - function added. 
	 * 
	 * @PostMapping -  read post-action 'manage/FunctionList'
	 * 
	 * @param project (company name) object initialized in form 
	 * @param model - read Model-object
	 * 
	 * @return String localization of View with forms add functions
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping("manage/FunctionList")
	public String saveNewFunctionA(@ModelAttribute Functions function, Model model, HttpServletResponse res)
			throws SecurityException, IOException {

		int information = 1;
		FunctionsDAO func = new FunctionsDAOImpl();
		int flag = func.saveNewFunction(function, information);

		switch (flag) {
		case 0: {
			Cookie cookie = new Cookie("duplicated", "duplicated");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			//return "/functions/addFunctionM";
			return "redirect:/manage/FunctionList";
		}
		case 1: {
			Cookie cookie = new Cookie("added", "added");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/manage/FunctionList";
		}
		default: {
			return "redirect:/manage/FunctionList";
		}
		}
	}
	
	/**
	 * Update description of function.
	 * 
	 * @PostMapping -  read post-action 'manage/editFunctionDescription'
	 * 
	 * @param function - read function to update
	 * @param model - read Model-object
	 * @param req - servlet request method, get new description
	 * @param res - add cookie after update
	 * 
	 * @return String localization of View with forms add functions
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("manage/editFunctionDescription")
	public String saveNewDescription(@ModelAttribute Functions function, Model model, HttpServletRequest req,
			HttpServletResponse res) throws SecurityException, IOException {

		String newDescription = req.getParameter("newDescription"); // z formularza

		FunctionsDAO func = new FunctionsDAOImpl();
		function = func.getFunctionByName(function.getFunctionName());
		System.out.println(newDescription + " " + function.toString());

		func.updateFunctionData(function, newDescription);

		Cookie cookie = new Cookie("descriptionUpdate", "descriptionUpdate");
		cookie.setMaxAge(5);

		res.addCookie(cookie);
		return "redirect:/manage/FunctionList";
	}

	/**
	 * Disable active function.
	 * 
	 * @PostMapping -  read post-action 'manage/disableFunction'
	 * 
	 * @param function - read function to disable
	 * @param model - read Model-object
	 * @param res - add cookie after update
	 * 
	 * @return String localization of View with forms add functions
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("manage/disableFunction")
	public String disableFunction(@ModelAttribute Functions function, Model model,
			HttpServletResponse res) throws SecurityException, IOException {

		FunctionsDAO func = new FunctionsDAOImpl();
		function = func.getFunctionsById(function.getId());
		func.updateFunctionData(function, "disable");

		Cookie cookie = new Cookie("disabledFunction", "disabledFunction");
		cookie.setMaxAge(5);

		res.addCookie(cookie);

		return "redirect:/manage/FunctionList";
	}

	/**
	 * Enable disabled function.
	 * 
	 * @PostMapping -  read post-action 'manage/enableFunction'
	 * 
	 * @param function - object-function to enable
	 * @param model - read Model-object
	 * @param res - add cookie after update
	 * 
	 * @return String localization of View with forms add functions
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("manage/enableFunction")
	public String enableFunction(@ModelAttribute Functions function, Model model, HttpServletResponse res) throws SecurityException, IOException {

		FunctionsDAO func = new FunctionsDAOImpl();
		function = func.getFunctionsById(function.getId());
		func.updateFunctionData(function, "enable");

		Cookie cookie = new Cookie("enabledFunction", "enabledFunction");
		cookie.setMaxAge(5);

		res.addCookie(cookie);
		return "redirect:/manage/FunctionList";
	}

	/**
	 * Method generate model only active functions (object list).
	 * 
	 * ModelAttribute - 'activeFunctionList'
	 * 
	 * @return object-list of Functions
	 */
	@ModelAttribute("activeFunctionList")
	public List<Functions> activeFunctionList() {
		FunctionsDAO func = new FunctionsDAOImpl();
		List<Functions> functions = func.getActiveFunctions();
		return functions;
	}

	/**
	 * Method generate model of all functions (object list).
	 * 
	 * ModelAttribute - 'functionList'
	 * 
	 * @return object-list of Functions
	 */
	@ModelAttribute("functionList")
	public List<Functions> FunctionsList() {
		FunctionsDAO func = new FunctionsDAOImpl();
		List<Functions> function = func.getFunctions();

		return function;
	}

	/**
	 * Method generate model only disabled functions (object list).
	 * 
	 * ModelAttribute - 'disabledCompaniesList'
	 * 
	 * @return object-list of Functions
	 */
	@ModelAttribute("disabledCompaniesList")
	public List<Functions> disabledFunctionsList() {
		FunctionsDAO func = new FunctionsDAOImpl();
		List<Functions> functions = func.getDisabledFunctions();
		return functions;
	}

	/**
	 * Method generate model of all functions name(string list).
	 * 
	 * ModelAttribute - 'functionsListStringName
	 * 
	 * @return String list with Functions-names 
	 */
	@ModelAttribute("functionsListStringName")
	public List<String> FunctionsStringList() {

		FunctionsDAO comp = new FunctionsDAOImpl();
		List<Functions> function = comp.getActiveFunctions();
		List<String> funcString = new ArrayList<String>();

		for (int i = 0; i < function.size(); i++) {
			String temp = function.get(i).getFunctionName();
			funcString.add(temp);
		}
		return funcString;
	}
	
	
	/**
	 * Method generate model (string list) of user_functions.
	 * Auxiliary class User_Function_String_Contener is used to translate relational Foreign Keys to string-names.
	 * 
	 * ModelAttribute - 'userFunctionsString'
	 * 
	 * @return list of objects: User_Function_String_Contener
	 */
	@ModelAttribute("userFunctionsString")
	public List<User_Function_String_Contener> UserFunctionsList() {
		User_FunctionsDAO func = new User_FunctionsDAOImpl();
		List<User_Function_String_Contener> function = func.getUserFunctionsString();

		return function;
	}
	
	
	/**
	 * Method generate model with list of all companies name(string list).
	 * 
	 * ModelAttribute - 'companiesListStringName'
	 * 
	 * @return String list of Companies names
	 */
	@ModelAttribute("companiesListStringName")
	public List<String> CompaniesStringList() {

		CompanyDAO comp = new CompanyDAOImpl();
		List<Company> companies = comp.getActiveCompanies();
		List<String> compString = new ArrayList();

		for (int i = 0; i < companies.size(); i++) {
			String temp = companies.get(i).getCompanyName();
			compString.add(temp);
		}
		return compString;
	}
	
	/**
	 * Model show active users list from db.
	 * 
	 * ModelAttribute - 'activeUsersList'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return active users list (as Users-object) 
	 */
	@ModelAttribute("activeUsersList")
	public List<Users> showActiveUsers(Model model) {

		UsersDAO usersDAO = new UsersDAOImpl();

		List<Users> usersList = usersDAO.getActiveUsers();

		return usersList;
	}
	
	/**
	 * Model show active userNames list from db - String-list.
	 * 
	 * ModelAttribute - 'activeUsersListString'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return List String with userNames
	 */
	@ModelAttribute("activeUsersListString")
	public List<String> showActiveUsersString(Model model) {

		UsersDAO usersDAO = new UsersDAOImpl();
		List<Users> usersList = usersDAO.getActiveUsers();
		List<String> list = new ArrayList<String>();
		
		for(int i=0;i<usersList.size();i++) {
			list.add(usersList.get(i).getUserName());
		}

		return list;
	}
	
	/**
	 * Model attribute for new Function.
	 * 
	 * ModelAttribute - 'newFunction'
	 * 
	 * @return empty Functions-object
	 */
	@ModelAttribute("newFunction")
	public Functions setFunction() {
		return new Functions();
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