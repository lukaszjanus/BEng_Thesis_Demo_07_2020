/**
 * 
 */
package com.ssd.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.Reports_SDDAO;
import com.ssd.dao.Reports_SDDAOImpl;
import com.ssd.dao.Reports_SchemaDAO;
import com.ssd.dao.Reports_SchemaDAOImpl;
import com.ssd.dao.views.Reports_SD_UploadedDAO;
import com.ssd.dao.views.Reports_SD_UploadedDAOImpl;
import com.ssd.dao.views.User_Functions_String_UserNameDAO;
import com.ssd.dao.views.User_Functions_String_UserNameDAOImpl;
import com.ssd.entity.viewsDB.Reports_SD_Uploaded;
import com.ssd.entity.viewsDB.User_Functions_String_UserName;
import com.ssd.logs.Logs;

/**
 *
 * Class to read reports from file.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 30 sty 2020
 * 
 */
@Controller
@RequestMapping("/employee")
public class EmployeeReportsController {

	/**
	 * Flag to inform @GetMapping("/emplyeeReports") that user has not added role
	 * "Add_Report_Employee". Value is added in Model "showCompanies"
	 */
	boolean flagNoFunctionAdd;

	/**
	 * Flag to inform @GetMapping("/emplyeeReports") that user has not added role
	 * "Show_Added_Reports_Employee". Value is added in Model ""
	 */
	boolean flagNoFunctionView;

	/**
	 * Open test View of Employers credentials.
	 * 
	 * Cookie to inform gui, that user have (or no have) functions.
	 * 
	 * @GetMapping - read get-action '/employee/emplyeeReports'
	 * 
	 * @param model - object Spring
	 * @param res-  Servlet Response method, to add cookie
	 * 
	 * @return String path of view
	 */
	@GetMapping("/emplyeeReports")
	public String reports(Model model, HttpServletResponse res) {

		/**
		 * check function 'Add_Report_Employee'
		 */
		if (flagNoFunctionAdd == true) {
			Cookie cookie = new Cookie("emptyAddReports", "emptyAddReports");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
		}

		/**
		 * check function 'Show_Added_Reports_Employee'
		 */
		if (flagNoFunctionView == true) {
			Cookie cookie = new Cookie("flagNoFunctionView", "flagNoFunctionView");
			cookie.setMaxAge(5);
			res.addCookie(cookie);
		}

		return "employee/reports";
	}

	/**
	 * Read file added by user.
	 *
	 * @param model
	 * @param file
	 * @return
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/reportUpload", method = { RequestMethod.POST, RequestMethod.PUT })
	public String fileUpload(Model model, @RequestParam CommonsMultipartFile file, HttpServletRequest req)
			throws IOException {

		String result = null;

		/**
		 * protection against add report without available role.
		 */

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<User_Functions_String_UserName> listCompanies = new ArrayList<User_Functions_String_UserName>();
		User_Functions_String_UserNameDAO user = new User_Functions_String_UserNameDAOImpl();
		listCompanies = user.getCompanies(userName);

		if (listCompanies.size() < 1) {
			result = " Acces Denied without role!";
			model.addAttribute("operationResult", result);
			return "employee/reports";
		}

		/**
		 * End of protection against add report without available role.
		 */

		String company_id_temp = req.getParameter("companyReported");
		int company_id = Integer.valueOf(company_id_temp);

		String period = req.getParameter("period");
		String month = period.substring(0, 2);
		String year = period.substring(3, 7);

		Reports_SDDAO reports = new Reports_SDDAOImpl();
		/**
		 * Check period and company -> protection against add report 'again'
		 */
		boolean flag = reports.preventAddReportAgain(company_id, month, year);
		if (flag == true) {
			result = "Cannot upload report for " + period + " - data are available in base.";
			Logs log = new Logs(
					SecurityContextHolder.getContext().getAuthentication().getName() + "try to add report of " + period
							+ " for company_id: " + company_id + ", but report with this period is available in db.",
					1);

			model.addAttribute("operationResult", result);
			return "employee/reports";
		}

		/**
		 * End check period and company.
		 */

		String fileName = file.getOriginalFilename();
		int lenghtName = fileName.length();
		String fileExtension = fileName.substring(lenghtName - 4, lenghtName); // recognize by name what file is

