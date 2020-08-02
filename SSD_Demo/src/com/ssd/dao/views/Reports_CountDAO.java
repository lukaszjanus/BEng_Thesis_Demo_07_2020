package com.ssd.dao.views;

import java.util.List;

import com.ssd.entity.viewsDB.Reports_Count;
import com.ssd.entity.viewsDB.Reports_Preview;

/**
 * Interface of class used to get data view from db with counts of reports. 
 * 
 * Full class: @see documentation of {@link Reports_CountDAOImpl}
 * ORM class with field descriptions: @see documentation of {@link Reports_Count}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 1 mar 2020 
 *
 */
public interface Reports_CountDAO {
	
	
	/**
	 * Method get data from temporary View of uploaded data with column 'count'.
	 * 
	 * @param monthCount - list of months to display
	 *
	 * @return Reports_Count-object list
	 */
	public List<Reports_Count> getFullTable(int monthCount);

	/**
	 * Method prepare list for XLSX file with informations about general quantity of tickets from given range of months.
	 * Show full counts and companyNames group by and order by companyName
	 * 
	 * @param monthCount int
	 * 
	 * @return list-Object
	 */
	public List<?> showCount(int monthCount);

}
