package com.ssd.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.containers.StandardReportContainerDAO;
import com.ssd.dao.containers.StandardReportContainerDAOImpl;
import com.ssd.dao.views.Reports_CountDAO;
import com.ssd.dao.views.Reports_CountDAOImpl;
import com.ssd.dao.views.Reports_PreviewDAO;
import com.ssd.dao.views.Reports_PreviewDAOImpl;
import com.ssd.entity.containers.StandardReportContainer;
import com.ssd.tools.XLSXExport;

/**
 * Controller-Class for manage reports by Administrator. Functionality:
 * 
 * - show uploaded reports (with possibility to chose criteria) - export data to
 * xlsx
 * 
 * Annotations: GetMapping - used only to display site. PostMapping - used to
 * Post-method of forms. ModelAttribute - added spring object (class Model) to
 * Front-End Controller - class used to request handling by Spring MVC (get and
 * post) RequestMapping - mapping of path, all methods have added prefix from
 * this annotation
 * ResponseBody - method called by ajax.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 24 lut 2020
 *
 */
@Controller
@RequestMapping("/adminTools")
public class AdminReportsController {

	int monthCount;

	static List<StandardReportContainer> listToExcel0;
	static List<StandardReportContainer> listToExcelFull1 = new ArrayList<StandardReportContainer>();
	static List<StandardReportContainer> listToExcelFull2 = new ArrayList<StandardReportContainer>();;
	static List<StandardReportContainer> listToExcelFull3 = new ArrayList<StandardReportContainer>();
	static List<?> listToExcel3;

	/**
	 * Show view 'uploaded reports'.s
	 *
	 * @param model - read Spring Model-object
	 * @return localization of view
	 */
	@GetMapping("/UploadedReports")
	public String showUploadedReports(Model model, HttpServletResponse res, HttpServletRequest req) {
		return "adminTools/reports/UploadedReports";
	}

	//////////// Ajax/////////

