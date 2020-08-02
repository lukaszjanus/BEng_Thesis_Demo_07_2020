package com.ssd.dao.containers;

import java.util.List;

import com.ssd.entity.containers.StandardReportContainer;

/**
*
* Class used to create object list from StandardReportContainer-class.
*
* Full code - @see documentation of {@link StandardReportContainerDAOImpl}
*
* @author Lukasz Janus
* @version 1.0
* @date 4 kwi 2020
*/
public interface StandardReportContainerDAO {

	/**
	 * Method generate table of data-report with filters: - range of months,
	 * company, agent, category.
	 * 
	 * Method return list of object with columns: -id, period, category, count
	 *
	 * @param monthCount      as integer
	 * @param company         string company name
	 * @param selectUser      string agent name
	 * @param selectCategory  string category-name
	 * 
	 * @return list of object StandardReportContainer
	 */
	public List<StandardReportContainer> getReportAgetnCategory3(int monthCount, String company, String selectUser,
			String selectCategory);

	/**
	 * Method return quantity of tickets with filters: - range of months,
	 * company, agent, category Category can be array with delimiter ',' as
	 * multiple-select.
	 * If no report has found by this criteria, method return '0'. 
	 *
	 * @param monthCount      as integer
	 * @param company         string company name
	 * @param selectUser      string agent name
	 * @param selectCategory  string category-name
	 * 
	 * @return string quantity of tickets by selected criteria.
	 */
	public String getAgentsTicketCategoryCount(int monthCount, String selectedCompany, String selectUser,
			String selectCategory);
}