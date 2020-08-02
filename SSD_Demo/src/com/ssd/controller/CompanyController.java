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
import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.entity.Company;

/**
 * Controller to manage Projects. Method available for roles:
 * - Administrator
 * - Manager
 * 
 * Main methods:
 * 
 * - show active companies
 * - add new company
 * - edit companies (change name, disable, enable)
 * - view disabled projects (administrator only)
 * 
 * All 'view-files' in folder 'projects'.
 * All controllers divided into security areas
 * - admintools/* used only with role Administrator
 * - manage/* used only with role Administrator
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
@Controller
public class CompanyController {

	/**
	 * Method show form 'Add Company' for administrator.
	 * 
	 * @GetMapping - read get-action 'adminTools/AddCompany'
	 * 
	 * @param model - read Model-objects
	 * 
	 * @return - string localization of view available for Administrator
	 */
	@GetMapping("adminTools/AddCompany")
	public String showAddCompanyA(Model model) {
		return "/projects/addCompanyA";
	}

	/**
	 * Method show form 'Add Company' for Manager.
	 * 
	 * @GetMapping - read get-action 'manage/AddCompany'
	 * 
	 * @param model - read Model-objects
	 *  
	 * @return - string localization of view available for Manager
	 */
	@GetMapping("manage/AddCompany")
	public String showAddCompanyM(Model model) {
		return "/projects/addCompanyM";
	}

	/**
	 * Method save new Company (Project) from form in form 'Add Company' and
	 * return form 'Add Company' again - for Administrator
	 * 
	 * Flag 'information': '1' - information for DAO, that new name of Company has been
	 * added. '2' - name of Company has been updated (in other controller/form).
	 * 
	 * @PostMapping - read post-action 'adminTools/AddCompany'
	 * 
	 * @param project - new project (company name)
	 * @param model - read Model-objects 
	 * 
	 * @return - string localization of view available for Administrator
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping("adminTools/AddCompany")
	public String saveNewCompanyAdministrator(@ModelAttribute Company project, Model model, HttpServletResponse res)
			throws SecurityException, IOException {

		int information = 1;

		CompanyDAO pr = new CompanyDAOImpl();
		int flag = pr.saveNewCompany(project, information);
		
		switch (flag) {
		case 0: {
			Cookie cookie = new Cookie("company", "duplicated");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			break;
		}
		case 1: {
			Cookie cookie = new Cookie("company", "added");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/adminTools/ShowActiveCompanies";
		}
		}
		return "redirect:/adminTools/ShowActiveCompanies";
	}

	/**
	 * Method save new Company (Project) from formularz in form 'Add Company' and
	 * return form 'Add Company' again - for Manager
	 * 
	 * 'information': '1' - information for DAO, that new name of Company has been
	 * added. '2' - name of Company has been updated (in other controller/form).
	 * 
	 * @param project - new project (company name)
	 * @param model - read Model-objects 
	 * 
	 * @return - string localization of controller available for Manager
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping("manage/AddCompany")
	public String saveNewCompanyManager(@ModelAttribute Company project, Model model, HttpServletResponse res)
			throws SecurityException, IOException {

		int information = 1;

		CompanyDAO pr = new CompanyDAOImpl();
		int flag = pr.saveNewCompany(project, information);

		switch (flag) {
		case 0: {
			Cookie cookie = new Cookie("company", "duplicated");
			cookie.setMaxAge(5);

			res.addCookie(cookie);
			break;
		}

		case 1: {
			Cookie cookie = new Cookie("company", "added");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/manage/ShowActiveCompanies";
		}
		}
		return "redirect:/manage/ShowActiveCompanies";
	}

	/**
	 * Show only active companies form - Administrator
	 * 
	 * @GetMapping - read get-action 'adminTools/ShowActiveCompanies'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return string localization of view
	 */
	@GetMapping("adminTools/ShowActiveCompanies")
	public String showActiveCompaniesA(Model model) {
		return "/projects/showActiveCompaniesA";
	}

	/**
	 * Show only active companies form - Manager
	 * 
	 * @GetMapping - read get-action 'manage/ShowActiveCompanies'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return string localization of view with active companies
	 */
	@GetMapping("manage/ShowActiveCompanies")
	public String showActiveCompaniesM(Model model) {
		return "/projects/showActiveCompaniesM";
	}

	/**
	 * Show all companies form - only for Administrator
	 * 
	 * @GetMapping - read get-action 'adminTools/ShowDisabledCompanies'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return string localization of view with disabled companies
	 */
	@GetMapping("adminTools/ShowDisabledCompanies")
	public String showDisabledCompaniesA(Model model) {

		// init menu
		Menu_OptionsDAO menu = new Menu_OptionsDAOImpl();
		model.addAttribute(menu.addMenuAndLabels(model));

		return "/projects/showDisabledCompaniesA";
	}

	/**
	 * Show All Companies form - Administrator
	 * 
	 * @GetMapping - read get-action 'adminTools/ShowActiveCompanies'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return string localization of view
	 */
	@GetMapping("adminTools/ShowAllCompanies")
	public String showAllCompaniesA(Model model) {
		return "/projects/showAllCompaniesA";
	}

	/**
	 * Controller to edit information about company, used by role manager:
	 * - change name
	 * - disable
	 * - enable
	 * 
	 * Flags:
	 * 'buttonValue' - servlet read label to use correct method in DAO
	 * 'flag' - default vaule '5' (error-action), other values: 0 (company duplicated),2 (company changed), 3 (company disabled), 4 (company enabled) 
	 * 
	 * @PostMapping - read post-action 'manage/editCompanies'
	 * 
	 * @param company - object before update
	 * @param model - read Model-object
	 * @param req - servlet object, here to read label of used submit, new name of company
	 * @param res - servlet object - to add cookies
	 * 
	 * @return string: localization of view or redirected to controller
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("manage/editCompanies")
	public String manageCompaniesM(@ModelAttribute("companies") Company company, Model model, HttpServletRequest req,
			HttpServletResponse res) throws SecurityException, IOException {

		String buttonValue = req.getParameter("submit");
		int flag = 5;
		
		switch (buttonValue) {
		case "Save": {
			String newName = req.getParameter("newName");
			CompanyDAO comp = new CompanyDAOImpl();
			company = comp.getCompanyByName(company.getCompanyName());
			flag = comp.updadeCompany(company, newName, 2);
			break;
		}
		case "Disable": {
			CompanyDAO comp = new CompanyDAOImpl();
			company = comp.getCompanyById(company.getId());
			flag = comp.updadeCompany(company, null, 3);
			break;
		}
		case "Enable": {
			CompanyDAO comp = new CompanyDAOImpl();
			company = comp.getCompanyById(company.getId());
			flag = comp.updadeCompany(company, null, 4);
			break;
		}
		}

		switch (flag) {
		case 0: {
			Cookie cookie = new Cookie("company", "duplicated");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "/projects/showActiveCompaniesM";
		}
		case 2: {
			Cookie cookie = new Cookie("company", "changed");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/manage/ShowActiveCompanies";
		}
		case 3: {
			Cookie cookie = new Cookie("company", "disabled");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/manage/ShowActiveCompanies";
		}
		case 4: {
			Cookie cookie = new Cookie("company", "enabled");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/manage/ShowActiveCompanies";

		}
		default: {
			Cookie cookie = new Cookie("company", "error");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "/projects/showActiveCompaniesM";
		}
		}
	}

	/**
	 * Controller to edit information about company, used by role Administrator:
	 * - change name
	 * - disable
	 * - enable
	 *  
	 * Flags:
	 * 'buttonValue' - servlet read label to use correct method in DAO
	 * 'flag' - default vaule '5' (error-action), other values: 0 (company duplicated),2 (company changed), 3 (company disabled), 4 (company enabled) 
	 * 
	 * @PostMapping - read post-action 'adminTools/editCompanies'
	 * 
	 * @param company - object before update
	 * @param model - read Model-object
	 * @param req - servlet object, here to read label of used submit, new name of company
	 * @param res - servlet object - to add cookies
	 * 
	 * @return string: localization of view or redirected to controller
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("adminTools/editCompanies")
	public String manageCompaniesA(@ModelAttribute("companies") Company company, Model model, HttpServletRequest req,
			HttpServletResponse res) throws SecurityException, IOException {

		String buttonValue = req.getParameter("submit");

		int flag = 5;
		switch (buttonValue) {
		case "Save": {
			String newName = req.getParameter("newName");
			CompanyDAO comp = new CompanyDAOImpl();
			company = comp.getCompanyByName(company.getCompanyName());
			flag = comp.updadeCompany(company, newName, 2);
			break;
		}
		case "Disable": {
			CompanyDAO comp = new CompanyDAOImpl();
			company = comp.getCompanyById(company.getId());
			flag = comp.updadeCompany(company, null, 3);
			break;
		}
		case "Enable": {

			CompanyDAO comp = new CompanyDAOImpl();
			company = comp.getCompanyById(company.getId());
			flag = comp.updadeCompany(company, null, 4);
			break;
		}
		}
		
		switch (flag) {
		case 0: {

			Cookie cookie = new Cookie("company", "duplicated");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "/projects/showActiveCompaniesA";
		}
		case 2: {
			Cookie cookie = new Cookie("company", "changed");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/adminTools/ShowActiveCompanies";
		}
		case 3: {
			Cookie cookie = new Cookie("company", "disabled");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/adminTools/ShowActiveCompanies";
		}
		case 4: {
			Cookie cookie = new Cookie("company", "enabled");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/adminTools/ShowDisabledCompanies";

		}
		default: {
			Cookie cookie = new Cookie("company", "error");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "/projects/showActiveCompaniesA";
		}
		}
	}

	/**
	 * Model attribute for new Company.
	 * 
	 * ModelAttribute - 'newCompany'
	 * 
	 * @return empty Company-object
	 */
	@ModelAttribute("newCompany")
	public Company setProject() {
		return new Company();
	}

	/**
	 * Method generate model of all companies (object list).
	 * 
	 * ModelAttribute - 'companiesList'
	 * 
	 * @return Company Object-list 
	 */
	@ModelAttribute("companiesList")
	public List<Company> CompaniesList() {
		CompanyDAO comp = new CompanyDAOImpl();
		List<Company> companies = comp.getCompanies();
		return companies;
	}

	/**
	 * Method generate model only active companies (object list).
	 * 
	 * ModelAttribute - 'activeCompaniesList'
	 * 
	 * @return Company Object-list
	 */
	@ModelAttribute("activeCompaniesList")
	public List<Company> activeCompaniesList() {
		CompanyDAO comp = new CompanyDAOImpl();
		List<Company> companies = comp.getActiveCompanies();
		return companies;
	}

	/**
	 * Method generate model only disabled companies (object list).
	 * 
	 * ModelAttribute - 'disabledCompaniesList'
	 * 
	 * @return Company Object-list
	 */
	@ModelAttribute("disabledCompaniesList")
	public List<Company> disabledCompaniesList() {
		CompanyDAO comp = new CompanyDAOImpl();
		List<Company> companies = comp.getDisabledCompanies();
		return companies;
	}

	/**
	 * Method generate model of all companies name(string list).
	 * 
	 * ModelAttribute - 'companiesListStringName'
	 * 
	 * @return string list with names of companies
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