	/*************** Universal tabs methods ***************************/
	/**
	 * All ticket counts for selected companies.
	 *
	 * @param req - Http Servlet Request method to get parameters
	 * 
	 * @return number as string
	 */
	@RequestMapping(value = "allTicketsCount", method = RequestMethod.GET)
	@ResponseBody
	public String getAllTicketsCount(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);
		String companies = req.getParameter("company0");

		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		String ticketCounts = report.getCount(monthCount, companies);
		return ticketCounts;
	}

	/************************** Tab 0 methods *************************/

	/**
	 * Ajax controller for tab0 - method upload standard report, data in columns: 1.
	 * Current month. 2. Last Month. 3. Two Months ago. 4. Sum of tickets for
	 * Quarter before data from column in 1-3. 6. Sum of tickets for Year before
	 * data from column 1-4.
	 * 
	 * Companies: all active and also unactive if is in report from this period.
	 *
	 * @param req - Http servlet request for get data from view
	 * 
	 * @return string with data as jsp table
	 */
	@RequestMapping(value = "uploadReporsCountTab0", method = RequestMethod.GET)
	@ResponseBody
	public String uploadReporsCountTab0(HttpServletRequest req) {

		String selectedCompany = req.getParameter("company0");
		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod)-18;
		int month = Integer.parseInt(tempPeriod)-18;

		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		List<StandardReportContainer> list = report.getCompanyFromReports(selectedCompany);
		list = report.getStandardCounts(list, month);
		
		String month0 = formatMonthString(month);
		String month1 = formatMonthString(month + 1);
		String month2 = formatMonthString(month + 2);

		listToExcel0 = new ArrayList<StandardReportContainer>();
		listToExcel0.add(new StandardReportContainer("id", "Company", month0, month1, month2, "Quarter", "Year"));
		for (int i = 0; i < list.size(); i++) {
			listToExcel0.add(list.get(i));
		}

		String result = "<table border=1px class=\"universalTableWide\"><tr><td>Id:</td><td>Company:</td><td>" + month0
				+ "</td><td>" + month1 + "</td><td>" + month2
				+ "</td><td>Quarter before:</td><td>Year before:</td></tr>";
		for (int i = 0; i < list.size(); i++) {
			StandardReportContainer tempObj = list.get(i);
			String temp = "<tr><td>" + tempObj.getId() + "</td><td>" + tempObj.getCompanyName() + "</td><td>"
					+ tempObj.getMonth_0() + "</td><td>" + tempObj.getMonth_1() + "</td><td>" + tempObj.getMonth_2()
					+ "</td><td>" + tempObj.getQuarter() + "</td><td>" + tempObj.getYear() + "</td></tr>";
			result = result + temp;
		}
		result = result + "</table>";

		return result;
	}

	/**
	 * Method to format month get from LocalDate.now() 'MONTH' to 'Month:', f.e.:
	 * 'JANUARY' -> 'January:'
	 *
	 * @param month as String
	 * @return month as String formatted
	 */
	private String formatMonthString(int month) {
		LocalDate now = LocalDate.now();
		String month0 = now.minusMonths(month).getMonth().toString() + ":";
		month0 = month0.substring(0, 1) + month0.substring(1).toLowerCase();
		return month0;
	}

	/**
	 * Generate Excel for tab0:
	 *
	 * @return new Object of XLSXExport class
	 */
	@RequestMapping(value = "/excelGenerateTab0", method = RequestMethod.GET)
	public ModelAndView excelGenerateFullTab0(HttpServletRequest req) {

		return new ModelAndView(new XLSXExport(), "excelExportTab0", listToExcel0);
	}

	/************************** Tab 1 methods *************************/

	/**
	 * Ajax controller for tab1 - method upload general quantity of tickets by
	 * chosen range of months (max 12) and available companies from last 12 months.
	 *
	 * @param req - Http servlet request for get data from view
	 * 
	 * @return string with data as jsp table
	 */
	@RequestMapping(value = "uploadReporsCountTab1", method = RequestMethod.GET)
	@ResponseBody
	public String uploadReporsCountTab1(HttpServletRequest req) {

		String selectedCompanyL = req.getParameter("company1l");
		String selectedCompanyR = req.getParameter("company1r");

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);

		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();

		List<?> list = null;
		if (selectedCompanyL != null) {
			list = report.getCountPeriodCompany(monthCount, selectedCompanyL);

			if (list.size() == 0) {
				String result = "<table border=1px \"universalTable\"><tr><td>No:</td><td>Company:</td><td>Count:</td></tr>";
				result = result + "<tr><td>1</td><td>" + selectedCompanyL + "</td><td>0</td></tr>";
				result = result + "</table>";
				return result;
			}
		}

		if (selectedCompanyR != null) {
			list = report.getCountPeriodCompany(monthCount, selectedCompanyR);

			if (list.size() == 0) {
				String result = "<table border=1px \"universalTable\"><tr><td>No:</td><td>Company:</td><td>Count:</td></tr>";
				result = result + "<tr><td>1</td><td>" + selectedCompanyR + "</td><td>0</td></tr>";
				result = result + "</table>";
				return result;
			}
		}

		String result = "<table border=1px \"universalTable\"><tr><td>No:</td><td>Company:</td><td>Count:</td></tr>";
		for (int i = 0; i < list.size(); i++) {
			Object[] row = (Object[]) list.get(i);
			String temp = "<tr><td>" + row[0] + "</td><td>" + row[1] + "</td><td>" + row[2] + "</td></tr>";
			result = result + temp;
		}
		result = result + "</table>";
		return result;
	}

	/**
	 * Ajax controller for tab1 - method upload general quantity of tickets group by
	 * periods, company and by chosen range of months (1,3,6,12).
	 *
	 * @param req - Http servlet request for get data from view
	 * 
	 * @return string with data as jsp table
	 */
	@RequestMapping(value = "uploadReporsFullTab1", method = RequestMethod.GET)
	@ResponseBody
	public String uploadReporsFullTab1(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		String selectedCompanyL = req.getParameter("company1l");
		String selectedCompanyR = req.getParameter("company1r");
		monthCount = Integer.parseInt(tempPeriod);
		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();

		List<StandardReportContainer> list = new ArrayList<StandardReportContainer>();
		if (selectedCompanyL != null) {
			list = report.getCompanyCountByPeriodsLists(monthCount, selectedCompanyL);
		}

		if (selectedCompanyR != null) {
			list = report.getCompanyCountByPeriodsLists(monthCount, selectedCompanyR);
		}

		String result = "<table border=1px \"universalTable\"><tr><th>No:</th><th>Period:</th><th>Company:</th><th>Count:</th></tr>";

		for (int i = 0; i < list.size(); i++) {

			String temp = "<tr><td>" + list.get(i).getId() + "</td><td>" + list.get(i).getCompanyName() + "</td><td>"
					+ list.get(i).getMonth_0() + "</td><td>" + list.get(i).getCount() + "</td></tr>";
			result = result + temp;
		}

		result = result + "</table>";
		return result;
	}

	/**
	 * Method prepared list to export with data of compared companis.
	 *
	 * @param req - standard Http Servlet method to request for data from view
	 */
	@RequestMapping(value = "excelPrepareFullTab1", method = RequestMethod.GET)
	@ResponseBody
	public void prepareExcelData(HttpServletRequest req) {
		String selectedCompanyL = req.getParameter("company1l");
		String selectedCompanyR = req.getParameter("company1r");
		Boolean compareFlag1 = Boolean.valueOf(req.getParameter("compareFlag1"));

		// listToExcelFull1
		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		List<StandardReportContainer> list1 = new ArrayList<StandardReportContainer>();
		List<StandardReportContainer> list2 = new ArrayList<StandardReportContainer>();

		list1 = report.getCompanyCountByPeriodsLists(monthCount, selectedCompanyL);

		if (listToExcelFull1.isEmpty() == false) {
			listToExcelFull1.clear();
		}
		listToExcelFull1.addAll(list1);

		if (compareFlag1 == true) {

			if (!selectedCompanyL.equals(selectedCompanyR)) {
				list2 = report.getCompanyCountByPeriodsLists(monthCount, selectedCompanyR);
				listToExcelFull1.addAll(list2);
			}
		}
	}

	/**
	 * Generate Excel for compared companies:
	 *
	 * @return new Object of XLSXExport class
	 */
	@RequestMapping(value = "/excelGenerateFullTab1", method = RequestMethod.GET)
	public ModelAndView excelGenerateFullTab1l(HttpServletRequest req) {
		return new ModelAndView(new XLSXExport(), "excelExportTab1", listToExcelFull1);
	}

	/************************** Tab 2 methods *************************/

	/**
	 * Ajax controller for tab2 - method prepared small window in jsp with: -
	 * fieldset - legent - select with agent's list - error label for select
	 *
	 * For this select needed one company ("company2l") to compare two users from
	 * the same location.
	 *
	 * @param req - Http servlet request for get data from view
	 * 
	 * @return string with data as jsp table
	 */
	@RequestMapping(value = "agentsSelect2", method = RequestMethod.GET)
	@ResponseBody
	public String uploadReporsCountTab2(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);

		String selectedCompanyL = req.getParameter("company2l");
		String action = req.getParameter("action");

		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		List<String> list = new ArrayList<String>();
		String tabLR = "";
		String colNum = "";
		if (action.equals("left")) {
			list = report.getAgents(monthCount, selectedCompanyL);
			tabLR = "l";
			colNum = " no 1";
		}
		if (action.equals("right")) {
			/* only one column with company is in view - compare two agents */
			list = report.getAgents(monthCount, selectedCompanyL);
			tabLR = "r";
			colNum = " no 2";
		}

		String result = "<fieldset><legend>Select Agent" + colNum + "!</legend><select onchange=\"countSelect2" + tabLR
				+ "()\" class=\"universalSelect\" id=\"selectUser2" + tabLR + "\" name=\"selectUser2" + tabLR
				+ "\" size=5 >";
		for (int i = 0; i < list.size(); i++) {
			String temp = "<option value=\"" + list.get(i) + "\">" + list.get(i) + "</option>";
			result = result + temp;
		}
		result = result + "</select><div id=\"selectUserCommunicate2" + tabLR
				+ "\" class=\"communicateLabel\"></div></fieldset>";

		return result;
	}

	/**
	 * Controller return quantity of all tickets selected by given values: - company
	 * - range of past months - agent
	 *
	 * @param req Standard Http Servlet Request Method
	 * 
	 * @return String result as jsp table
	 */
	@RequestMapping(value = "agentsTicketCount2", method = RequestMethod.GET)
	@ResponseBody
	public String agentsTicketCount2(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);
		String selectedCompany = req.getParameter("company2l");
		String agentName = req.getParameter("selectUser");

		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		String count = "";

		if (agentName.contentEquals("No agent")) {
			count = "0";
		} else if (agentName.contentEquals("No agent assigned")) {
			agentName = "";
			count = report.getAgentsTicketCount(monthCount, selectedCompany, agentName);
		} else {
			count = report.getAgentsTicketCount(monthCount, selectedCompany, agentName);
		}

		String result = "<table border=1px  class=\"universalTable\"><tr><td>Company:</td><td>Agent:</td><td>Quantity:</td></tr>"
				+ "<tr><td>" + selectedCompany + "</td><td>" + agentName + "</td><td>" + count + "</td></tr>"
				+ "</table>";
		return result;
	}

	/**
	 * Method show count of tickets group by period for selected agent and company.
	 *
	 * @param req Standard Http Servlet Request Method
	 * 
	 * @return String result as jsp table
	 */
	@RequestMapping(value = "agentsTicketPeriodCount2", method = RequestMethod.GET)
	@ResponseBody
	public String agentsTicketPeriodCount2(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);
		String selectedCompany = req.getParameter("company2l");
		String agentName = req.getParameter("selectUser");

		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		List<StandardReportContainer> list = new ArrayList<StandardReportContainer>();

		if (agentName.contentEquals("No agent")) {
			return "no agent selected";

		} else if (agentName.contentEquals("No agent assigned")) {
			agentName = "";
			list = report.getAgentsTicketPeriodCount(monthCount, selectedCompany, agentName);
		} else {
			list = report.getAgentsTicketPeriodCount(monthCount, selectedCompany, agentName);
		}

		String result = "<table border=1px  class=\"universalTable\"><tr><td>id:</td><td>Period:</td><td>Quantity:</td></tr>";

		for (int i = 0; i < list.size(); i++) {
			result = result + "<tr><td>" + (i + 1) + "</td><td>" + list.get(i).getMonth_0() + "</td><td>"
					+ list.get(i).getCount() + "</td></tr>";
		}
		result = result + "</table>";

		return result;
	}

	/**
	 * Method prepared list to export with data of compared agents.
	 *
	 * @param req - standard Http Servlet method to request for data from view
	 */
	@RequestMapping(value = "excelPrepareFullTab2", method = RequestMethod.GET)
	@ResponseBody
	public void excelPrepareFullTab2(HttpServletRequest req) {
		String tempPeriod = req.getParameter("monthCount");
		int monthCount = Integer.parseInt(tempPeriod);
		String selectedCompany = req.getParameter("company2l");
		String selectUser2l = req.getParameter("selectUser2l");
		String selectUser2r = req.getParameter("selectUser2r");
		Boolean compareFlag2 = Boolean.valueOf(req.getParameter("compareFlag2"));

		System.out.println(selectedCompany + " " + selectUser2l + " " + selectUser2r);
		// listToExcelFull2
		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		List<StandardReportContainer> list1 = new ArrayList<StandardReportContainer>();
		List<StandardReportContainer> list2 = new ArrayList<StandardReportContainer>();

		if (listToExcelFull2.isEmpty() == false) {
			listToExcelFull2.clear();
		}

		if (selectUser2l.length() > 1) {
			list1 = report.getAgentsTicketPeriodCount(monthCount, selectedCompany, selectUser2l);
			listToExcelFull2.addAll(list1);
		}

		if (compareFlag2 == true) {
			if (!(selectUser2l.equals(selectUser2r))) {
				if (selectUser2r.length() > 1) {
					list2 = report.getAgentsTicketPeriodCount(monthCount, selectedCompany, selectUser2r);
					listToExcelFull2.addAll(list2);
				}
			}
		}
	}

	/**
	 * Generate Excel for tab2 - users quantity ticket comparator:
	 *
	 * @return new Object of XLSXExport class
	 */
	@RequestMapping(value = "/excelGenerateFullTab2", method = RequestMethod.GET)
	public ModelAndView excelGenerateTab2(HttpServletRequest req) {
		return new ModelAndView(new XLSXExport(), "excelExportTab2", listToExcelFull2);
	}

	/************************** Tab 3 methods *************************/

	@RequestMapping(value = "showAgents3", method = RequestMethod.GET)
	@ResponseBody
	public String uploadReporsCountTab3(HttpServletRequest req, HttpServletResponse res) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);

		String selectedCompanyL = req.getParameter("company3l");
		String selectedCompanyR = req.getParameter("company3l");
		String action = req.getParameter("action");

		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		List<String> list = new ArrayList<String>();
		String tabLR = "";
		String colNum = "";
		if (action.equals("left")) {
			/* only one column with company is in view - compare two agents */
			list = report.getAgents(monthCount, selectedCompanyL);
			tabLR = "l";
			colNum = " no 1";
		}
		if (action.equals("right")) {
			/* only one column with company is in view - compare two agents */
			list = report.getAgents(monthCount, selectedCompanyR);
			tabLR = "r";
			colNum = " no 2";
		}

		String result = "<fieldset><legend>Select Agent" + colNum + ":</legend><select onchange=\"countSelect3" + tabLR
				+ "()\" class=\"universalSelect\" id=\"selectUser3" + tabLR + "\" name=\"selectUser3" + tabLR
				+ "\" size=5 >";
		for (int i = 0; i < list.size(); i++) {
			String temp = "<option value=\"" + list.get(i) + "\">" + list.get(i) + "</option>";
			result = result + temp;
		}
		result = result + "</select><div id=\"selectUserCommunicate3" + tabLR
				+ "\" class=\"communicateLabel\"></div></fieldset>";

		return result;
	}

	/**
	 * Show categories for selected range of months, companies and users.
	 * 
	 * @param req Http Servlet Request object
	 * 
	 * @return String result as jsp 'select-categories' list
	 */
	@RequestMapping(value = "showCategories3", method = RequestMethod.GET)
	@ResponseBody
	public String showCategories3(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);
		String selectedCompanyL = req.getParameter("company3l");
		String selectedCompanyR = req.getParameter("company3l");
		String action = req.getParameter("action");
		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();
		List<String> list = new ArrayList<String>();

		String tabLR = "";
		String colNum = "";
		if (action.equals("left")) {
			tabLR = "l";
			colNum = " no 1";
			list = report.getCategory(monthCount, selectedCompanyL);
		}
		if (action.equals("right")) {
			tabLR = "r";
			colNum = " no 2";
			list = report.getCategory(monthCount, selectedCompanyR);
		}

		if (list.size() == 0) {
			list.add("No category found at that period and company");
		}

		String result = "<fieldset><legend>Select Category" + colNum + ":</legend><select  onchange=\"countSelect3"
				+ tabLR + "()\" class=\"universalSelect\" id=\"selectCategory3" + tabLR + "\" name=\"selectCategory3"
				+ tabLR + "\" size=5 >";

		for (int i = 0; i < list.size(); i++) {
			String temp = "<option value=\"" + list.get(i) + "\">" + list.get(i) + "</option>";
			result = result + temp;
		}

		result = result + "</select><div id=\"selectCategoryCommunicate3" + tabLR
				+ "\" class=\"communicateLabel\"></div></fieldset>";
		return result;
	}

	/**
	 * Controller return quantity of all tickets selected by given values: -
	 * company, range of past months, agent, category
	 *
	 * @param req Standard Http Servlet Request Method
	 * 
	 * @return String result as jsp table
	 */
	@RequestMapping(value = "resultSumAgentCategory3", method = RequestMethod.GET)
	@ResponseBody
	public String resultSumAgentCategory3(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);
		String selectedCompany = req.getParameter("company3");
		String agentName = req.getParameter("selectUser3");
		String selectCategory3 = req.getParameter("selectCategory3");

		StandardReportContainerDAO report = new StandardReportContainerDAOImpl();
		String count = "";

		if (agentName.contentEquals("No agent")) {
			count = "0";
		} else if (agentName.contentEquals("No agent assigned")) {
			agentName = "";
			count = report.getAgentsTicketCategoryCount(monthCount, selectedCompany, agentName, selectCategory3);
		} else {
			count = report.getAgentsTicketCategoryCount(monthCount, selectedCompany, agentName, selectCategory3);
		}

		String result = "<table border=1px  class=\"universalTable\"><tr><td>Company:</td><td>Agent:</td><td>Category:</td><td>Quantity:</td></tr>"
				+ "<tr><td>" + selectedCompany + "</td><td>" + agentName + "</td><td>" + selectCategory3 + "</td><td>"
				+ count + "</td></tr>" + "</table>";
		return result;
	}

	/**
	 * Controller uploaded report with quantity depend from selected: - company,
	 * user, category, range month
	 * 
	 * @param req Http Servlet Request object
	 * 
	 * @return String result as jsp 'select-categories' list
	 */
	@RequestMapping(value = "uploadReportCategoryAgentCount3", method = RequestMethod.GET)
	@ResponseBody
	public String uploadReportCategoryAgentCount3(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		monthCount = Integer.parseInt(tempPeriod);
		String action = req.getParameter("action");
		String company3 = req.getParameter("company3");
		String selectUser3 = req.getParameter("selectUser3");
		String selectCategory3 = req.getParameter("selectCategory3");

		StandardReportContainerDAO report = new StandardReportContainerDAOImpl();
		List<StandardReportContainer> list = new ArrayList<StandardReportContainer>();
		list = report.getReportAgetnCategory3(monthCount, company3, selectUser3, selectCategory3);

		String tabLR = "";
		String colNum = "";
		if (action.equals("left")) {
			tabLR = "l";
			colNum = " no 1";

		}
		if (action.equals("right")) {
			tabLR = "r";
			colNum = " no 2";
		}

		String result = "<table border=1px  class=\"universalTable\"><tr><td>id:</td><td>Period:</td><td>"
				+ "Quantity:</td></tr>";

		for (int i = 0; i < list.size(); i++) {
			result = result + "<tr><td>" + list.get(i).getId() + "</td><td>" + list.get(i).getMonth_0() +
					"</td><td>" + list.get(i).getCount() + "</td></tr>";
		}

		result = result + "</table>";

		return result;
	}

	/**
	 * Method prepared list to export with data of compared agents.
	 *
	 * @param req - standard Http Servlet method to request for data from view
	 */
	@RequestMapping(value = "excelPrepareFullTab3", method = RequestMethod.GET)
	@ResponseBody
	public void excelPrepareFullTab3(HttpServletRequest req) {
		String tempPeriod = req.getParameter("monthCount");
		int monthCount = Integer.parseInt(tempPeriod);
		String selectedCompany3l = req.getParameter("company3l");
		String selectedCompany3r = req.getParameter("company3l");
		String selectUser3l = req.getParameter("selectUser3l");
		String selectUser3r = req.getParameter("selectUser3r");
		String selectCategory3l = req.getParameter("selectCategory3l");
		String selectCategory3r = req.getParameter("selectCategory3r");
		Boolean compareFlag3 = Boolean.valueOf(req.getParameter("compareFlag3"));

		StandardReportContainerDAO report = new StandardReportContainerDAOImpl();
		List<StandardReportContainer> list1 = new ArrayList<StandardReportContainer>();
		List<StandardReportContainer> list2 = new ArrayList<StandardReportContainer>();

		if (listToExcelFull3.isEmpty() == false) {
			listToExcelFull3.clear();
		}

		if (selectUser3l.length() > 1) {
			list1 = report.getReportAgetnCategory3(monthCount, selectedCompany3l, selectUser3l, selectCategory3l);
			listToExcelFull3.addAll(list1);
		}

		if (compareFlag3 == true) {
			if (!(selectUser3l.equals(selectUser3r)) || !(selectCategory3l.equals(selectCategory3r))) {

				System.out.println("TRUE: " + selectUser3r + " " + selectCategory3r);
				list2 = report.getReportAgetnCategory3(monthCount, selectedCompany3r, selectUser3r, selectCategory3r);
				listToExcelFull3.addAll(list2);
			}
		}

		for (int i = 0; i < listToExcelFull3.size(); i++) {
			listToExcelFull3.get(i).setId(String.valueOf(i + 1));
			System.out.println(listToExcelFull3.get(i).toString());
		}
	}

	/**
	 * Generate Excel for tab2 - users quantity ticket comparator:
	 *
	 * @return new Object of XLSXExport class
	 */
	@RequestMapping(value = "/excelGenerateFullTab3", method = RequestMethod.GET)
	public ModelAndView excelGenerateTab3(HttpServletRequest req) {
		return new ModelAndView(new XLSXExport(), "excelExportTab3", listToExcelFull3);
	}

	/****************** other controller methods ******************/

	/**
	 * to check - not implemented replaced and to delete
	 * 
	 * Main view of counts: full, group by months:
	 *
	 * @param req
	 * 
	 * @return
	 */
	@RequestMapping(value = "showCount", method = RequestMethod.GET)
	@ResponseBody
	public String showCount(HttpServletRequest req) {

		String tempPeriod = req.getParameter("monthCount");
		System.out.println("Company: " + tempPeriod);
		monthCount = Integer.parseInt(tempPeriod);
		Reports_CountDAO report = new Reports_CountDAOImpl();

		List<?> list = report.showCount(monthCount); // method to check/delete (not used-replaced?)

		// start table and add header:
		String result = "<table class=\"universalTable\"><tr>\r\n" + "<th>No:</th>\r\n" + "<th>Company:</th>\r\n"
				+ "<th>Count:</th>\r\n" + "</tr>";

		// format data to view:
		for (int i = 0; i < list.size(); i++) {
			Object[] row = (Object[]) list.get(i);
			String temp = "<tr><td>" + row[0] + "</td><td>" + row[1] + "</td><td>" + row[2] + "</td><td>"
					+ "</td></tr>";
			result = result + temp;
		}

		result = result + "</table>";
		return result;
	}

	/**
	 * Method prepare list with companies of uploaded reports from last 18 months
	 *
	 * @return String list
	 */
	@ModelAttribute("uploadedCompanies")
	private List<String> getCompanies() {
		Reports_PreviewDAO report = new Reports_PreviewDAOImpl();

		List<StandardReportContainer> listObjects = report.getCompanyFromReports("All");
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < listObjects.size(); i++) {
			list.add(listObjects.get(i).getCompanyName());
		}

		return list;
	}

	/**
	 * Model with list to select available period range: current month, last month,
	 * two months ago. (for standard report).
	 * 
	 *
	 * @param model - object of Spring
	 * 
	 * @return map with int-value and string-name of period
	 */
	@ModelAttribute
	public Model selectPeriodForStamdardReport(Model model) {

		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(18, "Current Month");
		map.put(19, "One Month ago");
		map.put(20, "Two Months ago");

		model.addAttribute("periodStandard", map);
		return model;
	}

	/**
	 * Model with list to select available period range last month, last three
	 * months, last six months, last year.
	 *
	 * @param model - object of Spring
	 * 
	 * @return map with int-value and string-name of period
	 */
	@ModelAttribute
	public Model selectPeriod(Model model) {

		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Last Month");
		map.put(3, "Last three Months");
		map.put(6, "Last six Months");
		map.put(12, "Last Year");

		model.addAttribute("period", map);
		return model;
	}

	/* Models: */

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