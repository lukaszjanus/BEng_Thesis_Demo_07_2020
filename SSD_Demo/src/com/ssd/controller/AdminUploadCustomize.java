package com.ssd.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssd.dao.CompanyDAO;
import com.ssd.dao.CompanyDAOImpl;
import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.Reports_SchemaDAO;
import com.ssd.dao.Reports_SchemaDAOImpl;
import com.ssd.entity.Company;
import com.ssd.entity.Reports_Schema;

/**
 * Controller-Class with methods to customize file uploading for role 'Administrator'.
 * 
 * Annotations:
 * GetMapping - used only to display site.
 * PostMapping - used to Post-method of forms.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * RequestMapping - mapping of Administrator area, all methods have added prefix from this annotation
 * ResponseBody - method called by ajax
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 13 lut 2020
 * 
 */
@Controller
@RequestMapping("/adminTools")
public class AdminUploadCustomize {

	/**
	 * View with functionality of customize file uploading
	 *
	 * @param model - standard Spring object
	 * @param res   - request servlet object, for read cookie information
	 * 
	 * @return localization of view as String
	 */
	@GetMapping("/UploadCustomize")
	public String reportsCustomizeGet(Model model, HttpServletRequest res) {
		
		model.addAttribute("operationResult", null);
		
		Cookie[] cookies = res.getCookies();
		for (Cookie cookie : cookies) {
			if ("schema".equals(cookie.getName()) && "updated".equals(cookie.getValue())) {
				model.addAttribute("operationResult", "Schema has been updated succesfully.");
			}
			if ("schema".equals(cookie.getName()) && "error".equals(cookie.getValue())) {
				model.addAttribute("operationResult", "Schema hasn't been updated - please contact to administrator.");
			}
			cookie.setValue(null);
			cookie.setMaxAge(0);
		}
		return "functions/UploadCustomizeA";
	}

	/**
	 * Save (update) schema.
	 *
	 * @param model   - read Spring Model-object
	 * @param company - company object selected in view
	 * @param schema  - schema object selected in view
	 * 
	 * @return String localization of View with forms add functions
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping("/UploadCustomize")
	public String reportsCustomizePost(HttpServletResponse res, Model model,
			@ModelAttribute("newCompany") Company company, @ModelAttribute("newSchema") Reports_Schema schema)
			throws SecurityException, IOException {

		int company_id = company.getId();
		schema.setCompany_id(company_id);
		Reports_SchemaDAO s = new Reports_SchemaDAOImpl();
		int flag = s.updateSchema(schema);
		
		switch (flag) {
		case 0: {
			Cookie cookie = new Cookie("schema", "updated");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			break;
		}
		case 1: {
			Cookie cookie = new Cookie("schema", "error");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
			return "redirect:/adminTools/UploadCustomize";
		}
		}
		return "redirect:/adminTools/UploadCustomize";
	}

	/* ajax controller */

	/**
	 * Show schema by given id - method for dynamic ajax. Schema show as html table.
	 *
	 * @param req - request servlet object
	 * @param res - response servlet object
	 * 
	 * @return result as string
	 */
	@RequestMapping(value = "showActualCustomizing", method = RequestMethod.GET)
	@ResponseBody
	public String showActualCustomizing(HttpServletRequest req, HttpServletResponse res) {

		int company_id = Integer.parseInt(req.getParameter("id"));

		Reports_SchemaDAO schema = new Reports_SchemaDAOImpl();
		Reports_Schema s = schema.getSchema(company_id);
		int type = s.getType();
		int closeDate = s.getCloseDate();
		int category = s.getCategory();
		int agent = s.getAgent();
		int registerTicket = s.getDate();
		String prefix = s.getPrefix();
		String isClosed = "All tickets";
		if (s.getIsClosed() == 1) {
			isClosed = "Only closed tickets";
		} else if (s.getIsClosed() == 0) {
			isClosed = "All tickets";
		}

		char splitter = s.getSplitter();

		return "<table class=\"universalTable\">"

				+ "<tr>" + "<td>Type:</td>" + "<td>" + type + "</td>" + "</tr>"

				+ "<tr>" + "<td>Close Date:</td>" + "<td>" + closeDate + "</td>" + "</tr>"

				+ "<tr>" + "<td>Category/Description:</td>" + "<td>" + category + "</td>" + "</tr>"

				+ "<tr>" + "<td>Agent:</td>" + "<td>" + agent + "</td>" + "</tr>"

				+ "<tr>" + "<td>Date of register ticket:</td>" + "<td>" + registerTicket + "</td>" + "</tr>"

				+ "<tr>" + "<td>Prefix of incident:</td>" + "<td>\"" + prefix + "\"</td>" + "</tr>"

				+ "<tr>" + "<td>Closing status:</td>" + "<td>" + isClosed + "</td>" + "</tr>"

				+ "<tr>" + "<td>CSV - splitter:</td>" + "<td>\"" + splitter + "\"</td>" + "</tr>"

				+ "</table>";
	}

	/* ** Models: ** */

	/**
	 * Model attribute for new Company schema of columns to upload.
	 * 
	 * ModelAttribute - 'newSchema'
	 * 
	 * @return empty Reports_Company_Schema -object
	 */
	@ModelAttribute("newSchema")
	public Reports_Schema setSchema() {
		return new Reports_Schema();
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