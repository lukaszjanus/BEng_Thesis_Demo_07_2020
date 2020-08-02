package com.ssd.dao.views;

import java.util.List;

import com.ssd.entity.containers.StandardReportContainer;
import com.ssd.entity.viewsDB.Reports_Preview;

/**
 * Interface of class used to get data view from db with uploaded reports.
 * 
 * Full class: @see documentation of {@link Reports_PreviewDAOImpl} ORM class
 * with field descriptions: @see documentation of {@link Reports_Preview}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 28 lut 2020
 * 
 */
public interface Reports_PreviewDAO {

	/**
	 * Method load data from db with - all uploaded reports and return as object
	 * list.
	 *
	 * @return list of Reports_Preview-objects, list can be null
	 */
	public List<Reports_Preview> getDataReports();

	/**
	 * Method get available agents from column agent in uploaded reports. List can
	 * be empty if any report hasn't been uploaded yet for given month-range
	 * or any company selected.
	 * If ticket hasn't assigned any agent, to list is added value 'no assigned'.
	 *
	 * @param month - range of months as int
	 * @param selectedCompany - to select agents only from chosen company
	 * 
	 * @return String list
	 */
	public List<String> getAgents(int month, String selectedCompany);

	/**
	 * Method get available Companies from column company in uploaded reports. List
	 * can be empty if any report hasn't been uploaded yet.
	 * 
	 * @param monthCount  - range of months as int
	 * 
	 * @return String list
	 */
	public List<String> getCompanies(int month);

	/**
	 * Method get available Periods from column period in uploaded reports. List can
	 * be empty if any report hasn't been uploaded yet.
	 * 
	 * @return String list
	 * 
	 */
	public List<String> getUploadedPeriods();

	/**
	 * Method get date of uploaded report from column uploaded_date in uploaded
	 * reports. List can be empty if any report hasn't been uploaded yet.
	 * 
	 * @return String list
	 * 
	 */
	public List<String> getUploadedDate();

	/**
	 * Method get Category/Description report from column category in uploaded
	 * reports. List can be empty if any report hasn't been uploaded yet.
	 * 
	 * @param monthCount  - range of months as int
	 * @param selectedCompany - categories available only for selected company
	 * 
	 * @return String list
	 * 
	 */
	public List<String> getCategory(int monthCount, String selectedCompany);

	/**
	 * Method return count of uploaded tickets from given counts of months
	 *
	 * @param monthCount  - range of months as int
	 * @param companies - list of company
	 * 
	 * @return count as string
	 */
	public String getCount(int monthCount, String companies);

	
	/**
	 * Get Count of tickets - columns group by CompanyName.
	 * 
	 * Displayed Columns: No, CompanyName, Count
	 *
	 * @param monthCount int 
	 * @param selectedCompany - companies Names as one String with delimiter ';'
	 * 
	 * @return list Object<?> (row can be parsed to String)
	 */
	public List<?> getCountPeriodCompany(int monthCount, String selectedCompany);

	
	/**
	 * Get Count of tickets - columns group by periods and companyName
	 *
	 *Displayed Columns: No, period, CompanyName, Count.
	 *
	 * @param monthCount as int
	 * @param selectedCompany - companies Names as one String with delimiter ';'
	 *  
	 * @return list Object<StandardReportContainer> 
	 */
	public List<StandardReportContainer> getCompanyCountByPeriodsLists(int monthCount, String selectedCompany);
	
	
	/**
	 * Get StandardReportContainer list with companies: 
	 * active and unactive, but with uploaded reports in db
	 * 
	 * Container StandardReportContainer has initialized in this method only id and companyName.
	 * 
	 * Period - last 18 months:
	 * - last three months
	 * - quarter before three months
	 * - year before three months and quarter
	 *
	 * @param selectedCompany - companies Names as one String with delimiter ';'
	 * 
	 * @return list StandardReportContainer, list can be empty
	 */
	public List<StandardReportContainer> getCompanyFromReports(String selectedCompany);

	
	/**
	 * Get counts for standard periods (last 18 months):
	 * - last three months (start from current month)
	 * - quarter before three months
	 * - year before three months and quarter
	 *
	 *
	 * @param object list from method getCompanyFromReports 
	 * @param startMonth as int, current month is '0'
	 * 
	 * @return full list StandardReportContainer, list can be empty
	 */
	public List<StandardReportContainer> getStandardCounts(List<StandardReportContainer> list, int startMonth);

	/**
	 * Method check quantity of agent in selected company and selected range month.
	 *
	 * @param monthCount as int
	 * @param selectedCompany as String
	 * @param agentName as String
	 * 
	 * @return count of tickets, can be null.
	 */
	public String getAgentsTicketCount(int monthCount, String selectedCompany, String agentName);

	
	/**
	 * Method check quantity of agent in selected company and selected range month
	 * and create object-list StandardReportContainer for every month.
	 *
	 * @param monthCount as int
	 * @param selectedCompany as String
	 * @param agentName as String
	 *
	 * @return object-list of StandardReportContainer, list can be empty
	 */
	public List<StandardReportContainer> getAgentsTicketPeriodCount(int monthCount, String selectedCompany, String agentName);

}
