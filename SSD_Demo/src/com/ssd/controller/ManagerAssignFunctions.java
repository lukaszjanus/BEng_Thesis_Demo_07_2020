package com.ssd.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.ssd.entity.User_Functions;
import com.ssd.entity.Users;
import com.ssd.entity.containers.User_Function_String_Contener;

/**
 * Class with controllers and models for management functions dedicated to role
 * 'Manager'.
 * 
 * 
 * Annotations: GetMapping - used only to display site. PostMapping - used to
 * Post-method of forms. ModelAttribute - added spring object (class Model) to
 * Front-End Controller - class used to request handling by Spring MVC (get and
 * post) RequestMapping - mapping of Administrator area, all methods have added
 * prefix from this annotation
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020
 *
 */
@Controller
@RequestMapping("/manage")
public class ManagerAssignFunctions {

	/**
	 * Method display View to manage functions to users.
	 * 
	 * @GetMapping - read get-action 'manage/ManageFunctions'
	 * 
	 * @param model - read Model-object
	 * @param res   - request servlet object, for read cookie information
	 * 
	 * @return String localization of View with forms add functions
	 */
	@GetMapping("/ManageFunctions")
	public String showManageFunctions(Model model, HttpServletRequest res) {

		model.addAttribute("operationResult", null);
		Cookie[] cookies = res.getCookies();
		for (Cookie cookie : cookies) {
			if ("removedFunction".equals(cookie.getName())) {
				model.addAttribute("operationResult", "Function has been removed!");
			}
			if ("addedFunction".equals(cookie.getName())) {
				model.addAttribute("operationResult", "New Function has been assigned to user!");
			}
			if ("duplicateOne".equals(cookie.getName())) {
				model.addAttribute("operationResult", "Choosen function for this user has been added earlier.");
			}
			if ("duplicateMoreOne".equals(cookie.getName())) {
				model.addAttribute("operationResult", "One or more users have this function added earlier.");
			}
		
			cookie.setValue(null);
			cookie.setMaxAge(0);
		}
		return "/functions/manageFunctionsM";
	}

	/**
	 * Method assign to users function and company. Users can be one or more. Form
	 * save users in format: 'user1,user2,user3,(...)'. Conversion to object-list is
	 * in method 'convertStringObjectToListId(object)' To see more specific, see
	 * documentation of @see {@link User_FunctionsDAO}
	 *
	 * 
	 * 
	 * @param res          - servlet Response object, here used for cookie
	 * @param userFunction - temporary object
	 * 
	 * @param model        - add object-model to view
	 * 
	 * @return String localization (here 'redirect' to controller get)
	 */
	@PostMapping("/AssignFunctionM")
	public String AssignFunctionToUserCompany(HttpServletResponse res,
			@ModelAttribute("User_Function_String_Contener") User_Function_String_Contener userFunction, Model model) {

		User_FunctionsDAO userFunctionDao = new User_FunctionsDAOImpl();
		List<User_Functions> list = userFunctionDao.convertStringObjectToListId(userFunction);

		int flag = userFunctionDao.addRelationUser_Function(list);

		if (flag == 1) {
			Cookie cookie = new Cookie("addedFunction", "true");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
		}
		
		if (flag == 2) {

			Cookie cookie = new Cookie("duplicateOne", "duplicateOne");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
		}
		
		if (flag == 3) {

			Cookie cookie = new Cookie("duplicateMoreOne", "duplicateMoreOne");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
		}

		return "redirect:/manage/ManageFunctions";
	}

	/**
	 * Method remove assigning (user+company+function) from db.
	 * 
	 *
	 * @param res          servlet response for add cookie
	 * @param id           as hidden parameter
	 * @param userName     string from form (to log)
	 * @param companyName  string from form (to log)
	 * @param functionName string from form (to log)
	 * @param model        - read Model-object
	 * 
	 * @return String localization (here 'redirect' to controller get)
	 *
	 */
	@PostMapping("/RemoveFunctionM")
	public String removeFunctionA(HttpServletResponse res, @RequestParam("id") int id,
			@RequestParam("userName") String userName, @RequestParam("companyName") String companyName,
			@RequestParam("functionName") String functionName, Model model) {

		List<String> list = new ArrayList<String>();
		list.add(userName);
		list.add(companyName);
		list.add(functionName);
		User_FunctionsDAO userFunctionDao = new User_FunctionsDAOImpl();
		userFunctionDao.removeRelation(id, list);

		Cookie cookie = new Cookie("removedFunction", "true");
		cookie.setMaxAge(5);
		res.addCookie(cookie);

		return "redirect:/manage/ManageFunctions";
	}

	/* * Spring-Models * */

	/**
	 * Model attribute for class User_Functions but in string-data.
	 * 
	 * ModelAttribute - 'User_Function_String_Contener'
	 * 
	 * @return empty User_Function_String_Contener-object
	 *
	 */
	@ModelAttribute("User_Function_String_Contener")
	public User_Function_String_Contener setAssignFunction() {

		return new User_Function_String_Contener();
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
	 * ModelAttribute - 'disabledFunctionsList'
	 * 
	 * @return object-list of Functions
	 */
	@ModelAttribute("disabledFunctionsList")
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
	 * Method generate model (string list) of user_functions. Auxiliary class
	 * User_Function_String_Contener is used to translate relational Foreign Keys to
	 * string-names.
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
		List<String> compString = new ArrayList<String>();

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
	 * 
	 * @return List String with userNames
	 */
	@ModelAttribute("activeUsersListString")
	public List<String> showActiveUsersString() {

		UsersDAO usersDAO = new UsersDAOImpl();
		List<Users> usersList = usersDAO.getActiveUsers();
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < usersList.size(); i++) {
			list.add(usersList.get(i).getUserName());
		}

		return list;
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