		switch (fileExtension) {
		case ".csv": {

			BufferedReader br;
			List<String> list = new ArrayList<String>();
			try {

				String line;
				InputStream is = file.getInputStream();
				br = new BufferedReader(new InputStreamReader(is));
				// br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
				// "UTF8"));
				while ((line = br.readLine()) != null) {
					list.add(line);
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}

			list.remove(0); // remove headers
			reports.uploadReportCSV(userName, company_id, month, year, list);
			result = "Report has been succesfully imported to db from file *.csv!";

			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully uploaded report of " + period + " for company_id: " + company_id + " from *.csv.",
					0);
			break;

		}
		case ".xls": {
			// System.out.println(" excel xsl");

			Reports_SchemaDAO d = new Reports_SchemaDAOImpl();
			char delimiter = d.getSchema(company_id).getSplitter();
			result = "Please save file as *.xlsx or export file to *.csv with delimiter for this company: \""
					+ delimiter + "\"";

			break;
		}
		case "xlsx": {
			// System.out.println(" excel xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			reports.uploadReportXLSX(userName, company_id, month, year, workbook);
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully uploaded report of " + period + " for company_id: " + company_id + " from *.xlsx.",
					0);
			result = "Report has been succesfully imported to db from file *.xlsx!";
			break;
		}
		default: {
			result = "File not added - wrong format or other error.\nPlease Contact to Administrator.";
			break;
		}
		}
		model.addAttribute("operationResult", result);
		return "employee/reports";
	}

	/****************** MODELS **********************/

	/**
	 * Model for generate Companies list with function 'Add_Report_Employee'.
	 * 
	 * If user has not functions, list return one object with companyName 'empty'.
	 * This is information to gui do not show form /add.
	 *
	 * @param model add object-list
	 * 
	 * @return model
	 */
	@ModelAttribute
	public Model showCompanies(Model model) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		List<User_Functions_String_UserName> listCompanies = new ArrayList<User_Functions_String_UserName>();

		User_Functions_String_UserNameDAO user = new User_Functions_String_UserNameDAOImpl();

		listCompanies = user.getCompanies(name);

		flagNoFunctionAdd = false;
		/* check user roles */
		if (listCompanies.size() < 1) {
			flagNoFunctionAdd = true;
		}

		model.addAttribute("listCompanies", listCompanies);

		return model;
	}

	/**
	 * Model for generate reports list uploaded by user (function
	 * 'Show_Added_Reports_Employee'). In list will be uploaded reports by other
	 * user, if company of current user are available in function
	 * 'Show_Added_Reports_Employee'.
	 * 
	 * If user has not functions, list return one object with companyName 'empty'.
	 * This is information to gui do not show form /add.
	 *
	 * @param model add object-list
	 * 
	 * @return model
	 */
	@ModelAttribute("reportsUploadedByUser")
	public List<Reports_SD_Uploaded> showUploadedReports(Model model) {

		// get name
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		// get added reports
		User_Functions_String_UserNameDAO function = new User_Functions_String_UserNameDAOImpl();
		List<User_Functions_String_UserName> functionList = function.getFunctionsShow(userName);

		List<Reports_SD_Uploaded> listCompanies = new ArrayList<Reports_SD_Uploaded>();

		flagNoFunctionView = false;

		/* check user roles */
		if (functionList.size() < 1) {
			flagNoFunctionView = true;
		} else {
			Reports_SD_UploadedDAO reports = new Reports_SD_UploadedDAOImpl();
			listCompanies = reports.showUploadedReports(functionList);
			for (int i = 0; i < listCompanies.size(); i++) {
				listCompanies.get(i).setId(i + 1);
			}
		}
		return listCompanies;
	}

	/**
	 * Model to show available last three month to add report to db.
	 *
	 * @param model - object of Spring
	 * 
	 * @return String list with period in format: mm-yyyy
	 */
	@ModelAttribute
	public Model actualPeriod(Model model) {

		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < 3; i++) {
			int monthTemp = month - i;
			int yearTemp = year;
			if (monthTemp < 1) {
				monthTemp += 12;
				yearTemp -= 1;
			}

			String dateForm;
			if (monthTemp < 10) {
				dateForm = "0" + String.valueOf(monthTemp) + "-" + String.valueOf(yearTemp);
				list.add(String.valueOf(dateForm));
			} else {
				dateForm = String.valueOf(monthTemp) + "-" + String.valueOf(yearTemp);
				list.add(String.valueOf(dateForm));
			}
		}

		model.addAttribute("period", list);
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