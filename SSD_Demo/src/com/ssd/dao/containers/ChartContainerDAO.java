package com.ssd.dao.containers;

import java.util.List;

import com.ssd.entity.containers.ChartContainer;


/**
 * Class prepare data to generate charts used ChartContainer.
 * 
 * Full code - @see documentation of {@ChartContainerDAOImpl}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 10 kwi 2020 
 *
 */
public interface ChartContainerDAO {
	
	/**
	 * Method prepare data for chart by selected company (max two) and range of months.
	 * 'headers' can be one company or list of companies with delimiter ';'  
	 *
	 * @param monthCount as String
	 * @param headers for data as String - used only to search data
	 * 
	 * @return object list of ChartContainer; can be null if any report has not found.
	 */
	public List<ChartContainer> prepareChartCompany(String monthCount, String headers);

	
	/**
	 * Method prepare data for chart by selected company (max two) and range of months.  
	 * 'users' can be one company or list of companies with delimiter ';'
	 *
	 * @param tempPeriod - monthCount as String
	 * @param company as String
	 * @param agents for data as String - used only to search data
	 * 
	 * @return object list of ChartContainer; can be null if any report has not found.
	 */
	public List<ChartContainer> prepareChartUser(String tempPeriod, String company, String agents);


	/**
	 * Method prepare data for chart by selected company (max two) and range of months.  
	 * 'users' can be one company or list of companies with delimiter ';'
	 *
	 * @param tempPeriod - monthCount as String
	 * @param company as String
	 * @param agents for data as String 
	 * 
	 * @param selectCategory3l - selected category for first user;
	 * @param selectCategory3r - selected category for second user, can be null
	 * 
	 * @return object list of ChartContainer; can be null if any report has not found.
	 */
	public List<ChartContainer> prepareChartUserCategory(String tempPeriod, String company, String agents,
			String selectCategory3l, String selectCategory3r);
}
